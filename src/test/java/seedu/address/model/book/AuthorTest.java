package seedu.address.model.book;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AuthorTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Author(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Author(invalidName));
    }

    @Test
    public void isValidAuthor() {
        // null author
        assertThrows(NullPointerException.class, () -> Author.isValidAuthor(null));

        // invalid author
        assertFalse(Author.isValidAuthor("")); // empty string
        assertFalse(Author.isValidAuthor(" ")); // spaces only
        assertFalse(Author.isValidAuthor("^")); // only non-alphanumeric characters
        assertFalse(Author.isValidAuthor("J&KRowling")); // contains non-alphanumeric character that is not dot
        assertFalse(Author.isValidAuthor(" JKRowling")); // starts with non-alphanumeric character

        // valid author
        assertTrue(Author.isValidAuthor("jkrowling")); //alphabets only
        assertTrue(Author.isValidAuthor("123")); // numbers only
        assertTrue(Author.isValidAuthor("Queen Elizabeth 1")); // alphanumeric characters
        assertTrue(Author.isValidAuthor("J.K.Rowling")); // with dot character
    }
}
