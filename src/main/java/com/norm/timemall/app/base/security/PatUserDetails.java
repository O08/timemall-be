package com.norm.timemall.app.base.security;

import org.springframework.security.core.authority.AuthorityUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

public class PatUserDetails extends CustomizeUser  implements Serializable {
    private static final long serialVersionUID = 1L;


    private LocalDateTime expiresAt;

    public PatUserDetails(String userId, String username, String brandId, LocalDateTime expiresAt) {
        super(
                userId,
                username,
                "N/A", // Password placeholder since PAT is passwordless
                brandId,
                AuthorityUtils.createAuthorityList("ROLE_AI_BOT") // Pre-seed the BOT authority
        );
        this.expiresAt = expiresAt;
    }
    public LocalDateTime getExpiresAt() { return expiresAt; }
    public void setExpiresAt(LocalDateTime expiresAt) { this.expiresAt = expiresAt; }

}
