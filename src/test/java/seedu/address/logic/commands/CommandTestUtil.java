package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AUTHOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ISBN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.book.EditBookCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.patron.EditPatronCommand;
import seedu.address.model.LibTask;
import seedu.address.model.Model;
import seedu.address.model.book.Book;
import seedu.address.model.patron.NameContainsKeywordsPredicate;
import seedu.address.model.patron.Patron;
import seedu.address.testutil.EditBookDescriptorBuilder;
import seedu.address.testutil.EditPatronDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ID_AMY = "A0123456H";
    public static final String VALID_ID_BOB = "A0123457H";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    public static final String VALID_BOOK_NAME_HARRY_POTTER = "Harry Potter and The Philosopher's Stone";
    public static final String VALID_BOOK_NAME_HUNGER_GAMES = "The Hunger Games: MockingJay";
    public static final String VALID_ISBN_HARRY_POTTER = "978-71617-018-8-5";
    public static final String VALID_ISBN_HARRY_POTTER_2 = "9787161701885";
    public static final String VALID_ISBN_HUNGER_GAMES = "9786029293883";
    public static final String VALID_AUTHOR_JK_ROWLING = "J. K. Rowling";
    public static final String VALID_AUTHOR_SUZANNE_COLLINS = "Suzanne Collins";
    public static final String VALID_TAG_ADVENTURE = "Adventure";
    public static final String VALID_TAG_MAGIC = "Magic";
    public static final String VALID_TAG_THRILLER = "Thriller";
    public static final String VALID_TAG_SCIFI = "Scifi";
    public static final String VALID_BORROW_DATE = "14-Fec-2022";
    public static final String VALID_RETURN_DATE = "28-Feb-2023";
    public static final String VALID_RETURN_DATE_2 = "28-Dec-2022";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ID_DESC_AMY = " " + PREFIX_ID + VALID_ID_AMY;
    public static final String ID_DESC_BOB = " " + PREFIX_ID + VALID_ID_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String BOOK_NAME_DESC_HARRY_POTTER = " " + PREFIX_NAME + VALID_BOOK_NAME_HARRY_POTTER;
    public static final String BOOK_NAME_DESC_HUNGER_GAMES = " " + PREFIX_NAME + VALID_BOOK_NAME_HUNGER_GAMES;
    public static final String ISBN_DESC_HARRY_POTTER = " " + PREFIX_ISBN + VALID_ISBN_HARRY_POTTER;
    public static final String ISBN_DESC_HUNGER_GAMES = " " + PREFIX_ISBN + VALID_ISBN_HUNGER_GAMES;
    public static final String ISBN_DESC_HARRY_POTTER_2 = " " + PREFIX_ISBN + VALID_ISBN_HARRY_POTTER_2;
    public static final String AUTHOR_DESC_JK_ROWLING = " " + PREFIX_AUTHOR + VALID_AUTHOR_JK_ROWLING;
    public static final String AUTHOR_DESC_SUZANNE_COLLINS = " " + PREFIX_AUTHOR + VALID_AUTHOR_SUZANNE_COLLINS;
    public static final String TAG_DESC_ADVENTURE = " " + PREFIX_TAG + VALID_TAG_ADVENTURE;
    public static final String TAG_DESC_MAGIC = " " + PREFIX_TAG + VALID_TAG_MAGIC;
    public static final String TAG_DESC_THRILLER = " " + PREFIX_TAG + VALID_TAG_THRILLER;
    public static final String TAG_DESC_SCIFI = " " + PREFIX_TAG + VALID_TAG_SCIFI;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ID_DESC = " " + PREFIX_ID; // empty string not allowed for ids
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String INVALID_BOOK_NAME_DESC = " " + PREFIX_NAME + "@HarryPotter"; // '@' not allowed in names
    public static final String INVALID_ISBN_DESC = " " + PREFIX_ISBN + "911a";
    public static final String INVALID_AUTHOR_DESC = " " + PREFIX_AUTHOR + "@@@"; // '@' not allowed for author
    public static final String INVALID_DATE = "28Feb2022";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditPatronCommand.EditPatronDescriptor DESC_AMY;
    public static final EditPatronCommand.EditPatronDescriptor DESC_BOB;
    public static final EditBookCommand.EditBookDescriptor DESC_HARRY_POTTER;
    public static final EditBookCommand.EditBookDescriptor DESC_HUNGER_GAMES;

    static {
        DESC_AMY = new EditPatronDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withId(VALID_ID_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPatronDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withId(VALID_ID_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        DESC_HARRY_POTTER = new EditBookDescriptorBuilder().withBookName(VALID_BOOK_NAME_HARRY_POTTER)
                .withIsbn(VALID_ISBN_HARRY_POTTER).withAuthors(VALID_AUTHOR_JK_ROWLING)
                .withTags(VALID_TAG_THRILLER, VALID_TAG_MAGIC).build();
        DESC_HUNGER_GAMES = new EditBookDescriptorBuilder().withBookName(VALID_BOOK_NAME_HUNGER_GAMES)
                .withIsbn(VALID_ISBN_HUNGER_GAMES).withAuthors(VALID_AUTHOR_SUZANNE_COLLINS)
                .withTags(VALID_TAG_THRILLER).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the LibTask, filtered patron list and selected patron in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        LibTask expectedLibTask = new LibTask(actualModel.getLibTask());
        List<Patron> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPatronList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedLibTask, actualModel.getLibTask());
        assertEquals(expectedFilteredList, actualModel.getFilteredPatronList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the patron at the given {@code targetIndex} in the
     * {@code model}'s LibTask.
     */
    public static void showPatronAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPatronList().size());

        Patron patron = model.getFilteredPatronList().get(targetIndex.getZeroBased());
        final String[] splitName = patron.getName().fullName.split("\\s+");
        model.updateFilteredPatronList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPatronList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the book at the given {@code targetIndex} in the
     * {@code model}'s LibTask.
     */
    public static void showBookAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredBookList().size());

        Book book = model.getFilteredBookList().get(targetIndex.getZeroBased());
        model.updateFilteredBookList(x -> x.equals(book));

        assertEquals(1, model.getFilteredBookList().size());
    }

}
