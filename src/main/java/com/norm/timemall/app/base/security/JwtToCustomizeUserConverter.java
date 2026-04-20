package com.norm.timemall.app.base.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class JwtToCustomizeUserConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        // Extract values from JWT Claims
        String userId = jwt.getSubject();
        String username = jwt.getClaimAsString("unm");
        String brandId = jwt.getClaimAsString("bid");

        // Define Authorities (Role for AI)
        Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_AI_BOT");

        // We pass "[PROTECTED]" as the password because the parent class requires a non-null string
        CustomizeUser user = new CustomizeUser(
                userId,
                username,
                "[PROTECTED]",
                brandId,
                authorities
        );

        return new UsernamePasswordAuthenticationToken(user, null, authorities);
    }
}

