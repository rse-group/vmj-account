/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prices.auth.providers.oauth2.auth0;

import prices.auth.providers.JWTPayloadAdapter;
import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkException;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.UrlJwkProvider;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import prices.auth.core.AuthPayload;
import prices.auth.core.TokenPayload;
import prices.auth.core.Verifier;
import prices.auth.utils.PropertiesReader;
import java.security.interfaces.RSAPublicKey;

/**
 *
 * @author ichla
 */
public class DefaultAuth0TokenVerifier implements Verifier {
    @Override
    public AuthPayload verifyAndParse(final String token) {
        final String rawClientId = (new PropertiesReader()).getClientId();
        String[] splittedClientId = splitAddressClientId(rawClientId);
        String address = "https://" + splittedClientId[0] + "/";
        String clientId = splittedClientId[1];
        try {
            DecodedJWT jwt = JWT.decode(token.trim());
            JwkProvider provider = new UrlJwkProvider(address + ".well-known/jwks.json");
            Jwk jwk = provider.get(jwt.getKeyId());
            Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) jwk.getPublicKey(), null);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(address)
                    .build(); //Reusable verifier instance
            jwt = verifier.verify(token.trim());

            TokenPayload payload = null;
            if (jwt != null) {
                payload = new JWTPayloadAdapter(jwt);
                System.out.println(payload.getAudiences());
                System.out.println(payload.getIssuer());
                System.out.println(payload.getEmail());
                if (!payload.getAudiences().contains(clientId) || !payload.getIssuer().equals(address)) {
                    throw new IllegalArgumentException("Client ID mismatch");
                }
            } else {
                throw new IllegalArgumentException("Invalid ID token.");
            }
            return payload;
        } catch (JWTVerificationException e) {
            System.out.println("Token problem: " + e.getLocalizedMessage());
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.out.println("Token problem: " + e.getLocalizedMessage());
            e.printStackTrace();
        } catch (JwkException e) {
            System.out.println("Security issue: " + e.getLocalizedMessage());
            e.printStackTrace();
        }

        return null;
    }

    private String[] splitAddressClientId(final String rawClientId) {
        return rawClientId.split("\\$");
    }
}
