package io.github.chanlugeon.p2j;

public class JsonOverwritingException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public JsonOverwritingException(String key) {
        super("The key has been written already: " + key);
    }
}