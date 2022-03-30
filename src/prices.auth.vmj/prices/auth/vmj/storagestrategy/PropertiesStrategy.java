package prices.auth.vmj.storagestrategy;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import prices.auth.core.AuthPayload;
import prices.auth.core.StorageStrategy;
import prices.auth.utils.PropertiesReader;

public class PropertiesStrategy implements StorageStrategy {
    public Map<String, Object> getUserData(AuthPayload payload) {
        Map<String, Object> data = new HashMap<>();
        data.put("email", payload.getEmail());
        return data;
    }
    // getRoles will return roles owned by an user along with list of allowed permissions
    public Map<String, List<String>> getRoles(AuthPayload payload) {
        Map<String, List<String>> data = new HashMap<>();
        PropertiesReader propReader = new PropertiesReader();
        List<String> roles = propReader.getRolesFromEmail(payload.getEmail());

        for (String role : roles) {
            List<String> perms = propReader.getRolePerms(new String[]{role});
            data.put(role, perms);
        }
        return data;
    }
}
