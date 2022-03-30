/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prices.auth.providers.oauth2.facebook;

import prices.auth.providers.ConnectionUtils;
import prices.auth.core.Verifier;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;
import prices.auth.core.AuthPayload;
import prices.auth.core.TokenPayload;
import prices.auth.utils.PropertiesReader;
import java.security.GeneralSecurityException;
import java.util.HashMap;

/**
 *
 * @author Ichlasul Affan
 */
public class AlternativeFacebookTokenVerifier implements Verifier {

    /**
     * @param rawClientId
     * @param token
     * @return
     */
    @Override
    public AuthPayload verifyAndParse(final String token) {
        final String rawClientId = (new PropertiesReader()).getClientId();
        String[] splittedClientId = splitClientIdSecret(rawClientId);
        String clientId = splittedClientId[0];
        String clientSecret = splittedClientId[1];

        Map<String, Object> payloads = new HashMap<>();
        try {
            String appToken = getAppToken(clientId, clientSecret);
            payloads.putAll(getTokenData(appToken.trim(), token.trim()));
            ((Map) payloads.get("data")).putAll(getUserData(token.trim()));
            TokenPayload payload = new FacebookPayloadAdapter(payloads);

            if (payload.getEmail() == null) {
                throw new GeneralSecurityException("Invalid ID token");
            }
            return payload;
        } catch (GeneralSecurityException e) {
            System.out.println("Security issue: " + e.getLocalizedMessage());
            e.printStackTrace();
        } catch (MalformedURLException e) {
            System.out.println("Network problem: " + e.getLocalizedMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Network problem: " + e.getLocalizedMessage());
            e.printStackTrace();
        }

        return null;
    }

    private String getAppToken(final String clientId, final String clientSecret)
            throws MalformedURLException, IOException {
        return (String) ConnectionUtils.getJsonLessSecure("https://graph.facebook.com/oauth/access_token?client_id="
                + clientId + "&client_secret=" + clientSecret + "&grant_type=client_credentials")
                .getOrDefault("access_token", "");
    }

    private Map<String, Object> getTokenData(final String appToken, final String userToken)
            throws MalformedURLException, IOException {
        return ConnectionUtils.getJsonLessSecure("https://graph.facebook.com/debug_token?input_token="
                + userToken.trim() + "&access_token=" + appToken.trim());
    }

    private Map<String, Object> getUserData(final String token) throws MalformedURLException, IOException {
        return ConnectionUtils.getJsonLessSecure("https://graph.facebook.com/me?access_token="
                + token.trim() + "&fields=id,name,email");
    }

    private String[] splitClientIdSecret(final String rawClientId) {
        return rawClientId.split("$");
    }
}
