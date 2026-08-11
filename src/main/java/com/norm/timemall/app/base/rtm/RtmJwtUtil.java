package com.norm.timemall.app.base.rtm;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.norm.timemall.app.base.exception.QuickMessageException;
import com.norm.timemall.app.base.security.AiSecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class RtmJwtUtil {



    @Autowired
    private AiSecurityProperties aiSecurityProperties;


    public  String createToken(String userId) {
        try {
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(userId)
                    .issueTime(new Date())
                    .expirationTime(new Date(System.currentTimeMillis() + 86400 * 1000)) // 24h
                    .build();

            JWSSigner signer = new MACSigner(aiSecurityProperties.getJwtSecret().getBytes(StandardCharsets.UTF_8));
            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
            signedJWT.sign(signer);

            return signedJWT.serialize();
        } catch (Exception e) {
            throw new QuickMessageException("生成RTM Token失败");
        }
    }

    public String verifyAndGetUserId(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWSVerifier verifier = new MACVerifier(aiSecurityProperties.getJwtSecret().getBytes(StandardCharsets.UTF_8));

            if (!signedJWT.verify(verifier)) return null;

            JWTClaimsSet claims = signedJWT.getJWTClaimsSet();

            // Check if claims or expiration time exists
            if (claims == null || claims.getExpirationTime() == null) return null;

            // Check expiration
            if (new Date().after(claims.getExpirationTime())) return null;

            return claims.getSubject();
        } catch (Exception e) {
            // Log it: 100k users means lots of invalid attempts/bots
            return null;
        }
    }

}
