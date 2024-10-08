package com.backend.configurations;

import com.backend.dtos.UserDto;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Collections;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class UserAuthProvider {

    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(UserDto userDto) {
        var now = new Date();
        var validity = new Date(now.getTime() + 3_600_000);

        return JWT.create()
                .withIssuer(userDto.getUsername())
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .withClaim("firstname", userDto.getFirstname())
                .withClaim("lastname", userDto.getLastname())
                .withClaim("username", userDto.getUsername())
                .sign(Algorithm.HMAC256(secretKey));
    }

    public Authentication validateToken(String token) {
        var algorithm = Algorithm.HMAC256(secretKey);
        var verifier = JWT.require(algorithm).build();
        var decodedJWT = verifier.verify(token);

        var user = UserDto.builder()
                .firstname(decodedJWT.getClaim("firstname").asString())
                .lastname(decodedJWT.getClaim("lastname").asString())
                .username(decodedJWT.getIssuer())
                .build();

        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
    }
}
