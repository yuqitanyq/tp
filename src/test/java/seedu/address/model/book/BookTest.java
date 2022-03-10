package seedu.address.model.book;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AUTHOR_SUZANNE_COLLINS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BOOK_NAME_HUNGER_GAMES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ISBN_HARRY_POTTER_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ISBN_HUNGER_GAMES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_SCIFI;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalBooks.HARRY_POTTER;
import static seedu.address.testutil.TypicalBooks.SEMAPHORE;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.BookBuilder;

public class BookTest {

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
    }
}
