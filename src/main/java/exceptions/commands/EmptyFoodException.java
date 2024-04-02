package exceptions.commands;

import exceptions.GitException;

public class EmptyFoodException extends GitException {
    /**
     * Constructs EmptyFoodException.
     */
    public EmptyFoodException() {
        message = "A food needs to be specified!";
    }
}
