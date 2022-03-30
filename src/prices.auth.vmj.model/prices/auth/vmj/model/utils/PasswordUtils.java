package prices.auth.vmj.model.utils;

import java.time.Instant;
import prices.auth.core.Constants;
import prices.auth.utils.PropertiesReader;
import prices.auth.utils.JWTUtils;
import prices.auth.utils.EncryptionUtils;

public class PasswordUtils {
    private static final String FORGOT_SEPARATOR = "---";

    public static String generateAuthToken(String userId, String email) {
        return JWTUtils.createJWT(userId, email, JWTUtils.getTTL());
    }

    public static String hashPassword(String password) {
    	return EncryptionUtils.hashSHA3512(password, getSalt());
    }

    public static String generateForgotToken(String email) {
        long exp = Instant.now().toEpochMilli() + JWTUtils.getTTL();
        return EncryptionUtils.encryptAES(email + FORGOT_SEPARATOR + Long.toString(exp),
            getSalt());
    }

    public static String getEmailFromForgotToken(String token) {
        String payload = EncryptionUtils.decryptAES(token, getSalt());
        String[] splitted = payload.split(FORGOT_SEPARATOR);
        if (splitted.length == 2 && Instant.now().toEpochMilli() < Long.parseLong(splitted[1])) {
            return splitted[0];
        }
        return "";
    }

    private static String getSalt() {
        return (new PropertiesReader()).getPropOrDefault(
            Constants.MANUAL_SECRET_KEY_PROP, Constants.MANUAL_SECRET_KEY_DEFAULT);
    }
}
