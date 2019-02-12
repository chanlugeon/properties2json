package test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class GsonTest {

    @Test
    void testGetAsJsonObjectDoesNotDeepCopy() {
        JsonObject json = new JsonObject();
        json.add("z", new JsonObject());
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        String before = gson.toJson(json);

        JsonObject explorer = json.getAsJsonObject("z");
        explorer.addProperty("y", true);
        String after = gson.toJson(json);

        assertNotEquals(before, after);
    }
}