package prices.auth.vmj.model.passworded;

import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;

import prices.auth.vmj.annotations.Restricted;
import prices.auth.vmj.model.core.*;
import prices.auth.vmj.model.*;
import prices.auth.vmj.model.utils.PasswordUtils;

import java.util.*;

public class UserPasswordedControllerDecorator extends UserControllerDecorator {
    
    public UserPasswordedControllerDecorator(UserControllerComponent user) {
        super(user);
    }

    // public boolean checkCurrentUser(VMJExchange vmjExchange) {
    //     String uriPath = vmjExchange.getHttpExchange().getRequestURI().getPath();
    //     String[] uriParts = Arrays.copyOfRange(uriPath.split("/"), 1, uriPath.split("/").length);

    //     try {
    //         if (uriParts.length == 3) {
    //             User user = userDao.getObject(Integer.parseInt(uriParts[2]));
    //             if (user.getEmail().equals(vmjExchange.getAuthPayload().getEmail())) {
    //                 return true;
    //             }

    //         } else if (vmjExchange.getPOSTBodyForm("email") != null) {
    //             List<User> userList = userDao.<String>getListObject("UserImpl", "email", vmjExchange.getAuthPayload().getEmail());
    //             if (userList.contains(vmjExchange.getPOSTBodyForm("email"))) {
    //                 return true;
    //             }
    //         }
    //     } catch (NullPointerException e) {}
        
    //     return false;    
    // }

    // @Route(url = "auth/user/change-permissions")
    // @Restricted(permissionName = "UpdatePermissions")
    // public HashMap<String, Object> changePermissions(VMJExchange vmjExchange) {
    //     Object id = vmjExchange.getPOSTBodyForm("id");
    //     Object allowedPermissions = vmjExchange.getPOSTBodyForm("allowedPermissions");

    //     User user = userDao.getObject((Integer) id);
    //     user.setAllowedPermissions((String) allowedPermissions);

    //     try {
    //         return user.toHashMap();
    //     } catch (NullPointerException e) {
    //         HashMap<String, Object> blank = new HashMap<>();
    //         blank.put("error", "User not found");
    //         return blank;
    //     }
    // }

    @Route(url = "auth/changePassword")
    @Restricted(permissionName = "UpdateUserImpl", customPermissionMethod = "checkCurrentUser")
    public HashMap<String, Object> changePassword(VMJExchange vmjExchange) {
        Object email = vmjExchange.getPOSTBodyForm("email");
        Object oldPasswordRaw = vmjExchange.getPOSTBodyForm("oldPassword");
        String oldPassword = PasswordUtils.hashPassword((String) oldPasswordRaw);
        Object newPasswordRaw = vmjExchange.getPOSTBodyForm("newPassword");
        String newPassword = PasswordUtils.hashPassword((String) newPasswordRaw);

        List<User> users = userDao.<Object>getListObject("UserPasswordedImpl", "email", email);
        if (users.isEmpty()) {
            HashMap<String, Object> hasil = new HashMap<>();
            hasil.put("status", "email dan password lama tidak cocok");
            return hasil;
        }

        User user = users.get(0);
        if (!user.getPassword().equals(oldPassword)) {
            HashMap<String, Object> hasil = new HashMap<>();
            hasil.put("status", "email dan password lama tidak cocok");
            return hasil;
        }

        user.setPassword(newPassword);
        userDao.saveObject(user);

        // String checkUserQuery = "SELECT id FROM auth_user WHERE email='" + email + "' AND " +
        //     "password='" + oldPassword + "';";
        // ArrayList<Object> ids = vmjDBUtil.queryForAColumn(checkUserQuery, "id");
        // if (ids.isEmpty()) {
        //     HashMap<String, Object> hasil = new HashMap<>();
        //     hasil.put("status", "email dan password lama tidak cocok");
        //     return hasil;
        // }

        // String sqlCommand = "UPDATE auth_user SET password='" + newPassword +
        //     "' WHERE email='" + email + "' AND password='" + oldPassword + "';";

        // vmjDBUtil.hitDatabase(sqlCommand);
        HashMap<String, Object> hasil = new HashMap<>();
        hasil.put("status", "sukses mengganti password");
        return hasil;
    }

    @Route(url = "auth/forgotPasswordToken")
    public HashMap<String, Object> getForgotPasswordToken(VMJExchange vmjExchange) {
        Object email = vmjExchange.getPOSTBodyForm("email");

        List<User> users = userDao.<Object>getListObject("UserPasswordedImpl", "email", email);

        // String sqlQuery = "SELECT email FROM auth_user WHERE email='" + email + "';";
        // ArrayList<Object> emails = vmjDBUtil.queryForAColumn(sqlQuery, "email");
        HashMap<String, Object> hasil = new HashMap<>();
        if (users.isEmpty()) {
            hasil.put("status", "email tidak ditemukan");
            return hasil;
        }

        // TODO: buat mekanisme email; tidak seharusnya token langsung dilempar keluar
        hasil.put("token", PasswordUtils.generateForgotToken((String) email));
        return hasil;
    }

    @Route(url = "auth/forgotPassword")
    public HashMap<String, Object> forgotPassword(VMJExchange vmjExchange) {
        Object token = vmjExchange.getPOSTBodyForm("token");
        String email = PasswordUtils.getEmailFromForgotToken((String) token);
        Object newPasswordRaw = vmjExchange.getPOSTBodyForm("password");
        String newPassword = PasswordUtils.hashPassword((String) newPasswordRaw);

        List<User> users = userDao.<Object>getListObject("UserPasswordedImpl", "email", email);
        if (users.isEmpty()) {
            HashMap<String, Object> hasil = new HashMap<>();
            hasil.put("status", "token tidak valid");
            return hasil;
        }

        // String checkUserQuery = "SELECT id FROM auth_user WHERE email='" + email + "';";
        
        User user = users.get(0);
        user.setPassword(newPassword);
        userDao.updateObject(user);
        // String sqlCommand = "UPDATE auth_user SET password='" + newPassword +
        //     "' WHERE email='" + email + "';";
        // vmjDBUtil.hitDatabase(sqlCommand);

        HashMap<String, Object> hasil = new HashMap<>();
        hasil.put("status", "sukses mengganti password");
        return hasil;
    }

    @Route(url = "auth/login")
    public HashMap<String, Object> login(VMJExchange vmjExchange) {
        Object email = vmjExchange.getPOSTBodyForm("email");
        Object passwordRaw = vmjExchange.getPOSTBodyForm("password");
        String password = PasswordUtils.hashPassword((String) passwordRaw);

        // ArrayList<String> columns = new ArrayList<>();

        // String checkUserQuery = "SELECT * FROM auth_user " +
        //     "WHERE email='" + email + "' AND password='" + password + "';";
        // ArrayList<String> columns = new ArrayList<>();
        // Collections.addAll(columns, "id", "name", "email", "allowedPermissions");
        // List<HashMap<String, Object>> users = vmjDBUtil.hitDatabaseForQueryATable(checkUserQuery, columns);
        
        List<User> users = userDao.<Object>getListObject("UserPasswordedImpl", "email", email);
        if (users.isEmpty()) {
            HashMap<String, Object> hasil = new HashMap<>();
            hasil.put("status", "email dan password tidak cocok");
            return hasil;
        }

        User user = users.get(0);
        if (!user.getPassword().equals(password)) {
            HashMap<String, Object> hasil = new HashMap<>();
            hasil.put("status", "email dan password tidak cocok");
            return hasil;
        }

        HashMap<String, Object> hasil = new HashMap<>();
        hasil.put("token", PasswordUtils.generateAuthToken(Integer.toString(user.getId()), (String) email));
        hasil.put("name", user.getName());
        hasil.put("email", email);
        return hasil;
    }

    @Route(url = "auth/register")
    public HashMap<String, Object> register(VMJExchange vmjExchange) {
        Object name = vmjExchange.getPOSTBodyForm("name");
        Object email = vmjExchange.getPOSTBodyForm("email");
        Object passwordRaw = vmjExchange.getPOSTBodyForm("password");
        String password = PasswordUtils.hashPassword((String) passwordRaw);

        User user = UserFactory.createUser("prices.auth.vmj.model.passworded.UserPasswordedImpl", (String) name, (String) email, password);
        System.out.println(name);
        HashMap<String, Object> hasil = new HashMap<>();
        try {
            userDao.saveObject(user);
            hasil.put("status", "akun berhasil didaftarkan");
            return hasil;
        } catch (Exception e) {
            hasil.put("status", "akun gagal didaftarkan");
            return hasil;
        }
        // String sqlQuery = "SELECT email FROM auth_user WHERE email='" + email + "';";
        // ArrayList<Object> emails = vmjDBUtil.queryForAColumn(sqlQuery, "email");
        // if (!emails.isEmpty()) {
        //     HashMap<String, Object> hasil = new HashMap<>();
        //     hasil.put("status", "email sudah pernah didaftarkan");
        //     return hasil;
        // }

        // ArrayList<String> columns = new ArrayList<>();
        // HashMap<String, Object> insertData = new HashMap<>();
        // insertData.put("name", name);
        // insertData.put("email", email);
        // insertData.put("allowedPermissions", "");
        // insertData.put("password", password);
        // int idUser = vmjDBUtil.insertDataToATable("auth_user", insertData);

    }

}
