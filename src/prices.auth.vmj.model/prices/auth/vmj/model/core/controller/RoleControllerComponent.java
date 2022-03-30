package prices.auth.vmj.model.core;

import java.util.*;

import vmj.routing.route.VMJExchange;
import vmj.hibernate.integrator.DaoUtil;

public abstract class RoleControllerComponent implements RoleController {
    protected DaoUtil<Role> roleDao;

    public RoleControllerComponent() {
        this.roleDao = new DaoUtil<Role>(prices.auth.vmj.model.core.RoleComponent.class);
    }

    public abstract HashMap<String, Object> changePermissions(VMJExchange vmjExchange);
}
