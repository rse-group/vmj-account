package prices.auth.vmj.model.core;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;

import prices.auth.vmj.annotations.CRUDRestrictions;
import prices.auth.vmj.enums.CRUDMethod;
import prices.auth.vmj.annotations.RestrictCRUD;
import vmj.routing.route.VMJExchange;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="auth_user_comp")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class UserComponent implements User {

    // protected UUID id;
    @Id
    public int id;

    @OneToOne(mappedBy = "user")
    public UserRoleImpl userRoleImpl;

    // public UUID getId() {
    public int getId() {
        return this.id;
    }
    // public void setId(UUID id) {
    public void setId(int id) {
        this.id = id;
    }

    // public abstract boolean checkCurrentUser(VMJExchange vmjExchange);

    // public abstract HashMap<String, Object> changePermissions(VMJExchange vmjExchange);

    public abstract void setName(String name);
    public abstract String getName();

    public abstract void setEmail(String email);
    public abstract String getEmail();

    public abstract void setAllowedPermissions(String allowedPermissions);
    public abstract String getAllowedPermissions();

    public abstract void setPassword(String password);
    public abstract String getPassword();

    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> userMap = new HashMap<String,Object>();
        userMap.put("id", getId());
        userMap.put("name", getName());
        userMap.put("email", getEmail());
        userMap.put("allowedPermissions", getAllowedPermissions());
        return userMap;
    }

}
