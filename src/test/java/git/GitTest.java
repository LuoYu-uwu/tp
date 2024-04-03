package git;

import exceptions.GitException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class GitTest {
    @Test
    public void executeCommand_invalidCommand_success() {
        try {
            Ui ui = Ui.getInstance();
            Parser parser = new Parser(ui);
            String[] commandParts = {"nonsense", ""};
            parser.executeCommand(commandParts, "grocery");
        } catch (GitException e) {
            assertEquals("Unknown command. Type 'help' for a list of commands.", e.getMessage());;
        }
    }
}
