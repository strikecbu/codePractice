package com.cloud.userws.security;

import com.cloud.userws.domain.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static java.time.temporal.ChronoUnit.MINUTES;

@Component
public class TokenProvider {

    @Value("${token.secret}")
    private String secret;

    @Value("${token.expired.time}")
    private Integer expiredTime;

    public static final String AUTHORITIES_KEY = "Roles";

    public String token(UserEntity userEntity) {
        Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        LocalDateTime dateTime = LocalDateTime.now()
                .plus(expiredTime, MINUTES);
        Date date = Date.from(dateTime.atZone(ZoneId.systemDefault())
                .toInstant());
        return Jwts.builder()
                .setSubject(userEntity.getPublicId())
                .setIssuedAt(new Date())
                .setExpiration(date)
                .claim(AUTHORITIES_KEY, List.of("ADMIN", "OWNER"))
                .signWith(key)
                .compact();


    }

    public Claims parseToUserPublicId(String token) {
        Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        JwtParser build = Jwts.parserBuilder()
                .setSigningKey(key)
                .build();
        return build.parseClaimsJws(token)
                .getBody();
    }
}
