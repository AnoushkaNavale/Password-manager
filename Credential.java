package app;

import java.time.LocalDateTime;

public class Credential {
    private String account;
    private String username;
    private String encryptedPassword; // base64(iv + ciphertext + tag)
    private String encryptedNotes;    // base64(iv + ciphertext + tag)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    
    // Required by Gson
    public Credential() {}

    public Credential(String account, String username, String encryptedPassword, String encryptedNotes) {
        this.account = account;
        this.username = username;
        this.encryptedPassword = encryptedPassword;
        this.encryptedNotes = encryptedNotes;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public String getAccount() { return account; }
    public String getUsername() { return username; }
    public String getEncryptedPassword() { return encryptedPassword; }
    public String getEncryptedNotes() { return encryptedNotes; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setEncryptedPassword(String encryptedPassword) { this.encryptedPassword = encryptedPassword; touch(); }
    public void setEncryptedNotes(String encryptedNotes) { this.encryptedNotes = encryptedNotes; touch(); }
    public void touch() { this.updatedAt = LocalDateTime.now(); }
}
