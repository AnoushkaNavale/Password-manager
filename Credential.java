import java.time.Instant;
import java.util.UUID;

/**
 * Simple POJO representing a credential entry stored by the Password Manager.
 *
 * This class is designed to be serialized/deserialized to/from JSON (via Gson/Jackson)
 * and to carry the encrypted password + metadata required for safe storage.
 */
public class Credential {
    private String id;                 // unique id (UUID)
    private String account;            // example: "Gmail"
    private String username;           // example: "me@gmail.com"
    private String encryptedPassword;  // AES-encrypted password (Base64)
    private String iv;                 // AES IV used for this entry (Base64)
    private String category;           // optional: "Social", "Banking", etc.
    private String notes;              // optional notes
    private long createdAt;            // epoch millis
    private long updatedAt;            // epoch millis

    public Credential() {
        this.id = UUID.randomUUID().toString();
        long now = Instant.now().toEpochMilli();
        this.createdAt = now;
        this.updatedAt = now;
    }

    public Credential(String account, String username, String encryptedPassword, String iv, String category, String notes) {
        this();
        this.account = account;
        this.username = username;
        this.encryptedPassword = encryptedPassword;
        this.iv = iv;
        this.category = category;
        this.notes = notes;
    }

    // Getters and setters

    public String getId() {
        return id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
        touch();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        touch();
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
        touch();
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
        touch();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
        touch();
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
        touch();
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    private void touch() {
        this.updatedAt = Instant.now().toEpochMilli();
    }

    @Override
    public String toString() {
        return "Credential{" +
                "id='" + id + '\'' +
                ", account='" + account + '\'' +
                ", username='" + username + '\'' +
                ", category='" + category + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
