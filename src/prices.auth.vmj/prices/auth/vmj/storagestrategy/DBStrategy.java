package prices.auth.vmj.storagestrategy;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import prices.auth.core.AuthPayload;
import prices.auth.core.StorageStrategy;
import prices.auth.utils.PropertiesReader;
import vmj.object.mapper.VMJDatabaseMapper;
import vmj.object.mapper.VMJDatabaseUtil;

public class DBStrategy implements StorageStrategy {
    private PropertiesReader propReader = new PropertiesReader();
    private VMJDatabaseUtil vmjDbUtil = new VMJDatabaseUtil();

    private static final String USER_TABLE_NAME_PROP = "auth_db.user_table_name";
    private static final String ROLE_TABLE_NAME_PROP = "auth_db.role_table_name";
    private static final String USER_ROLE_TABLE_NAME_PROP = "auth_db.user_role_table_name";
    private static final String DEFAULT_USER_TABLE_NAME = "auth_user";
    private static final String DEFAULT_ROLE_TABLE_NAME = "auth_role";
    private static final String DEFAULT_USER_ROLE_TABLE_NAME = "auth_user_role";

    private String getUserTableName() {
        return propReader.getPropOrDefault(USER_TABLE_NAME_PROP, DEFAULT_USER_TABLE_NAME);
    }

    private String getRoleTableName() {
        return propReader.getPropOrDefault(ROLE_TABLE_NAME_PROP, DEFAULT_ROLE_TABLE_NAME);
    }

    private String getUserRoleTableName() {
        return propReader.getPropOrDefault(USER_ROLE_TABLE_NAME_PROP, DEFAULT_USER_ROLE_TABLE_NAME);
    }

    private List<String> splitPermissions(String permissions) {
        return Arrays.asList(permissions.split(","));
    }

    public Map<String, Object> getUserData(AuthPayload payload) {
        ArrayList<String> tableColumns = new ArrayList<>();
        tableColumns.addAll(Arrays.asList(new String[]{"id", "name", "email", "allowedPermissions"}));
        String userTableName = this.getUserTableName();

        String sql = "SELECT * FROM " + userTableName + " WHERE email='" + payload.getEmail() + "' LIMIT 1;";
        Map<String, Object> user = this.vmjDbUtil.hitDatabaseForQuery(sql, tableColumns);
        user.put("allowedPermissions", this.splitPermissions((String) user.get("allowedPermissions")));
        return user;
    }

    // getRoles will return roles owned by an user along with list of allowed permissions
    public Map<String, List<String>> getRoles(AuthPayload payload) {
        Map<String, Object> userData = this.getUserData(payload);
        String userTableName = this.getUserTableName();
        String roleTableName = this.getRoleTableName();
        String userRoleTableName = this.getUserRoleTableName();

        String roleSql = "SELECT role.name, role.allowedPermissions " +
            "FROM " + userTableName + " AS user, " + roleTableName + " AS role, " + userRoleTableName + " AS ur " +
            "WHERE user.email='" + payload.getEmail() + "' AND user.id = ur.user AND role.id = ur.role";
        ArrayList<String> roleColumns = new ArrayList<>();
        roleColumns.addAll(Arrays.asList(new String[]{"name", "allowedPermissions"}));
        List<HashMap<String, Object>> roles = this.vmjDbUtil.hitDatabaseForQueryATable(roleSql, roleColumns);

        Map<String, List<String>> result = new HashMap<>();
        for (Map<String, Object> role : roles) {
            if (((String) role.get("name")).equals("administrator")) {
                result.put((String) role.get("name"), Arrays.asList(new String[]{"administrator"}));
            }
            result.put((String) role.get("name"), this.splitPermissions((String) role.get("allowedPermissions")));
        }
        return result;
    }
}
