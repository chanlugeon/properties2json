package io.github.chanlugeon.p2j.generator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import io.github.chanlugeon.p2j.JsonOverwritingException;
import io.github.chanlugeon.p2j.NoJsonObjectException;
import io.github.chanlugeon.p2j.json.PToJson;

public class JsonGenerator {

    private final String path;

    public static JsonGenerator newInstance(String propertiesPath) {
        return new JsonGenerator(propertiesPath);
    }

    private JsonGenerator(String propertiesPath) {
        path = propertiesPath;
    }

    public void generateJsonFile() {
        String json = mapToJson(readPropertiesFileAsMap());
        createJsonFile(json);
    }

    private Map<String, String> readPropertiesFileAsMap() {
        String lines = openWithoutComment();
        return propertiesAsMap(lines);
    }

    private String openWithoutComment() {
        StringBuilder builder = new StringBuilder();

        try (
                BufferedReader br = new BufferedReader(new FileReader(path));
                ) {
            String readLine;
            while ((readLine = br.readLine()) != null) {
                if (isNotProperty(readLine))
                    continue;

                builder.append(readLine).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return builder.substring(0, builder.length() - 1).toString();
    }

    private static boolean isNotProperty(String line) {
        return line.matches("^\\s*[!#].*$") || line.matches("^\\s*$");
    }

    private static Map<String, String> propertiesAsMap(String properties) {
        Map<String, String> map = new HashMap<>();
        String lines = combineProperty(properties);
        for (String line : lines.split("\n")) {
            map.putAll(propertyToMap(line));
        }
        return map;
    }

    private static String combineProperty(String properties) {
        return properties.replaceAll("\\\\\n\\s*", "");
    }

    private static Map<String, String> propertyToMap(String property) {
        String[] kv = property.split("\\s*[:=]\\s*", 2);
        Map<String, String> map = new HashMap<>();
        map.put(spaceToUnderscore(kv[0]), kv[1]);
        return map;
    }

    private static String spaceToUnderscore(String key) {
        return key.replace("\\ ", "_");
    }

    private static String mapToJson(Map<String, String> map) {
        PToJson json = PToJson.newInstance();
        map.forEach((key, value) -> {
            try {
                json.putProperty(key, value);
            } catch (NoJsonObjectException | JsonOverwritingException e) {
                System.out.println(e.getMessage());
            }
        });

        return json.toString();
    }

    private void createJsonFile(String json) {
        try (
                BufferedWriter bw = new BufferedWriter(Files.newBufferedWriter(
                        Paths.get(jsonFilePath()), StandardCharsets.UTF_8))
                ) {
            bw.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String jsonFilePath() {
        return path.replaceAll(".properties$", ".json");
    }
}