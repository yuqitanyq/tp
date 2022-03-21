package seedu.address.logic.commands.book;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.commands.book.AddBookCommand.MESSAGE_SAME_ISBN_INCONSISTENT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPatrons.ALICE;
import static seedu.address.testutil.TypicalPatrons.BOB;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.LibTask;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyLibTask;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.book.Book;
import seedu.address.model.book.Isbn;
import seedu.address.model.book.exceptions.BookNotFoundException;
import seedu.address.model.patron.Patron;
import seedu.address.testutil.BookBuilder;

public class AddBookCommandTest {

    @Test
    public void constructor_nullBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddBookCommand(null));
    }

    @Test
    public void execute_bookAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingBookAdded modelStub = new ModelStubAcceptingBookAdded();
        Book validBook = new BookBuilder().build();

        CommandResult commandResult = new AddBookCommand(validBook).execute(modelStub);

        assertEquals(String.format(AddBookCommand.MESSAGE_SUCCESS, validBook), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validBook), modelStub.booksAdded);
    }

    @Test
    public void execute_bookWithSameIsbnExistButDifferentAuthor_throwsCommandException() throws CommandException {
        ModelStubAcceptingBookAdded modelStub = new ModelStubAcceptingBookAdded();
        Book validBook = new BookBuilder().build();
        new AddBookCommand(validBook).execute(modelStub);

        // Fails to add a book which isbn was already added, but has different author
        Book validBookDiffAuthor = new BookBuilder(validBook).withAuthors("diff author").build();
        AddBookCommand errorCommand = new AddBookCommand(validBookDiffAuthor);
        String expectedMessage = String.format(MESSAGE_SAME_ISBN_INCONSISTENT, validBookDiffAuthor.getIsbn());
        assertThrows(CommandException.class, expectedMessage, () -> errorCommand.execute(modelStub));
    }

    @Test
    public void execute_bookWithSameIsbnExistButDifferentName_throwsCommandException() throws CommandException {
        ModelStubAcceptingBookAdded modelStub = new ModelStubAcceptingBookAdded();
        Book validBook = new BookBuilder().build();
        new AddBookCommand(validBook).execute(modelStub);

        // Fails to add a book which isbn was already added, but has different name
        Book validBookDiffName = new BookBuilder(validBook).withName("diff author").build();
        AddBookCommand errorCommand = new AddBookCommand(validBookDiffName);
        String expectedMessage = String.format(MESSAGE_SAME_ISBN_INCONSISTENT, validBookDiffName.getIsbn());
        assertThrows(CommandException.class, expectedMessage, () -> errorCommand.execute(modelStub));
    }

    @Test
    public void execute_bookWithSameIsbnExistButDifferentTags_addSuccessful() throws CommandException {
        ModelStubAcceptingBookAdded modelStub = new ModelStubAcceptingBookAdded();
        Book validBook = new BookBuilder().build();
        new AddBookCommand(validBook).execute(modelStub);

        // Able to add book that has same isbn as existing books, but different tags
        Book validBookDiffTags = new BookBuilder(validBook).withTags("difftag1", "difftag2").build();
        CommandResult commandResult = new AddBookCommand(validBookDiffTags).execute(modelStub);
        assertEquals(String.format(AddBookCommand.MESSAGE_SUCCESS, validBookDiffTags),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validBook, validBookDiffTags), modelStub.booksAdded);
    }

    @Test
    public void execute_successfulDeleteRequest() throws CommandException {
        ModelStubAcceptingBookAdded modelStub = new ModelStubAcceptingBookAdded();
        Book validBook = new BookBuilder().withRequesters(ALICE, BOB).build();
        new AddBookCommand(validBook).execute(modelStub);

        // After a book that has same isbn as existing books is added, all requests for that isbn is removed
        Book validBookCopy = new BookBuilder(validBook).withRequesters().build();
        String expectedMessage = String.format(AddBookCommand.MESSAGE_SUCCESS, validBookCopy)
                + String.format("You should notify %s about availability of %s\n",
                BOB.getName(), validBook.getBookName())
                + String.format("You should notify %s about availability of %s\n",
                ALICE.getName(), validBook.getBookName());
        CommandResult commandResult = new AddBookCommand(validBookCopy).execute(modelStub);
        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validBookCopy, validBookCopy), modelStub.booksAdded);
    }

    @Test
    public void equals() {
        Book harryPotter = new BookBuilder().withName("Harry Potter").build();
        Book hungerGames = new BookBuilder().withName("Hunger Games").build();
        AddBookCommand addHarryPotterCommand = new AddBookCommand(harryPotter);
        AddBookCommand addHungerGamesCommand = new AddBookCommand(hungerGames);

        // same object -> returns true
        assertTrue(addHarryPotterCommand.equals(addHarryPotterCommand));

        // same values -> returns true
        AddBookCommand addHarryPotterCommandCopy = new AddBookCommand(harryPotter);
        assertTrue(addHarryPotterCommand.equals(addHarryPotterCommandCopy));

        // different types -> returns false
        assertFalse(addHarryPotterCommand.equals(1));

        // null -> returns false
        assertFalse(addHarryPotterCommand.equals(null));

        // different book -> returns false
        assertFalse(addHarryPotterCommand.equals(addHungerGamesCommand));

        // different timeAdded for book -> returns true
        Book harryPotter2 = new BookBuilder(harryPotter).withTimeAdded(0).build();
        AddBookCommand addHarryPotterCommand2 = new AddBookCommand(harryPotter2);
        assertTrue(addHarryPotterCommand.equals(addHarryPotterCommand2));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getLibTaskFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setLibTaskFilePath(Path libTaskFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPatron(Patron patron) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addBook(Book book) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setLibTask(ReadOnlyLibTask newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyLibTask getLibTask() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPatron(Patron patron) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasBook(Book book) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasSameIsbnDiffAuthorsOrName(Book bookToCheck) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String deleteAllRequests(Book ... books) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePatron(Patron target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteBook(Book target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPatron(Patron target, Patron editedPatron) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setBook(Book target, Book editedBook) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void returnAllBorrowedBooks(Patron borrower) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isBorrowingSomeBook(Patron borrower) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void borrowBook(Patron borrower, Book bookToBorrow, String returnDate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Patron> getFilteredPatronList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPatronList(Predicate<Patron> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Book> getFilteredBookList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredBookList(Predicate<Book> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single book.
     */
    private class ModelStubWithBook extends ModelStub {
        private final Book book;

        ModelStubWithBook(Book book) {
            requireNonNull(book);
            this.book = book;
        }

        @Override
        public boolean hasBook(Book book) {
            requireNonNull(book);
            return this.book.isSameBook(book);
        }
    }

    /**
     * A Model stub that always accept the book being added.
     */
    private class ModelStubAcceptingBookAdded extends ModelStub {
        final ArrayList<Book> booksAdded = new ArrayList<>();

        @Override
        public boolean hasBook(Book book) {
            requireNonNull(book);
            return booksAdded.stream().anyMatch(book::isSameBook);
        }

        @Override
        public void addBook(Book book) {
            requireNonNull(book);
            booksAdded.add(book);
        }

        @Override
        public boolean hasSameIsbnDiffAuthorsOrName(Book bookToCheck) {
            requireNonNull(bookToCheck);
            return booksAdded.stream().anyMatch(book -> book.hasSameIsbn(bookToCheck) &&
                    (!book.hasSameAuthors(bookToCheck) || !book.hasSameName(bookToCheck)));
        }

        @Override
        public String deleteAllRequests(Book ... books) {
            HashSet<Isbn> notifiedBooks = new HashSet<>(); // To prevent adding the same notification multiple times
            StringBuilder builder = new StringBuilder();
            List<Book> booksToProcess = Arrays.stream(books).collect(Collectors.toList());
            for (Book book : booksToProcess) {
                if (notifiedBooks.contains(book.getIsbn())) {
                    continue;
                }
                notifiedBooks.add(book.getIsbn());
                builder.append(deleteRequestSingleBook(book));
            }
            return builder.toString();
        }

        @Override
        public void setBook(Book target, Book editedBook) {
            requireAllNonNull(target, editedBook);

            int index = booksAdded.indexOf(target);
            if (index == -1) {
                throw new BookNotFoundException();
            }
            booksAdded.set(index, editedBook);
        }

        @Override
        public ReadOnlyLibTask getLibTask() {
            return new LibTask();
        }

        private String deleteRequestSingleBook(Book bookToDelete) {
            StringBuilder builder = new StringBuilder();
            boolean hasNotified = false; // flag to prevent appending the same notification many times
            for (Book book : booksAdded) {
                if (!book.hasSameIsbn(bookToDelete) || book.getRequesters().isEmpty()) {
                    continue;
                }
                if (!hasNotified) {
                    book.getRequesters().stream().forEach(requester ->
                        builder.append(String.format("You should notify %s about availability of %s\n",
                                requester.getName(), book.getBookName())));
                    hasNotified = true;
                }
                Book updatedBookEmptyRequest = book.getBookWithEmptyRequest();
                setBook(book, updatedBookEmptyRequest);
            }
            return builder.toString();
        }
    }
}
