package com.example.repository;

import com.example.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findBySenderAndReceiverOrReceiverAndSenderOrderByCreatedAtAsc(
            String sender,
            String receiver,
            String receiver2,
            String sender2
    );

    @Query("""
        SELECT DISTINCT 
        CASE 
         WHEN c.sender='admin' THEN c.receiver
         ELSE c.sender
        END
        FROM ChatMessage c
        """)
    List<String> findDistinctUsers();
}