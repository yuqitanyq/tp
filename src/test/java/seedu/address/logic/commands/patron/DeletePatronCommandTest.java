package seedu.address.logic.commands.patron;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPatronAtIndex;
import static seedu.address.testutil.TypicalBooks.HARRY_POTTER;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PATRON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PATRON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.book.Book;
import seedu.address.model.patron.Patron;
import seedu.address.testutil.BookBuilder;
import seedu.address.testutil.TypicalLibTask;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeletePatronCommandTest {

    private Model model = new ModelManager(TypicalLibTask.getTypicalLibTask(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Patron patronToDelete = model.getFilteredPatronList().get(INDEX_SECOND_PATRON.getZeroBased());
        DeletePatronCommand deleteCommand = new DeletePatronCommand(INDEX_SECOND_PATRON);

        String expectedMessage = String.format(DeletePatronCommand.MESSAGE_DELETE_PATRON_SUCCESS, patronToDelete);

        ModelManager expectedModel = new ModelManager(model.getLibTask(), new UserPrefs());
        expectedModel.deletePatron(patronToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_patronIsBorrowingSomeBooks_failure() {
        // The first patron in sample data is borrowing some books
        DeletePatronCommand deleteCommand = new DeletePatronCommand(INDEX_FIRST_PATRON);

        assertCommandFailure(deleteCommand, model, DeletePatronCommand.MESSAGE_HAS_BORROWED_BOOKS);
    }

    @Test
    public void execute_patronHasRequestedBooks_updateRequestedBooks() {
        Patron patronToDelete = model.getFilteredPatronList().get(INDEX_SECOND_PATRON.getZeroBased());
        Book requestedBook = new BookBuilder(HARRY_POTTER).withRequesters(patronToDelete).build();
        Book notRequestedBook = new BookBuilder(requestedBook).withRequesters().build();
        model.addBook(requestedBook);

        DeletePatronCommand deleteCommand = new DeletePatronCommand(INDEX_SECOND_PATRON);
        String expectedMessage = String.format(DeletePatronCommand.MESSAGE_DELETE_PATRON_SUCCESS, patronToDelete)
                + String.format("%s is also deleted from the requesters list of some books\n",
                patronToDelete.getName());

        ModelManager expectedModel = new ModelManager(model.getLibTask(), new UserPrefs());
        expectedModel.deletePatron(patronToDelete);
        expectedModel.setBook(requestedBook, notRequestedBook);
        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPatronList().size() + 1);
        DeletePatronCommand deleteCommand = new DeletePatronCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PATRON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPatronAtIndex(model, INDEX_SECOND_PATRON);

        Patron patronToDelete = model.getFilteredPatronList().get(INDEX_FIRST_PATRON.getZeroBased());
        DeletePatronCommand deleteCommand = new DeletePatronCommand(INDEX_FIRST_PATRON);

        String expectedMessage = String.format(DeletePatronCommand.MESSAGE_DELETE_PATRON_SUCCESS, patronToDelete);

        Model expectedModel = new ModelManager(model.getLibTask(), new UserPrefs());
        expectedModel.deletePatron(patronToDelete);
        showNoPatron(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPatronAtIndex(model, INDEX_FIRST_PATRON);

        Index outOfBoundIndex = INDEX_SECOND_PATRON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getLibTask().getPatronList().size());

        DeletePatronCommand deleteCommand = new DeletePatronCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PATRON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeletePatronCommand deleteFirstCommand = new DeletePatronCommand(INDEX_FIRST_PATRON);
        DeletePatronCommand deleteSecondCommand = new DeletePatronCommand(INDEX_SECOND_PATRON);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeletePatronCommand deleteFirstCommandCopy = new DeletePatronCommand(INDEX_FIRST_PATRON);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different patron -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPatron(Model model) {
        model.updateFilteredPatronList(p -> false);

        assertTrue(model.getFilteredPatronList().isEmpty());
    }
}
