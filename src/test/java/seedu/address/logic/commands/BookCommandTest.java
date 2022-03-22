package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BORROW_DATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RETURN_DATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RETURN_DATE_2;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showBookAtIndex;
import static seedu.address.logic.commands.CommandTestUtil.showPatronAtIndex;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_BOOKS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BOOK;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PATRON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_BOOK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PATRON;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.book.Book;
import seedu.address.model.patron.Patron;
import seedu.address.testutil.TypicalLibTask;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code BorrowCommand}.
 */
public class BookCommandTest {

    private static final int BORROWED_BOOK_ONE_BASED_INDEX = 3; // refers to the book AI in TypicalBooks
    private Model model;

    @BeforeEach
    public void setup() {
        model = new ModelManager(TypicalLibTask.getTypicalLibTask(), new UserPrefs());
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Patron borrower = model.getFilteredPatronList().get(INDEX_FIRST_PATRON.getZeroBased());
        Book bookToBorrow = model.getFilteredBookList().get(INDEX_FIRST_BOOK.getZeroBased());
        BorrowCommand borrowCommand = new BorrowCommand(INDEX_FIRST_PATRON, INDEX_FIRST_BOOK, VALID_RETURN_DATE);

        String expectedMessage = String.format(BorrowCommand.MESSAGE_BORROW_BOOK_SUCCESS, borrower.getName(),
                bookToBorrow.getBookName(), VALID_RETURN_DATE);

        ModelManager expectedModel = new ModelManager(model.getLibTask(), new UserPrefs());
        expectedModel.borrowBook(borrower, bookToBorrow, VALID_RETURN_DATE);

        assertCommandSuccess(borrowCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPatronIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPatronList().size() + 1);
        BorrowCommand borrowCommand = new BorrowCommand(outOfBoundIndex, INDEX_FIRST_BOOK, VALID_RETURN_DATE);

        assertCommandFailure(borrowCommand, model, Messages.MESSAGE_INVALID_PATRON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidBookIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredBookList().size() + 1);
        BorrowCommand borrowCommand = new BorrowCommand(INDEX_FIRST_PATRON, outOfBoundIndex, VALID_RETURN_DATE);

        assertCommandFailure(borrowCommand, model, Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_bookAlreadyBorrowedByOthers_throwsCommandException() {
        Index borrowedBookIndex = Index.fromZeroBased(BORROWED_BOOK_ONE_BASED_INDEX);
        Book alreadyBorrowedBook = model.getFilteredBookList().get(BORROWED_BOOK_ONE_BASED_INDEX);
        BorrowCommand borrowCommand = new BorrowCommand(INDEX_SECOND_PATRON, borrowedBookIndex, VALID_RETURN_DATE);
        String expectedMessage = String.format(
                Messages.MESSAGE_BOOK_ALREADY_BORROWED, alreadyBorrowedBook.getBookName());
        assertCommandFailure(borrowCommand, model, expectedMessage);
    }

    @Test
    public void execute_copyOfBookAlreadyBorrowedBySameBorrower_throwsCommandException() {
        // The first patron has already borrowed the 3rd book
        Index borrowedBookIndex = Index.fromZeroBased(BORROWED_BOOK_ONE_BASED_INDEX);
        BorrowCommand borrowCommand = new BorrowCommand(INDEX_FIRST_PATRON, borrowedBookIndex, VALID_RETURN_DATE);
        String expectedMessage = String.format(
                BorrowCommand.MESSAGE_IS_BORROWING, model.getFilteredPatronList().get(0).getName());
        assertCommandFailure(borrowCommand, model, expectedMessage);
    }

    @Test
    public void execute_returnDateBeforeCurrentDate_throwsCommandException() {
        BorrowCommand borrowCommand = new BorrowCommand(INDEX_FIRST_PATRON, INDEX_FIRST_BOOK, VALID_BORROW_DATE);
        String expectedMessage = String.format(Messages.MESSAGE_RETURN_DATE_TOO_EARLY);
        assertCommandFailure(borrowCommand, model, expectedMessage);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showBookAtIndex(model, INDEX_FIRST_BOOK);
        showPatronAtIndex(model, INDEX_FIRST_PATRON);

        Book bookToBorrow = model.getFilteredBookList().get(INDEX_FIRST_BOOK.getZeroBased());
        Patron borrower = model.getFilteredPatronList().get(INDEX_FIRST_PATRON.getZeroBased());
        BorrowCommand borrowCommand = new BorrowCommand(INDEX_FIRST_PATRON, INDEX_FIRST_BOOK, VALID_RETURN_DATE);

        String expectedMessage = String.format(BorrowCommand.MESSAGE_BORROW_BOOK_SUCCESS, borrower.getName(),
                bookToBorrow.getBookName(), VALID_RETURN_DATE);

        ModelManager expectedModel = new ModelManager(model.getLibTask(), new UserPrefs());
        showPatronAtIndex(expectedModel, INDEX_FIRST_PATRON);
        expectedModel.borrowBook(borrower, bookToBorrow, VALID_RETURN_DATE);
        expectedModel.updateFilteredBookList(PREDICATE_SHOW_ALL_BOOKS);

        assertCommandSuccess(borrowCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPatronIndexFilteredList_throwsCommandException() {
        showBookAtIndex(model, INDEX_FIRST_BOOK);
        showPatronAtIndex(model, INDEX_FIRST_PATRON);

        Index outOfBoundIndex = INDEX_SECOND_PATRON;
        // ensures that outOfBoundIndex is still in bounds of patron list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getLibTask().getPatronList().size());
        // ensures that book index is valid and in bounds of filtered book list
        assertTrue(INDEX_FIRST_BOOK.getZeroBased() < model.getFilteredBookList().size());

        BorrowCommand borrowCommand = new BorrowCommand(outOfBoundIndex, INDEX_FIRST_BOOK, VALID_RETURN_DATE);

        assertCommandFailure(borrowCommand, model, Messages.MESSAGE_INVALID_PATRON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidBookIndexFilteredList_throwsCommandException() {
        showBookAtIndex(model, INDEX_FIRST_BOOK);
        showPatronAtIndex(model, INDEX_FIRST_PATRON);

        Index outOfBoundIndex = INDEX_SECOND_BOOK;
        // ensures that outOfBoundIndex is still in bounds of book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getLibTask().getBookList().size());
        // ensures that patron index is valid and in bounds of filtered patron list
        assertTrue(INDEX_FIRST_PATRON.getZeroBased() < model.getFilteredPatronList().size());

        BorrowCommand borrowCommand = new BorrowCommand(INDEX_FIRST_PATRON, outOfBoundIndex, VALID_RETURN_DATE);

        assertCommandFailure(borrowCommand, model, Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        BorrowCommand borrowFirstCommand = new BorrowCommand(INDEX_FIRST_PATRON, INDEX_FIRST_BOOK, VALID_RETURN_DATE);
        BorrowCommand borrowSecondCommand = new BorrowCommand(INDEX_SECOND_PATRON, INDEX_FIRST_BOOK, VALID_RETURN_DATE);
        BorrowCommand borrowThirdCommand = new BorrowCommand(INDEX_FIRST_PATRON, INDEX_SECOND_BOOK, VALID_RETURN_DATE);
        BorrowCommand borrowFourthCommand =
                new BorrowCommand(INDEX_FIRST_PATRON, INDEX_FIRST_BOOK, VALID_RETURN_DATE_2);

        // same object -> returns true
        assertTrue(borrowFirstCommand.equals(borrowFirstCommand));

        // same values -> returns true
        BorrowCommand borrowFirstCommandCopy =
                new BorrowCommand(INDEX_FIRST_PATRON, INDEX_FIRST_BOOK, VALID_RETURN_DATE);
        assertTrue(borrowFirstCommand.equals(borrowFirstCommandCopy));

        // different types -> returns false
        assertFalse(borrowFirstCommand.equals(1));

        // null -> returns false
        assertFalse(borrowFirstCommand.equals(null));

        // different patron index -> returns false
        assertFalse(borrowFirstCommand.equals(borrowSecondCommand));

        // different book index -> returns false
        assertFalse(borrowFirstCommand.equals(borrowThirdCommand));

        // different return date -> returns false
        assertFalse(borrowFirstCommand.equals(borrowFourthCommand));
    }
}
