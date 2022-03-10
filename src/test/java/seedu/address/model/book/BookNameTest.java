package seedu.address.model.book;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class BookNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new BookName(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new BookName(invalidName));
    }

    @Test
    public void isValidBookName() {
        // null book name
        assertThrows(NullPointerException.class, () -> BookName.isValidBookName(null));

        // invalid book name
        assertFalse(BookName.isValidBookName("")); // empty string
        assertFalse(BookName.isValidBookName(" ")); // spaces only
        assertFalse(BookName.isValidBookName("^")); // only non-alphanumeric characters
        assertFalse(BookName.isValidBookName("Harry&Potter")); // contains non-alphanumeric characters not : or '
        assertFalse(BookName.isValidBookName(" HarryPotter")); // starts with non-alphanumeric character

        // valid name
        assertTrue(BookName.isValidBookName("Harry Potter")); //alphabets only
        assertTrue(BookName.isValidBookName("123")); // numbers only
        assertTrue(BookName.isValidBookName("Harry Potter 2")); // alphanumeric characters
        assertTrue(BookName.isValidBookName("Harry Potter 1 The Philosopher's Stone")); // with ' character
        assertTrue(BookName.isValidBookName("Algorithms: 1st Edition")); // with : character
    }
}

