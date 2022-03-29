package seedu.address.model.patron;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPatrons.ALICE;
import static seedu.address.testutil.TypicalPatrons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PatronBuilder;

public class PatronTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Patron patron = new PatronBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> patron.getTags().remove(0));
    }

    @Test
    public void isSamePatron() {
        // same object -> returns true
        assertTrue(ALICE.isSamePatron(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePatron(null));

        // same name and id, all other attributes different -> returns true
        Patron editedAlice = new PatronBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSamePatron(editedAlice));

        // different name, all other attributes same -> returns true
        editedAlice = new PatronBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertTrue(ALICE.isSamePatron(editedAlice));

        // different id, all other attributes same -> returns true
        editedAlice = new PatronBuilder(ALICE).withId(VALID_ID_BOB).build();
        assertTrue(ALICE.isSamePatron(editedAlice));

        // name differs in case, all other attributes same -> returns true
        Patron editedBob = new PatronBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertTrue(BOB.isSamePatron(editedBob));

        // name has trailing spaces, all other attributes same -> returns true
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new PatronBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertTrue(BOB.isSamePatron(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Patron aliceCopy = new PatronBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different patron -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Patron editedAlice = new PatronBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PatronBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PatronBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different id -> returns false
        editedAlice = new PatronBuilder(ALICE).withId(VALID_ID_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new PatronBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
