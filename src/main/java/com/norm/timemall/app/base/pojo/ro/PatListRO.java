package com.norm.timemall.app.base.pojo.ro;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class PatListRO {
    private String id;
    private String name;
    private String tokenLast;    // Shows only "****1a2b" for user recognition
    private LocalDateTime expiresAt;
    private LocalDateTime lastUsedAt;
    private LocalDateTime createdAt;
    public PatListRO(String id, String name, String tokenLast, LocalDateTime expiresAt, LocalDateTime lastUsedAt, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.tokenLast = tokenLast;
        this.expiresAt = expiresAt;
        this.lastUsedAt = lastUsedAt;
        this.createdAt = createdAt;
    }
}
