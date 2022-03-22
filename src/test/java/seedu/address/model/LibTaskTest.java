package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AUTHOR_SUZANNE_COLLINS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BOOK_NAME_HUNGER_GAMES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ISBN_HUNGER_GAMES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_SCIFI;
import static seedu.address.model.util.SampleDataUtil.getSampleBorrowedStatus;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalBooks.HARRY_POTTER;
import static seedu.address.testutil.TypicalBooks.HUNGER_GAMES;
import static seedu.address.testutil.TypicalPatrons.ALICE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.book.Book;
import seedu.address.model.book.BookList;
import seedu.address.model.patron.Patron;
import seedu.address.model.patron.exceptions.DuplicatePatronException;
import seedu.address.testutil.BookBuilder;
import seedu.address.testutil.PatronBuilder;
import seedu.address.testutil.TypicalLibTask;

public class LibTaskTest {

    private final LibTask libTask = new LibTask();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), libTask.getPatronList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> libTask.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyLibTask_replacesData() {
        LibTask newData = TypicalLibTask.getTypicalLibTask();
        libTask.resetData(newData);
        assertEquals(newData, libTask);
    }

    @Test
    public void resetData_withDuplicatePatrons_throwsDuplicatePatronException() {
        // Two patrons with the same identity fields
        Patron editedAlice = new PatronBuilder(ALICE).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Patron> newPatrons = Arrays.asList(ALICE, editedAlice);
        LibTaskStub newData = new LibTaskStub(newPatrons, new ArrayList<>());

        assertThrows(DuplicatePatronException.class, () -> libTask.resetData(newData));
    }

    @Test
    public void hasPatron_nullPatron_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> libTask.hasPatron(null));
    }

    @Test
    public void hasPatron_patronNotInLibTask_returnsFalse() {
        assertFalse(libTask.hasPatron(ALICE));
    }

    @Test
    public void hasPatron_patronInLibTask_returnsTrue() {
        libTask.addPatron(ALICE);
        assertTrue(libTask.hasPatron(ALICE));
    }

    @Test
    public void hasPatron_patronWithSameIdentityFieldsInLibTask_returnsTrue() {
        libTask.addPatron(ALICE);
        Patron editedAlice = new PatronBuilder(ALICE).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(libTask.hasPatron(editedAlice));
    }

    @Test
    public void getPatronList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> libTask.getPatronList().remove(0));
    }

    @Test
    public void hasBook_nullBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> libTask.hasBook(null));
    }

    @Test
    public void hasBook_bookNotInLibTask_returnsFalse() {
        assertFalse(libTask.hasBook(HARRY_POTTER));
    }

    @Test
    public void hasBook_bookInLibTask_returnsTrue() {
        libTask.addBook(HARRY_POTTER);
        assertTrue(libTask.hasBook(HARRY_POTTER));
    }

    @Test
    public void hasBook_bookWithSameIdentityFieldsInLibTask_returnsTrue() {
        libTask.addBook(HARRY_POTTER);
        Book editedHarryPotter = new BookBuilder(HARRY_POTTER)
                .withName(VALID_BOOK_NAME_HUNGER_GAMES)
                .withAuthors(VALID_AUTHOR_SUZANNE_COLLINS)
                .withTags(VALID_TAG_SCIFI)
                .build();
        assertTrue(libTask.hasBook(editedHarryPotter));
    }

    @Test
    public void hasSameIsbnDiffAuthorsOrName_nullBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> libTask.hasSameIsbnDiffAuthorsOrName(null));
    }

    @Test
    public void hasSameIsbnDiffAuthorsOrName_hasInconsistentBook_returnsTrue() {
        libTask.addBook(HARRY_POTTER);
        Book inconsistentBook = new BookBuilder(HARRY_POTTER).withName("diffname").build();
        assertTrue(libTask.hasSameIsbnDiffAuthorsOrName(inconsistentBook));
    }

    @Test
    public void hasSameIsbnDiffAuthorsOrName_consistentBook_returnsFalse() {
        libTask.addBook(HARRY_POTTER);
        // book with same name but different isbn is consistent
        Book consistentBook = new BookBuilder(HARRY_POTTER).withIsbn(VALID_ISBN_HUNGER_GAMES).build();
        assertFalse(libTask.hasSameIsbnDiffAuthorsOrName(consistentBook));
    }

    @Test
    public void setAndEditBook_nullTarget_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> libTask.setAndEditBook(null, HARRY_POTTER));
    }

    @Test
    public void setAndEditBook_nullTEditedBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> libTask.setAndEditBook(HARRY_POTTER, null));
    }

    @Test
    public void updateBookAfterPatronEdit_someFieldsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> libTask.updateBookAfterPatronEdit(null, ALICE));
        assertThrows(NullPointerException.class, () -> libTask.updateBookAfterPatronEdit(ALICE,null));
    }

    @Test
    public void updateBookAfterPatronDelete_nullDeletedPatron_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> libTask.updateBookAfterPatronDelete(null));
    }

    @Test
    public void addRequest_someFieldsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> libTask.addRequest(null, ALICE));
        assertThrows(NullPointerException.class, () -> libTask.addRequest(HARRY_POTTER, null));
    }

    @Test
    public void addRequest_hasBookWithDesiredIsbn_requestAdded() {
        libTask.addBook(HARRY_POTTER);
        Book bookWithRequest = new BookBuilder(HARRY_POTTER).withRequesters(ALICE).build();
        LibTask expectedLibTask = new LibTask();
        expectedLibTask.addBook(bookWithRequest);

        Book bookWithSameIsbnOnly = new BookBuilder(HARRY_POTTER).withName(VALID_BOOK_NAME_HUNGER_GAMES).withTags()
                .withAuthors(VALID_AUTHOR_SUZANNE_COLLINS).build();
        libTask.addRequest(bookWithSameIsbnOnly, ALICE);
        assertEquals(expectedLibTask, libTask);
    }

    @Test
    public void addRequest_hasNoBookWithDesiredIsbn_requestNotAdded() {
        libTask.addBook(HARRY_POTTER);
        LibTask expectedLibTask = new LibTask();
        expectedLibTask.addBook(HARRY_POTTER);

        Book bookWithDifferentIsbn = new BookBuilder(HARRY_POTTER).withIsbn(VALID_ISBN_HUNGER_GAMES).build();
        libTask.addRequest(bookWithDifferentIsbn, ALICE);
        assertEquals(expectedLibTask, libTask);
    }

    @Test
    public void hasAvailableCopy_nullBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> libTask.hasAvailableCopy(null));
    }

    @Test
    public void hasAvailableCopy_hasAvailableSameIsbnCopy_returnsTrue() {
        // has available copy with same isbn -> returns true
        Book bookWithSameIsbnOnly = new BookBuilder(HARRY_POTTER).withName(VALID_BOOK_NAME_HUNGER_GAMES).withTags()
                .withAuthors(VALID_AUTHOR_SUZANNE_COLLINS).build();
        libTask.addBook(bookWithSameIsbnOnly);
        assertTrue(libTask.hasAvailableCopy(HARRY_POTTER));
    }

    @Test
    public void hasAvailableCopy_hasAvailableDifferentIsbnCopy_returnsFalse() {
        // has available copy with different isbn -> returns false
        Book bookWithDifferentIsbn = new BookBuilder(HARRY_POTTER).withIsbn(VALID_ISBN_HUNGER_GAMES).build();
        libTask.addBook(bookWithDifferentIsbn);
        assertFalse(libTask.hasAvailableCopy(HARRY_POTTER));
    }

    @Test
    public void hasAvailableCopy_allCopiesBorrowed_returnsFalse() {
        // has available copy with different isbn -> returns false
        Book borrowedBookWithSameIsbn = new BookBuilder(HARRY_POTTER).withBookStatus(getSampleBorrowedStatus()).build();
        libTask.addBook(borrowedBookWithSameIsbn);
        assertFalse(libTask.hasAvailableCopy(HARRY_POTTER));
    }

    @Test
    public void isBorrowing_someFieldsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> libTask.isBorrowing(null, HARRY_POTTER));
        assertThrows(NullPointerException.class, () -> libTask.isBorrowing(ALICE, null));
    }

    @Test
    public void hasSameIsbn_noSameIsbn_returnsFalse() {
        libTask.addBook(HARRY_POTTER);
        assertFalse(libTask.hasSameIsbn(HUNGER_GAMES));
    }

    @Test
    public void hasSameIsbn_hasSameIsbn_returnsTrue() {
        libTask.addBook(HARRY_POTTER);
        Book allFieldsDifferentExceptIsbn = new BookBuilder(HARRY_POTTER).withName(VALID_BOOK_NAME_HUNGER_GAMES)
                .withTags().withAuthors().withRequesters(ALICE).withBookStatus(getSampleBorrowedStatus())
                .withTimeAdded(12345).build();
        assertTrue(libTask.hasSameIsbn(allFieldsDifferentExceptIsbn));
    }

    @Test
    public void getBookList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> libTask.getBookList().remove(0));
    }

    /**
     * A stub ReadOnlyLibTask whose patrons list can violate interface constraints.
     */
    private static class LibTaskStub implements ReadOnlyLibTask {
        private final ObservableList<Patron> patrons = FXCollections.observableArrayList();
        private final ObservableList<Book> books = FXCollections.observableArrayList();

        LibTaskStub(Collection<Patron> patrons, Collection<Book> books) {
            this.patrons.setAll(patrons);
            this.books.setAll(books);
        }

        @Override
        public ObservableList<Patron> getPatronList() {
            return patrons;
        }

        @Override
        public ObservableList<Book> getBookList() {
            return books;
        }
    }

}
