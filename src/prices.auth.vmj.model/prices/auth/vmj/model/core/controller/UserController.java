package prices.auth.vmj.model.core;

import java.util.*;

import vmj.routing.route.VMJExchange;

public interface UserController {
    boolean checkCurrentUser(VMJExchange vmjExchange);
    HashMap<String, Object> changePermissions(VMJExchange vmjExchange);
}
