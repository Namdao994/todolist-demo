package com.lifetex.todolist.util;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtil {
    private static final String SECRET_STRING = "mysecretkeymysecretkeymysecretkey";
    private static final Key SECRET_KEY = Keys.hmacShaKeyFor(SECRET_STRING.getBytes());

        private static final long EXPIRATION_TIME = 60 * 60 * 24 * 1000 ; // 24h
//    private static final long EXPIRATION_TIME = 1000 * 10 ;
    private static final long REFRESH_EXPIRATION_TIME = 60 * 60 * 24 * 7 * 1000; // 24h

    // Generate Token
    public static String generateAccessToken(Long userId, String username) {
        System.out.println("SECRET_KEY >>>> " + SECRET_KEY);
        return Jwts.builder().setSubject(username).claim("userId", userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }

    public static String generateRefreshToken(Long userId, String username) {
        return Jwts.builder()
                .setSubject(username)
                .claim("userId", userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }

    //Parse token (core)
    private static Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    //Extract username
    public static String extractUsername(String token) {
        return getClaims(token).getSubject();
    }
    //Extract userId
    public static Long extractUserId(String token) {
        return getClaims(token).get("userId", Long.class);
    }
    //Validate Token
    public static boolean isTokenValid(String token) {
        try {
            getClaims(token); // nếu lỗi sẽ throw exception
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
    //Check expired
    public static boolean isTokenExpired(String token) {
        Date expiration = getClaims(token).getExpiration();
        return expiration.before(new Date());
    }
}
