package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AUTHOR_SUZANNE_COLLINS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BOOK_NAME_HUNGER_GAMES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_BOB;
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
import seedu.address.testutil.TypicalAddressBook;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getPatronList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = TypicalAddressBook.getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicatePatrons_throwsDuplicatePatronException() {
        // Two patrons with the same identity fields
        Patron editedAlice = new PatronBuilder(ALICE).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Patron> newPatrons = Arrays.asList(ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newPatrons, new ArrayList<>());

        assertThrows(DuplicatePatronException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasPatron_nullPatron_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasPatron(null));
    }

    @Test
    public void hasPatron_patronNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasPatron(ALICE));
    }

    @Test
    public void hasPatron_patronInAddressBook_returnsTrue() {
        addressBook.addPatron(ALICE);
        assertTrue(addressBook.hasPatron(ALICE));
    }

    @Test
    public void hasPatron_patronWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addPatron(ALICE);
        Patron editedAlice = new PatronBuilder(ALICE).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasPatron(editedAlice));
    }

    @Test
    public void getPatronList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getPatronList().remove(0));
    }

    @Test
    public void hasBook_nullBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasBook(null));
    }

    @Test
    public void hasBook_bookNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasBook(HARRY_POTTER));
    }

    @Test
    public void hasBook_bookInAddressBook_returnsTrue() {
        addressBook.addBook(HARRY_POTTER);
        assertTrue(addressBook.hasBook(HARRY_POTTER));
    }

    @Test
    public void hasBook_bookWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addBook(HARRY_POTTER);
        Book editedHarryPotter = new BookBuilder(HARRY_POTTER)
                .withName(VALID_BOOK_NAME_HUNGER_GAMES)
                .withAuthors(VALID_AUTHOR_SUZANNE_COLLINS)
                .withTags(VALID_TAG_SCIFI)
                .build();
        assertTrue(addressBook.hasBook(editedHarryPotter));
    }

    @Test
    public void getBookList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getBookList().remove(0));
    }

    /**
     * A stub ReadOnlyAddressBook whose patrons list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Patron> patrons = FXCollections.observableArrayList();
        private final ObservableList<Book> books = FXCollections.observableArrayList();

        AddressBookStub(Collection<Patron> patrons, Collection<Book> books) {
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
