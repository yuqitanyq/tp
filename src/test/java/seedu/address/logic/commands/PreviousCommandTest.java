package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.PreviousCommand.MESSAGE_SUCCESS;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

class PreviousCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    void getMessageForUi() {
        PreviousCommand previousCommand = new PreviousCommand("testMessage");
        String expectedMessage = "testMessage|" + MESSAGE_SUCCESS;
        assertEquals(expectedMessage, previousCommand.getMessageForUi());
    }

    @Test
    void execute_previousCommand_success() {
        String expectedSuccessMessage = "|" + MESSAGE_SUCCESS;
        CommandResult expectedCommandResult = new CommandResult(expectedSuccessMessage, false, false,
                true);
        assertCommandSuccess(new PreviousCommand(""), model, expectedCommandResult, expectedModel);
    }
}
