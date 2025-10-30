import java.io.*;
import java.util.*;

public class FileHandler {

    private static final String FILE_NAME = "passwords.txt";

    public static void saveToFile(Map<String, String> data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Map.Entry<String, String> entry : data.entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue());
                writer.newLine();
            }
            System.out.println("Passwords saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    public static Map<String, String> loadFromFile() {
        Map<String, String> data = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) data.put(parts[0], parts[1]);
            }
            System.out.println("Passwords loaded successfully.");
        } catch (IOException e) {
            System.out.println("No existing password file found.");
        }
        return data;
    }
}
