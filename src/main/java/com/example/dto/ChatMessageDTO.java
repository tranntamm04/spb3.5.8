package com.example.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessageDTO {

    private String sender;
    private String receiver;
    private String content;

}