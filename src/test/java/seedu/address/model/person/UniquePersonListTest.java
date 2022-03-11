package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.PersonBuilder;

public class UniquePersonListTest {

    private final UniquePersonList uniquePatronList = new UniquePersonList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePatronList.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniquePatronList.contains(ALICE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniquePatronList.add(ALICE);
        assertTrue(uniquePatronList.contains(ALICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniquePatronList.add(ALICE);
        Patron editedAlice = new PersonBuilder(ALICE).withId(VALID_ID_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniquePatronList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePatronList.add(null));
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniquePatronList.add(ALICE);
        assertThrows(DuplicatePersonException.class, () -> uniquePatronList.add(ALICE));
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePatronList.setPatron(null, ALICE));
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePatronList.setPatron(ALICE, null));
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniquePatronList.setPatron(ALICE, ALICE));
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniquePatronList.add(ALICE);
        uniquePatronList.setPatron(ALICE, ALICE);
        UniquePersonList expectedUniquePatronList = new UniquePersonList();
        expectedUniquePatronList.add(ALICE);
        assertEquals(expectedUniquePatronList, uniquePatronList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniquePatronList.add(ALICE);
        Patron editedAlice = new PersonBuilder(ALICE).withId(VALID_ID_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniquePatronList.setPatron(ALICE, editedAlice);
        UniquePersonList expectedUniquePatronList = new UniquePersonList();
        expectedUniquePatronList.add(editedAlice);
        assertEquals(expectedUniquePatronList, uniquePatronList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniquePatronList.add(ALICE);
        uniquePatronList.setPatron(ALICE, BOB);
        UniquePersonList expectedUniquePatronList = new UniquePersonList();
        expectedUniquePatronList.add(BOB);
        assertEquals(expectedUniquePatronList, uniquePatronList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniquePatronList.add(ALICE);
        uniquePatronList.add(BOB);
        assertThrows(DuplicatePersonException.class, () -> uniquePatronList.setPatron(ALICE, BOB));
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePatronList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniquePatronList.remove(ALICE));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniquePatronList.add(ALICE);
        uniquePatronList.remove(ALICE);
        UniquePersonList expectedUniquePatronList = new UniquePersonList();
        assertEquals(expectedUniquePatronList, uniquePatronList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePatronList.setPatrons((UniquePersonList) null));
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniquePatronList.add(ALICE);
        UniquePersonList expectedUniquePatronList = new UniquePersonList();
        expectedUniquePatronList.add(BOB);
        uniquePatronList.setPatrons(expectedUniquePatronList);
        assertEquals(expectedUniquePatronList, uniquePatronList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePatronList.setPatrons((List<Patron>) null));
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniquePatronList.add(ALICE);
        List<Patron> patronList = Collections.singletonList(BOB);
        uniquePatronList.setPatrons(patronList);
        UniquePersonList expectedUniquePatronList = new UniquePersonList();
        expectedUniquePatronList.add(BOB);
        assertEquals(expectedUniquePatronList, uniquePatronList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Patron> listWithDuplicatePatrons = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicatePersonException.class, () -> uniquePatronList.setPatrons(listWithDuplicatePatrons));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniquePatronList.asUnmodifiableObservableList().remove(0));
    }
}
