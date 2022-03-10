package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AddressTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Id(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidAddress = "";
        assertThrows(IllegalArgumentException.class, () -> new Id(invalidAddress));
    }

    @Test
    public void isValidAddress() {
        // null address
        assertThrows(NullPointerException.class, () -> Id.isValidId(null));

        // invalid addresses
        assertFalse(Id.isValidId("")); // empty string
        assertFalse(Id.isValidId(" ")); // spaces only

        // valid addresses
        assertTrue(Id.isValidId("A0123456H"));
        assertTrue(Id.isValidId("A0123457H"));
        assertTrue(Id.isValidId("A0123458H"));
    }
}
