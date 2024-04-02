package exceptions.nosuch;


import exceptions.GitException;

/**
 * Represents the exception thrown when the Object the code is looking for does not exist.
 */
public class NoSuchObjectException extends GitException {
    /**
     * Constructs NoSuchObjectException.
     */
    public NoSuchObjectException(String object) {
        message = "The " + object + " does not exist!";
    }
}
