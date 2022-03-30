package prices.auth.vmj.model.core;

import java.util.*;

import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;

import prices.auth.vmj.model.UserFactory;

import prices.auth.vmj.annotations.Restricted;

public class RoleControllerImpl extends RoleControllerComponent {
    
    public RoleControllerImpl() {}

    @Route(url = "auth/role/change-permissions")
    @Restricted(permissionName = "UpdatePermissions")
    public HashMap<String, Object> changePermissions(VMJExchange vmjExchange) {
        Object id = vmjExchange.getPOSTBodyForm("id");
        Object allowedPermissions = vmjExchange.getPOSTBodyForm("allowedPermissions");
        
        HashMap<String, Object> hasil = new HashMap<>();
        Role role = roleDao.getObject((Integer) id);
        if (role == null) {
            hasil.put("status", "role tidak ditemukan");
            return hasil;
        }

        // String checkUserQuery = "SELECT id FROM auth_role WHERE id='" + id + "';";
        // ArrayList<Object> ids = vmjDBUtil.queryForAColumn(checkUserQuery, "id");
        // if (ids.isEmpty()) {
        //     HashMap<String, Object> hasil = new HashMap<>();
        //     hasil.put("status", "role tidak ditemukan");
        //     return hasil;
        // }
        
        role.setAllowedPermissions((String) allowedPermissions);
        roleDao.updateObject(role);

        // String sqlCommand = "UPDATE auth_role SET allowedPermissions='" + allowedPermissions +
        //     "' WHERE id='" + id + ";";

        // vmjDBUtil.hitDatabase(sqlCommand);
        // HashMap<String, Object> hasil = new HashMap<>();
        hasil.put("status", "sukses mengganti permission dari role");
        return hasil;
    }
}
