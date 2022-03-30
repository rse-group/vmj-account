package prices.auth.core.exceptions;

public class TokenEmptyException extends AuthException {
    public TokenEmptyException() {
        super("Token is empty.");
    }
}
