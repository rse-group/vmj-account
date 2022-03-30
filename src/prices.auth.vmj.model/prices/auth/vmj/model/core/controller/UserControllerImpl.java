package prices.auth.vmj.model.core;

import java.util.*;

import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;

import prices.auth.vmj.model.UserFactory;

import prices.auth.vmj.annotations.Restricted;

public class UserControllerImpl extends UserControllerComponent {
    
    public UserControllerImpl() {}
    
    public boolean checkCurrentUser(VMJExchange vmjExchange) {
        String uriPath = vmjExchange.getHttpExchange().getRequestURI().getPath();
        String[] uriParts = Arrays.copyOfRange(uriPath.split("/"), 1, uriPath.split("/").length);

        try {
            if (uriParts.length == 3) {
                User user = userDao.getObject(Integer.parseInt(uriParts[2]));
                if (user.getEmail().equals(vmjExchange.getAuthPayload().getEmail())) {
                    return true;
                }

                // String sqlQuery = "SELECT email FROM auth_user " + "WHERE id=" + uriParts[2] +
                //                   " AND email='" + vmjExchange.getAuthPayload().getEmail() + "';";
                // ArrayList<Object> emails = vmjDBUtil.queryForAColumn(sqlQuery, "email");
                // return emails.size() > 0;
            } else if (vmjExchange.getPOSTBodyForm("email") != null) {
                List<User> userList = userDao.<String>getListObject("UserImpl", "email", vmjExchange.getAuthPayload().getEmail());
                if (userList.contains(vmjExchange.getPOSTBodyForm("email"))) {
                    return true;
                }

                // String sqlQuery = "SELECT email FROM auth_user" +
                //                   " WHERE email='" + vmjExchange.getPOSTBodyForm("email") + "'" +
                //                   " AND email='" + vmjExchange.getAuthPayload().getEmail() + "';";
                // ArrayList<Object> emails = vmjDBUtil.queryForAColumn(sqlQuery, "email");
                // return emails.size() > 0;
            }
        } catch (NullPointerException e) {}
        
        return false;    
    }

    @Route(url = "auth/user/change-permissions")
    @Restricted(permissionName = "UpdatePermissions")
    public HashMap<String, Object> changePermissions(VMJExchange vmjExchange) {
        Object id = vmjExchange.getPOSTBodyForm("id");
        Object allowedPermissions = vmjExchange.getPOSTBodyForm("allowedPermissions");

        User user = userDao.getObject((Integer) id);
        user.setAllowedPermissions((String) allowedPermissions);

        // String checkUserQuery = "SELECT email FROM auth_user WHERE id='" + id + "';";
        // ArrayList<Object> emails = vmjDBUtil.queryForAColumn(checkUserQuery, "email");
        // if (emails.isEmpty()) {
        //     HashMap<String, Object> hasil = new HashMap<>();
        //     hasil.put("status", "akun tidak ditemukan");
        //     return hasil;
        // }

        // String sqlCommand = "UPDATE auth_user SET allowedPermissions='" + allowedPermissions +
        //     "' WHERE id='" + id + ";";

        // vmjDBUtil.hitDatabase(sqlCommand);
        // HashMap<String, Object> hasil = new HashMap<>();
        // hasil.put("status", "sukses mengganti permission dari akun");
        // return hasil;

        try {
            return user.toHashMap();
        } catch (NullPointerException e) {
            HashMap<String, Object> blank = new HashMap<>();
            blank.put("error", "User not found");
            return blank;
        }
    }
}
