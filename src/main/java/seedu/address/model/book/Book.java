package seedu.address.model.book;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Book in LibTask.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Book {

    // Identity fields
    private final BookName bookName;
    private final Isbn isbn;
    //private final long timeAdded;

    // Data fields
    private final List<Author> authors = new ArrayList<Author>();
    private final Set<Tag> tags = new HashSet<Tag>();

    /**
     * Every field must be present and not null.
     */
    public Book(BookName bookName, Isbn isbn, List<Author> authors, Set<Tag> tags) {
        requireAllNonNull(bookName, isbn, authors, tags);
        this.bookName = bookName;
        this.isbn = isbn;
        this.authors.addAll(authors);
        this.tags.addAll(tags);
        //this.timeAdded = new Date().getTime();
    }

    public BookName getBookName() {
        return bookName;
    }

    public Isbn getIsbn() {
        return isbn;
    }

    public List<Author> getAuthors() {
        return Collections.unmodifiableList(authors);
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both books have the same isbn.
     * This defines a weaker notion of equality between two books.
     */
    public boolean isSameBook(Book otherBook) {
        if (otherBook == this) {
            return true;
        }
        return otherBook != null
                && otherBook.getIsbn().equals(getIsbn());
    }

    /**
     * Returns true if both books have the same identity and data fields.
     * This defines a stronger notion of equality between two books.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Book)) {
            return false;
        }

        Book otherBook = (Book) other;
        return otherBook.getBookName().equals(getBookName())
                && otherBook.getAuthors().equals(getAuthors())
                && otherBook.getTags().equals(getTags())
                && otherBook.getIsbn().equals(getIsbn());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(bookName, isbn, authors, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getBookName())
                .append("; Isbn: ")
                .append(getIsbn());

        List<Author> authors = getAuthors();
        if (!authors.isEmpty()) {
            builder.append("; Authors: ");
            authors.forEach(builder::append);
        }

        Set<Tag> tags = getTags();
        if (!tags.isEmpty()) {
            builder.append("; Tags: ");
            tags.forEach(builder::append);
        }
        return builder.toString();
    }
}
