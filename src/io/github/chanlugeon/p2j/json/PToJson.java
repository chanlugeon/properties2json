package io.github.chanlugeon.p2j.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import io.github.chanlugeon.p2j.JsonOverwritingException;
import io.github.chanlugeon.p2j.NoJsonObjectException;

public class PToJson {

    public static final Gson GSON = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();

    private final JsonObject json = new JsonObject();

    public static PToJson newInstance() {
        return new PToJson();
    }

    private PToJson() {
    }

    public String putProperty(String key, String value) {
        JsonObject explorer = exploreJsonObject(key);
        if (explorer.has(key))
            throw new JsonOverwritingException(key);

        explorer.addProperty(keyWithoutParentObject(key), value);
        return null;
    }

    public String getProperty(String key) {
        JsonObject explorer = exploreJsonObject(key);
        return explorer.get(keyWithoutParentObject(key)).getAsString();
    }

    private JsonObject exploreJsonObject(String key) {
        if (hasNoParentObject(key))
            return json;

        JsonObject explorer = json;

        String[] parentObjects = divideJsonObject(key);
        for (String parentObj : parentObjects) {
            if (!explorer.has(parentObj))
                explorer.add(parentObj, new JsonObject());

            try {
                explorer = explorer.getAsJsonObject(parentObj);
            } catch (ClassCastException e) {
                throw new NoJsonObjectException(parentObj, key);
            }
        }

        return explorer;
    }

    private boolean hasNoParentObject(String key) {
        return !key.contains(".");
    }

    private static String[] divideJsonObject(String key) {
        return divideKeyByDot(combinedParentObject(key));
    }

    private static String[] divideKeyByDot(String key) {
        return key.split("\\.");
    }

    private static String combinedParentObject(String key) {
        int idx = key.lastIndexOf('.');
        if (idx < 0)
            return "";

        return key.substring(0, idx);
    }

    private static String keyWithoutParentObject(String key) {
        int idx = key.lastIndexOf('.');
        if (idx < 0)
            return key;

        return key.substring(idx + 1);
    }

    @Override
    public String toString() {
        return GSON.toJson(json);
    }
}
