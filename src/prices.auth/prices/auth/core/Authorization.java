package prices.auth.core;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import prices.auth.core.exceptions.AuthException;

public abstract class Authorization {
    private List<Verifier> verifiers = new ArrayList<>();
    private StorageStrategy storageStrategy;

    public abstract AuthPayload authorize(String permissionName) throws AuthException;
    public abstract boolean isAdministrator(AuthPayload payload);
    public abstract AuthExchange getRequest();

    public List<Verifier> getVerifiers() {
        return Collections.unmodifiableList(this.verifiers);
    }

    public void setVerifiers(List<Verifier> verifiers) {
        this.verifiers = verifiers;
    }

    public StorageStrategy getStorageStrategy() {
        return this.storageStrategy;
    }

    public void setStorageStrategy(StorageStrategy storageStrategy) {
        this.storageStrategy = storageStrategy;
    }
}
