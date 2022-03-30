package prices.auth.core.exceptions;

public class AuthException extends Exception {
    public AuthException() {
        super("Authorization Error: unknown.");
    }

    public AuthException(String message) {
        super("Authorization Error: " + message);
    }
}
