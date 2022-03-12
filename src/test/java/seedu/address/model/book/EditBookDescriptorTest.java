package seedu.address.model.book;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_HARRY_POTTER;
import static seedu.address.logic.commands.CommandTestUtil.DESC_HUNGER_GAMES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AUTHOR_SUZANNE_COLLINS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BOOK_NAME_HUNGER_GAMES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ISBN_HUNGER_GAMES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_SCIFI;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.book.EditBookCommand.EditBookDescriptor;
import seedu.address.testutil.EditBookDescriptorBuilder;

public class EditBookDescriptorTest {
    @Test
    public void equals() {
        // same values -> return true
        EditBookDescriptor bookDescriptorWithSameValues = new EditBookDescriptor(DESC_HARRY_POTTER);
        assertTrue(DESC_HARRY_POTTER.equals(bookDescriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_HARRY_POTTER.equals(DESC_HARRY_POTTER));

        // null -> returns false
        assertFalse(DESC_HARRY_POTTER.equals(null));

        // different types -> returns false
        assertFalse(DESC_HARRY_POTTER.equals(10));

        // different values -> returns false
        assertFalse(DESC_HARRY_POTTER.equals(DESC_HUNGER_GAMES));

        //different name -> return false
        EditBookDescriptor editedHarryPotter = new EditBookDescriptorBuilder(DESC_HARRY_POTTER)
                .withBookName(VALID_BOOK_NAME_HUNGER_GAMES).build();
        assertFalse(DESC_HARRY_POTTER.equals(editedHarryPotter));

        //different isbn -> return false
        editedHarryPotter = new EditBookDescriptorBuilder(DESC_HARRY_POTTER)
                .withIsbn(VALID_ISBN_HUNGER_GAMES).build();
        assertFalse(DESC_HARRY_POTTER.equals(editedHarryPotter));

        //different authors -> return false
        editedHarryPotter = new EditBookDescriptorBuilder(DESC_HARRY_POTTER)
                .withAuthors(VALID_AUTHOR_SUZANNE_COLLINS).build();
        assertFalse(DESC_HARRY_POTTER.equals(editedHarryPotter));

        //different tags -> return false
        editedHarryPotter = new EditBookDescriptorBuilder(DESC_HARRY_POTTER)
                .withTags(VALID_TAG_SCIFI).build();
        assertFalse(DESC_HARRY_POTTER.equals(editedHarryPotter));
    }
}
