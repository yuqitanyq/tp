package seedu.address.model.book;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AUTHOR_SUZANNE_COLLINS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BOOK_NAME_HUNGER_GAMES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ISBN_HUNGER_GAMES;
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
