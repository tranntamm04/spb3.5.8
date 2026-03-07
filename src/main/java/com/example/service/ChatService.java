package com.example.service;

import com.example.entity.ChatMessage;

import java.util.List;

public interface ChatService {

    ChatMessage save(ChatMessage message);
    List<String> getUsersChatWithAdmin();
    List<ChatMessage> getHistory(String user1, String user2);

}