package seedu.address.logic.commands.book;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.book.BookAuthorContainsKeywordsPredicate;
import seedu.address.model.book.BookNameContainsKeywordsPredicate;
import seedu.address.model.book.BookTagContainsKeywordsPredicate;
import seedu.address.testutil.TypicalLibTask;

class FindBookCommandTest {

    private Model model = new ModelManager(TypicalLibTask.getTypicalLibTask(), new UserPrefs());
    private Model expectedModel = new ModelManager(TypicalLibTask.getTypicalLibTask(), new UserPrefs());

    @BeforeEach
    void setUp() {
        model = new ModelManager(TypicalLibTask.getTypicalLibTask(), new UserPrefs());
        expectedModel = new ModelManager(TypicalLibTask.getTypicalLibTask(), new UserPrefs());
    }



    @Test
    void execute_tagPredicate_noBooksFound() {
        String expectedMessage = String.format(Messages.MESSAGE_BOOKS_LISTED_OVERVIEW, 0);
        //user input a tag query
        BookTagContainsKeywordsPredicate predicate = preparePredicateTag("t/Gangster");
        FindBookCommand findBookCommand = new FindBookCommand(predicate);
        expectedModel.updateFilteredBookList(predicate);
        assertCommandSuccess(findBookCommand, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredBookList());
    }

    @Test
    void execute_namePredicate_noBooksFound() {
        String expectedMessage = String.format(Messages.MESSAGE_BOOKS_LISTED_OVERVIEW, 0);
        //user input name query
        BookNameContainsKeywordsPredicate predicate = preparePredicateName("n/Abandon all hope");
        FindBookCommand findBookCommand = new FindBookCommand(predicate);
        expectedModel.updateFilteredBookList(predicate);
        assertCommandSuccess(findBookCommand, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredBookList());
    }

    @Test
    void execute_authorPredicate_noBooksFound() {
        String expectedMessage = String.format(Messages.MESSAGE_BOOKS_LISTED_OVERVIEW, 0);
        //user input author
        BookAuthorContainsKeywordsPredicate predicate = preparePredicateAuthor("a/OOP");
        FindBookCommand findBookCommand = new FindBookCommand(predicate);
        expectedModel.updateFilteredBookList(predicate);
        assertCommandSuccess(findBookCommand, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredBookList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private BookNameContainsKeywordsPredicate preparePredicateName(String userInput) {
        return new BookNameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code BookAuthorContainsKeywordsPredicate}.
     */
    private BookAuthorContainsKeywordsPredicate preparePredicateAuthor(String userInput) {
        return new BookAuthorContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code BookTagContainsKeywordsPredicate}.
     */
    private BookTagContainsKeywordsPredicate preparePredicateTag(String userInput) {
        return new BookTagContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
