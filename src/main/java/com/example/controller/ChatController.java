package com.example.controller;

import com.example.dto.ChatMessageDTO;
import com.example.entity.ChatMessage;
import com.example.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatService chatService;

    @MessageMapping("/chat")
    public void sendMessage(ChatMessageDTO dto) {
        ChatMessage message = ChatMessage.builder()
                .sender(dto.getSender())
                .receiver(dto.getReceiver())
                .content(dto.getContent())
                .build();

        chatService.save(message);

        messagingTemplate.convertAndSendToUser(dto.getReceiver(), "/queue/messages", message);
        messagingTemplate.convertAndSendToUser(dto.getSender(), "/queue/messages", message);
    }

    @GetMapping("/api/chat/history")
    public List<ChatMessage> getHistory(@RequestParam String user1, @RequestParam String user2) {
        return chatService.getHistory(user1, user2);
    }

    @GetMapping("/api/chat/users")
    public List<String> getUsers() {
        return chatService.getUsersChatWithAdmin();
    }
}