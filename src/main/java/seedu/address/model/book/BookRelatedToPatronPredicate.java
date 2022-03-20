package seedu.address.model.book;

import java.util.function.Predicate;

import seedu.address.model.patron.Patron;

/**
 * Tests that a {@code Book}'s {@code isBorrowedBy} matches the given patron.
 */
public class BookRelatedToPatronPredicate implements Predicate<Book> {

    private final Patron patronToRelate;

    public BookRelatedToPatronPredicate(Patron patronToRelate) {
        this.patronToRelate = patronToRelate;
    }

    @Override
    public boolean test(Book book) {
        return book.isBorrowedBy(patronToRelate); //  || book.isRequestedBy(patronToRelate)
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BookRelatedToPatronPredicate // instanceof handles nulls
                && patronToRelate.equals(((BookRelatedToPatronPredicate) other).patronToRelate)); // state check
    }
}
