package exceptions.emptyinput;

import exceptions.GitException;

/**
 * Represents the exception thrown when the input is not given after the command.
 */
public class EmptyInputException extends GitException {
    /**
     * Constructs EmptyInputException.
     */
    public EmptyInputException(String input) {
        message = "A " + input + " needs to be specified!";
    }
}
