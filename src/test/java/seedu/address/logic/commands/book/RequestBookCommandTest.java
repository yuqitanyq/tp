package seedu.address.logic.commands.book;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showBookAtIndex;
import static seedu.address.logic.commands.CommandTestUtil.showPatronAtIndex;
import static seedu.address.logic.commands.book.RequestBookCommand.MESSAGE_ALREADY_REQUESTED;
import static seedu.address.logic.commands.book.RequestBookCommand.MESSAGE_HAS_AVAILABLE;
import static seedu.address.logic.commands.book.RequestBookCommand.MESSAGE_IS_BORROWING;
import static seedu.address.logic.commands.book.RequestBookCommand.MESSAGE_MAX_REQUEST;
import static seedu.address.logic.commands.book.RequestBookCommand.MESSAGE_REQUEST_BOOK_SUCCESS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BOOK;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PATRON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FOURTH_BOOK;
import static seedu.address.testutil.TypicalIndexes.INDEX_FOURTH_PATRON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_BOOK;
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
 * {@code RequestBookCommand}.
 */
public class RequestBookCommandTest {

    private Model model = new ModelManager(TypicalLibTask.getTypicalLibTask(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        // Use the fourth book because it is borrowed. Otherwise, requesting is not allowed.
        Book bookToRequest = model.getFilteredBookList().get(INDEX_FOURTH_BOOK.getZeroBased());
        Patron requester = model.getFilteredPatronList().get(INDEX_SECOND_PATRON.getZeroBased());
        RequestBookCommand requestCommand = new RequestBookCommand(INDEX_SECOND_PATRON, INDEX_FOURTH_BOOK);

        String expectedMessage = String.format(MESSAGE_REQUEST_BOOK_SUCCESS, requester.getName(),
                bookToRequest.getBookName());

        ModelManager expectedModel = new ModelManager(model.getLibTask(), new UserPrefs());
        Book updatedBook = bookToRequest.addRequester(requester);
        expectedModel.setBook(bookToRequest, updatedBook);

        assertCommandSuccess(requestCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_requesterAlreadyBorrowedBook_throwsCommandException() {
        // The first patron already borrows a copy of the fourth book
        Patron requester = model.getFilteredPatronList().get(INDEX_FIRST_PATRON.getZeroBased());
        RequestBookCommand requestCommand = new RequestBookCommand(INDEX_FIRST_PATRON, INDEX_FOURTH_BOOK);

        String expectedMessage = String.format(MESSAGE_IS_BORROWING, requester.getName());

        assertCommandFailure(requestCommand, model, expectedMessage);
    }

    @Test
    public void execute_hasAvailableCopyOfBook_throwsCommandException() {
        // The first book is available and hence cannot be requested
        Book bookToRequest = model.getFilteredBookList().get(INDEX_FIRST_BOOK.getZeroBased());
        RequestBookCommand requestCommand = new RequestBookCommand(INDEX_SECOND_PATRON, INDEX_FIRST_BOOK);

        String expectedMessage = String.format(MESSAGE_HAS_AVAILABLE, bookToRequest.getBookName());

        assertCommandFailure(requestCommand, model, expectedMessage);
    }

    @Test
    public void execute_requesterAlreadyRequestedForSameBook_throwsCommandException() {

        Book bookToRequest = model.getFilteredBookList().get(INDEX_FOURTH_BOOK.getZeroBased());
        Patron requester = model.getFilteredPatronList().get(INDEX_SECOND_PATRON.getZeroBased());
        Book alreadyRequestedBook = new BookBuilder(bookToRequest).withRequesters(requester).build();

        // Modify the model so that the first book is already requested by the second patron
        model.setBook(bookToRequest, alreadyRequestedBook);
        RequestBookCommand requestCommand = new RequestBookCommand(INDEX_SECOND_PATRON, INDEX_FOURTH_BOOK);

        String expectedMessage = String.format(
                MESSAGE_ALREADY_REQUESTED, requester.getName(), bookToRequest.getBookName());
        assertCommandFailure(requestCommand, model, expectedMessage);
    }

    @Test
    public void execute_tooManyPatronsRequestedForBook_throwsCommandException() {
        // The fourth book must be used because it is borrowed. Otherwise, a different error message is thrown.
        Book bookToRequest = model.getFilteredBookList().get(INDEX_FOURTH_BOOK.getZeroBased());
        Patron requester1 = model.getFilteredPatronList().get(INDEX_FIRST_PATRON.getZeroBased());
        Book alreadyRequestedBook = new BookBuilder(bookToRequest).withRequesters(requester1)
                .build();

        // Modify the model so that the first book is already requested by 1 patron, and there is only 1 copy of book
        model.setBook(bookToRequest, alreadyRequestedBook);
        RequestBookCommand requestCommand = new RequestBookCommand(INDEX_FOURTH_PATRON, INDEX_FOURTH_BOOK);

        String expectedMessage = String.format(MESSAGE_MAX_REQUEST, bookToRequest.getBookName());
        assertCommandFailure(requestCommand, model, expectedMessage);
    }

    @Test
    public void execute_invalidRequesterIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPatronList().size() + 1);
        RequestBookCommand requestCommand = new RequestBookCommand(outOfBoundIndex, INDEX_FOURTH_BOOK);

        assertCommandFailure(requestCommand, model, Messages.MESSAGE_INVALID_PATRON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidBookIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredBookList().size() + 1);
        RequestBookCommand requestCommand = new RequestBookCommand(INDEX_FIRST_PATRON, outOfBoundIndex);

        assertCommandFailure(requestCommand, model, Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showBookAtIndex(model, INDEX_FOURTH_BOOK);
        showPatronAtIndex(model, INDEX_SECOND_PATRON);
        Patron requester = model.getFilteredPatronList().get(INDEX_FIRST_PATRON.getZeroBased());
        Book bookToRequest = model.getFilteredBookList().get(INDEX_FIRST_BOOK.getZeroBased());
        RequestBookCommand requestCommand = new RequestBookCommand(INDEX_FIRST_PATRON, INDEX_FIRST_BOOK);

        String expectedMessage = String.format(MESSAGE_REQUEST_BOOK_SUCCESS, requester.getName(),
                bookToRequest.getBookName());

        ModelManager expectedModel = new ModelManager(model.getLibTask(), new UserPrefs());
        Book updatedBook = bookToRequest.addRequester(requester);
        expectedModel.setBook(bookToRequest, updatedBook);
        showPatronAtIndex(expectedModel, INDEX_SECOND_PATRON);

        assertCommandSuccess(requestCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidRequesterIndexFilteredList_throwsCommandException() {
        showBookAtIndex(model, INDEX_FOURTH_BOOK);

        Index outOfBoundIndex = INDEX_SECOND_BOOK;
        // ensures that outOfBoundIndex is still in bounds of book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getLibTask().getBookList().size());

        RequestBookCommand requestCommand = new RequestBookCommand(INDEX_SECOND_PATRON, outOfBoundIndex);

        assertCommandFailure(requestCommand, model, Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidBookIndexFilteredList_throwsCommandException() {
        showPatronAtIndex(model, INDEX_SECOND_PATRON);

        Index outOfBoundIndex = INDEX_SECOND_PATRON;
        // ensures that outOfBoundIndex is still in bounds of book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getLibTask().getPatronList().size());

        RequestBookCommand requestCommand = new RequestBookCommand(outOfBoundIndex, INDEX_FOURTH_BOOK);

        assertCommandFailure(requestCommand, model, Messages.MESSAGE_INVALID_PATRON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        RequestBookCommand requestFirstCommand = new RequestBookCommand(INDEX_FIRST_PATRON, INDEX_FOURTH_BOOK);
        RequestBookCommand requestSecondCommand = new RequestBookCommand(INDEX_SECOND_PATRON, INDEX_FOURTH_BOOK);
        RequestBookCommand requestThirdCommand = new RequestBookCommand(INDEX_FIRST_PATRON, INDEX_FIRST_BOOK);

        // same object -> returns true
        assertTrue(requestFirstCommand.equals(requestFirstCommand));

        // same values -> returns true
        RequestBookCommand requestFirstCommandCopy = new RequestBookCommand(INDEX_FIRST_PATRON, INDEX_FOURTH_BOOK);
        assertTrue(requestFirstCommand.equals(requestFirstCommandCopy));

        // different types -> returns false
        assertFalse(requestFirstCommand.equals(1));

        // null -> returns false
        assertFalse(requestFirstCommand.equals(null));

        // different requester index -> returns false
        assertFalse(requestFirstCommand.equals(requestSecondCommand));

        // different book index -> returns false
        assertFalse(requestFirstCommand.equals(requestThirdCommand));
    }
}
