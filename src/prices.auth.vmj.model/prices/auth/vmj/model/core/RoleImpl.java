package prices.auth.vmj.model.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import prices.auth.vmj.annotations.CRUDRestrictions;
import prices.auth.vmj.enums.CRUDMethod;
import prices.auth.vmj.annotations.RestrictCRUD;
import prices.auth.vmj.annotations.Restricted;
import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="auth_role_impl")
public class RoleImpl extends RoleComponent {

    @Column
    public String name;

    @Column
    public String allowedPermissions;

    public RoleImpl(String name, String allowedPermissions) {
        Random r = new Random();
        this.id = r.nextInt();
        this.name = name;
        this.allowedPermissions = allowedPermissions;
    }

    public RoleImpl() {
        Random r = new Random();
        this.id = r.nextInt();
        this.name = "";
        this.allowedPermissions = "";
    }

    @Override
    public void setName(String name) {
        this.name = name;
    };

    @Override
    public String getName() {
        return this.name;
    };

    @Override
    public void setAllowedPermissions(String allowedPermissions) {
        this.allowedPermissions = allowedPermissions;
    };

    @Override
    public String getAllowedPermissions() {
        return this.allowedPermissions;
    };

    // @Route(url = "auth/role/change-permissions")
    // @Restricted(permissionName = "UpdatePermissions")
    // public HashMap<String, Object> changePermissions(VMJExchange vmjExchange) {
    //     Object id = vmjExchange.getPOSTBodyForm("id");
    //     Object allowedPermissions = vmjExchange.getPOSTBodyForm("allowedPermissions");

    //     String checkUserQuery = "SELECT id FROM auth_role WHERE id='" + id + "';";
    //     ArrayList<Object> ids = vmjDBUtil.queryForAColumn(checkUserQuery, "id");
    //     if (ids.isEmpty()) {
    //         HashMap<String, Object> hasil = new HashMap<>();
    //         hasil.put("status", "role tidak ditemukan");
    //         return hasil;
    //     }

    //     String sqlCommand = "UPDATE auth_role SET allowedPermissions='" + allowedPermissions +
    //         "' WHERE id='" + id + ";";

    //     vmjDBUtil.hitDatabase(sqlCommand);
    //     HashMap<String, Object> hasil = new HashMap<>();
    //     hasil.put("status", "sukses mengganti permission dari role");
    //     return hasil;
    // }
}
