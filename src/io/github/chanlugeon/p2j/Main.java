package io.github.chanlugeon.p2j;

import java.util.Scanner;

import io.github.chanlugeon.p2j.generator.JsonGenerator;

public class Main {

    public static void main(String[] paths) {
        welcome();

        if (paths.length == 0)
            paths = waitForInput();

        for (String path : paths)
            generateJsonFileWithPropertiesFile(path);
    }

    private static void welcome() {
        System.out.printf("properties2json\n", "");
        System.out.printf("%40s\n", "Created by Chan");
        System.out.println();
    }

    private static String[] waitForInput() {
        System.out.print("Input paths to Java properties file: ");
        return receiveArguments();
    }

    private static String[] receiveArguments() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        scanner.close();
        return input.split("\\s+");
    }

    public static void generateJsonFileWithPropertiesFile(String propertiesPath) {
        JsonGenerator gen = JsonGenerator.newInstance(propertiesPath);
        gen.generateJsonFile();
        System.out.println("Generated " + gen.jsonFilePath());
    }
}
