package prices.auth.vmj.model.core;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;

import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import java.util.Random;

@Entity
@Table(name="auth_user_impl")
public class UserImpl extends UserComponent {

    @Column
    public String name;

    @Column
    public String email;

    @Column
    public String allowedPermissions;

    public UserImpl(String name, String email, String allowedPermissions) {
        Random r = new Random();
        this.id = r.nextInt();
        this.name = name;
        this.email = email;
        this.allowedPermissions = allowedPermissions;
    }

    public UserImpl(String name, String email) {
        Random r = new Random();
        this.id = r.nextInt();
        this.name = name;
        this.email = email;
        this.allowedPermissions = "";
    }

    public UserImpl() {
        Random r = new Random();
        this.id = r.nextInt();
        this.name = "";
        this.email = "";
        this.allowedPermissions = "";
    }

    // @Override
    // public boolean checkCurrentUser(VMJExchange vmjExchange) {
    //     String uriPath = vmjExchange.getHttpExchange().getRequestURI().getPath();
    //     String[] uriParts = Arrays.copyOfRange(uriPath.split("/"), 1, uriPath.split("/").length);

    //     if (uriParts.length == 3) {
    //         String sqlQuery = "SELECT email FROM auth_user " + "WHERE id=" + uriParts[2] +
    //                           " AND email='" + vmjExchange.getAuthPayload().getEmail() + "';";
    //         ArrayList<Object> emails = vmjDBUtil.queryForAColumn(sqlQuery, "email");
    //         return emails.size() > 0;
    //     } else if (vmjExchange.getPOSTBodyForm("email") != null) {
    //         String sqlQuery = "SELECT email FROM auth_user" +
    //                           " WHERE email='" + vmjExchange.getPOSTBodyForm("email") + "'" +
    //                           " AND email='" + vmjExchange.getAuthPayload().getEmail() + "';";
    //         ArrayList<Object> emails = vmjDBUtil.queryForAColumn(sqlQuery, "email");
    //         return emails.size() > 0;
    //     }
    //     return false;
    // }

    // @Route(url = "auth/user/change-permissions")
    // @Restricted(permissionName = "UpdatePermissions")
    // public HashMap<String, Object> changePermissions(VMJExchange vmjExchange) {
    //     Object id = vmjExchange.getPOSTBodyForm("id");
    //     Object allowedPermissions = vmjExchange.getPOSTBodyForm("allowedPermissions");

    //     String checkUserQuery = "SELECT email FROM auth_user WHERE id='" + id + "';";
    //     ArrayList<Object> emails = vmjDBUtil.queryForAColumn(checkUserQuery, "email");
    //     if (emails.isEmpty()) {
    //         HashMap<String, Object> hasil = new HashMap<>();
    //         hasil.put("status", "akun tidak ditemukan");
    //         return hasil;
    //     }

    //     String sqlCommand = "UPDATE auth_user SET allowedPermissions='" + allowedPermissions +
    //         "' WHERE id='" + id + ";";

    //     vmjDBUtil.hitDatabase(sqlCommand);
    //     HashMap<String, Object> hasil = new HashMap<>();
    //     hasil.put("status", "sukses mengganti permission dari akun");
    //     return hasil;
    // }
    
    @Override
    public void setName(String name) {
        this.name = name;
    };

    @Override
    public String getName() {
        return this.name;
    };

    @Override
    public void setEmail(String email) {
        this.email = email;
    };

    @Override
    public String getEmail() {
        return this.email;
    };

    @Override
    public void setAllowedPermissions(String allowedPermissions) {
        this.allowedPermissions = allowedPermissions;
    };

    @Override
    public String getAllowedPermissions() {
        return this.allowedPermissions;
    };

    @Override
    public void setPassword(String password) {
        
    };

    @Override
    public String getPassword() {
        return "";
    };

}
