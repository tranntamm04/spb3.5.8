package com.example.service.impl;

import com.example.entity.Chatbot;
import com.example.entity.Product;
import com.example.entity.ProductType;
import com.example.repository.ChatbotRepository;
import com.example.repository.ProductRepository;
import com.example.repository.ProductTypeRepository;
import com.example.service.ChatbotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.PostConstruct;
import java.text.Normalizer;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatbotServiceImpl implements ChatbotService {

    private final ProductRepository productRepository;
    private final ChatbotRepository chatbotRepository;
    private final ProductTypeRepository productTypeRepository;

    private final Map<String, ConversationMemory> memoryStore = new HashMap<>();

    private List<ProductType> brandCache = new ArrayList<>();

    private final Map<String, List<Product>> pendingProductSelection = new HashMap<>();

    @PostConstruct
    public void loadBrands() {
        brandCache = productTypeRepository.findAll();
    }

    private boolean matchProduct(String question, String productName) {

        String q = normalize(question);
        String name = normalize(productName);

        Integer qModel = extractModelNumber(q);
        Integer pModel = extractModelNumber(name);

        if (qModel != null && pModel != null && !qModel.equals(pModel)) {
            return false;
        }

        if (q.contains(name)) return true;

        String[] tokens = name.split(" ");

        int match = 0;

        for (String token : tokens) {

            if (token.length() < 3) continue;

            if (q.contains(token)) match++;
        }

        if (qModel != null) {
            return match >= 1;
        }

        return match >= 2;
    }

    @Override
    public String chat(String sessionId, String question) {

        String q = normalize(question);

        ConversationMemory memory = memoryStore.getOrDefault(sessionId, new ConversationMemory());

        if (pendingProductSelection.containsKey(sessionId)) {

            List<Product> pending = pendingProductSelection.get(sessionId);

            for (Product p : pending) {

                if (matchProduct(q, p.getProductName()) ||
                        normalize(p.getProductName()).contains(q)) {

                    pendingProductSelection.remove(sessionId);

                    memory.setLastProduct(p);

                    String answer = buildProductDetail(p);

                    memoryStore.put(sessionId, memory);
                    saveHistory(sessionId, question, answer);

                    return answer;
                }
            }

            if (q.contains("cu")) {

                for (Product p : pending) {

                    if (normalize(p.getProductName()).contains("cu")) {

                        pendingProductSelection.remove(sessionId);

                        memory.setLastProduct(p);

                        String answer = buildProductDetail(p);

                        memoryStore.put(sessionId, memory);
                        saveHistory(sessionId, question, answer);

                        return answer;
                    }
                }
            }

            String answer = "Bạn có thể nói rõ phiên bản hơn không?";
            saveHistory(sessionId, question, answer);
            return answer;
        }

        QueryEntities entities = extractEntities(q);

        mergeMemory(entities, memory);

        List<Product> allProducts = productRepository.findAll()
                .stream()
                .filter(p -> p.getStatus() == 1)
                .toList();

        if (containsAny(q,"pin cao nhat","pin lon nhat","pin khung","pin trau nhat","pin khoe nhat")) {

            Product p = findMaxBattery(allProducts);

            String answer = buildProductDetail(p);

            memoryStore.put(sessionId, memory);
            saveHistory(sessionId, question, answer);

            return answer;
        }

        if (containsAny(q,"ram cao nhat","ram lon nhat","ram khung","ram tot nhat")) {

            Product p = findMaxRam(allProducts);

            String answer = buildProductDetail(p);

            memoryStore.put(sessionId, memory);
            saveHistory(sessionId, question, answer);

            return answer;
        }

        if (containsAny(q,"camera cao nhat","camera khung","cam xin nhat","chup anh dep nhat","chup anh tot nhat")) {

            Product p = findMaxCamera(allProducts);

            String answer = buildProductDetail(p);

            memoryStore.put(sessionId, memory);
            saveHistory(sessionId, question, answer);

            return answer;
        }

        if (containsAny(q,"hz cao nhat","tan so quet cao nhat","man hinh muot nhat","man hinh tot nhat","man hinh net nhat")) {

            Product p = findMaxHz(allProducts);

            String answer = buildProductDetail(p);

            memoryStore.put(sessionId, memory);
            saveHistory(sessionId, question, answer);

            return answer;
        }
        if (containsAny(q,
                "chi tiet",
                "thong so",
                "cau hinh",
                "mo ta",
                "ky thuat",
                "thong tin",
                "spec",
                "cau hinh chi tiet"
        )) {

            List<Product> matched = findProductByName(q);

            if (matched.size() > 1) {

                pendingProductSelection.put(sessionId, matched);

                StringBuilder sb = new StringBuilder();
                sb.append("Mình tìm thấy nhiều phiên bản của sản phẩm này.\n");
                sb.append("Bạn muốn xem phiên bản nào?\n\n");

                for (Product p : matched) {
                    sb.append("- ").append(p.getProductName()).append("\n");
                }

                String answer = sb.toString();

                memoryStore.put(sessionId, memory);
                saveHistory(sessionId, question, answer);

                return answer;
            }

            if (matched.size() == 1) {

                Product p = matched.get(0);

                memory.setLastProduct(p);

                String answer = buildProductDetail(p);

                memoryStore.put(sessionId, memory);
                saveHistory(sessionId, question, answer);

                return answer;
            }

            if (memory.getLastProduct() != null && extractModelNumber(q) == null) {

                String answer = buildProductDetail(memory.getLastProduct());

                memoryStore.put(sessionId, memory);
                saveHistory(sessionId, question, answer);

                return answer;
            }
        }

        if (containsAny(q,"san pham hot","hot","ban chay","ban chay nhat")) {

            List<Product> hotProducts =
                    productRepository.findTop5ByStatusOrderBySoldDesc(1);

            String answer = buildProductAnswer(
                    hotProducts,
                    "🔥 Những sản phẩm bán chạy nhất trong shop:"
            );

            memoryStore.put(sessionId, memory);
            saveHistory(sessionId, question, answer);

            return answer;
        }

        if (containsAny(q,"danh gia cao","5 sao","rating cao","tot nhat")) {

            List<Product> bestRated =
                    productRepository.findTop5ByStatusOrderByNumOfStarDesc(1);

            String answer = buildProductAnswer(
                    bestRated,
                    "⭐ Những sản phẩm được đánh giá cao nhất:"
            );

            memoryStore.put(sessionId, memory);
            saveHistory(sessionId, question, answer);

            return answer;
        }
        List<Product> products = recommend(entities);

        if (!products.isEmpty()) {
            memory.setLastProduct(products.get(0));
        }

        String answer = buildProductAnswer(products,
                "Sản phẩm được shop mình gợi ý phù hợp cho bạn nè:");

        memoryStore.put(sessionId, memory);

        saveHistory(sessionId, question, answer);

        return answer;
    }

    private Integer extractModelNumber(String text) {

        Matcher m = Pattern.compile("\\b(1[0-9]|20)\\b").matcher(text);

        if (m.find()) {
            return Integer.parseInt(m.group(1));
        }

        return null;
    }

    private List<Product> findProductByName(String keyword) {

        List<Product> all = productRepository.findAll();

        Integer model = extractModelNumber(keyword);

        return all.stream()

                .filter(p -> p.getStatus() == 1)
                .filter(p -> {

                    if (model == null) return true;
                    return normalize(p.getProductName())
                            .contains(String.valueOf(model));
                })
                .filter(p -> matchProduct(keyword, p.getProductName()))
                .toList();
    }

    private QueryEntities extractEntities(String q) {

        QueryEntities e = new QueryEntities();

        for (ProductType type : brandCache) {

            String brand = normalize(type.getNameType());

            if (q.contains(brand)) {
                e.setBrand(brand);
                break;
            }

            if (brand.equals("apple") && q.contains("iphone")) {
                e.setBrand("apple");
                break;
            }
        }

        if (containsPrice(q))
            e.setPrice(extractPrice(q));

        if (containsAny(q,"choi game","gaming","genshin","pubg"))
            e.setGaming(true);

        if (containsAny(q,"camera","chup anh","selfie"))
            e.setCamera(true);

        if (containsAny(q,"pin","trau","dung lau"))
            e.setBattery(true);

        return e;
    }

    private void mergeMemory(QueryEntities e, ConversationMemory m) {
        if (e.getPrice() != null) {
            m.setLastPrice(e.getPrice());
        }

        if (e.getBrand() != null && !e.getBrand().equals(m.getLastBrand())) {
            m.setLastPrice(null);
        }

        if (e.getBrand() != null) {
            m.setLastBrand(e.getBrand());
        }

        if (e.getPrice() == null) {
            e.setPrice(m.getLastPrice());
        }

        if (e.getBrand() == null) {
            e.setBrand(m.getLastBrand());
        }
    }

    private List<Product> recommend(QueryEntities q) {

        List<Product> products = productRepository.findAll();

        return products.stream()

                .filter(p -> p.getStatus() == 1)

                .filter(p -> {
                    if (q.getBrand() == null) return true;
                    if (p.getProductType() == null) return false;

                    String brand = normalize(p.getProductType().getNameType());
                    return brand.contains(q.getBrand());
                })

                .filter(p -> {
                    if (q.getPrice() == null) return true;
                    return p.getPrice() <= q.getPrice();
                })

                .sorted((a, b) ->
                        scoreProduct(b, q) - scoreProduct(a, q))

                .limit(5)

                .toList();
    }

    private int scoreProduct(Product p, QueryEntities q) {

        int score = 0;

        String desc = normalize(p.getDescription());

        Integer ram = extractRam(desc);
        Integer battery = extractBattery(desc);
        Integer hz = extractRefreshRate(desc);
        Integer camera = extractCameraMp(desc);

        if (q.isGaming()) {

            if (containsAny(desc,"snapdragon","dimensity","a17","a18","a19"))
                score += 20;

            if (ram != null && ram >= 8)
                score += 15;

            if (hz != null && hz >= 120)
                score += 10;

            if (battery != null && battery >= 4500)
                score += 10;
        }

        if (q.isCamera()) {

            if (camera != null) {

                if (camera >= 200) score += 30;
                else if (camera >= 108) score += 25;
                else if (camera >= 64) score += 20;
                else if (camera >= 48) score += 15;
                else if (camera >= 12) score += 10;
            }
        }

        if (q.isBattery() &&
                battery != null && battery >= 4500)
            score += 20;

        if (p.getNumOfStar() != null)
            score += p.getNumOfStar() * 5;

        score += p.getSold() / 50;

        return score;
    }

    private int getFinalPrice(Product product) {

        int price = product.getPrice();

        if (product.getPromotion() == null)
            return price;

        if ("PERCENT".equalsIgnoreCase(product.getPromotion().getTypePromotion())) {
            return (int) Math.round(price * (1 - product.getPromotion().getPromotionalValue() / 100.0) - 1000);
        }

        if ("MONEY".equalsIgnoreCase(product.getPromotion().getTypePromotion())) {
            return Math.max(price - (int) product.getPromotion().getPromotionalValue(), 0);
        }

        return price;
    }

    private String buildProductAnswer(List<Product> products, String title) {

        if (products.isEmpty())
            return "Không tìm thấy sản phẩm với mức giá mà bạn đưa ra ở trong shop ạ.";

        StringBuilder sb = new StringBuilder(title + "\n\n");

        for (Product p : products) {
            sb.append(p.getProductName())
                    .append(" - ")
                    .append(formatPrice(getFinalPrice(p)))
                    .append(" VNĐ")
                    .append(" | ID=")
                    .append(p.getIdProduct())
                    .append("\n");
        }

        return sb.toString();
    }

    private String buildProductDetail(Product p) {

        StringBuilder sb = new StringBuilder();

        sb.append(p.getProductName())
                .append(" - ")
                .append(formatPrice(getFinalPrice(p)))
                .append(" VNĐ")
                .append(" | ID=")
                .append(p.getIdProduct())
                .append("\n\n");

        sb.append("📋 Thông số chi tiết:\n");

        if (p.getDescription() != null)
            sb.append(p.getDescription());

        return sb.toString();
    }

    private boolean containsPrice(String text) {
        return Pattern.compile("(\\d+)(\\s)?(tr|trieu)")
                .matcher(text).find();
    }

    private int extractPrice(String text) {
        Matcher matcher =
                Pattern.compile("(\\d+)(\\s)?(tr|trieu)")
                        .matcher(text);

        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1)) * 1_000_000;
        }
        return 0;
    }

    private boolean containsAny(String text, String... keywords) {
        for (String k : keywords)
            if (text.contains(k)) return true;
        return false;
    }

    private String normalize(String input) {
        if (input == null) return "";

        String temp = Normalizer.normalize(input, Normalizer.Form.NFD);
        temp = temp.replaceAll("\\p{M}", "");
        return temp.toLowerCase();
    }

    private String formatPrice(int price) {
        return String.format("%,d", price);
    }

    private void saveHistory(String sessionId, String question, String answer) {
        Chatbot chat = new Chatbot();
        chat.setSessionId(sessionId);
        chat.setQuestion(question);
        chat.setAnswer(answer);
        chatbotRepository.save(chat);
    }

    private Integer extractRam(String text) {

        Matcher m = Pattern.compile("(\\d+)\\s?gb").matcher(text);

        if (m.find()) {
            return Integer.parseInt(m.group(1));
        }

        return null;
    }

    private Integer extractBattery(String text) {

        Matcher m = Pattern.compile("(\\d{3,5})\\s?mah").matcher(text);

        if (m.find()) {
            return Integer.parseInt(m.group(1));
        }

        return null;
    }

    private Integer extractRefreshRate(String text) {

        Matcher m = Pattern.compile("(\\d{2,3})\\s?hz").matcher(text);

        if (m.find()) {
            return Integer.parseInt(m.group(1));
        }

        return null;
    }

    private Integer extractCameraMp(String text) {

        Matcher m = Pattern.compile("(\\d{2,3})\\s?mp").matcher(text);

        int max = 0;

        while (m.find()) {

            int value = Integer.parseInt(m.group(1));

            if (value > max) {
                max = value;
            }
        }

        return max == 0 ? null : max;
    }

    private Product findMaxBattery(List<Product> products){

        Product best = null;
        int max = 0;

        for(Product p : products){

            Integer battery = extractBattery(normalize(p.getDescription()));

            if(battery != null && battery > max){
                max = battery;
                best = p;
            }
        }

        return best;
    }

    private Product findMaxRam(List<Product> products){

        Product best = null;
        int max = 0;

        for(Product p : products){

            Integer ram = extractRam(normalize(p.getDescription()));

            if(ram != null && ram > max){
                max = ram;
                best = p;
            }
        }

        return best;
    }

    private Product findMaxCamera(List<Product> products){

        Product best = null;
        int max = 0;

        for(Product p : products){

            Integer cam = extractCameraMp(normalize(p.getDescription()));

            if(cam != null && cam > max){
                max = cam;
                best = p;
            }
        }

        return best;
    }

    private Product findMaxHz(List<Product> products){

        Product best = null;
        int max = 0;

        for(Product p : products){

            Integer hz = extractRefreshRate(normalize(p.getDescription()));

            if(hz != null && hz > max){
                max = hz;
                best = p;
            }
        }

        return best;
    }
}