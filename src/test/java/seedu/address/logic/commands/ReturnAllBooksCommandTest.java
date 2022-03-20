package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPatronAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PATRON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PATRON;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.book.Book;
import seedu.address.model.book.BookStatus;
import seedu.address.model.patron.Patron;
import seedu.address.testutil.BookBuilder;
import seedu.address.testutil.TypicalBooks;
import seedu.address.testutil.TypicalLibTask;
import seedu.address.testutil.TypicalPatrons;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code ReturnAllBooksCommand}.
 */
public class ReturnAllBooksCommandTest {

    private static final Patron borrower = TypicalPatrons.getTypicalPatrons().get(0);
    private static final Book borrowedBook = TypicalBooks.getTypicalBooks().get(3);
    private Model model;

    @BeforeEach
    public void setup() {
        model = new ModelManager(TypicalLibTask.getTypicalLibTask(), new UserPrefs());
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        // The fourth book TypicalBooks is already borrowed by the first patron in TypicalPatrons
        ReturnAllBooksCommand returnCommand = new ReturnAllBooksCommand(INDEX_FIRST_PATRON);

        String expectedMessage = String.format(ReturnAllBooksCommand.MESSAGE_SUCCESS, borrower.getName());

        ModelManager expectedModel = new ModelManager(model.getLibTask(), new UserPrefs());
        Book updatedBook = new BookBuilder(borrowedBook).withBookStatus(BookStatus.createAvailableBookStatus()).build();
        expectedModel.setBook(borrowedBook, updatedBook);

        assertCommandSuccess(returnCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPatronList().size() + 1);
        ReturnAllBooksCommand returnCommand = new ReturnAllBooksCommand(outOfBoundIndex);

        assertCommandFailure(returnCommand, model, Messages.MESSAGE_INVALID_PATRON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_patronHasNoBookToReturn_throwsCommandException() {
        Patron patronIndexTwo = model.getFilteredPatronList().get(INDEX_SECOND_PATRON.getZeroBased());
        ReturnAllBooksCommand returnCommand = new ReturnAllBooksCommand(INDEX_SECOND_PATRON);

        String expectedMessage = String.format(Messages.MESSAGE_NO_BOOKS_BORROWED, patronIndexTwo.getName());

        assertCommandFailure(returnCommand, model, expectedMessage);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPatronAtIndex(model, INDEX_FIRST_PATRON);

        Index outOfBoundIndex = INDEX_SECOND_PATRON;
        // ensures that outOfBoundIndex is still in bounds of book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getLibTask().getPatronList().size());

        ReturnAllBooksCommand returnCommand = new ReturnAllBooksCommand(outOfBoundIndex);

        assertCommandFailure(returnCommand, model, Messages.MESSAGE_INVALID_PATRON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPatronAtIndex(model, INDEX_FIRST_PATRON);
        ReturnAllBooksCommand returnCommand = new ReturnAllBooksCommand(INDEX_FIRST_PATRON);

        String expectedMessage = String.format(ReturnAllBooksCommand.MESSAGE_SUCCESS, borrower.getName());

        Model expectedModel = new ModelManager(TypicalLibTask.getTypicalLibTask(), new UserPrefs());
        Book updatedBook = new BookBuilder(borrowedBook).withBookStatus(BookStatus.createAvailableBookStatus()).build();
        expectedModel.setBook(borrowedBook, updatedBook);

        model.updateFilteredPatronList(Model.PREDICATE_SHOW_ALL_PATRONS);
        assertCommandSuccess(returnCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        ReturnAllBooksCommand returnCommandFirst = new ReturnAllBooksCommand(INDEX_FIRST_PATRON);
        ReturnAllBooksCommand returnCommandSecond = new ReturnAllBooksCommand(INDEX_SECOND_PATRON);

        // same object -> returns true
        assertTrue(returnCommandFirst.equals(returnCommandFirst));

        // same values -> returns true
        ReturnAllBooksCommand returnCommandFirstCopy = new ReturnAllBooksCommand(INDEX_FIRST_PATRON);
        assertTrue(returnCommandFirst.equals(returnCommandFirstCopy));

        // different types -> returns false
        assertFalse(returnCommandFirst.equals(1));

        // null -> returns false
        assertFalse(returnCommandFirst.equals(null));

        // different index -> returns false
        assertFalse(returnCommandFirst.equals(returnCommandSecond));
    }
}
