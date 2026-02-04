package com.example.dto;

import lombok.*;
@Getter
@Setter
public class ChatRequest {
    private String sessionId;
    private String question;
}
