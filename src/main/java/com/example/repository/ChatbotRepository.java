package com.example.repository;

import com.example.entity.Chatbot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatbotRepository extends JpaRepository<Chatbot, Long> {

    List<Chatbot> findBySessionIdOrderByCreatedAtAsc(String sessionId);
}
