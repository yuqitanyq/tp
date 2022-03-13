package seedu.address.logic.commands.book;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_HARRY_POTTER;
import static seedu.address.logic.commands.CommandTestUtil.DESC_HUNGER_GAMES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AUTHOR_JK_ROWLING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BOOK_NAME_HARRY_POTTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BOOK_NAME_HUNGER_GAMES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ISBN_HARRY_POTTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_MAGIC;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showBookAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BOOK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_BOOK;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.book.EditBookCommand.EditBookDescriptor;
import seedu.address.model.LibTask;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.book.Book;
import seedu.address.testutil.BookBuilder;
import seedu.address.testutil.EditBookDescriptorBuilder;
import seedu.address.testutil.TypicalLibTask;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditBookCommand.
 */
public class EditBookCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(TypicalLibTask.getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_allBookFieldsSpecifiedUnfilteredList_success() {
        Book editedBook = new BookBuilder().build();
        EditBookDescriptor bookDescriptor = new EditBookDescriptorBuilder(editedBook).build();
        EditBookCommand editBookCommand = new EditBookCommand(INDEX_FIRST_BOOK, bookDescriptor);

        String expectedMessage = String.format(EditBookCommand.MESSAGE_EDIT_BOOK_SUCCESS, editedBook);

        Model expectedModel = new ModelManager(new LibTask(model.getAddressBook()), new UserPrefs());
        expectedModel.setBook(model.getFilteredBookList().get(0), editedBook);

        assertCommandSuccess(editBookCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_bookNameFieldSpecifiedUnfilteredList_success() {
        Index indexLastBook = Index.fromOneBased(model.getFilteredBookList().size());
        Book lastBook = model.getFilteredBookList().get(indexLastBook.getZeroBased());

        BookBuilder bookInList = new BookBuilder(lastBook);
        Book editedBook = bookInList.withName(VALID_BOOK_NAME_HARRY_POTTER).build();

        EditBookDescriptor bookDescriptor = new EditBookDescriptorBuilder()
                .withBookName(VALID_BOOK_NAME_HARRY_POTTER).build();
        EditBookCommand editBookCommand = new EditBookCommand(indexLastBook, bookDescriptor);

        String expectedMessage = String.format(EditBookCommand.MESSAGE_EDIT_BOOK_SUCCESS, editedBook);

        Model expectedModel = new ModelManager(new LibTask(model.getAddressBook()), new UserPrefs());
        expectedModel.setBook(lastBook, editedBook);

        assertCommandSuccess(editBookCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_isbnFieldSpecifiedUnfilteredList_success() {
        Index indexLastBook = Index.fromOneBased(model.getFilteredBookList().size());
        Book lastBook = model.getFilteredBookList().get(indexLastBook.getZeroBased());

        BookBuilder bookInList = new BookBuilder(lastBook);
        Book editedBook = bookInList.withIsbn(VALID_ISBN_HARRY_POTTER).build();

        EditBookDescriptor bookDescriptor = new EditBookDescriptorBuilder()
                .withIsbn(VALID_ISBN_HARRY_POTTER).build();
        EditBookCommand editBookCommand = new EditBookCommand(indexLastBook, bookDescriptor);

        String expectedMessage = String.format(EditBookCommand.MESSAGE_EDIT_BOOK_SUCCESS, editedBook);

        Model expectedModel = new ModelManager(new LibTask(model.getAddressBook()), new UserPrefs());
        expectedModel.setBook(lastBook, editedBook);

        assertCommandSuccess(editBookCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_authorFieldSpecifiedUnfilteredList_success() {
        Index indexLastBook = Index.fromOneBased(model.getFilteredBookList().size());
        Book lastBook = model.getFilteredBookList().get(indexLastBook.getZeroBased());

        BookBuilder bookInList = new BookBuilder(lastBook);
        Book editedBook = bookInList.withAuthors(VALID_AUTHOR_JK_ROWLING).build();

        EditBookDescriptor bookDescriptor = new EditBookDescriptorBuilder()
                .withAuthors(VALID_AUTHOR_JK_ROWLING).build();
        EditBookCommand editBookCommand = new EditBookCommand(indexLastBook, bookDescriptor);

        String expectedMessage = String.format(EditBookCommand.MESSAGE_EDIT_BOOK_SUCCESS, editedBook);

        Model expectedModel = new ModelManager(new LibTask(model.getAddressBook()), new UserPrefs());
        expectedModel.setBook(lastBook, editedBook);

        assertCommandSuccess(editBookCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_tagFieldSpecifiedUnfilteredList_success() {
        Index indexLastBook = Index.fromOneBased(model.getFilteredBookList().size());
        Book lastBook = model.getFilteredBookList().get(indexLastBook.getZeroBased());

        BookBuilder bookInList = new BookBuilder(lastBook);
        Book editedBook = bookInList.withTags(VALID_TAG_MAGIC).build();

        EditBookDescriptor bookDescriptor = new EditBookDescriptorBuilder()
                .withTags(VALID_TAG_MAGIC).build();
        EditBookCommand editBookCommand = new EditBookCommand(indexLastBook, bookDescriptor);

        String expectedMessage = String.format(EditBookCommand.MESSAGE_EDIT_BOOK_SUCCESS, editedBook);

        Model expectedModel = new ModelManager(new LibTask(model.getAddressBook()), new UserPrefs());
        expectedModel.setBook(lastBook, editedBook);

        assertCommandSuccess(editBookCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noBookFieldSpecifiedUnfilteredList_success() {
        EditBookCommand editBookCommand = new EditBookCommand(INDEX_FIRST_BOOK, new EditBookDescriptor());
        Book editedBook = model.getFilteredBookList().get(INDEX_FIRST_BOOK.getZeroBased());

        String expectedMessage = String.format(EditBookCommand.MESSAGE_EDIT_BOOK_SUCCESS, editedBook);

        Model expectedModel = new ModelManager(new LibTask(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editBookCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showBookAtIndex(model, INDEX_FIRST_BOOK);

        Book bookInFilteredList = model.getFilteredBookList().get(INDEX_FIRST_BOOK.getZeroBased());
        Book editedBook = new BookBuilder(bookInFilteredList).withName(VALID_BOOK_NAME_HARRY_POTTER).build();
        EditBookCommand editBookCommand = new EditBookCommand(INDEX_FIRST_BOOK,
                new EditBookDescriptorBuilder().withBookName(VALID_BOOK_NAME_HARRY_POTTER).build());

        String expectedMessage = String.format(EditBookCommand.MESSAGE_EDIT_BOOK_SUCCESS, editedBook);

        Model expectedModel = new ModelManager(new LibTask(model.getAddressBook()), new UserPrefs());
        expectedModel.setBook(model.getFilteredBookList().get(0), editedBook);

        assertCommandSuccess(editBookCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateBookUnfilteredList_success() {
        Book firstBook = model.getFilteredBookList().get(INDEX_FIRST_BOOK.getZeroBased());
        Book editedBook = new BookBuilder(firstBook).build();
        EditBookDescriptor bookDescriptor = new EditBookDescriptorBuilder(firstBook).build();
        EditBookCommand editBookCommand = new EditBookCommand(INDEX_SECOND_BOOK, bookDescriptor);

        String expectedMessage = String.format(EditBookCommand.MESSAGE_EDIT_BOOK_SUCCESS, editedBook);

        Model expectedModel = new ModelManager(new LibTask(model.getAddressBook()), new UserPrefs());
        expectedModel.setBook(model.getFilteredBookList().get(1), editedBook);

        assertCommandSuccess(editBookCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateBookFilteredList_success() {
        showBookAtIndex(model, INDEX_FIRST_BOOK);

        Book bookInList = model.getAddressBook().getBookList().get(INDEX_SECOND_BOOK.getZeroBased());
        EditBookCommand editBookCommand = new EditBookCommand(INDEX_FIRST_BOOK,
                new EditBookDescriptorBuilder(bookInList).build());
        Book editedBook = new BookBuilder(bookInList).build();

        String expectedMessage = String.format(EditBookCommand.MESSAGE_EDIT_BOOK_SUCCESS, editedBook);

        Model expectedModel = new ModelManager(new LibTask(model.getAddressBook()), new UserPrefs());
        expectedModel.setBook(model.getFilteredBookList().get(0), editedBook);

        assertCommandSuccess(editBookCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidBookIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredBookList().size() + 1);
        EditBookDescriptor bookDescriptor = new EditBookDescriptorBuilder()
                .withBookName(VALID_BOOK_NAME_HARRY_POTTER).build();
        EditBookCommand editBookCommand = new EditBookCommand(outOfBoundIndex, bookDescriptor);

        assertCommandFailure(editBookCommand, model, Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidBookIndexFilteredList_failure() {
        showBookAtIndex(model, INDEX_FIRST_BOOK);

        Index outOfBoundIndex = INDEX_SECOND_BOOK;
        //ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getBookList().size());

        EditBookCommand editBookCommand = new EditBookCommand(outOfBoundIndex,
                new EditBookDescriptorBuilder().withBookName(VALID_BOOK_NAME_HUNGER_GAMES).build());

        assertCommandFailure(editBookCommand, model, Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditBookCommand standardBookCommand = new EditBookCommand(INDEX_FIRST_BOOK, DESC_HARRY_POTTER);

        // same values -> returns true
        EditBookDescriptor copyBookDescriptor = new EditBookDescriptor(DESC_HARRY_POTTER);

        // same object -> returns true
        assertTrue(standardBookCommand.equals(standardBookCommand));

        // null -> returns false
        assertFalse(standardBookCommand.equals(null));

        //different types -> returns false
        assertFalse(standardBookCommand.equals(new ExitCommand()));

        //different index -> returns false
        assertFalse(standardBookCommand.equals(new EditBookCommand(INDEX_SECOND_BOOK, DESC_HARRY_POTTER)));

        //different descriptor -> returns false
        assertFalse(standardBookCommand.equals(new EditBookCommand(INDEX_FIRST_BOOK, DESC_HUNGER_GAMES)));
    }

}
