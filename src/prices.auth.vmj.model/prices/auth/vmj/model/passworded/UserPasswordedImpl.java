package prices.auth.vmj.model.passworded;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import prices.auth.vmj.annotations.CRUDRestrictions;
import prices.auth.vmj.annotations.Restricted;
import prices.auth.vmj.annotations.RestrictCRUD;
import prices.auth.vmj.enums.CRUDMethod;
import prices.auth.vmj.model.core.UserComponent;
import prices.auth.vmj.model.core.UserDecorator;
import prices.auth.vmj.model.utils.PasswordUtils;
import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import java.util.*;

@Entity
@Table(name="auth_user_passworded")
public class UserPasswordedImpl extends UserDecorator {

    @Column
    public String password;

    @Column
    public String name;

    @Column
    public String email;

    @Column
    public String allowedPermissions;

    public UserPasswordedImpl() {
        super();
        Random r = new Random();
        this.id = r.nextInt();
    }

    public UserPasswordedImpl(String name, String email, String password) {
        super();
        Random r = new Random();
        this.id = r.nextInt();
        this.name = name;
        this.email = email;
        this.password = password;
    }
    
    public UserPasswordedImpl(UserComponent user) {
        super(user);
        Random r = new Random();
        this.id = r.nextInt();
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    // @Route(url = "auth/changePassword")
    // @Restricted(permissionName = "UpdateUserImpl", customPermissionMethod = "checkCurrentUser")
    // public HashMap<String, Object> changePassword(VMJExchange vmjExchange) {
    //     Object email = vmjExchange.getPOSTBodyForm("email");
    //     Object oldPasswordRaw = vmjExchange.getPOSTBodyForm("oldPassword");
    //     String oldPassword = PasswordUtils.hashPassword((String) oldPasswordRaw);
    //     Object newPasswordRaw = vmjExchange.getPOSTBodyForm("newPassword");
    //     String newPassword = PasswordUtils.hashPassword((String) newPasswordRaw);

    //     String checkUserQuery = "SELECT id FROM auth_user WHERE email='" + email + "' AND " +
    //         "password='" + oldPassword + "';";
    //     ArrayList<Object> ids = vmjDBUtil.queryForAColumn(checkUserQuery, "id");
    //     if (ids.isEmpty()) {
    //         HashMap<String, Object> hasil = new HashMap<>();
    //         hasil.put("status", "email dan password lama tidak cocok");
    //         return hasil;
    //     }

    //     String sqlCommand = "UPDATE auth_user SET password='" + newPassword +
    //         "' WHERE email='" + email + "' AND password='" + oldPassword + "';";

    //     vmjDBUtil.hitDatabase(sqlCommand);
    //     HashMap<String, Object> hasil = new HashMap<>();
    //     hasil.put("status", "sukses mengganti password");
    //     return hasil;
    // }

    // @Route(url = "auth/forgotPasswordToken")
    // public HashMap<String, Object> getForgotPasswordToken(VMJExchange vmjExchange) {
    //     Object email = vmjExchange.getPOSTBodyForm("email");

    //     String sqlQuery = "SELECT email FROM auth_user WHERE email='" + email + "';";
    //     ArrayList<Object> emails = vmjDBUtil.queryForAColumn(sqlQuery, "email");
    //     HashMap<String, Object> hasil = new HashMap<>();
    //     if (emails.isEmpty()) {
    //         hasil.put("status", "email tidak ditemukan");
    //         return hasil;
    //     }

    //     // TODO: buat mekanisme email; tidak seharusnya token langsung dilempar keluar
    //     hasil.put("token", PasswordUtils.generateForgotToken((String) email));
    //     return hasil;
    // }

    // @Route(url = "auth/forgotPassword")
    // public HashMap<String, Object> forgotPassword(VMJExchange vmjExchange) {
    //     Object token = vmjExchange.getPOSTBodyForm("token");
    //     String email = PasswordUtils.getEmailFromForgotToken((String) token);
    //     Object newPasswordRaw = vmjExchange.getPOSTBodyForm("password");
    //     String newPassword = PasswordUtils.hashPassword((String) newPasswordRaw);

    //     String checkUserQuery = "SELECT id FROM auth_user WHERE email='" + email + "';";
    //     ArrayList<Object> ids = vmjDBUtil.queryForAColumn(checkUserQuery, "id");
    //     if (ids.isEmpty()) {
    //         HashMap<String, Object> hasil = new HashMap<>();
    //         hasil.put("status", "token tidak valid");
    //         return hasil;
    //     }

    //     String sqlCommand = "UPDATE auth_user SET password='" + newPassword +
    //         "' WHERE email='" + email + "';";
    //     vmjDBUtil.hitDatabase(sqlCommand);

    //     HashMap<String, Object> hasil = new HashMap<>();
    //     hasil.put("status", "sukses mengganti password");
    //     return hasil;
    // }

    // @Route(url = "auth/login")
    // public HashMap<String, Object> login(VMJExchange vmjExchange) {
    //     Object email = vmjExchange.getPOSTBodyForm("email");
    //     Object passwordRaw = vmjExchange.getPOSTBodyForm("password");
    //     String password = PasswordUtils.hashPassword((String) passwordRaw);

    //     String checkUserQuery = "SELECT * FROM auth_user " +
    //         "WHERE email='" + email + "' AND password='" + password + "';";
    //     ArrayList<String> columns = new ArrayList<>();
    //     Collections.addAll(columns, "id", "name", "email", "allowedPermissions");
    //     List<HashMap<String, Object>> users = vmjDBUtil.hitDatabaseForQueryATable(checkUserQuery, columns);
    //     if (users.isEmpty()) {
    //         HashMap<String, Object> hasil = new HashMap<>();
    //         hasil.put("status", "email dan password tidak cocok");
    //         return hasil;
    //     }

    //     HashMap<String, Object> hasil = new HashMap<>();
    //     hasil.put("token", PasswordUtils.generateAuthToken(users.get(0).get("id").toString(), (String) email));
    //     hasil.put("user", users.get(0));
    //     return hasil;
    // }

    // @Route(url = "auth/register")
    // public HashMap<String, Object> register(VMJExchange vmjExchange) {
    //     Object name = vmjExchange.getPOSTBodyForm("name");
    //     Object email = vmjExchange.getPOSTBodyForm("email");
    //     Object passwordRaw = vmjExchange.getPOSTBodyForm("password");
    //     String password = PasswordUtils.hashPassword((String) passwordRaw);

    //     String sqlQuery = "SELECT email FROM auth_user WHERE email='" + email + "';";
    //     ArrayList<Object> emails = vmjDBUtil.queryForAColumn(sqlQuery, "email");
    //     if (!emails.isEmpty()) {
    //         HashMap<String, Object> hasil = new HashMap<>();
    //         hasil.put("status", "email sudah pernah didaftarkan");
    //         return hasil;
    //     }

    //     ArrayList<String> columns = new ArrayList<>();
    //     HashMap<String, Object> insertData = new HashMap<>();
    //     insertData.put("name", name);
    //     insertData.put("email", email);
    //     insertData.put("allowedPermissions", "");
    //     insertData.put("password", password);
    //     int idUser = vmjDBUtil.insertDataToATable("auth_user", insertData);

    //     HashMap<String, Object> hasil = new HashMap<>();
    //     if (idUser > 0) {
    //         hasil.put("status", "akun berhasil didaftarkan");
    //         return hasil;
    //     }
    //     hasil.put("status", "akun gagal didaftarkan");
    //     return hasil;
    // }
}
