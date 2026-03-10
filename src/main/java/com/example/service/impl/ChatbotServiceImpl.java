package com.example.service.impl;

import com.example.entity.Chatbot;
import com.example.entity.Product;
import com.example.repository.ChatbotRepository;
import com.example.repository.ProductRepository;
import com.example.service.ChatbotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.Normalizer;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatbotServiceImpl implements ChatbotService {

    private final ProductRepository productRepository;
    private final ChatbotRepository chatbotRepository;

    @Override
    public String chat(String sessionId, String question) {

        String q = normalize(question);
        String answer;

        if (containsAny(q, "xin chao", "hello", "hi", "chao", "alo")) {

            answer = """
                    👋 Xin chào bạn!
                    Mình là chatbot tư vấn của TTStore.
                    
                    Mình có thể giúp bạn:
                    - Tư vấn điện thoại theo giá
                    - Gợi ý điện thoại chơi game
                    - Điện thoại chụp ảnh đẹp
                    - Sản phẩm bán chạy
                    """;
        }
        else if (containsAny(q, "iphone", "apple")) {
            List<Product> products = productRepository.findByBrandForChatbot("Apple");
            answer = buildProductAnswer(
                    products,
                    "Điện thoại iPhone nổi bật:\nClick sản phẩm để xem chi tiết"
            );
        }

        else if (containsAny(q, "samsung")) {
            List<Product> products = productRepository.findByBrandForChatbot("Samsung");
            answer = buildProductAnswer(
                    products,
                    "Điện thoại Samsung nổi bật:\nClick sản phẩm để xem chi tiết"
            );
        }

        else if (containsAny(q, "xiaomi", "redmi")) {
            List<Product> products = productRepository.findByBrandForChatbot("Xiaomi");
            answer = buildProductAnswer(
                    products,
                    "Điện thoại Xiaomi đáng mua:\nClick sản phẩm để xem chi tiết"
            );
        }

        else if (containsAny(q, "oppo")) {
            List<Product> products = productRepository.findByBrandForChatbot("Oppo");
            answer = buildProductAnswer(
                    products,
                    "Điện thoại OPPO nổi bật:\nClick sản phẩm để xem chi tiết"
            );
        }
        else if (containsAny(q, "phu kien", "phụ kiện")) {
            List<Product> products = productRepository.findByBrandForChatbot("Phụ kiện");
            answer = buildProductAnswer(
                    products,
                    "Điện thoại OPPO nổi bật:\nClick sản phẩm để xem chi tiết"
            );
        }
        else if (containsAny(q, "vivo")) {
            List<Product> products = productRepository.findByBrandForChatbot("Vivo");
            answer = buildProductAnswer(
                    products,
                    "Điện thoại VIVO đáng chú ý:\nClick sản phẩm để xem chi tiết"
            );
        }
        else if (containsAny(q, "huawei")) {
            List<Product> products = productRepository.findByBrandForChatbot("Huawei");
            answer = buildProductAnswer(
                    products,
                    "Điện thoại VIVO đáng chú ý:\nClick sản phẩm để xem chi tiết"
            );
        }
        else if (containsAny(q, "nokia")) {
            List<Product> products = productRepository.findByBrandForChatbot("Nokia");
            answer = buildProductAnswer(
                    products,
                    "Điện thoại Nokia đáng mua:\nClick sản phẩm để xem chi tiết"
            );
        }
        else if (containsPrice(q)) {
            int max = extractPrice(q);
            int min = Math.max(0, max - 4_000_000);
            List<Product> products = productRepository.findPhoneForChatbot(min, max);
            answer = buildProductAnswer(products,
                    "📱 Điện thoại trong tầm giá:\nClick sản phẩm để xem chi tiết"
            );
        }

        else if (containsAny(q, "choi game", "gaming", "pubg", "lien quan", "genshin")) {
            List<Product> products = productRepository.findTop5ByStatusOrderBySoldDesc(1);
            answer = buildProductAnswer(
                    products,
                    "🎮 Điện thoại chơi game được ưa chuộng:\nClick sản phẩm để xem chi tiết"
            );
        }

        else if (containsAny(q, "chup anh", "camera", "selfie", "quay video")) {
            List<Product> products = productRepository.findTop5ByStatusOrderByNumOfStarDesc(1);
            answer = buildProductAnswer(products,
                    "📸 Điện thoại chụp ảnh đẹp::\nClick sản phẩm để xem chi tiết"
            );
        }

        else if (containsAny(q, "ban chay", "hot", "nhieu nguoi mua", "pho bien")) {
            List<Product> products = productRepository.findTop5ByStatusOrderBySoldDesc(1);
            answer = buildProductAnswer(products,
                    "🔥 Sản phẩm bán chạy nhất:\nClick sản phẩm để xem chi tiết"
            );
        }

        else {
            answer = """
            Mình chưa hiểu rõ câu hỏi.
            Bạn có thể thử:
            [SUGGEST]Tôi có tầm 13 triệu[/SUGGEST]
            [SUGGEST]Điện thoại khoảng 17 triệu[/SUGGEST]
            [SUGGEST]Dưới 20 triệu có máy nào tốt?[/SUGGEST]
            [SUGGEST]Điện thoại chơi game ngon[/SUGGEST]
            [SUGGEST]Sản phẩm bán chạy nhất[/SUGGEST]
            [SUGGEST]Phụ kiện điện thoại[/SUGGEST]
            """;
        }

        saveHistory(sessionId, question, answer);
        return answer;
    }

    private String buildProductAnswer(List<Product> products, String title) {
        if (products == null || products.isEmpty()) {
            return "Xin lỗi bạn nhưng hiện tại chưa có sản phẩm phù hợp ạ."; }
        StringBuilder sb = new StringBuilder(); sb.append(title).append("\n");
        for (Product p : products) {
            sb.append(p.getProductName())
                    .append(" - ")
                    .append(formatPrice(p.getPrice()))
                    .append(" VNĐ")
                    .append(" | ID=")
                    .append(p.getIdProduct())
                    .append("\n");
        }
        return sb.toString();
    }

    // ✔ kiểm tra có chứa số tiền không
    private boolean containsPrice(String text) {
        return Pattern.compile("(\\d+)(\\s)?(tr|trieu)").matcher(text).find();
    }

    // ✔ lấy giá tiền khách nhập (triệu -> VNĐ)
    private int extractPrice(String text) {
        Matcher matcher = Pattern.compile("(\\d+)(\\s)?(tr|trieu)").matcher(text);

        if (matcher.find()) {
            int million = Integer.parseInt(matcher.group(1));
            return million * 1_000_000;
        }
        return 0;
    }

    private boolean containsAny(String text, String... keywords) {
        for (String k : keywords) {
            if (text.contains(k)) return true;
        }
        return false;
    }

    private void saveHistory(String sessionId, String question, String answer) {
        Chatbot chat = new Chatbot();
        chat.setSessionId(sessionId);
        chat.setQuestion(question);
        chat.setAnswer(answer);
        chatbotRepository.save(chat);
    }

    private String formatPrice(int price) {
        return String.format("%,d", price);
    }

    private String normalize(String input) {
        if (input == null) return "";
        String temp = Normalizer.normalize(input, Normalizer.Form.NFD);
        temp = Pattern.compile("\\p{InCombiningDiacriticalMarks}+")
                .matcher(temp).replaceAll("");
        return temp.toLowerCase(Locale.ROOT);
    }
}


