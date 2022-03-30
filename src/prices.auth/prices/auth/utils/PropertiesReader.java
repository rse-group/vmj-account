package prices.auth.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Properties;
import prices.auth.core.Constants;

public class PropertiesReader {
    private String fileName = Constants.DEFAULT_AUTH_FILE;

    public PropertiesReader(String fileName) {
        this.fileName = fileName;
    }

    public PropertiesReader() {}

    private Properties loadProperties() {
        FileInputStream input = null;
        Properties prop = new Properties();

        try {
            input = new FileInputStream(this.fileName);
            // load a properties file
            prop.load(input);
        } catch (IOException ex) {
            System.out.println(ex);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {}
            }
        }
        return prop;
    }

    public String getProp(String propName) {
        Properties prop = this.loadProperties();
        return prop.getProperty(propName);
    }

    public String getPropOrDefault(String propName, String defaultValue) {
        String result = this.getProp(propName);
        return result != null ? result : defaultValue;
    }

    public String getEnvThenProp(String envName, String propName) {
        if (System.getenv(envName) != null) {
            return System.getenv(envName);
        }
        Properties prop = this.loadProperties();
        return prop.getProperty(propName);
    }

    public String getAuthType() {
        String result = this.getEnvThenProp(Constants.AUTH_TYPE_ENV, Constants.AUTH_TYPE_PROP);
        if (result != null) {
            return result.trim();
        }
        return "none";
    }

    public String getClientId() {
        return this.getEnvThenProp(Constants.CLIENT_ID_ENV, Constants.CLIENT_ID_PROP);
    }

    public String getClientSecret() {
        return this.getEnvThenProp(Constants.CLIENT_SECRET_ENV, Constants.CLIENT_SECRET_PROP);
    }

    public String getAuthStorageType() {
        String result = this.getEnvThenProp(Constants.AUTH_STORAGE_ENV, Constants.AUTH_STORAGE_PROP);
        return result != null ? result.trim().toLowerCase() : null;
    }

    public List<String> getAllowedEmail(String[] roles) {
        Properties prop = this.loadProperties();
        List<String> allowedEmail = new ArrayList<>();

        String administrator_emails = prop.getProperty(
            Constants.ROLE_MEMBERS_PREFIX_PROP + "." + Constants.ADMINISTRATOR_ROLE_NAME_PROP);
        for (String administrator_email : administrator_emails.split(",")) {
            allowedEmail.add(administrator_email.trim());
        }

        for (String role : roles) {
            String[] roleMembers = prop.getProperty(
                Constants.ROLE_MEMBERS_PREFIX_PROP + "." + roles).split(",");
            for (String email : roleMembers) {
                allowedEmail.add(email.trim());
            }
        }
        return allowedEmail;
    }

    public List<String> getRolePerms(String[] roles) {
        Properties prop = this.loadProperties();
        List<String> allowedPerms = new ArrayList<>();

        for (String role : roles) {
            if (role.equals(Constants.ADMINISTRATOR_ROLE_NAME_PROP)) {
                return Collections.singletonList(Constants.ADMINISTRATOR_PERMS_SIGN);
            }

            String perms = prop.getProperty(
                Constants.ROLE_PERMS_PREFIX_PROP + "." + role);
            if (perms != null) {
                for (String perm : perms.split(",")) {
                    allowedPerms.add(perm.trim());
                }
            }
        }
        return allowedPerms;
    }

    public List<String> getRolesFromEmail(String email) {
        Properties prop = this.loadProperties();
        List<String> roles = new ArrayList<>();
        String administrator_emails = prop.getProperty(
            Constants.ROLE_MEMBERS_PREFIX_PROP + "." + Constants.ADMINISTRATOR_ROLE_NAME_PROP);
        for (String administrator_email : administrator_emails.split(",")) {
            if (administrator_email.equals(email)) {
                roles.add(Constants.ADMINISTRATOR_ROLE_NAME_PROP);
                return roles;
            }
        }

        for (Object roleProp : prop.keySet()) {
            if (((String) roleProp).startsWith(Constants.ROLE_MEMBERS_PREFIX_PROP + ".")) {
                String[] roleMembers = prop.getProperty((String) roleProp).split(",");
                for (String roleMember : roleMembers) {
                    if (roleMember.trim().equals(email)) {
                        roles.add(((String) roleProp).replace(Constants.ROLE_MEMBERS_PREFIX_PROP + ".", "").trim());
                        break;
                    }
                }
            }
        }
        return roles;
    }
}
