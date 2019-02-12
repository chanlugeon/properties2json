package test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import java.io.File;

import org.junit.jupiter.api.Test;

import io.github.chanlugeon.p2j.generator.JsonGenerator;

public class GeneratorTest {

    private static final String EXAMPLE_PATH = (new File("").getAbsolutePath()) + "/example";

    @Test
    void test() {
        String exam = EXAMPLE_PATH + "/exam.properties";
        JsonGenerator gen = JsonGenerator.newInstance(exam);
        gen.generateJsonFile();
        String output = gen.jsonFilePath();
        dynamicTest("Check " + output, () -> assertTrue(new File(output).exists()));
    };
}
