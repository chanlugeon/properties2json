package io.github.chanlugeon.p2j;

public class NoJsonObjectException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NoJsonObjectException(String parentObject, String key) {
        super("A parent directory is not JsonObject: " + parentObject + " of " + key);
    }
}
