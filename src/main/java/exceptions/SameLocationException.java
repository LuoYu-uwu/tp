package exceptions;

/**
 * Represents the exception thrown when the user tries to store a grocery in the same grocery.
 */
public class SameLocationException extends GitException {
    /**
     * Constructs SameLocationException.
     */
    public SameLocationException(String grocery, String location) {
        message = grocery + " is already stored in " + location + ".";
    }
}
