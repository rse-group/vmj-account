/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prices.auth.providers.manual;

import com.auth0.jwt.interfaces.DecodedJWT;
import prices.auth.providers.JWTPayloadAdapter;
import prices.auth.core.AuthPayload;
import prices.auth.core.TokenPayload;
import prices.auth.core.Verifier;
import prices.auth.utils.JWTUtils;

/**
 *
 * @author ichla
 */
public class ManualLoginVerifier implements Verifier {
    @Override
    public AuthPayload verifyAndParse(final String token) {
        try {
            DecodedJWT jwt = JWTUtils.decodeJWT(token.trim());
            TokenPayload payload = null;
            if (jwt != null) {
                payload = new JWTPayloadAdapter(jwt);
                System.out.println(payload.getAudiences());
                System.out.println(payload.getIssuer());
                System.out.println(payload.getEmail());
                if (!payload.getIssuer().equals(JWTUtils.getIssuer())) {
                    throw new IllegalArgumentException("Issuer mismatch");
                }
            } else {
                throw new IllegalArgumentException("Invalid ID token.");
            }
            return payload;
        } catch (IllegalArgumentException e) {
            System.out.println("Token problem: " + e.getLocalizedMessage());
            e.printStackTrace();
        }
        return null;
    }
}
