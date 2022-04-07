package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.PreviousCommand.MESSAGE_SUCCESS;
import static seedu.address.logic.commands.PreviousCommand.NO_PREVIOUS_COMMAND;

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
    void execute_noPreviousCommand_success() {
        String expectedSuccessMessage = "|" + NO_PREVIOUS_COMMAND;
        CommandResult expectedCommandResult = new CommandResult(expectedSuccessMessage, false, false,
                true);
        assertCommandSuccess(new PreviousCommand(""), model, expectedCommandResult, expectedModel);
    }

    @Test
    void execute_hasPreviousCommand_success() {
        String expectedSuccessMessage = "test|" + MESSAGE_SUCCESS;
        CommandResult expectedCommandResult = new CommandResult(expectedSuccessMessage, false, false,
                true);
        assertCommandSuccess(new PreviousCommand("test"), model, expectedCommandResult, expectedModel);
    }
}
