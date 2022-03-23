package seedu.address.logic.commands.patron;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPatrons.ALICE;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.TypicalLibTask;

public class OverduePatronCommandTest {

    private Model model = new ModelManager(TypicalLibTask.getTypicalLibTask(), new UserPrefs());
    private Model expectedModel = new ModelManager(TypicalLibTask.getTypicalLibTask(), new UserPrefs());

    @Test
    public void execute_listPatronsWithOverdueBooks() {
        String expectedMessage = OverduePatronCommand.MESSAGE_SUCCESS;
        OverduePatronCommand command = new OverduePatronCommand();
        expectedModel.updateFilteredPatronList(x -> expectedModel.hasOverdueBooks(x));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE), expectedModel.getFilteredPatronList());
    }
}
