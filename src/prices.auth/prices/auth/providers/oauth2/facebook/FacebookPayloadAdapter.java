/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prices.auth.providers.oauth2.facebook;

import prices.auth.core.TokenPayload;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ichla
 */
public class FacebookPayloadAdapter implements TokenPayload {

    final private Map<String, Object> obj;

    public FacebookPayloadAdapter(Map<String, Object> obj) {
        this.obj = (Map<String, Object>) obj.get("data");
    }

    @Override
    public String getEmail() {
        return (String) obj.getOrDefault("email", null);
    }

    @Override
    public List<String> getAudiences() {
        Object data = obj.getOrDefault("app_id", null);
        if (data instanceof List) {
            return (List<String>) data;
        } else {
            List<String> result = new ArrayList<>();
            result.add((String) data);
            return result;
        }
    }

    @Override
    public String getIssuer() {
        return (String) obj.getOrDefault("iss", null);
    }
}
