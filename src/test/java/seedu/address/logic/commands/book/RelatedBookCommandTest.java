package seedu.address.logic.commands.book;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPatronAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PATRON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PATRON;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.book.BookRelatedToPatronPredicate;
import seedu.address.model.patron.Patron;
import seedu.address.testutil.TypicalLibTask;

/**
 * Contains integration tests (Interactions with the Model) and unit tests for
 * {@code RelatedBookCommand}.
 */
public class RelatedBookCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(TypicalLibTask.getTypicalLibTask(), new UserPrefs());
    }

    @Test
    public void execute_invalidPatronIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPatronList().size() + 1);
        RelatedBookCommand relatedBookCommand = new RelatedBookCommand(outOfBoundIndex);

        assertCommandFailure(relatedBookCommand, model, Messages.MESSAGE_INVALID_PATRON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validPatronIndexUnfilteredList_success() {
        Patron patronToRelate = model.getFilteredPatronList().get(INDEX_FIRST_PATRON.getZeroBased());
        RelatedBookCommand relatedBookCommand = new RelatedBookCommand(INDEX_FIRST_PATRON);

        String expectedMessage = String.format(RelatedBookCommand.MESSAGE_RELATED_BOOK_SUCCESS, patronToRelate);

        Model expectedModel = new ModelManager(TypicalLibTask.getTypicalLibTask(), new UserPrefs());
        BookRelatedToPatronPredicate predicate = preparePredicate(patronToRelate);
        expectedModel.updateFilteredBookList(predicate);
        assertCommandSuccess(relatedBookCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validPatronIndexFilteredList_success() {
        showPatronAtIndex(model, INDEX_FIRST_PATRON);
        Patron patronToRelate = model.getFilteredPatronList().get(INDEX_FIRST_PATRON.getZeroBased());
        RelatedBookCommand relatedBookCommand = new RelatedBookCommand(INDEX_FIRST_PATRON);

        String expectedMessage = String.format(RelatedBookCommand.MESSAGE_RELATED_BOOK_SUCCESS, patronToRelate);

        Model expectedModel = new ModelManager(TypicalLibTask.getTypicalLibTask(), new UserPrefs());
        BookRelatedToPatronPredicate predicate = preparePredicate(patronToRelate);

        showPatronAtIndex(expectedModel, INDEX_FIRST_PATRON);
        expectedModel.updateFilteredBookList(predicate);
        assertCommandSuccess(relatedBookCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        RelatedBookCommand relatedBookFirstCommand = new RelatedBookCommand(INDEX_FIRST_PATRON);
        RelatedBookCommand relatedBookSecondCommand = new RelatedBookCommand(INDEX_SECOND_PATRON);

        // same object -> returns true
        assertTrue(relatedBookFirstCommand.equals(relatedBookFirstCommand));

        // same values -> return true
        RelatedBookCommand relatedBookFirstCommandCopy = new RelatedBookCommand(INDEX_FIRST_PATRON);
        assertTrue(relatedBookFirstCommand.equals(relatedBookFirstCommandCopy));

        // different types -> returns false
        assertFalse(relatedBookFirstCommand.equals("A"));

        // null -> returns false
        assertFalse(relatedBookFirstCommand.equals(null));

        // different patron index -> returns false
        assertFalse(relatedBookFirstCommand.equals(relatedBookSecondCommand));
    }

    /**
     * Converts {@code patronToRelate} into a {@code BookRelatedToPatronPredicate}.
     */
    private BookRelatedToPatronPredicate preparePredicate(Patron patronToRelate) {
        return new BookRelatedToPatronPredicate(patronToRelate);
    }
}
