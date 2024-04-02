package exceptions.commands;

import exceptions.GitException;

/**
 * Represents the exception thrown when the command does not follow the proper format.
 */
public class CommandWrongFormatException extends GitException {
    /**
     * Constructs CommandWrongFormatException.
     */
    public CommandWrongFormatException(String command, String parameter) {
        message = printWrongFormatFix(command, parameter);
    }

    /**
     * Creates a message that reminds the user of the proper command format.
     */
    public String printWrongFormatFix(String command, String parameter) {
        return "Command is in the wrong format, type \"help\" for more information." +
                System.lineSeparator() +
                command + " needs '" + parameter + "'";
    }
}
