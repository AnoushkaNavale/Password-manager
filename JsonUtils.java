package app;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Map;

public class JsonUtils {
    private static final Gson gson = new Gson();
    private static final Type MAP_TYPE = new TypeToken<Map<String, Credential>>(){}.getType();

    public static String toJson(Map<String, Credential> map) {
        return gson.toJson(map);
    }

    public static Map<String, Credential> fromJson(String json) {
        if (json == null || json.trim().isEmpty()) return new java.util.HashMap<>();
        return gson.fromJson(json, MAP_TYPE);
    }
}
