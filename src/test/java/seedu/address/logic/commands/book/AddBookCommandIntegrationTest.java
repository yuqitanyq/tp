package seedu.address.logic.commands.book;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.book.Book;
import seedu.address.testutil.BookBuilder;
import seedu.address.testutil.TypicalAddressBook;

/**
 * Contains integration tests (interaction with the Model) for {@code AddBookCommand}.
 */
public class AddBookCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(TypicalAddressBook.getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newBook_success() {
        Book validBook = new BookBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addBook(validBook);

        assertCommandSuccess(new AddBookCommand(validBook), model,
                String.format(AddBookCommand.MESSAGE_SUCCESS, validBook), expectedModel);
    }

}
