/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prices.auth.providers.oauth2.google;

import prices.auth.providers.ConnectionUtils;
import prices.auth.providers.JWTPayloadAdapter;
import prices.auth.core.AuthPayload;
import prices.auth.core.TokenPayload;
import prices.auth.core.Verifier;
import prices.auth.utils.PropertiesReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.security.GeneralSecurityException;
import java.util.Map;

/**
 *
 * @author Ichlasul Affan
 */
public class AlternativeGoogleTokenVerifier implements Verifier {

    /**
     * @param clientId
     * @param token
     * @return
     */
    @Override
    public AuthPayload verifyAndParse(final String token) {
        final String clientId = (new PropertiesReader()).getClientId();
        try {
            Map<String, Object> rawPayload = ConnectionUtils.getJsonLessSecure(
                    "https://www.googleapis.com/oauth2/v3/tokeninfo?id_token=" + token.trim());
            TokenPayload payload = new GoogleRawPayloadAdapter(rawPayload);
            if (payload.getEmail() == null) {
                throw new GeneralSecurityException("Invalid ID token");
            } else if (!payload.getIssuer().endsWith("accounts.google.com")) {
                throw new GeneralSecurityException("Token not issued from accounts.google.com");
            } else if (!payload.getAudiences().contains(clientId)) {
                throw new IllegalArgumentException("Client ID mismatch");
            }
            return payload;
        } catch (GeneralSecurityException e) {
            System.out.println("Security issue: " + e.getLocalizedMessage());
            e.printStackTrace();
        } catch (MalformedURLException e) {
            System.out.println("Network problem: " + e.getLocalizedMessage());
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.out.println("Token problem: " + e.getLocalizedMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Network problem: " + e.getLocalizedMessage());
            e.printStackTrace();
        }

        return null;
    }
}
