package exceptions;

public class InvalidActivenessException extends GitException {

    /**
     * Constructs InvalidActivenessException.
     */
    public InvalidActivenessException() {
        message = "The activeness provided is invalid!" ;
    }
}
