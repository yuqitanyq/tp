package seedu.address.model.book;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.BookBuilder;



class BookTagContainsKeywordsPredicateTest {

    @Test
    public void test_tagContainsKeywords() {
        // One keyword
        BookTagContainsKeywordsPredicate predicate =
                new BookTagContainsKeywordsPredicate(Collections.singletonList("Adventure"));
        assertTrue(predicate.test(new BookBuilder().withTags("Adventure").build()));

        // Only one matching keyword
        predicate = new BookTagContainsKeywordsPredicate(Arrays.asList("Fiction", "Carol"));
        assertTrue(predicate.test(new BookBuilder().withTags("SciFi", "Carol").build()));

        //Multiple keywords
        predicate = new BookTagContainsKeywordsPredicate(Arrays.asList("SciFi", "Fiction"));
        assertTrue(predicate.test(new BookBuilder().withTags("SciFi", "Fiction").build()));

        // Mixed-case keywords
        predicate = new BookTagContainsKeywordsPredicate(Arrays.asList("sciFi", "fICTION"));
        assertTrue(predicate.test(new BookBuilder().withTags("SciFi", "Fiction").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords() {
        //One keyword
        BookTagContainsKeywordsPredicate predicate =
                new BookTagContainsKeywordsPredicate(Collections.singletonList("Adventure"));
        assertFalse(predicate.test(new BookBuilder().withTags("Bouldering").build()));

        //Multiple keywords
        predicate = new BookTagContainsKeywordsPredicate(Arrays.asList("Kat", "Tom"));
        assertFalse(predicate.test(new BookBuilder().withTags("SciFi", "Fiction").build()));
    }
}
