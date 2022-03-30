package prices.auth.vmj.model.core;

import java.util.*;

import prices.auth.vmj.annotations.Restricted;

import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;

public abstract class UserControllerDecorator extends UserControllerComponent {
    
    protected UserControllerComponent user;

    public UserControllerDecorator(UserControllerComponent user) {
        this.user = user;
    }

    public boolean checkCurrentUser(VMJExchange vmjExchange) {
        return user.checkCurrentUser(vmjExchange);
    }

    @Route(url = "auth/user/change-permissions")
    @Restricted(permissionName = "UpdatePermissions")
    public HashMap<String, Object> changePermissions(VMJExchange vmjExchange) {
        return user.changePermissions(vmjExchange);
    }

}
