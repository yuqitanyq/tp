package seedu.address.logic.commands.book;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showBookAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BOOK;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.TypicalLibTask;

/**
 * Contains Integration tests (interaction with the Model) and unit tests for ListBookCommand.
 */

public class ListBookCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(TypicalLibTask.getTypicalLibTask(), new UserPrefs());
        expectedModel = new ModelManager(model.getLibTask(), new UserPrefs());
    }

    @Test
    public void execute_bookListIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListBookCommand(), model, ListBookCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_bookListIsFiltered_showsEverything() {
        showBookAtIndex(model, INDEX_FIRST_BOOK);
        assertCommandSuccess(new ListBookCommand(), model, ListBookCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
