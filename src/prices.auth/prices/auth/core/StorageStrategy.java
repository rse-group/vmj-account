package prices.auth.core;

import java.util.List;
import java.util.Map;

public interface StorageStrategy {
    public Map<String, Object> getUserData(AuthPayload payload);
    // getRoles will return roles owned by an user along with list of allowed permissions
    public Map<String, List<String>> getRoles(AuthPayload payload);
}
