package prices.auth.core;

public class Constants {
    public static final String DEFAULT_AUTH_FILE = "auth.properties";

    public static final String AUTH_TYPE_PROP = "auth";
    public static final String AUTH_STORAGE_PROP = "auth_storage";
    public static final String CLIENT_ID_PROP = "client_id";
    public static final String CLIENT_SECRET_PROP = "client_secret";

    public static final String AUTH_TYPE_ENV = "AUTH_TYPE";
    public static final String AUTH_STORAGE_ENV = "AUTH_STROAGE";
    public static final String CLIENT_ID_ENV = "CLIENT_ID";
    public static final String CLIENT_SECRET_ENV = "CLIENT_SECRET";

    public static final String ROLE_MEMBERS_PREFIX_PROP = "role_members";
    public static final String ROLE_PERMS_PREFIX_PROP = "role_permissions";
    public static final String ADMINISTRATOR_ROLE_NAME_PROP = "administrator";
    public static final String ADMINISTRATOR_PERMS_SIGN = "administrator";

    public static final String MANUAL_SECRET_KEY_PROP = "jwt.secretkey";
    public static final String MANUAL_SECRET_KEY_DEFAULT = "pricesrselablabrseprices";
    public static final String MANUAL_JWT_ISSUER_PROP = "jwt.issuer";
    public static final String MANUAL_JWT_ISSUER_DEFAULT = "amanah.cs.ui.ac.id";
    public static final String MANUAL_JWT_TTL_PROP = "jwt.ttl";
    public static final String MANUAL_JWT_TTL_DEFAULT = "86400000";
}
