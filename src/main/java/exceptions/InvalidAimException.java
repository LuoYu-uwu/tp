package exceptions;

public class InvalidAimException extends GitException {

    /**
     * Constructs InvalidAimException.
     */
    public InvalidAimException() {
        message = "The aim provided is invalid!" ;
    }
}
