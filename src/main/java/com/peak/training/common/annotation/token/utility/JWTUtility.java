package com.peak.training.common.annotation.token.utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import com.peak.training.common.annotation.token.TokenExpiredException;
import com.peak.training.common.annotation.token.dto.UserDTO;
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
public class JWTUtility implements Serializable {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.duration}")
    private int duration;

    private Jws<Claims> parseJwt(String jwtString) {

        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret), SignatureAlgorithm.HS256.getJcaName());

        Jws<Claims> jwt = Jwts.parserBuilder()
                .setSigningKey(hmacKey)
                .build()
                .parseClaimsJws(jwtString);

        return jwt;
    }

    /*private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = parseJwt(token).getBody();
        return claimsResolver.apply(claims);
    }

    public String getLoginIDFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }*/

    private UserDTO getUserDTOFromToken(String token){
        final Claims claims = parseJwt(token).getBody();
        boolean expired = claims.getExpiration().toInstant().isBefore(Instant.now());
        return UserDTO.builder()
                .firstName(claims.get("firstName", String.class))
                .userID(claims.get("userID", Integer.class))
                .lastName(claims.get("lastName", String.class))
                .roleList(claims.get("role", String.class))
                .isExpired(expired)
                .methodList(claims.get("method", String.class))
                .emailAddress(claims.get("emailAddress", String.class))
                .build();

    }

    public UserDTO validatetoken(String token) throws TokenExpiredException {
        UserDTO user = getUserDTOFromToken(token) ;
        if (user.isExpired()) throw new TokenExpiredException("token has been expired") ;
        return user ;
    }

    public String createJwtSignedHMAC(UserDTO user) {
        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret), SignatureAlgorithm.HS256.getJcaName());

        Instant now = Instant.now();
        String jwtToken = Jwts.builder()
                .claim("userID", user.getUserID())
                .claim("firstName", user.getFirstName())
                .claim("lastName", user.getLastName())
                .claim("role", user.getRoleList())
                .claim("method", user.getMethodList())
                .claim("emailAddress", user.getEmailAddress())
                .setSubject("userDTO")
                .setId(user.getUuid())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(duration, ChronoUnit.MINUTES)))
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
