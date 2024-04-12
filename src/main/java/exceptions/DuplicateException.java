package exceptions;

/**
 * Represents the exception thrown when the user tries to add a duplicate of an item.
 */
public class DuplicateException extends GitException {
    /**
     * Constructs DuplicateException.
     */
    public DuplicateException(String item, String input) {
        message = "The " + item + " (" + input + ") already exists, a duplicate will not be added.";
    }
}
