package prices.auth.vmj;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import prices.auth.core.Authorization;
import prices.auth.core.AuthFactory;
import prices.auth.utils.PropertiesReader;

import prices.auth.core.Verifier;
import prices.auth.core.StorageStrategy;


public class VMJAuthFactory extends AuthFactory {
    public VMJAuthFactory() {}

    public VMJAuthorization createAuth(StorageStrategy storageStrategy, Verifier... verifiers) {
        VMJAuthorization object = new VMJAuthorization();
        object.setStorageStrategy(storageStrategy);
        object.setVerifiers(Arrays.asList(verifiers));
        return object;
    }

    @Override
    public Authorization createAuth() {
        String authType = (new PropertiesReader()).getAuthType();
        String authStorageType = (new PropertiesReader()).getAuthStorageType();

        return this.createAuth(
            createStorageStrategy(authStorageType),
            createVerifiers(authType));
    }

    public StorageStrategy createStorageStrategy(String authStorageType) {
        if (authStorageType.equals("db")) {
            return this.createStorageStrategyObject("prices.auth.vmj.storagestrategy.DBStrategy");
        } else if (authStorageType.equals("properties")) {
            return this.createStorageStrategyObject("prices.auth.vmj.storagestrategy.PropertiesStrategy");
        }
        return this.createStorageStrategyObject(authStorageType.trim());
    }

    public Verifier[] createVerifiers(String authType) {
        List<Verifier> verifiers = new ArrayList<>();
        for (String type : authType.split(",")) {
            if (type.trim().equals("manual")) {
                verifiers.add(this.createVerifierObject("prices.auth.providers.manual.ManualLoginVerifier"));
            } else if (type.trim().equals("google")) {
                verifiers.add(this.createVerifierObject("prices.auth.providers.oauth2.google.DefaultGoogleTokenVerifier"));
                verifiers.add(this.createVerifierObject("prices.auth.providers.oauth2.google.AlternativeGoogleTokenVerifier"));
            } else if (type.trim().equals("facebook")) {
                verifiers.add(this.createVerifierObject("prices.auth.providers.oauth2.facebook.DefaultFacebookTokenVerifier"));
                verifiers.add(this.createVerifierObject("prices.auth.providers.oauth2.facebook.AlternativeFacebookTokenVerifier"));
            } else if (type.trim().equals("auth0")) {
                verifiers.add(this.createVerifierObject("prices.auth.providers.oauth2.auth0.DefaultAuth0TokenVerifier"));
            } else {
                verifiers.add(this.createVerifierObject(type.trim()));
            }
        }
        Verifier[] result = new Verifier[verifiers.size()];
        verifiers.toArray(result);
        return result;
    }
}
