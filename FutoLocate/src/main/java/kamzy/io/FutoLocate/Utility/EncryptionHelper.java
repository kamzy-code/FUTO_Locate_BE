package kamzy.io.FutoLocate.Utility;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Component
public class EncryptionHelper {

    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private static final String ALGORITHM = "AES";
    private static final String KEY = "YourSecretKey123";  // Use a securely generated key

    // Method to hash a password
    public String hashPassword(String plainPassword) {
        return passwordEncoder.encode(plainPassword);
    }

    // Method to verify a password
    public boolean verifyPassword(String plainPassword, String hashedPassword) {
        return passwordEncoder.matches(plainPassword, hashedPassword);
    }



    // Encrypt data
    public static String encryptData(String data) throws Exception {
        SecretKeySpec key = new SecretKeySpec(KEY.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encrypted = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }

    // Decrypt data
    public static String decryptData(String encryptedData) throws Exception {
        SecretKeySpec key = new SecretKeySpec(KEY.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] original = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        return new String(original);
    }
}
