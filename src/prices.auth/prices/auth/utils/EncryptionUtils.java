package prices.auth.utils;

import org.apache.commons.codec.digest.DigestUtils;
import java.util.Base64;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionUtils {
    public static String hashSHA3512(String content, String salt) {
    	return DigestUtils.sha3_512Hex(salt + content);
    }

    public static String encryptAES(String valueToEnc, String encKey) {
        String ALGORITHM = "AES";
        try {
            Key key = new SecretKeySpec(encKey.getBytes(), ALGORITHM);
            Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.ENCRYPT_MODE, key);
            byte[] encValue = c.doFinal(valueToEnc.getBytes());
            return Base64.getEncoder().encodeToString(encValue);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return "";
        }
    }

    public static String decryptAES(String valueToDec, String encKey) {
        String ALGORITHM = "AES";
        try {
            Key key = new SecretKeySpec(encKey.getBytes(), ALGORITHM);
            Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.DECRYPT_MODE, key);
            byte[] valueToDecBytes = Base64.getDecoder().decode(valueToDec);
            byte[] decValue = c.doFinal(valueToDecBytes);
            return new String(decValue);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return "";
        }
    }
}
