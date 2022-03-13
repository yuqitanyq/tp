package seedu.address.logic.commands.patron;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPatronAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PATRON;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.TypicalLibTask;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListPatronCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(TypicalLibTask.getTypicalLibTask(), new UserPrefs());
        expectedModel = new ModelManager(model.getLibTask(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListPatronCommand(), model, ListPatronCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPatronAtIndex(model, INDEX_FIRST_PATRON);
        assertCommandSuccess(new ListPatronCommand(), model, ListPatronCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
