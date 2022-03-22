package seedu.address.logic.commands.patron;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

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
import seedu.address.model.patron.Patron;
import seedu.address.testutil.PatronBuilder;

public class AddPatronCommandTest {

    @Test
    public void constructor_nullPatron_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddPatronCommand(null));
    }

    @Test
    public void execute_patronAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPatronAdded modelStub = new ModelStubAcceptingPatronAdded();
        Patron validPatron = new PatronBuilder().build();

        CommandResult commandResult = new AddPatronCommand(validPatron).execute(modelStub);

        assertEquals(String.format(AddPatronCommand.MESSAGE_SUCCESS, validPatron), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPatron), modelStub.patronsAdded);
    }

    @Test
    public void execute_duplicatePatron_throwsCommandException() {
        Patron validPatron = new PatronBuilder().build();
        AddPatronCommand addCommand = new AddPatronCommand(validPatron);
        ModelStub modelStub = new ModelStubWithPatron(validPatron);

        assertThrows(CommandException.class, AddPatronCommand.MESSAGE_DUPLICATE_PATRON, () ->
            addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Patron alice = new PatronBuilder().withName("Alice").build();
        Patron bob = new PatronBuilder().withName("Bob").build();
        AddPatronCommand addAliceCommand = new AddPatronCommand(alice);
        AddPatronCommand addBobCommand = new AddPatronCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddPatronCommand addAliceCommandCopy = new AddPatronCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different patron -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
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
        public boolean hasSameIsbn(Book bookToCheck) {
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
        public boolean setAndEditBook(Book target, Book editedBook) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String updateBookAfterPatronEdit(Patron target, Patron editedPatron) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String updateBookAfterPatronDelete(Patron deletedPatron) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addRequest(Book bookToRequest, Patron requester) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasAvailableCopy(Book book) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isBorrowing(Patron patron, Book book) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public List<Book> returnAllBorrowedBooks(Patron borrower) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isBorrowingSomeBook(Patron borrower) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasOverdueBooks(Patron patron) {
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
     * A Model stub that contains a single patron.
     */
    private class ModelStubWithPatron extends ModelStub {
        private final Patron patron;

        ModelStubWithPatron(Patron patron) {
            requireNonNull(patron);
            this.patron = patron;
        }

        @Override
        public boolean hasPatron(Patron patron) {
            requireNonNull(patron);
            return this.patron.isSamePatron(patron);
        }
    }

    /**
     * A Model stub that always accept the patron being added.
     */
    private class ModelStubAcceptingPatronAdded extends ModelStub {
        final ArrayList<Patron> patronsAdded = new ArrayList<>();

        @Override
        public boolean hasPatron(Patron patron) {
            requireNonNull(patron);
            return patronsAdded.stream().anyMatch(patron::isSamePatron);
        }

        @Override
        public void addPatron(Patron patron) {
            requireNonNull(patron);
            patronsAdded.add(patron);
        }

        @Override
        public ReadOnlyLibTask getLibTask() {
            return new LibTask();
        }
    }

}
