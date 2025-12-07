import java.util.*;
import java.time.LocalDateTime;

public class JsonUtils {

    public static String toJson(Map<String, Credential> map) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");

        int i = 0;
        for (String key : map.keySet()) {
            Credential c = map.get(key);

            sb.append("\"").append(key).append("\": {");
            sb.append("\"encryptedPassword\":\"").append(c.getEncryptedPassword()).append("\",");
            sb.append("\"notes\":\"").append(c.getNotes()).append("\",");
            sb.append("\"createdAt\":\"").append(c.getCreatedAt()).append("\",");
            sb.append("\"updatedAt\":\"").append(c.getUpdatedAt()).append("\"");
            sb.append("}");

            if (i < map.size() - 1) sb.append(",");
            i++;
        }

        sb.append("}");
        return sb.toString();
    }


    public static Map<String, Credential> fromJson(String json) {
        Map<String, Credential> map = new HashMap<>();

        if (json == null || json.trim().isEmpty()) return map;

        json = json.trim().substring(1, json.length() - 1); // remove {}

        if (json.isEmpty()) return map;

        String[] entries = json.split("},");
        for (String entry : entries) {

            if (!entry.endsWith("}")) entry = entry + "}";

            String[] parts = entry.split(":", 2);
            String key = parts[0].replace("\"", "").trim();

            String obj = parts[1].trim();
            obj = obj.substring(1, obj.length() - 1);

            Map<String, String> attrs = new HashMap<>();
            for (String field : obj.split(",")) {
                String[] kv = field.split(":", 2);
                String k = kv[0].replace("\"", "").trim();
                String v = kv[1].replace("\"", "").trim();
                attrs.put(k, v);
            }

            Credential c = new Credential(
                key,
                attrs.get("encryptedPassword"),
                attrs.get("notes")
            );

            map.put(key, c);
        }

        return map;
    }
}
