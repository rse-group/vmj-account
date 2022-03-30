package prices.auth.vmj.model.core;

import java.util.*;

import vmj.routing.route.VMJExchange;

public interface RoleController {
    public HashMap<String, Object> changePermissions(VMJExchange vmjExchange);
}
