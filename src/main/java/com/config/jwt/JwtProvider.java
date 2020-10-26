package com.config.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtProvider {

    private final String JWT_SECRET = "Creating filter chain: any request, [org.springframework.security.web.context.request.async.WebAs";
    private final long JWT_EXPIRE = 1800000L;

    public String generateToken(Long userId) {
        Date now = new Date();
        Date expire = new Date(now.getTime() + JWT_EXPIRE);
        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setIssuedAt(now)
                .setExpiration(expire)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    public Long getUserIdFromToken(String token) {
        try {
            return Long.parseLong(Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody().getSubject());
        } catch (Exception e) {
            return -1L;
        }
    }

    public boolean validationToken(String token) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
