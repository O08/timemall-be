package com.norm.timemall.app.base.handlers;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.norm.timemall.app.base.exception.QuickMessageException;
import com.norm.timemall.app.base.mo.Brand;
import com.norm.timemall.app.base.security.AiSecurityProperties;
import com.norm.timemall.app.base.service.AccountService;
import com.norm.timemall.app.base.service.BaseSysOauthCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

/**
 * oauth2 handler
 */
@Component
public class TokenAuthenticationHandler {
    @Autowired
    private BaseSysOauthCodeService baseSysOauthCodeService;

    @Autowired
    private AiSecurityProperties aiSecurityProperties;

    @Autowired
    private AccountService accountService;



    public String findUserIdByJwtCode(String code) {
        return baseSysOauthCodeService.findUserIdUsingCode(code);
    }

    public void removeJwtCode(String code) {
        baseSysOauthCodeService.removeOneJwtCode(code);
    }

    public String createOauthToken(String userId) {
        try {
            Brand user = accountService.findBrandInfoByUserId(userId);

            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(user.getCustomerId())           // 存入 userId
                    .claim("unm", user.getBrandName())    // 存入 username
                    .claim("bid", user.getId())     // 存入 brandId
                    .claim("authorities", List.of("ROLE_AI_BOT"))
                    .expirationTime(new Date(System.currentTimeMillis() + 86400 * 1000)) // 24h
                    .build();

            JWSSigner signer = new MACSigner(aiSecurityProperties.getJwtSecret().getBytes(StandardCharsets.UTF_8));
            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);

            signedJWT.sign(signer);

            return signedJWT.serialize();
        } catch (Exception e) {
            throw new QuickMessageException("Failed to generate JWT");
        }
    }
}
