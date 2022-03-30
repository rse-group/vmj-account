package prices.auth.vmj.model.core;

import java.util.HashMap;
import vmj.routing.route.VMJExchange;

public interface User {
    void setId(int id);
    int getId();

    void setName(String name);
    String getName();
    
    void setEmail(String email);
    String getEmail();
    
    void setAllowedPermissions(String allowedPermissions);
    String getAllowedPermissions();

    void setPassword(String password);
    String getPassword();

    HashMap<String, Object> toHashMap();
}
