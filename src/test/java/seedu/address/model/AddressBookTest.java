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
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.book.Book;
import seedu.address.model.person.Patron;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.testutil.BookBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TypicalAddressBook;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getPersonList());
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
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Patron editedAlice = new PersonBuilder(ALICE).withId(VALID_ID_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Patron> newPersons = Arrays.asList(ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newPersons, new ArrayList<>());

        assertThrows(DuplicatePersonException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        assertTrue(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        Patron editedAlice = new PersonBuilder(ALICE).withId(VALID_ID_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getPersonList().remove(0));
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
     * A stub ReadOnlyAddressBook whose persons list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Patron> persons = FXCollections.observableArrayList();
        private final ObservableList<Book> books = FXCollections.observableArrayList();

        AddressBookStub(Collection<Patron> persons, Collection<Book> books) {
            this.persons.setAll(persons);
            this.books.setAll(books);
        }

        @Override
        public ObservableList<Patron> getPersonList() {
            return persons;
        }

        @Override
        public ObservableList<Book> getBookList() {
            return books;
        }
    }

}
