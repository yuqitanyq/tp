package seedu.address.model.book;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.HashSet;

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

    @Test
    public void equals() {
        // Differ by spaces -> returns true
        assertEquals(new Author("jk rowling"), new Author("j k rowling"));
        // Differ by case -> returns true
        assertEquals(new Author("j K Rowling"), new Author("j k rowling"));
        // Differ by punctuation -> returns false
        assertNotEquals(new Author("J. K. Rowling"), new Author("J K Rowling"));
    }

    @Test
    public void hashCodeTest() {
        // Differ by spaces -> returns true
        assertEquals(new Author("jk rowling").hashCode(), new Author("j k rowling").hashCode());
        // Differ by case -> returns true
        assertEquals(new Author("j K Rowling").hashCode(), new Author("j k rowling").hashCode());
        // Differ by punctuation -> returns false
        assertNotEquals(new Author("J. K. Rowling").hashCode(), new Author("J K Rowling").hashCode());

        // Testing equality on HashSet
        HashSet<Author> set1 = new HashSet<>();
        set1.add(new Author("jk rowling"));
        HashSet<Author> set2 = new HashSet<>();
        set2.add(new Author("j k rowling"));
        assertEquals(set1, set2);

        // Testing equality on lists
        ArrayList<Author> list1 = new ArrayList<>();
        list1.add(new Author("jk rowling"));
        list1.add(new Author("suzanne collins"));
        ArrayList<Author> list2 = new ArrayList<>();
        list2.add(new Author("j k rowling"));
        list2.add(new Author("Suzanne Collins"));
        assertEquals(list1, list2);
        assertEquals(list1.hashCode(), list2.hashCode());
    }
}
