package prices.auth.vmj.model.core;

import java.util.*;

import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;

public abstract class RoleControllerDecorator {
    protected RoleControllerComponent role;

    public RoleControllerDecorator(RoleControllerComponent role) {
        this.role = role;
    }
}
