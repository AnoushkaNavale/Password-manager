import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        PasswordManager pm = new PasswordManager();

        // Load existing data
        pm.getAllCredentials().putAll(FileHandler.loadFromFile());

        while (true) {
            System.out.println("\n==== Password Manager ====");
            System.out.println("1. Add new account");
            System.out.println("2. View password");
            System.out.println("3. Check password strength");
            System.out.println("4. Generate strong password");
            System.out.println("5. View all accounts");
            System.out.println("6. Save and Exit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
            sc.nextLine(); // clear buffer

            switch (choice) {
                case 1:
                    System.out.print("Enter account name: ");
                    String account = sc.nextLine();
                    System.out.print("Enter password: ");
                    String password = sc.nextLine();
                    pm.addAccount(account, password);
                    break;

                case 2:
                    System.out.print("Enter account name: ");
                    String accName = sc.nextLine();
                    String pass = pm.getPassword(accName);
                    if (pass != null)
                        System.out.println("Password: " + pass);
                    else
                        System.out.println("Account not found.");
                    break;

                case 3:
                    System.out.print("Enter password: ");
                    String pwd = sc.nextLine();
                    int score = PasswordStrengthChecker.checkStrength(pwd);
                    System.out.println("Strength: " + PasswordStrengthChecker.getStrengthLabel(score));
                    break;

                case 4:
                    System.out.print("Enter desired password length: ");
                    int len = sc.nextInt();
                    sc.nextLine();
                    String generated = Utils.generateRandomPassword(len);
                    System.out.println("Generated Password: " + generated);
                    System.out.println("Strength: " + PasswordStrengthChecker.getStrengthLabel(
                        PasswordStrengthChecker.checkStrength(generated)
                    ));
                    break;

                case 5:
                    pm.showAllAccounts();
                    break;

                case 6:
                    FileHandler.saveToFile(pm.getAllCredentials());
                    System.out.println("Exiting...");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}
