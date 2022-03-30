package prices.auth.vmj.model.core;

import java.util.HashMap;
import vmj.routing.route.VMJExchange;

public interface Role {
    void setId(int id);
    int getId();

    void setName(String name);
    String getName();
    
    void setAllowedPermissions(String allowedPermissions);
    String getAllowedPermissions();
}
