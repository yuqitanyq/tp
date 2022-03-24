package seedu.address.model.patron;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPatrons.ALICE;
import static seedu.address.testutil.TypicalPatrons.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.patron.exceptions.DuplicatePatronException;
import seedu.address.model.patron.exceptions.PatronNotFoundException;
import seedu.address.testutil.PatronBuilder;

public class UniquePatronListTest {

    private final UniquePatronList uniquePatronList = new UniquePatronList();

    @Test
    public void contains_nullPatron_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePatronList.contains(null));
    }

    @Test
    public void contains_patronNotInList_returnsFalse() {
        assertFalse(uniquePatronList.contains(ALICE));
    }

    @Test
    public void contains_patronInList_returnsTrue() {
        uniquePatronList.add(ALICE);
        assertTrue(uniquePatronList.contains(ALICE));
    }

    @Test
    public void contains_patronWithSameIdentityFieldsInList_returnsTrue() {
        uniquePatronList.add(ALICE);
        Patron editedAlice = new PatronBuilder(ALICE).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniquePatronList.contains(editedAlice));
    }

    @Test
    public void add_nullPatron_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePatronList.add(null));
    }

    @Test
    public void add_duplicatePatron_throwsDuplicatePatronException() {
        uniquePatronList.add(ALICE);
        assertThrows(DuplicatePatronException.class, () -> uniquePatronList.add(ALICE));
    }

    @Test
    public void setPatron_nullTargetPatron_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePatronList.setPatron(null, ALICE));
    }

    @Test
    public void setPatron_nullEditedPatron_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePatronList.setPatron(ALICE, null));
    }

    @Test
    public void setPatron_targetPatronNotInList_throwsPatronNotFoundException() {
        assertThrows(PatronNotFoundException.class, () -> uniquePatronList.setPatron(ALICE, ALICE));
    }

    @Test
    public void setPatron_editedPatronIsSamePatron_success() {
        uniquePatronList.add(ALICE);
        uniquePatronList.setPatron(ALICE, ALICE);
        UniquePatronList expectedUniquePatronList = new UniquePatronList();
        expectedUniquePatronList.add(ALICE);
        assertEquals(expectedUniquePatronList, uniquePatronList);
    }

    @Test
    public void setPatron_editedPatronHasSameIdentity_success() {
        uniquePatronList.add(ALICE);
        Patron editedAlice = new PatronBuilder(ALICE).withId(VALID_ID_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniquePatronList.setPatron(ALICE, editedAlice);
        UniquePatronList expectedUniquePatronList = new UniquePatronList();
        expectedUniquePatronList.add(editedAlice);
        assertEquals(expectedUniquePatronList, uniquePatronList);
    }

    @Test
    public void setPatron_editedPatronHasDifferentIdentity_success() {
        uniquePatronList.add(ALICE);
        uniquePatronList.setPatron(ALICE, BOB);
        UniquePatronList expectedUniquePatronList = new UniquePatronList();
        expectedUniquePatronList.add(BOB);
        assertEquals(expectedUniquePatronList, uniquePatronList);
    }

    @Test
    public void setPatron_editedPatronHasNonUniqueIdentity_throwsDuplicatePatronException() {
        uniquePatronList.add(ALICE);
        uniquePatronList.add(BOB);
        assertThrows(DuplicatePatronException.class, () -> uniquePatronList.setPatron(ALICE, BOB));
    }

    @Test
    public void remove_nullPatron_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePatronList.remove(null));
    }

    @Test
    public void remove_patronDoesNotExist_throwsPatronNotFoundException() {
        assertThrows(PatronNotFoundException.class, () -> uniquePatronList.remove(ALICE));
    }

    @Test
    public void remove_existingPatron_removesPatron() {
        uniquePatronList.add(ALICE);
        uniquePatronList.remove(ALICE);
        UniquePatronList expectedUniquePatronList = new UniquePatronList();
        assertEquals(expectedUniquePatronList, uniquePatronList);
    }

    @Test
    public void setPatrons_nullUniquePatronList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePatronList.setPatrons((UniquePatronList) null));
    }

    @Test
    public void setPatrons_uniquePatronList_replacesOwnListWithProvidedUniquePatronList() {
        uniquePatronList.add(ALICE);
        UniquePatronList expectedUniquePatronList = new UniquePatronList();
        expectedUniquePatronList.add(BOB);
        uniquePatronList.setPatrons(expectedUniquePatronList);
        assertEquals(expectedUniquePatronList, uniquePatronList);
    }

    @Test
    public void setPatrons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePatronList.setPatrons((List<Patron>) null));
    }

    @Test
    public void setPatrons_list_replacesOwnListWithProvidedList() {
        uniquePatronList.add(ALICE);
        List<Patron> patronList = Collections.singletonList(BOB);
        uniquePatronList.setPatrons(patronList);
        UniquePatronList expectedUniquePatronList = new UniquePatronList();
        expectedUniquePatronList.add(BOB);
        assertEquals(expectedUniquePatronList, uniquePatronList);
    }

    @Test
    public void setPatrons_listWithDuplicatePatrons_throwsDuplicatePatronException() {
        List<Patron> listWithDuplicatePatrons = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicatePatronException.class, () -> uniquePatronList.setPatrons(listWithDuplicatePatrons));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniquePatronList.asUnmodifiableObservableList().remove(0));
    }
}
