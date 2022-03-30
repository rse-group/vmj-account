/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prices.auth.vmj;

import prices.auth.core.Authorization;
import prices.auth.core.AuthExchange;
import prices.auth.core.AuthPayload;
import prices.auth.core.TokenPayload;
import prices.auth.core.exceptions.*;
import prices.auth.providers.manual.ManualLoginVerifier;
import prices.auth.utils.PropertiesReader;
import prices.auth.core.Verifier;
import prices.auth.core.Constants;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import com.sun.net.httpserver.HttpExchange;

/**
 *
 * @author Afifun
 */
public class VMJAuthorization extends Authorization {
    private PropertiesReader propReader = new PropertiesReader(Constants.DEFAULT_AUTH_FILE);
    private VMJAuthExchange request;

    public VMJAuthorization(HttpExchange request) {
        this.setRequest(request);
    }

    public VMJAuthorization() {}

    public AuthExchange getRequest() {
        return request;
    }

    public void setRequest(HttpExchange request) {
        this.request = new VMJAuthExchange(request);
    }

    @Override
    public AuthPayload authorize(String permissionName) throws AuthException {
        String token = this.request.getAuthToken();
        if (token == null) {
            throw new TokenNullException();
        }
        if (token.length() <= 0) {
            throw new TokenEmptyException();
        }

        AuthPayload payload;
        if (this.getVerifiers() != null && !this.getVerifiers().isEmpty()) {
            for (Verifier verifier : this.getVerifiers()) {
                System.out.println("Check using verifier: " + verifier.getClass().getName());
                payload = verifier.verifyAndParse(token);
                if (this.verifyPayload(payload, permissionName)) {
                    return payload;
                } else if (payload != null) {
                    throw new NotPermittedException(permissionName);
                }
                System.out.println("CONTINUE TO NEXT VERIFIER");
            }
        } else {
            throw new AuthException("Invalid auth type.");
        }

        // if reached here, reject, because it's invalid (null for all strategies)
        throw new TokenInvalidException();
    }

    @Override
    public boolean isAdministrator(AuthPayload payload) {
        boolean allowed = false;
        if (payload != null) {
            Map<String, List<String>> roles = this.getStorageStrategy().getRoles(payload);
            for (List<String> permissions : roles.values()) {
                for (String permission : permissions) {
                    if (permission.equals(Constants.ADMINISTRATOR_PERMS_SIGN)) {
                        return true;
                    }
                }
            }
            Map<String, Object> user = this.getStorageStrategy().getUserData(payload);
            List<String> userPermissions = (List<String>) user.getOrDefault("allowedPermissions", new ArrayList<>());
            for (String permission : userPermissions) {
                if (permission.equals(Constants.ADMINISTRATOR_PERMS_SIGN)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean verifyPayload(AuthPayload payload, String permissionName) {
        boolean allowed = false;
        if (payload != null) {
            Map<String, List<String>> roles = this.getStorageStrategy().getRoles(payload);
            for (List<String> permissions : roles.values()) {
                for (String permission : permissions) {
                    if (permission.equals(Constants.ADMINISTRATOR_PERMS_SIGN)) {
                        return true;
                    } else if (permission.equals(permissionName)) {
                        allowed = true;
                    } else if (permission.equals("-" + permissionName)) {
                        allowed = false;
                    }
                }
            }
            Map<String, Object> user = this.getStorageStrategy().getUserData(payload);
            List<String> userPermissions = (List<String>) user.getOrDefault("allowedPermissions", new ArrayList<>());
            for (String permission : userPermissions) {
                if (permission.equals(Constants.ADMINISTRATOR_PERMS_SIGN)) {
                    return true;
                } else if (permission.equals(permissionName)) {
                    allowed = true;
                } else if (permission.equals("-" + permissionName)) {
                    allowed = false;
                }
            }
        }
        return allowed;
    }
}
