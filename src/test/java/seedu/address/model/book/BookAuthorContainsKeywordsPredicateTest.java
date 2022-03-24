package seedu.address.model.book;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.BookBuilder;

class BookAuthorContainsKeywordsPredicateTest {

    @Test
    public void test_nameContainsKeywords() {
        // One keyword
        BookAuthorContainsKeywordsPredicate predicate =
                new BookAuthorContainsKeywordsPredicate(Collections.singletonList("Lewis"));
        assertTrue(predicate.test(new BookBuilder().withAuthors("Lewis Carrol").build()));

        // Only one matching keyword
        predicate = new BookAuthorContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new BookBuilder().withAuthors("Alice Carol").build()));

        //Multiple keywords
        predicate = new BookAuthorContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new BookBuilder().withAuthors("Alice Bob").build()));

        // Mixed-case keywords
        predicate = new BookAuthorContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new BookBuilder().withAuthors("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords() {
        //One keyword
        BookAuthorContainsKeywordsPredicate predicate =
                new BookAuthorContainsKeywordsPredicate(Collections.singletonList("Lewis"));
        assertFalse(predicate.test(new BookBuilder().withAuthors("J.K Rowling").build()));

        //Multiple keywords
        predicate = new BookAuthorContainsKeywordsPredicate(Arrays.asList("Kat", "Tom"));
        assertFalse(predicate.test(new BookBuilder().withAuthors("Alice Bob").build()));
    }
}
