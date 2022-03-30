package prices.auth.core.exceptions;

public class NotPermittedException extends AuthException {
    public NotPermittedException() {
        super("You are not permitted.");
    }

    public NotPermittedException(String permissionName) {
        super("You are not granted permission: " + permissionName);
    }
}
