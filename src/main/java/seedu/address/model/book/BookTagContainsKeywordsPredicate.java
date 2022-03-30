package seedu.address.model.book;

import java.util.List;
import java.util.function.Predicate;


/**
 * Test that a {@code Book}'s {@code BookTag} matches any of the keywords given
 */
public class BookTagContainsKeywordsPredicate implements Predicate<Book> {

    private final List<String> keywords;

    public BookTagContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Book book) {
        return keywords.stream()
                .anyMatch(keywords -> book.getTags().stream().anyMatch(x ->
                        x.toString().toUpperCase().contains(keywords.toUpperCase())));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BookTagContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((BookTagContainsKeywordsPredicate) other).keywords)); // state check
    }
}
