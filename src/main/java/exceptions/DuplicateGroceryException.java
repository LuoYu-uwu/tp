package exceptions;

/**
 * Represents the exception thrown when the user tries to add a duplicate of a grocery.
 */
public class DuplicateGroceryException extends GitException {
    /**
     * Constructs DuplicateGroceryException.
     */
    public DuplicateGroceryException(String input) {
        message = "The grocery (" + input + ") already exists, duplicate will not be added.";
    }
}
