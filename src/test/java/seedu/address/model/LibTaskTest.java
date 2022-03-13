package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AUTHOR_SUZANNE_COLLINS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BOOK_NAME_HUNGER_GAMES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_SCIFI;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalBooks.HARRY_POTTER;
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
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        LibTask newData = TypicalLibTask.getTypicalAddressBook();
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
    public void hasPatron_patronNotInAddressBook_returnsFalse() {
        assertFalse(libTask.hasPatron(ALICE));
    }

    @Test
    public void hasPatron_patronInAddressBook_returnsTrue() {
        libTask.addPatron(ALICE);
        assertTrue(libTask.hasPatron(ALICE));
    }

    @Test
    public void hasPatron_patronWithSameIdentityFieldsInAddressBook_returnsTrue() {
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
    public void hasBook_bookNotInAddressBook_returnsFalse() {
        assertFalse(libTask.hasBook(HARRY_POTTER));
    }

    @Test
    public void hasBook_bookInAddressBook_returnsTrue() {
        libTask.addBook(HARRY_POTTER);
        assertTrue(libTask.hasBook(HARRY_POTTER));
    }

    @Test
    public void hasBook_bookWithSameIdentityFieldsInAddressBook_returnsTrue() {
        libTask.addBook(HARRY_POTTER);
        Book editedHarryPotter = new BookBuilder(HARRY_POTTER)
                .withName(VALID_BOOK_NAME_HUNGER_GAMES)
                .withAuthors(VALID_AUTHOR_SUZANNE_COLLINS)
                .withTags(VALID_TAG_SCIFI)
                .build();
        assertTrue(libTask.hasBook(editedHarryPotter));
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
