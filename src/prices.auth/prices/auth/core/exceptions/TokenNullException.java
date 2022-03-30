package prices.auth.core.exceptions;

public class TokenNullException extends AuthException {
    public TokenNullException() {
        super("Token is not provided.");
    }
}
