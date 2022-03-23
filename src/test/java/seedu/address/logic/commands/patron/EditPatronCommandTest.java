package seedu.address.logic.commands.patron;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPatronAtIndex;
import static seedu.address.testutil.TypicalBooks.HARRY_POTTER;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PATRON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PATRON;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.patron.EditPatronCommand.EditPatronDescriptor;
import seedu.address.model.LibTask;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.book.Book;
import seedu.address.model.book.BookStatus;
import seedu.address.model.book.BookStatusType;
import seedu.address.model.patron.Patron;
import seedu.address.testutil.BookBuilder;
import seedu.address.testutil.EditPatronDescriptorBuilder;
import seedu.address.testutil.PatronBuilder;
import seedu.address.testutil.TypicalLibTask;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditPatronCommandTest {

    private Model model = new ModelManager(TypicalLibTask.getTypicalLibTask(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Patron editedPatron = new PatronBuilder().build();
        EditPatronCommand.EditPatronDescriptor descriptor = new EditPatronDescriptorBuilder(editedPatron).build();
        EditPatronCommand editCommand = new EditPatronCommand(INDEX_SECOND_PATRON, descriptor);

        String expectedMessage = String.format(EditPatronCommand.MESSAGE_EDIT_PATRON_SUCCESS, editedPatron);

        Model expectedModel = new ModelManager(new LibTask(model.getLibTask()), new UserPrefs());
        expectedModel.setPatron(model.getFilteredPatronList().get(1), editedPatron);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPatron = Index.fromOneBased(model.getFilteredPatronList().size());
        Patron lastPatron = model.getFilteredPatronList().get(indexLastPatron.getZeroBased());

        PatronBuilder patronInList = new PatronBuilder(lastPatron);
        Patron editedPatron = patronInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditPatronCommand.EditPatronDescriptor descriptor = new EditPatronDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditPatronCommand editCommand = new EditPatronCommand(indexLastPatron, descriptor);

        String expectedMessage = String.format(EditPatronCommand.MESSAGE_EDIT_PATRON_SUCCESS, editedPatron);

        Model expectedModel = new ModelManager(new LibTask(model.getLibTask()), new UserPrefs());
        expectedModel.setPatron(lastPatron, editedPatron);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditPatronCommand editCommand = new EditPatronCommand(INDEX_SECOND_PATRON,
                new EditPatronCommand.EditPatronDescriptor());
        Patron editedPatron = model.getFilteredPatronList().get(INDEX_SECOND_PATRON.getZeroBased());

        String expectedMessage = String.format(EditPatronCommand.MESSAGE_EDIT_PATRON_SUCCESS, editedPatron);

        Model expectedModel = new ModelManager(new LibTask(model.getLibTask()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPatronAtIndex(model, INDEX_SECOND_PATRON);

        Patron patronInFilteredList = model.getFilteredPatronList().get(INDEX_FIRST_PATRON.getZeroBased());
        Patron editedPatron = new PatronBuilder(patronInFilteredList).withName(VALID_NAME_BOB).build();
        EditPatronCommand editCommand = new EditPatronCommand(INDEX_FIRST_PATRON,
                new EditPatronDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditPatronCommand.MESSAGE_EDIT_PATRON_SUCCESS, editedPatron);

        Model expectedModel = new ModelManager(new LibTask(model.getLibTask()), new UserPrefs());
        expectedModel.setPatron(model.getFilteredPatronList().get(0), editedPatron);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_patronHasRequestedBooks_updateRequestedBooks() {
        Patron patronToEdit = model.getFilteredPatronList().get(INDEX_SECOND_PATRON.getZeroBased());
        Book requestedBook = new BookBuilder(HARRY_POTTER).withRequesters(patronToEdit).build();
        model.addBook(requestedBook);

        Patron editedPatron = new PatronBuilder(patronToEdit).withName(VALID_NAME_BOB).build();
        Book editedRequestedBook = new BookBuilder(requestedBook).withRequesters(editedPatron).build();


        EditPatronCommand editCommand = new EditPatronCommand(INDEX_SECOND_PATRON,
                new EditPatronDescriptorBuilder().withName(VALID_NAME_BOB).build());
        String expectedMessage = String.format(EditPatronCommand.MESSAGE_EDIT_PATRON_SUCCESS, editedPatron)
                + String.format("Requester information of %s is also edited in some books\n", editedPatron.getName());

        ModelManager expectedModel = new ModelManager(model.getLibTask(), new UserPrefs());
        expectedModel.setPatron(model.getFilteredPatronList().get(1), editedPatron);
        expectedModel.setBook(requestedBook, editedRequestedBook);
        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_patronHasBorrowedBooks_updateBorrowedBooks() {
        Patron patronToEdit = model.getFilteredPatronList().get(INDEX_SECOND_PATRON.getZeroBased());
        BookStatus dummyStatus = new BookStatus(BookStatusType.BORROWED, Optional.of(patronToEdit),
                Optional.of("22-May-2022"), Optional.of("31-Dec-2022"));
        Book borrowedBook = new BookBuilder(HARRY_POTTER).withBookStatus(dummyStatus).build();
        model.addBook(borrowedBook);

        Patron editedPatron = new PatronBuilder(patronToEdit).withName(VALID_NAME_BOB).build();
        BookStatus updatedStatus = dummyStatus.editBorrower(editedPatron);
        Book editedBorrowedBook = new BookBuilder(borrowedBook).withBookStatus(updatedStatus).build();

        EditPatronCommand editCommand = new EditPatronCommand(INDEX_SECOND_PATRON,
                new EditPatronDescriptorBuilder().withName(VALID_NAME_BOB).build());
        String expectedMessage = String.format(EditPatronCommand.MESSAGE_EDIT_PATRON_SUCCESS, editedPatron)
                + String.format("Borrower information of %s is also edited in some books\n", editedPatron.getName());

        ModelManager expectedModel = new ModelManager(model.getLibTask(), new UserPrefs());
        expectedModel.setPatron(model.getFilteredPatronList().get(1), editedPatron);
        expectedModel.setBook(borrowedBook, editedBorrowedBook);
        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePatronUnfilteredList_failure() {
        Patron firstPatron = model.getFilteredPatronList().get(INDEX_FIRST_PATRON.getZeroBased());
        EditPatronCommand.EditPatronDescriptor descriptor = new EditPatronDescriptorBuilder(firstPatron).build();
        EditPatronCommand editCommand = new EditPatronCommand(INDEX_SECOND_PATRON, descriptor);

        assertCommandFailure(editCommand, model, EditPatronCommand.MESSAGE_DUPLICATE_PATRON);
    }

    @Test
    public void execute_duplicatePatronFilteredList_failure() {
        showPatronAtIndex(model, INDEX_FIRST_PATRON);

        // edit patron in filtered list into a duplicate in LibTask
        Patron patronInList = model.getLibTask().getPatronList().get(INDEX_SECOND_PATRON.getZeroBased());
        EditPatronCommand editCommand = new EditPatronCommand(INDEX_FIRST_PATRON,
                new EditPatronDescriptorBuilder(patronInList).build());

        assertCommandFailure(editCommand, model, EditPatronCommand.MESSAGE_DUPLICATE_PATRON);
    }

    @Test
    public void execute_invalidPatronIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPatronList().size() + 1);
        EditPatronCommand.EditPatronDescriptor descriptor = new EditPatronDescriptorBuilder()
                .withName(VALID_NAME_BOB).build();
        EditPatronCommand editCommand = new EditPatronCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PATRON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of LibTask
     */
    @Test
    public void execute_invalidPatronIndexFilteredList_failure() {
        showPatronAtIndex(model, INDEX_FIRST_PATRON);
        Index outOfBoundIndex = INDEX_SECOND_PATRON;
        // ensures that outOfBoundIndex is still in bounds of LibTask list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getLibTask().getPatronList().size());

        EditPatronCommand editCommand = new EditPatronCommand(outOfBoundIndex,
                new EditPatronDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PATRON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditPatronCommand standardCommand = new EditPatronCommand(INDEX_FIRST_PATRON, DESC_AMY);

        // same values -> returns true
        EditPatronCommand.EditPatronDescriptor copyDescriptor = new EditPatronDescriptor(DESC_AMY);
        EditPatronCommand commandWithSameValues = new EditPatronCommand(INDEX_FIRST_PATRON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditPatronCommand(INDEX_SECOND_PATRON, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditPatronCommand(INDEX_FIRST_PATRON, DESC_BOB)));
    }

}
