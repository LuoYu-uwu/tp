package exceptions.invalidinput;

import exceptions.GitException;

/**
 * Represents the exception thrown when the cost inputted by the user is invalid.
 */
public class InvalidCostException extends GitException {
    /**
     * Constructs InvalidCostException.
     */
    public InvalidCostException() {

        message = "Cost entered is invalid!\n" +
                "Please enter the cost (e.g., $1.20):";
    }
}
