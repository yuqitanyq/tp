package seedu.address.model.book;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AUTHOR_SUZANNE_COLLINS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BOOK_NAME_HUNGER_GAMES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ISBN_HUNGER_GAMES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RETURN_DATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_SCIFI;
import static seedu.address.model.util.SampleDataUtil.getSampleBorrowedStatus;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalBooks.AI;
import static seedu.address.testutil.TypicalBooks.HARRY_POTTER;
import static seedu.address.testutil.TypicalBooks.HUNGER_GAMES;
import static seedu.address.testutil.TypicalPatrons.ALICE;
import static seedu.address.testutil.TypicalPatrons.getTypicalPatrons;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.model.book.exceptions.BookNotFoundException;
import seedu.address.model.patron.Patron;
import seedu.address.testutil.BookBuilder;
import seedu.address.testutil.PatronBuilder;

public class BookListTest {

    private final BookList bookList = new BookList();

    @Test
    public void contains_nullBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> bookList.contains(null));
    }

    @Test
    public void contains_bookNotInList_returnsFalse() {
        assertFalse(bookList.contains(HARRY_POTTER));
    }

    @Test
    public void contains_bookInList_returnsTrue() {
        bookList.add(HARRY_POTTER);
        assertTrue(bookList.contains(HARRY_POTTER));
    }

    @Test
    public void contains_patronWithSameIdentityFieldsInList_returnsTrue() {
        bookList.add(HARRY_POTTER);
        Book editedHarryPotter = new BookBuilder(HARRY_POTTER).withName(VALID_BOOK_NAME_HUNGER_GAMES)
                .withAuthors(VALID_AUTHOR_SUZANNE_COLLINS)
                .withTags(VALID_TAG_SCIFI)
                .build();
        assertTrue(bookList.contains(editedHarryPotter));
    }

    @Test
    public void add_nullBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> bookList.add(null));
    }

    @Test
    public void setBook_nullTargetBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> bookList.setBook(null, HARRY_POTTER));
    }

    @Test
    public void setBook_nullEditedBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> bookList.setBook(HARRY_POTTER, null));
    }

    @Test
    public void setBook_targetBookNotInList_throwsBookNotFoundException() {
        assertThrows(BookNotFoundException.class, () -> bookList.setBook(HARRY_POTTER, HARRY_POTTER));
    }

    @Test
    public void setBook_editedBookIsSameBook_success() {
        bookList.add(HARRY_POTTER);
        bookList.setBook(HARRY_POTTER, HARRY_POTTER);
        BookList expectedBookList = new BookList();
        expectedBookList.add(HARRY_POTTER);
        assertEquals(expectedBookList, bookList);
    }

    @Test
    public void setBook_editedBookHasSameIdentity_success() {
        bookList.add(HARRY_POTTER);
        Book editedHarryPotter = new BookBuilder(HARRY_POTTER).withAuthors(VALID_AUTHOR_SUZANNE_COLLINS)
                .withTags(VALID_TAG_SCIFI)
                .build();
        bookList.setBook(HARRY_POTTER, editedHarryPotter);
        BookList expectedBookList = new BookList();
        expectedBookList.add(editedHarryPotter);
        assertEquals(expectedBookList, bookList);
    }

    @Test
    public void setBook_editedBookHasDifferentIdentity_success() {
        bookList.add(HARRY_POTTER);
        bookList.setBook(HARRY_POTTER, HUNGER_GAMES);
        BookList expectedBookList = new BookList();
        expectedBookList.add(HUNGER_GAMES);
        assertEquals(expectedBookList, bookList);
    }

    @Test
    public void updateBookAfterPatronEdit_someFieldsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> bookList.updateBookAfterPatronEdit(null, ALICE));
        assertThrows(NullPointerException.class, () -> bookList.updateBookAfterPatronEdit(ALICE, null));
    }

    @Test
    public void updateBookAfterPatronEdit_editedPersonHasBorrowedBooks_booksUpdated() {
        Patron patronToEdit = ALICE;
        BookStatus dummyStatus = new BookStatus(BookStatusType.BORROWED, Optional.of(patronToEdit),
                Optional.of("22-May-2022"), Optional.of("31-Dec-2022"));
        Book borrowedBook = new BookBuilder(HARRY_POTTER).withBookStatus(dummyStatus).build();
        bookList.add(borrowedBook);

        Patron editedPatron = new PatronBuilder(patronToEdit).withName(VALID_NAME_BOB).build();
        BookStatus updatedStatus = dummyStatus.editBorrower(editedPatron);
        Book editedBorrowedBook = new BookBuilder(borrowedBook).withBookStatus(updatedStatus).build();

        String expectedMessage = String.format("Borrower information of %s is also edited in some books\n",
                editedPatron.getName());
        BookList expectedBookList = new BookList();
        expectedBookList.add(editedBorrowedBook);

        assertEquals(bookList.updateBookAfterPatronEdit(patronToEdit, editedPatron), expectedMessage);
        assertEquals(bookList, expectedBookList);
    }

    @Test
    public void updateBookAfterPatronEdit_editedPersonHasRequestedBooks_booksUpdated() {
        Patron patronToEdit = ALICE;
        Book requestedBook = new BookBuilder(HARRY_POTTER).withRequesters(patronToEdit).build();
        bookList.add(requestedBook);

        Patron editedPatron = new PatronBuilder(patronToEdit).withName(VALID_NAME_BOB).build();
        Book editedRequestedBook = new BookBuilder(requestedBook).withRequesters(editedPatron).build();

        String expectedMessage = String.format("Requester information of %s is also edited in some books\n",
                editedPatron.getName());
        BookList expectedBookList = new BookList();
        expectedBookList.add(editedRequestedBook);

        assertEquals(bookList.updateBookAfterPatronEdit(patronToEdit, editedPatron), expectedMessage);
        assertEquals(bookList, expectedBookList);
    }

    @Test
    public void updateBookAfterPatronDelete_nullDeletedPatron_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> bookList.updateBookAfterPatronDelete(null));
    }

    @Test
    public void updateBookAfterPatronDelete_deletedPatronHasRequestedBooks_booksUpdated() {
        Patron patronToDelete = ALICE;
        Book requestedBook = new BookBuilder(HARRY_POTTER).withRequesters(patronToDelete).build();
        bookList.add(requestedBook);

        Book editedRequestedBook = new BookBuilder(requestedBook).withRequesters().build();

        String expectedMessage = String.format("%s is also deleted from the requesters list of some books\n",
                patronToDelete.getName());
        BookList expectedBookList = new BookList();
        expectedBookList.add(editedRequestedBook);

        assertEquals(bookList.updateBookAfterPatronDelete(patronToDelete), expectedMessage);
        assertEquals(bookList, expectedBookList);
    }

    @Test
    public void addRequest_someFieldsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> bookList.addRequest(null, ALICE));
        assertThrows(NullPointerException.class, () -> bookList.addRequest(HARRY_POTTER, null));
    }

    @Test
    public void addRequest_hasBookWithDesiredIsbn_requestAdded() {
        bookList.add(HARRY_POTTER);
        Book bookWithRequest = new BookBuilder(HARRY_POTTER).withRequesters(ALICE).build();
        BookList expectedBookList = new BookList();
        expectedBookList.add(bookWithRequest);

        Book bookWithSameIsbnOnly = new BookBuilder(HARRY_POTTER).withName(VALID_BOOK_NAME_HUNGER_GAMES).withTags()
                .withAuthors(VALID_AUTHOR_SUZANNE_COLLINS).build();
        bookList.addRequest(bookWithSameIsbnOnly, ALICE);
        assertEquals(expectedBookList, bookList);
    }

    @Test
    public void addRequest_hasNoBookWithDesiredIsbn_requestNotAdded() {
        bookList.add(HARRY_POTTER);
        BookList expectedBookList = new BookList();
        expectedBookList.add(HARRY_POTTER);

        Book bookWithDifferentIsbn = new BookBuilder(HARRY_POTTER).withIsbn(VALID_ISBN_HUNGER_GAMES).build();
        bookList.addRequest(bookWithDifferentIsbn, ALICE);
        assertEquals(expectedBookList, bookList);
    }

    @Test
    public void hasAvailableCopy_nullBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> bookList.hasAvailableCopy(null));
    }

    @Test
    public void hasAvailableCopy_hasAvailableSameIsbnCopy_returnsTrue() {
        // has available copy with same isbn -> returns true
        Book bookWithSameIsbnOnly = new BookBuilder(HARRY_POTTER).withName(VALID_BOOK_NAME_HUNGER_GAMES).withTags()
                .withAuthors(VALID_AUTHOR_SUZANNE_COLLINS).build();
        bookList.add(bookWithSameIsbnOnly);
        assertTrue(bookList.hasAvailableCopy(HARRY_POTTER));
    }

    @Test
    public void hasAvailableCopy_hasAvailableDifferentIsbnCopy_returnsFalse() {
        // has available copy with different isbn -> returns false
        Book bookWithDifferentIsbn = new BookBuilder(HARRY_POTTER).withIsbn(VALID_ISBN_HUNGER_GAMES).build();
        bookList.add(bookWithDifferentIsbn);
        assertFalse(bookList.hasAvailableCopy(HARRY_POTTER));
    }

    @Test
    public void hasAvailableCopy_allCopiesBorrowed_returnsFalse() {
        // has available copy with different isbn -> returns false
        Book borrowedBookWithSameIsbn = new BookBuilder(HARRY_POTTER).withBookStatus(getSampleBorrowedStatus()).build();
        bookList.add(borrowedBookWithSameIsbn);
        assertFalse(bookList.hasAvailableCopy(HARRY_POTTER));
    }

    @Test
    public void isBorrowing_someFieldsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> bookList.isBorrowing(null, HARRY_POTTER));
        assertThrows(NullPointerException.class, () -> bookList.isBorrowing(ALICE, null));
    }

    @Test
    public void hasSameIsbn_noSameIsbn_returnsFalse() {
        bookList.add(HARRY_POTTER);
        assertFalse(bookList.hasSameIsbn(HUNGER_GAMES));
    }

    @Test
    public void hasSameIsbn_hasSameIsbn_returnsTrue() {
        bookList.add(HARRY_POTTER);
        Book allFieldsDifferentExceptIsbn = new BookBuilder(HARRY_POTTER).withName(VALID_BOOK_NAME_HUNGER_GAMES)
                .withTags().withAuthors().withRequesters(ALICE).withBookStatus(getSampleBorrowedStatus())
                .withTimeAdded(12345).build();
        assertTrue(bookList.hasSameIsbn(allFieldsDifferentExceptIsbn));
    }

    @Test
    public void returnAllBorrowedBooks() {
        bookList.add(HARRY_POTTER);
        bookList.add(AI);
        BookList expectedBookList = new BookList();
        expectedBookList.add(HARRY_POTTER);
        expectedBookList.add(new Book(AI, BookStatus.createAvailableBookStatus()));
        bookList.returnAllBorrowedBooks(getTypicalPatrons().get(0));
        assertEquals(expectedBookList, bookList);
    }

    @Test
    public void hasSameIsbnDiffAuthorsOrName() {
        bookList.add(HARRY_POTTER);

        // same isbn but different name -> returns true
        Book book2 = new BookBuilder(HARRY_POTTER).withName("diff name").build();
        assertTrue(bookList.hasSameIsbnDiffAuthorsOrName(book2));

        // same isbn but different authors -> returns true
        book2 = new BookBuilder(HARRY_POTTER).withAuthors("some author").build();
        assertTrue(bookList.hasSameIsbnDiffAuthorsOrName(book2));

        // different isbn, but same authors and book name is allowed -> returns false
        book2 = new BookBuilder(HARRY_POTTER).withIsbn(VALID_ISBN_HUNGER_GAMES).build();
        assertFalse(bookList.hasSameIsbnDiffAuthorsOrName(book2));
    }

    @Test
    public void isBorrowingSomeBook_nullBorrower_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> bookList.isBorrowingSomeBook(null));
    }

    @Test
    public void isBorrowingSomeBook_borrowsNothing_returnsFalse() {
        Patron borrower = getTypicalPatrons().get(2);
        bookList.add(HARRY_POTTER);
        assertFalse(bookList.isBorrowingSomeBook(borrower));
    }

    @Test
    public void isBorrowingSomeBook_hasBorrowedBook_returnsTrue() {
        Patron borrower = getTypicalPatrons().get(0);
        bookList.add(AI);
        assertTrue(bookList.isBorrowingSomeBook(borrower));
    }

    @Test
    public void borrowBook_someFieldsNull_throwsBookNotFoundException() {
        bookList.add(HARRY_POTTER);
        // null borrower
        assertThrows(NullPointerException.class, () ->
                bookList.borrowBook(null, HARRY_POTTER, VALID_RETURN_DATE));
        // null borrower
        assertThrows(NullPointerException.class, () ->
                bookList.borrowBook(ALICE, null, VALID_RETURN_DATE));
        // null return date
        assertThrows(NullPointerException.class, () ->
                bookList.borrowBook(ALICE, HARRY_POTTER, null));
    }

    @Test
    public void borrowBook_validInputs_success() {
        bookList.add(HARRY_POTTER);
        Patron borrower = ALICE;
        BookStatus borrowedStatus = new BookStatus(BookStatusType.BORROWED,
                Optional.of(borrower), Optional.of(BookStatus.getCurrentDateString()), Optional.of(VALID_RETURN_DATE));
        Book editedHarryPotter = new BookBuilder(HARRY_POTTER).withBookStatus(borrowedStatus).build();
        bookList.borrowBook(borrower, HARRY_POTTER, VALID_RETURN_DATE);

        // no longer contains the old available book
        assertFalse(bookList.asUnmodifiableObservableList().contains(HARRY_POTTER));
        // contains the new borrowed book
        assertTrue(bookList.asUnmodifiableObservableList().contains(editedHarryPotter));
    }

    @Test
    public void remove_nullBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> bookList.remove(null));
    }

    @Test
    public void remove_bookDoesNotExist_throwsBookNotFoundException() {
        assertThrows(BookNotFoundException.class, () -> bookList.remove(HARRY_POTTER));
    }

    @Test
    public void remove_existingBook_removesBook() {
        bookList.add(HARRY_POTTER);
        bookList.remove(HARRY_POTTER);
        BookList expectedBookList = new BookList();
        assertEquals(expectedBookList, bookList);
    }

    @Test
    public void setBooks_nullBookList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> bookList.setBooks((BookList) null));
    }

    @Test
    public void setBooks_bookList_replacesOwnListWithProvidedBookList() {
        bookList.add(HARRY_POTTER);
        BookList expectedBookList = new BookList();
        expectedBookList.add(HUNGER_GAMES);
        bookList.setBooks(expectedBookList);
        assertEquals(expectedBookList, bookList);
    }

    @Test
    public void setBooks_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> bookList.setBooks((List<Book>) null));
    }

    @Test
    public void setBooks_list_replacesOwnListWithProvidedList() {
        bookList.add(HARRY_POTTER);
        List<Book> listOfBooks = Collections.singletonList(HUNGER_GAMES);
        bookList.setBooks(listOfBooks);
        BookList expectedBookList = new BookList();
        expectedBookList.add(HUNGER_GAMES);
        assertEquals(expectedBookList, bookList);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> bookList.asUnmodifiableObservableList().remove(0));
    }
}
