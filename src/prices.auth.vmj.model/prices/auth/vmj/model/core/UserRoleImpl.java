package prices.auth.vmj.model.core;

// import prices.auth.vmj.enums.CRUDMethod;
// import prices.auth.vmj.annotations.CRUDRestrictions;
// import prices.auth.vmj.annotations.RestrictCRUD;
// import vmj.object.mapper.VMJDatabaseField;
// import vmj.object.mapper.VMJDatabaseTable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

// @VMJDatabaseTable(tableName = "auth_user_role")
// @CRUDRestrictions(restrictions = {
//     @RestrictCRUD(permissionName = "ViewRoleImpl",
//         allowedMethods = {CRUDMethod.GET, CRUDMethod.LIST}),
//     @RestrictCRUD(permissionName = "UpdateRoleImpl",
//         allowedMethods = {CRUDMethod.POST, CRUDMethod.PUT, CRUDMethod.DELETE})
// })
@Entity
@Table(name="auth_user_role")
public class UserRoleImpl {
    // @VMJDatabaseField(primaryKey=true)
    @Id
    public int id;

    // @VMJDatabaseField(foreignTableName = "auth_role", foreignColumnName = "id")
    @ManyToOne
    @JoinColumn(name = "authRole", referencedColumnName = "id")
    public RoleComponent role;

    // @VMJDatabaseField(foreignTableName = "auth_user", foreignColumnName = "id")
    @OneToOne
    @JoinColumn(name = "authUser", referencedColumnName = "id")
    public UserComponent user;

    public UserRoleImpl() {}
}
