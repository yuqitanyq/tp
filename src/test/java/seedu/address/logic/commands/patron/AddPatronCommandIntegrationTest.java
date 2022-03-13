package seedu.address.logic.commands.patron;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.patron.Patron;
import seedu.address.testutil.PatronBuilder;
import seedu.address.testutil.TypicalLibTask;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddPatronCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(TypicalLibTask.getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newPatron_success() {
        Patron validPatron = new PatronBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addPatron(validPatron);

        assertCommandSuccess(new AddPatronCommand(validPatron), model,
                String.format(AddPatronCommand.MESSAGE_SUCCESS, validPatron), expectedModel);
    }

    @Test
    public void execute_duplicatePatron_throwsCommandException() {
        Patron patronInList = model.getAddressBook().getPatronList().get(0);
        assertCommandFailure(new AddPatronCommand(patronInList), model, AddPatronCommand.MESSAGE_DUPLICATE_PATRON);
    }

}
