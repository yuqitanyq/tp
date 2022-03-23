package seedu.address.model.book;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Books}'s {@code Author} matches any of the keywords given.
 */
public class BookAuthorContainsKeywordsPredicate implements Predicate<Book> {
    private final List<String> keywords;

    public BookAuthorContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Book book) {
        return keywords.stream()
                .anyMatch(keyword -> (book.getAuthors().toString().contains(keyword.toUpperCase())));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BookAuthorContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((BookAuthorContainsKeywordsPredicate) other).keywords)); // state check
    }
}
