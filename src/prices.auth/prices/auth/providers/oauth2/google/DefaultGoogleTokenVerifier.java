/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prices.auth.providers.oauth2.google;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import prices.auth.core.Verifier;
import prices.auth.utils.PropertiesReader;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import prices.auth.core.AuthPayload;
import prices.auth.core.TokenPayload;

/**
 *
 * @author Afifun
 */
public class DefaultGoogleTokenVerifier implements Verifier {

    /**
     * @param clientId
     * @param token
     * @return
     */
    @Override
    public AuthPayload verifyAndParse(final String token) {
        final String clientId = (new PropertiesReader()).getClientId();
        try {
            NetHttpTransport transport = new NetHttpTransport();
            JsonFactory jsonFactory = new GsonFactory();
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                    .setAudience(Collections.singletonList(clientId)).build();

            GoogleIdToken idToken = verifier.verify(token.trim());
            TokenPayload payload = null;

            if (idToken != null) {
                payload = new GooglePayloadAdapter(idToken.getPayload());
                System.out.println(payload.getAudiences());
                System.out.println(payload.getIssuer());
                System.out.println(payload.getEmail());
                if (!payload.getAudiences().contains(clientId)) {
                    throw new IllegalArgumentException("Client ID mismatch");
                }
            } else {
                throw new IllegalArgumentException("Invalid ID token.");
            }
            return payload;
        } catch (GeneralSecurityException e) {
            System.out.println("Security issue: " + e.getLocalizedMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Network problem: " + e.getLocalizedMessage());
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.out.println("Token problem: " + e.getLocalizedMessage());
            e.printStackTrace();
        }

        return null;
    }
}
