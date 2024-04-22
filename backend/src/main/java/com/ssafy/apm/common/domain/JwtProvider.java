package com.ssafy.apm.common.domain;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Component
@Slf4j
public class JwtProvider {

    @Value("${jwt.accessExpTime}")
    long accessExpTime;

    private final SecretKey SECRET_KEY;

    public JwtProvider(@Value("${jwt.secret}") String key) {
        this.SECRET_KEY = new SecretKeySpec(key.getBytes(), SignatureAlgorithm.HS512.getJcaName());
    }

    // 액세스 토큰 생성
    public String createAccessToken(Long id, String role) {
        //        System.out.println("accessToken : " + accessToken);
        return Jwts.builder()
                .claim("userId", id)
                .claim("type", "access")
                .claim("role", role)
                .setExpiration(new Date(System.currentTimeMillis() + accessExpTime))
                .signWith(SECRET_KEY,SignatureAlgorithm.HS512)
                .compact();
    }

    public boolean verifyToken(String token) {
        try {
            token = token.replace("Bearer ", "");
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token);
            return claims.getBody()
                    .getExpiration()
                    .after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public String validateToken(String accessToken) {
//        System.out.println("validate check : " + accessToken);
        accessToken = accessToken.replace("Bearer ", "");
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
            String type = (String) claims.get("type");
            return type;
        } catch (ExpiredJwtException e) {
            return "expired";
        } catch (JwtException | IllegalArgumentException e) {
            return "invalid";
        } catch (NoSuchElementException e) {
            return "not_found";
        } catch (ArrayIndexOutOfBoundsException e) {
            return "index_out_of_bounds";
        } catch (NullPointerException e) {
            return "null";
        } catch (Exception e) {
            return "unknown";
        }
    }

    public Long getUserId(String jwt) {
        jwt = jwt.replace("Bearer ", "");
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(jwt)
                .getBody();
        return ((Integer) claims.get("userId")).longValue();
    }

    public List<String> getUserRole(String jwt) {
        jwt = jwt.replace("Bearer ", "");
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(jwt)
                .getBody();
        return (List<String>) claims.get("role");
    }

}
