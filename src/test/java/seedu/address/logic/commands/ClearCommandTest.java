package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.model.LibTask;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.TypicalLibTask;

public class ClearCommandTest {

    @Test
    public void execute_emptyLibTask_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyLibTask_success() {
        Model model = new ModelManager(TypicalLibTask.getTypicalLibTask(), new UserPrefs());
        Model expectedModel = new ModelManager(TypicalLibTask.getTypicalLibTask(), new UserPrefs());
        expectedModel.setLibTask(new LibTask());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
