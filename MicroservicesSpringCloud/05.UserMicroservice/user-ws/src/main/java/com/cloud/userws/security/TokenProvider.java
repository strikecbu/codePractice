package com.cloud.userws.security;

import com.cloud.userws.domain.UserEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static java.time.temporal.ChronoUnit.MINUTES;

@Component
public class TokenProvider {

    @Value("${token.secret}")
    private String secret;

    @Value("${token.expired.time}")
    private Integer expiredTime;

    public String token(UserEntity userEntity) {
        LocalDateTime dateTime = LocalDateTime.now()
                .plus(expiredTime, MINUTES);
        Date date = Date.from(dateTime.atZone(ZoneId.systemDefault())
                .toInstant());
        return Jwts.builder()
                .setSubject(userEntity.getPublicId())
                .setIssuedAt(new Date())
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
}
