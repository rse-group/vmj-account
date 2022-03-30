package prices.auth.vmj.model.core;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;

import prices.auth.vmj.annotations.CRUDRestrictions;
import prices.auth.vmj.enums.CRUDMethod;
import prices.auth.vmj.annotations.RestrictCRUD;
import prices.auth.vmj.annotations.Restricted;
import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;
import vmj.object.mapper.VMJDatabaseField;
import vmj.object.mapper.VMJDatabaseTable;
import vmj.object.mapper.VMJDatabaseUtil;

import java.util.Random;

public abstract class RoleDecorator extends RoleComponent {

    protected RoleComponent role;

    public RoleDecorator(RoleComponent role) {
        this.role = role;
    }

    public RoleDecorator() {
        super();
        this.role = new RoleImpl();
        Random r = new Random();
        this.id = r.nextInt();
    }

    @Override
    public void setName(String name) {
        this.role.setName(name);
    };

    @Override
    public String getName() {
        return this.role.getName();
    };

    @Override
    public void setAllowedPermissions(String allowedPermissions) {
        this.role.setAllowedPermissions(allowedPermissions);
    };

    @Override
    public String getAllowedPermissions() {
        return this.role.getAllowedPermissions();
    };

    // @Override
    // @Route(url = "auth/role/change-permissions")
    // @Restricted(permissionName = "UpdatePermissions")
    // public HashMap<String, Object> changePermissions(VMJExchange vmjExchange) {
    //     return role.changePermissions(vmjExchange);
    // };
}
