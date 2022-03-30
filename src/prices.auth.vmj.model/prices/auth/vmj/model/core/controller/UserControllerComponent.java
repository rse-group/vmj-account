package prices.auth.vmj.model.core;

import java.util.*;

import vmj.routing.route.VMJExchange;
import vmj.hibernate.integrator.DaoUtil;

public abstract class UserControllerComponent implements UserController {
    protected DaoUtil<User> userDao;

    public UserControllerComponent() {
        this.userDao = new DaoUtil<User>(prices.auth.vmj.model.core.UserComponent.class);
    }

    public abstract boolean checkCurrentUser(VMJExchange vmjExchange);
    public abstract HashMap<String, Object> changePermissions(VMJExchange vmjExchange);
}
