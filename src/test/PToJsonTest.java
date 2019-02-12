package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import io.github.chanlugeon.p2j.json.PToJson;

public class PToJsonTest {

    @Test
    void test() {
        PToJson json = PToJson.newInstance();
        String key = "k1.k2.k3.k4";
        json.putProperty(key, "Key 4");
        assertEquals(json.getProperty(key), "Key 4");
    }
}
