package com.example.service.impl;

import com.example.entity.ChatMessage;
import com.example.repository.ChatMessageRepository;
import com.example.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatMessageRepository chatRepo;

    @Override
    public ChatMessage save(ChatMessage message) {
        message.setCreatedAt(LocalDateTime.now());
        return chatRepo.save(message);
    }

    @Override
    public List<ChatMessage> getHistory(String user1, String user2) {

        return chatRepo
                .findBySenderAndReceiverOrReceiverAndSenderOrderByCreatedAtAsc(
                        user1,
                        user2,
                        user1,
                        user2
                );
    }

    public List<String> getUsersChatWithAdmin() {
        return chatRepo.findDistinctUsers();
    }
}