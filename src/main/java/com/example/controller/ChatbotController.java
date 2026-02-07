package com.example.controller;

import com.example.dto.ChatRequest;
import com.example.service.ChatbotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/chatbot")
@RequiredArgsConstructor
public class ChatbotController {

    private final ChatbotService chatbotService;

    @PostMapping("/chat")
    public ResponseEntity<String> chat(@RequestBody ChatRequest request) {
        if (request.getQuestion() == null || request.getQuestion().isBlank()) {
            return ResponseEntity.badRequest().body("Câu hỏi không hợp lệ");
        }
        String answer = chatbotService.chat(request.getSessionId(), request.getQuestion());
        return ResponseEntity.ok(answer);
    }

}
