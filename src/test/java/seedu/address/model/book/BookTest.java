package seedu.address.model.book;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AUTHOR_SUZANNE_COLLINS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BOOK_NAME_HARRY_POTTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BOOK_NAME_HUNGER_GAMES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ISBN_HARRY_POTTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ISBN_HARRY_POTTER_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ISBN_HUNGER_GAMES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_SCIFI;
import static seedu.address.model.util.SampleDataUtil.SAMPLE_BOOK_CREATED_TIME;
import static seedu.address.model.util.SampleDataUtil.getSampleBorrowedStatus;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalBooks.HARRY_POTTER;
import static seedu.address.testutil.TypicalBooks.SEMAPHORE;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.tag.Tag;
import seedu.address.testutil.BookBuilder;

public class BookTest {
    private static final BookName VALID_BOOK_NAME = new BookName(VALID_BOOK_NAME_HARRY_POTTER);
    private static final BookStatus VALID_AVAILABLE_STATUS = BookStatus.createAvailableBookStatus();
    private static final Isbn VALID_ISBN = new Isbn(VALID_ISBN_HARRY_POTTER);
    private static final List<Author> VALID_AUTHORS = new ArrayList<>();
    private static final Set<Tag> VALID_TAGS = new HashSet<>();
    private static final BookStatus VALID_BORROWED_STATUS = getSampleBorrowedStatus();

    @Test
    public void constructor_nullBookName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Book(null, VALID_ISBN, VALID_AUTHORS, VALID_TAGS,
                SAMPLE_BOOK_CREATED_TIME, VALID_AVAILABLE_STATUS));
    }

    @Test
    public void constructor_nullIsbn_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Book(VALID_BOOK_NAME, null, VALID_AUTHORS, VALID_TAGS,
                SAMPLE_BOOK_CREATED_TIME, VALID_AVAILABLE_STATUS));
    }

    @Test
    public void constructor_nullAuthors_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Book(VALID_BOOK_NAME, VALID_ISBN, null, VALID_TAGS,
                SAMPLE_BOOK_CREATED_TIME, VALID_AVAILABLE_STATUS));
    }

    @Test
    public void constructor_nullTags_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Book(VALID_BOOK_NAME, VALID_ISBN, VALID_AUTHORS, null,
                SAMPLE_BOOK_CREATED_TIME, VALID_AVAILABLE_STATUS));
    }

    @Test
    public void constructor_nullBookStatus_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Book(VALID_BOOK_NAME, VALID_ISBN, VALID_AUTHORS, VALID_TAGS,
                SAMPLE_BOOK_CREATED_TIME, null));
    }

    @Test
    public void constructor_copyFromOriginalBook_changesBookStatus() {
        Book originalBook = new Book(VALID_BOOK_NAME, VALID_ISBN, VALID_AUTHORS, VALID_TAGS,
                SAMPLE_BOOK_CREATED_TIME, VALID_AVAILABLE_STATUS);
        Book newBook = new Book(originalBook, VALID_BORROWED_STATUS);
        Book expectedBook = new BookBuilder(originalBook).withBookStatus(VALID_BORROWED_STATUS).build();
        assertEquals(newBook, expectedBook);
    }

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Book book = new BookBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> book.getTags().remove(0));
    }

    @Test
    public void isSameBook() {
        // same object -> returns true
        assertTrue(HARRY_POTTER.isSameBook(HARRY_POTTER));

        // null -> returns false
        assertFalse(HARRY_POTTER.isSameBook(null));

        // same isbn, all other attributes different -> returns true
        Book editedAlice = new BookBuilder(HARRY_POTTER).withName(VALID_BOOK_NAME_HUNGER_GAMES)
                .withAuthors(VALID_AUTHOR_SUZANNE_COLLINS)
                .withTags(VALID_TAG_SCIFI).build();
        assertTrue(HARRY_POTTER.isSameBook(editedAlice));

        // different isbn, all other attributes same -> returns false
        editedAlice = new BookBuilder(HARRY_POTTER).withIsbn(VALID_ISBN_HUNGER_GAMES).build();
        assertFalse(HARRY_POTTER.isSameBook(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Book harryPotterCopy = new BookBuilder(HARRY_POTTER).build();
        assertTrue(HARRY_POTTER.equals(harryPotterCopy));

        // same object -> returns true
        assertTrue(HARRY_POTTER.equals(HARRY_POTTER));

        // null -> returns false
        assertFalse(HARRY_POTTER.equals(null));

        // different type -> returns false
        assertFalse(HARRY_POTTER.equals(5));

        // different book -> returns false
        assertFalse(HARRY_POTTER.equals(SEMAPHORE));

        // different name -> returns false
        Book editedHarryPotter = new BookBuilder(HARRY_POTTER).withName(VALID_BOOK_NAME_HUNGER_GAMES).build();
        assertFalse(HARRY_POTTER.equals(editedHarryPotter));

        // different isbn -> returns false
        editedHarryPotter = new BookBuilder(HARRY_POTTER).withIsbn(VALID_ISBN_HUNGER_GAMES).build();
        assertFalse(HARRY_POTTER.equals(editedHarryPotter));

        // same isbn but different hyphen positions -> returns true
        editedHarryPotter = new BookBuilder(HARRY_POTTER).withIsbn(VALID_ISBN_HARRY_POTTER_2).build();
        assertTrue(HARRY_POTTER.equals(editedHarryPotter));

        // different tags -> returns false
        editedHarryPotter = new BookBuilder(HARRY_POTTER).withTags(VALID_TAG_SCIFI).build();
        assertFalse(HARRY_POTTER.equals(editedHarryPotter));

        // different timeAdded -> returns false
        editedHarryPotter = new BookBuilder(HARRY_POTTER).withTimeAdded(0).build();
        assertFalse(HARRY_POTTER.equals(editedHarryPotter));

        // different status -> returns false
        editedHarryPotter = new BookBuilder(HARRY_POTTER).withBookStatus(getSampleBorrowedStatus()).build();
        assertFalse(HARRY_POTTER.equals(editedHarryPotter));
    }

    @Test
    public void isBookAvailable_bookAvailable_returnsTrue() {

        // Book available -> returns true
        assertTrue(() -> new Book(VALID_BOOK_NAME, VALID_ISBN, VALID_AUTHORS, VALID_TAGS, SAMPLE_BOOK_CREATED_TIME,
                VALID_AVAILABLE_STATUS).isAvailable());
    }

    @Test
    public void isBookAvailable_bookBorrowed_returnsFalse() {
        // book borrowed -> return false
        assertFalse(() -> new Book(VALID_BOOK_NAME, VALID_ISBN, VALID_AUTHORS, VALID_TAGS, SAMPLE_BOOK_CREATED_TIME,
                VALID_BORROWED_STATUS).isAvailable());
    }

}
