package prices.auth.vmj.model.core;

import java.util.Random;

public abstract class UserDecorator extends UserComponent {

    protected UserComponent user;

    public UserDecorator(UserComponent user) {
        super();
        this.user = user;
        Random r = new Random();
        this.id = r.nextInt();
    }

    public UserDecorator() {
        super();
        this.user = new UserImpl();
        Random r = new Random();
        this.id = r.nextInt();
    }

    @Override
    public void setName(String name) {
        this.user.setName(name);
    };

    @Override
    public String getName() {
        return this.user.getName();
    };

    @Override
    public void setEmail(String email) {
        this.user.setEmail(email);
    };

    @Override
    public String getEmail() {
        return this.user.getEmail();
    };

    @Override
    public void setAllowedPermissions(String allowedPermissions) {
        this.user.setAllowedPermissions(allowedPermissions);
    };

    @Override
    public String getAllowedPermissions() {
        return this.user.getAllowedPermissions();
    };

    @Override
    public void setPassword(String password) {
        
    };

    @Override
    public String getPassword() {
        return "";
    };

    // @Override
    // public boolean checkCurrentUser(VMJExchange vmjExchange) {
    //     return user.checkCurrentUser(vmjExchange);
    // };

    // @Override
    // @Route(url = "auth/user/change-permissions")
    // @Restricted(permissionName = "UpdatePermissions")
    // public HashMap<String, Object> changePermissions(VMJExchange vmjExchange) {
    //     return user.changePermissions(vmjExchange);
    // };
}
