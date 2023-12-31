package com.sap.ordermanagergreen.util;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sap.ordermanagergreen.dto.TokenDTO;
import com.sap.ordermanagergreen.model.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtToken {

    private static final String JWT_SECRET = "mySecretKey";
    private static final long JWT_EXPIRATION = 604800000L; // 7 days in milliseconds

    public static String generateToken(User user) {
        Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET.getBytes());
        Date now = new Date();
        System.out.println("begin");
        String accessToken = JWT.create()
                .withClaim("roleName", user.getRole().getId())
                .withClaim("id", user.getId())
                .withClaim("companyId", user.getCompany().getId())
                .withExpiresAt(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
                .sign(algorithm);
        System.out.println("accessToken");
        return accessToken;
    }

    public static TokenDTO decodeToken(String token)  {


        Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET.getBytes());
        DecodedJWT jwt = JWT.require(algorithm).build().verify(token);
        TokenDTO decodedToken = new TokenDTO();
        decodedToken.setUserId(jwt.getClaim("id").asString());
        decodedToken.setCompanyId(jwt.getClaim("companyId").asString());
        decodedToken.setRoleId(jwt.getClaim("roleId").asString());
        decodedToken.setExpirationDate(jwt.getExpiresAt());
        return decodedToken;

    }
}

