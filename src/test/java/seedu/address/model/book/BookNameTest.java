package seedu.address.model.book;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.HashSet;

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

        // invalid book name but should be valid books
        assertFalse(BookName.isValidBookName("Who Moved My Cheese?")); // ends with "?"
        assertFalse(BookName.isValidBookName("4.50_from_Paddington")); // contains "."
        assertFalse(BookName.isValidBookName("11/22/63")); // contains "/"
        assertFalse(BookName.isValidBookName("Winnie-the-Pooh")); // contains "-"

        // valid name
        assertTrue(BookName.isValidBookName("Harry Potter")); //alphabets only
        assertTrue(BookName.isValidBookName("123")); // numbers only
        assertTrue(BookName.isValidBookName("Harry Potter 2")); // alphanumeric characters
        assertTrue(BookName.isValidBookName("Harry Potter 1 The Philosopher's Stone")); // with ' character
        assertTrue(BookName.isValidBookName("Algorithms: 1st Edition")); // with : character
    }

    @Test
    public void equals() {
        // Differ by spaces -> returns true
        assertEquals(new BookName("harrypotter"), new BookName("harry potter"));
        // Differ by case -> returns true
        assertEquals(new BookName("harry potter"), new BookName("Harry Potter"));
        // Differ by punctuation -> returns false
        assertNotEquals(new BookName("harry:potter"), new BookName("harry potter"));
    }

    @Test
    public void hashCodeTest() {
        // Differ by spaces -> returns true
        assertEquals(new BookName("harrypotter").hashCode(), new BookName("harry potter").hashCode());
        // Differ by case -> returns true
        assertEquals(new BookName("harry potter").hashCode(), new BookName("Harry Potter").hashCode());
        // Differ by punctuation -> returns false
        assertNotEquals(new BookName("harry:potter").hashCode(), new BookName("harry potter").hashCode());

        // Testing equality on HashSet
        HashSet<BookName> set1 = new HashSet<>();
        set1.add(new BookName("harrypotter"));
        HashSet<BookName> set2 = new HashSet<>();
        set2.add(new BookName("Harry Potter"));
        assertEquals(set1, set2);

        // Testing equality on lists
        ArrayList<BookName> list1 = new ArrayList<>();
        list1.add(new BookName("harrypotter"));
        list1.add(new BookName("hunger games"));
        ArrayList<BookName> list2 = new ArrayList<>();
        list2.add(new BookName("harry potter"));
        list2.add(new BookName("Hunger Games"));
        assertEquals(list1, list2);
        assertEquals(list1.hashCode(), list2.hashCode());
    }
}

