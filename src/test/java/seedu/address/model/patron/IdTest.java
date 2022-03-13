package seedu.address.model.patron;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class IdTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Id(null));
    }

    @Test
    public void constructor_invalidId_throwsIllegalArgumentException() {
        String invalidId = "";
        assertThrows(IllegalArgumentException.class, () -> new Id(invalidId));
    }

    @Test
    public void isValidId() {
        // null id
        assertThrows(NullPointerException.class, () -> Id.isValidId(null));

        // invalid id
        assertFalse(Id.isValidId("")); // empty string
        assertFalse(Id.isValidId(" ")); // spaces only
        assertFalse(Id.isValidId("K0123456H")); //id not starting with A
        assertFalse(Id.isValidId("M0123452346H")); //id longer than 9 characters
        assertFalse(Id.isValidId("L01256H")); //id shorter than 9 characters


        // valid id
        assertTrue(Id.isValidId("A0123456H"));
        assertTrue(Id.isValidId("A0123457K"));
        assertTrue(Id.isValidId("A0123458L"));
    }
}
