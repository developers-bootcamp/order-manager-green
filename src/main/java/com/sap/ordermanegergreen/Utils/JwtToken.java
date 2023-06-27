package com.sap.ordermanegergreen.Utils;
import com.sap.ordermanegergreen.Models.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtToken {

    private static final String JWT_SECRET = "mySecretKey";
    private static final long JWT_EXPIRATION = 604800000L; // 7 days in milliseconds

    public String generateToken(User user) {
        Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET.getBytes());
        Date now = new Date();
        String accessToken = JWT.create()
                .withSubject(user.getId())
                .withSubject(user.getCompanyId().getId())
                .withSubject(user.getRoleId().getId())
                .withExpiresAt(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
                .withIssuer("nameManager")
                .sign(algorithm);
        return accessToken;
    }
}
