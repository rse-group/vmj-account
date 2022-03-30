package prices.auth.core.exceptions;

public class TokenInvalidException extends AuthException {
    public TokenInvalidException() {
        super("Token is invalid.");
    }

    public TokenInvalidException(String authType) {
        super("Token is invalid for authentication type: " + authType);
    }
}
