package seedu.address.model.book;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import seedu.address.model.patron.Patron;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.testutil.BookBuilder;
import seedu.address.testutil.PatronBuilder;

public class BookRelatedToPatronPredicateTest {

    @Test
    public void equals() {
        Patron firstPredicatePatron = new PatronBuilder().withName("Alice Bob").build();
        Patron secondPredicatePatron = new PatronBuilder().withName("Alice Carol").build();

        BookRelatedToPatronPredicate firstPredicate = new BookRelatedToPatronPredicate(firstPredicatePatron);
        BookRelatedToPatronPredicate secondPredicate = new BookRelatedToPatronPredicate(secondPredicatePatron);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> return true
        BookRelatedToPatronPredicate firstPredicateCopy = new BookRelatedToPatronPredicate(firstPredicatePatron);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> return false
        assertFalse(firstPredicate.equals("A"));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different patron -> return false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_bookRelatedToPatron_returnsTrue() {
        // Patron related to book
        Patron predicatePatron = SampleDataUtil.getSamplePatrons()[0];
        BookRelatedToPatronPredicate predicate = new BookRelatedToPatronPredicate(predicatePatron);
        assertTrue(predicate.test(BookBuilder.bookBuilderWithBorrowedStatus().build()));
    }

    @Test
    public void test_bookRelatedToPatron_returnsFalse() {
        // Zero patrons
        BookRelatedToPatronPredicate predicate = new BookRelatedToPatronPredicate(null);
        assertFalse(predicate.test(BookBuilder.bookBuilderWithBorrowedStatus().build()));

        // Non matching patron
        Patron predicatePatron = SampleDataUtil.getSamplePatrons()[1];
        predicate = new BookRelatedToPatronPredicate(predicatePatron);
        assertFalse(predicate.test(BookBuilder.bookBuilderWithBorrowedStatus().build()));
    }
}
