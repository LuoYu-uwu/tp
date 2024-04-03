package exceptions;

/**
 * Represents the exception thrown when the information given is insufficient to calculate BMR.
 */
public class InsufficientInfoException extends GitException {

    /**
     * Constructs InsufficientInfoException.
     */
    public InsufficientInfoException() {
        message = "User's information is insufficient to calculate BMR," +
                " please check the current information";
    }
}
