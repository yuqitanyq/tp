package seedu.address.model.book;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class IsbnTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Isbn(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Isbn(invalidName));
    }

    @Test
    public void isValidIsbn_invalidFormat_returnsFalse() {
        // null isbn
        assertThrows(NullPointerException.class, () -> Isbn.isValidIsbn(null));

        // isbn with invalid string pattern
        assertFalse(Isbn.isValidIsbn("")); // empty string
        assertFalse(Isbn.isValidIsbn(" ")); // spaces only
        assertFalse(Isbn.isValidIsbn("false")); // contains non-numeric characters that are not hyphen
        assertFalse(Isbn.isValidIsbn("978--71617-018-8-5")); // contains consecutive hyphen characters
        assertFalse(Isbn.isValidIsbn("-978-71617-018-8-5")); // starts with non-numeric character
        assertFalse(Isbn.isValidIsbn("978-71617-018-8-5-")); // ends with non-numeric character
        assertFalse(Isbn.isValidIsbn("978-71617-018-8-4")); // wrong check sum for ISBN13 format
        assertFalse(Isbn.isValidIsbn("17-18-13730-4")); // wrong check sum for ISBN10 format
        assertFalse(Isbn.isValidIsbn("980-71617-018-8-4")); // does not start with 978 or 979 for ISBN13 format
        assertFalse(Isbn.isValidIsbn("978-71617-0184")); // wrong number of digits
    }

    @Test
    public void isValidIsbn_validFormat_returnsTrue() {
        assertTrue(Isbn.isValidIsbn("978-71617-018-8-5")); // ISBN13 with hyphen
        assertTrue(Isbn.isValidIsbn("9787161701885")); // ISBN13 without hyphen
        assertTrue(Isbn.isValidIsbn("17-18-13730-3")); // ISBN10 with hyphen
        assertTrue(Isbn.isValidIsbn("1718137303")); // ISBN10 without hyphen
        assertTrue(Isbn.isValidIsbn("97-971617-018-8-4")); // ISBN13 starting with 979
    }

    @Test
    public void equals() {
        // Differ hyphen arrangements -> returns true
        assertEquals(new Isbn("978-71617-018-8-5"), new Isbn("9-7-8-71617-018-8-5"));
        // Differ strings after hyphen removal -> returns false
        assertNotEquals(new Isbn("978-71617-018-8-5"), new Isbn("17-18-13730-3"));
    }

    @Test
    public void hashCodeTest() {
        // Differ hyphen arrangements -> returns true
        assertEquals(new Isbn("978-71617-018-8-5").hashCode(), new Isbn("9-7-8-71617-018-8-5").hashCode());
        // Differ strings after hyphen removal -> returns false
        assertNotEquals(new Isbn("978-71617-018-8-5").hashCode(), new Isbn("17-18-13730-3").hashCode());

        // Testing equality on HashSet
        HashSet<Isbn> set1 = new HashSet<>();
        set1.add(new Isbn("978-71617-018-8-5"));
        HashSet<Isbn> set2 = new HashSet<>();
        set2.add(new Isbn("9-7-8-71617-018-8-5"));
        assertEquals(set1, set2);
        HashSet<Isbn> set3 = new HashSet<>();
        set1.add(new Isbn("17-18-13730-3"));
        assertNotEquals(set1, set3);
    }
}
