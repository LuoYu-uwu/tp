package git;

import enumerations.RecipeCommand;
import exceptions.GitException;
import exceptions.InvalidCommandException;

public class ParserForRecipe {

    private Ui ui;
    private boolean isRunning;
    private String newMode;
    private Parser parser;

    public boolean getIsRunning() {
        return isRunning;
    }

    public String getNewMode() {
        return newMode;
    }


}
