package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showBookAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BOOK;
import static seedu.address.testutil.TypicalIndexes.INDEX_FOURTH_BOOK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_BOOK;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.book.Book;
import seedu.address.model.book.BookStatus;
import seedu.address.testutil.BookBuilder;
import seedu.address.testutil.TypicalLibTask;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code ReturnOneBookCommand}.
 */
public class ReturnOneBookCommandTest {

    private Model model;

    @BeforeEach
    public void setup() {
        model = new ModelManager(TypicalLibTask.getTypicalLibTask(), new UserPrefs());
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        // The fourth book TypicalBooks is already borrowed
        Book bookToReturn = model.getFilteredBookList().get(INDEX_FOURTH_BOOK.getZeroBased());
        ReturnOneBookCommand returnCommand = new ReturnOneBookCommand(INDEX_FOURTH_BOOK);

        String expectedMessage = String.format(ReturnOneBookCommand.MESSAGE_SUCCESS, bookToReturn.getBookName());

        ModelManager expectedModel = new ModelManager(model.getLibTask(), new UserPrefs());
        Book updatedBook = new BookBuilder(bookToReturn).withBookStatus(BookStatus.createAvailableBookStatus()).build();
        expectedModel.setBook(bookToReturn, updatedBook);

        assertCommandSuccess(returnCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_bookNotBorrowed_throwsCommandException() {
        // The first book TypicalBooks is not borrowed
        Book bookToReturn = model.getFilteredBookList().get(INDEX_FIRST_BOOK.getZeroBased());
        ReturnOneBookCommand returnCommand = new ReturnOneBookCommand(INDEX_FIRST_BOOK);

        String expectedMessage = String.format(Messages.MESSAGE_BOOK_NOT_BORROWED, bookToReturn.getBookName());

        assertCommandFailure(returnCommand, model, expectedMessage);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredBookList().size() + 1);
        ReturnOneBookCommand returnCommand = new ReturnOneBookCommand(outOfBoundIndex);

        assertCommandFailure(returnCommand, model, Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showBookAtIndex(model, INDEX_FOURTH_BOOK);

        Book bookToReturn = model.getFilteredBookList().get(INDEX_FIRST_BOOK.getZeroBased());
        ReturnOneBookCommand returnCommand = new ReturnOneBookCommand(INDEX_FIRST_BOOK);

        String expectedMessage = String.format(ReturnOneBookCommand.MESSAGE_SUCCESS, bookToReturn.getBookName());

        Model expectedModel = new ModelManager(TypicalLibTask.getTypicalLibTask(), new UserPrefs());
        Book updatedBook = new BookBuilder(bookToReturn).withBookStatus(BookStatus.createAvailableBookStatus()).build();

        expectedModel.setBook(bookToReturn, updatedBook);

        assertCommandSuccess(returnCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showBookAtIndex(model, INDEX_FIRST_BOOK);

        Index outOfBoundIndex = INDEX_SECOND_BOOK;
        // ensures that outOfBoundIndex is still in bounds of book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getLibTask().getBookList().size());

        ReturnOneBookCommand returnCommand = new ReturnOneBookCommand(outOfBoundIndex);

        assertCommandFailure(returnCommand, model, Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ReturnOneBookCommand returnCommandFirst = new ReturnOneBookCommand(INDEX_FIRST_BOOK);
        ReturnOneBookCommand returnCommandSecond = new ReturnOneBookCommand(INDEX_SECOND_BOOK);

        // same object -> returns true
        assertTrue(returnCommandFirst.equals(returnCommandFirst));

        // same values -> returns true
        ReturnOneBookCommand returnCommandFirstCopy = new ReturnOneBookCommand(INDEX_FIRST_BOOK);
        assertTrue(returnCommandFirst.equals(returnCommandFirstCopy));

        // different types -> returns false
        assertFalse(returnCommandFirst.equals(1));

        // null -> returns false
        assertFalse(returnCommandFirst.equals(null));

        // different index -> returns false
        assertFalse(returnCommandFirst.equals(returnCommandSecond));
    }
}
