package prices.auth.vmj.model.core;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import prices.auth.vmj.annotations.CRUDRestrictions;
import prices.auth.vmj.enums.CRUDMethod;
import prices.auth.vmj.annotations.RestrictCRUD;
import vmj.routing.route.VMJExchange;
import vmj.object.mapper.VMJDatabaseField;
import vmj.object.mapper.VMJDatabaseTable;
import vmj.object.mapper.VMJDatabaseUtil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="auth_role_comp")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class RoleComponent implements Role {

    @Id
    public int id;

    @OneToMany(mappedBy="role")
    public Set<UserRoleImpl> userRoles;

    @Override
    public void setId(int id) {
        this.id = id;
    };

    @Override
    public int getId() {
        return this.id;
    };

    // @Column
    // public abstract HashMap<String, Object> changePermissions(VMJExchange vmjExchange);
}
