/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prices.auth.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import prices.auth.core.Constants;

/**
 *
 * @author ichla
 */
public class JWTUtils {
    public static String createJWT(String userId, String email, long ttlMillis) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        String jwtString = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(getSecretKey());
            JWTCreator.Builder builder = JWT.create()
                    .withIssuer(getIssuer())
                    .withIssuedAt(now)
                    .withSubject(userId + "::" + email)
                    .withClaim("email", email);
            if (ttlMillis > 0) {
                long expMillis = nowMillis + ttlMillis;
                Date exp = new Date(expMillis);
                builder.withExpiresAt(exp);
            }
            jwtString = builder.sign(algorithm);
        } catch (JWTCreationException e){
            System.out.println("Token problem: " + e.getLocalizedMessage());
            e.printStackTrace();
        }
        return jwtString;
    }

    public static DecodedJWT decodeJWT(String token) {
        DecodedJWT jwt = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(getSecretKey());
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(getIssuer())
                    .build();
            jwt = verifier.verify(token);
        } catch (JWTVerificationException e){
            System.out.println("Token problem: " + e.getLocalizedMessage());
            e.printStackTrace();
        }
        return jwt;
    }

    private static String getSecretKey() {
        return (new PropertiesReader()).getPropOrDefault(
            Constants.MANUAL_SECRET_KEY_PROP, Constants.MANUAL_SECRET_KEY_DEFAULT);
    }

    public static String getIssuer() {
        return (new PropertiesReader()).getPropOrDefault(
            Constants.MANUAL_JWT_ISSUER_PROP, Constants.MANUAL_JWT_ISSUER_DEFAULT);
    }

    public static long getTTL() {
        return Long.parseLong((new PropertiesReader()).getPropOrDefault(
            Constants.MANUAL_JWT_TTL_PROP, Constants.MANUAL_JWT_TTL_DEFAULT));
    }
}
