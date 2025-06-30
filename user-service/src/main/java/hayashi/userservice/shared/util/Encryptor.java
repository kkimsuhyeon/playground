package hayashi.userservice.shared.util;

import hayashi.userservice.shared.exception.exceptions.ServerErrorException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class Encryptor {

    private static final String KEY = "123456789hayashi";
    private static final String ALGORITHM = "AES";

    public static String encrypt(String value) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            return Base64.getEncoder().encodeToString(cipher.doFinal(value.getBytes()));
        } catch (Exception e) {
            throw new ServerErrorException("[Encryptor] 암호화 에러: " + e);
        }
    }

    public static String decrypt(String value) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            return new String(cipher.doFinal(Base64.getDecoder().decode(value)));
        } catch (Exception e) {
            throw new ServerErrorException("[Encryptor] 복호화 에러: " + e);
        }
    }
}
