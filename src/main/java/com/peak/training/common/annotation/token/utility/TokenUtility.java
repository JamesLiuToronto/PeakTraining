package com.peak.training.common.annotation.token.utility;

import com.peak.training.common.annotation.token.dto.TokenDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.io.Serializable;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Component
public class TokenUtility implements Serializable {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.duration}")
    private int last;

    private Jws<Claims> parseJwt(String jwtString) {

        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret), SignatureAlgorithm.HS256.getJcaName());

        Jws<Claims> jwt = Jwts.parserBuilder()
                .setSigningKey(hmacKey)
                .build()
                .parseClaimsJws(jwtString);

        return jwt;
    }

    public TokenDTO getTokenDTOFromToken(String token){
        final Claims claims = parseJwt(token).getBody();
        boolean expired = claims.getExpiration().toInstant().isBefore(Instant.now());
        return TokenDTO.builder()
                .type(claims.get("type", String.class))
                .keyName(claims.get("keyName", String.class))
                .keyValue(claims.get("keyValue", String.class))
                .isExpired(expired)
                .build();

    }


    public String createJwtSignedHMAC(TokenDTO token) {
        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret), SignatureAlgorithm.HS256.getJcaName());

        log.info("duration=" + last);
        Instant now = Instant.now();
        String jwtToken = Jwts.builder()
                .claim("type", token.getType())
                .claim("keyName", token.getKeyName())
                .claim("keyValue", token.getKeyValue())
                .setSubject("tokenDTO")
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(last, ChronoUnit.MINUTES)))
                .signWith(hmacKey)
                .compact();

        return jwtToken;
    }

    public String generateNewSecret(String base){
        String secret= Base64.getEncoder().encodeToString(base.getBytes()) ;
        byte[] bts = Base64.getDecoder().decode(secret);
        Key hmacKey = new SecretKeySpec(bts, SignatureAlgorithm.HS256.getJcaName());

        System.out.println(hmacKey.toString());
        Instant now = Instant.now();
        String jwtToken = Jwts.builder()
                .setSubject("PEAK-TRAINING")
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(60l, ChronoUnit.MINUTES)))
                .signWith(hmacKey)
                .compact();
        return secret ;

    }

}
