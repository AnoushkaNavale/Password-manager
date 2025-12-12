package app;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * AES-GCM encryption utilities + PBKDF2 key derivation.
 * - encrypt(SecretKeySpec, String) / decrypt(...)
 * - deriveKey(char[] masterPassword, byte[] salt)
 * - generateSalt()
 *
 * Notes:
 * - IV = 12 bytes (96 bits) recommended for GCM
 * - tag length 128 bits
 * - PBKDF2 with HmacSHA256 used for key derivation
 */
public class EncryptionUtils {
    private static final String KDF_ALGO = "PBKDF2WithHmacSHA256";
    private static final int KDF_ITER = 65536;
    private static final int KEY_LEN = 256; // bits
    private static final String CIPHER = "AES/GCM/NoPadding";
    private static final int IV_LENGTH = 12; // bytes
    private static final int TAG_LENGTH = 128; // bits

    public static SecretKeySpec deriveKey(char[] masterPassword, byte[] salt) throws Exception {
        PBEKeySpec spec = new PBEKeySpec(masterPassword, salt, KDF_ITER, KEY_LEN);
        SecretKeyFactory skf = SecretKeyFactory.getInstance(KDF_ALGO);
        byte[] key = skf.generateSecret(spec).getEncoded();
        return new SecretKeySpec(key, "AES");
    }

    // Encrypt plaintext with provided key. Output: base64(iv + ciphertext+tag)
    public static String encrypt(SecretKeySpec key, String plain) throws Exception {
        byte[] iv = new byte[IV_LENGTH];
        SecureRandom rnd = new SecureRandom();
        rnd.nextBytes(iv);

        Cipher cipher = Cipher.getInstance(CIPHER);
        GCMParameterSpec spec = new GCMParameterSpec(TAG_LENGTH, iv);
        cipher.init(Cipher.ENCRYPT_MODE, key, spec);
        byte[] ct = cipher.doFinal(plain.getBytes("UTF-8"));

        byte[] out = new byte[iv.length + ct.length];
        System.arraycopy(iv, 0, out, 0, iv.length);
        System.arraycopy(ct, 0, out, iv.length, ct.length);
        return Base64.getEncoder().encodeToString(out);
    }

    // Decrypt base64(iv + ciphertext+tag) with provided key
    public static String decrypt(SecretKeySpec key, String base64) throws Exception {
        byte[] all = Base64.getDecoder().decode(base64);
        byte[] iv = new byte[IV_LENGTH];
        System.arraycopy(all, 0, iv, 0, IV_LENGTH);
        byte[] ct = new byte[all.length - IV_LENGTH];
        System.arraycopy(all, IV_LENGTH, ct, 0, ct.length);

        Cipher cipher = Cipher.getInstance(CIPHER);
        GCMParameterSpec spec = new GCMParameterSpec(TAG_LENGTH, iv);
        cipher.init(Cipher.DECRYPT_MODE, key, spec);
        byte[] plain = cipher.doFinal(ct);
        return new String(plain, "UTF-8");
    }

    public static byte[] generateSalt() {
        byte[] s = new byte[16];
        new SecureRandom().nextBytes(s);
        return s;
    }
}
