

import java.util.*;

public class PasswordManager {
    private Map<String, String> credentials;

    public PasswordManager() {
        credentials = new HashMap<>();
    }

    public void addAccount(String account, String password) {
        credentials.put(account, password);
        System.out.println("Account added: " + account);
    }

    public String getPassword(String account) {
        return credentials.get(account);
    }

    public void showAllAccounts() {
        if (credentials.isEmpty()) {
            System.out.println("No accounts stored yet.");
        } else {
            System.out.println("\nStored Accounts:");
            for (String acc : credentials.keySet()) {
                System.out.println("- " + acc);
            }
        }
    }

    public Map<String, String> getAllCredentials() {
        return credentials;
    }
}
