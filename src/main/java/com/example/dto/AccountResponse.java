package com.example.dto;

import java.util.Date;
import lombok.*;

@Getter
@Setter
public class AccountResponse {
    private String userName;
    private Date lastUpdate;
    private String token;
    private String role;
    private String positionName;

    public AccountResponse(String userName, String token, String role, String positionName) {
        this.userName = userName;
        this.token = token;
        this.role = role;
        this.positionName = positionName;
    }

    public AccountResponse(String token) {
        this.token = token;
    }
}
