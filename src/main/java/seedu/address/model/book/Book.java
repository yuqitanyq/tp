package seedu.address.model.book;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.patron.Patron;
import seedu.address.model.tag.Tag;

/**
 * Represents a Book in LibTask.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Book {

    public static final String TIME_ADDED_MESSAGE_CONSTRAINTS = "Time added should be able to be parsed to long.\n";

    // Identity fields
    private final BookName bookName;
    private final Isbn isbn;
    private final long timeAdded;

    // Data fields
    private final List<Author> authors = new ArrayList<Author>();
    private final Set<Tag> tags = new HashSet<Tag>();
    private final BookStatus bookStatus;

    /**
     * Every field must be present and not null.
     */
    public Book(BookName bookName, Isbn isbn, List<Author> authors, Set<Tag> tags, long timeAdded,
                BookStatus bookStatus) {
        requireAllNonNull(bookName, isbn, authors, tags, bookStatus);
        this.bookName = bookName;
        this.isbn = isbn;
        this.authors.addAll(authors);
        this.tags.addAll(tags);
        this.timeAdded = timeAdded;
        this.bookStatus = bookStatus;
    }

    /**
     * Constructs a Book that is same as {@code originalBook} in every aspect except book status.
     */
    public Book(Book originalBook, BookStatus updatedBookStatus) {
        this (originalBook.getBookName(), originalBook.getIsbn(), originalBook.getAuthors(),
                originalBook.getTags(), originalBook.getTimeAdded(), updatedBookStatus);
    }

    public BookName getBookName() {
        return bookName;
    }

    public Isbn getIsbn() {
        return isbn;
    }

    public long getTimeAdded() {
        return timeAdded;
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

    public BookStatus getBookStatus() {
        return bookStatus;
    }

    /**
     * Returns a String representing the borrow date of this book, or an empty string if this book is not borrowed.
     */
    public String getBorrowDateString() {
        return bookStatus.getBorrowDateString();
    }

    /**
     * Returns a String representing the return date of this book, or an empty string if this book is not borrowed.
     */
    public String getReturnDateString() {
        return bookStatus.getReturnDateString();
    }

    /**
     * Returns a String representing the borrower's name of this book, or an empty string if this book is not borrowed.
     */
    public String getBorrowerName() {
        return bookStatus.getBorrowerName();
    }

    public boolean isAvailable() {
        return bookStatus.isAvailable();
    }

    public boolean isBorrowed() {
        return bookStatus.isBorrowed();
    }

    /**
     * Returns true if this book is borrowed by the specified patron.
     */
    public boolean isBorrowedBy(Patron patron) {
        if (!isBorrowed()) {
            return false;
        }
        return bookStatus.getBorrower().map(p -> p.equals(patron)).orElse(false);
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
     * Returns true if both books have the same set of authors.
     * Authors are considered to be equal based on {@link Author#equals(Object)}
     */
    public boolean hasSameAuthors(Book other) {
        HashSet<Author> thisAuthors = new HashSet<>();
        thisAuthors.addAll(authors);
        HashSet<Author> otherAuthors = new HashSet<>();
        otherAuthors.addAll(other.authors);
        return thisAuthors.equals(otherAuthors);
    }

    /**
     * Returns true if both books have the same isbn.
     * Isbn are considered to be equal based on {@link Isbn#equals(Object)}
     */
    public boolean hasSameIsbn(Book other) {
        return isbn.equals(other.isbn);
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
                && hasSameAuthors(otherBook)
                && otherBook.getTags().equals(getTags())
                && otherBook.getIsbn().equals(getIsbn())
                && otherBook.timeAdded == timeAdded
                && otherBook.bookStatus.equals(bookStatus);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(bookName, isbn, authors, tags, timeAdded, bookStatus);
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
        builder.append("; Status: ").append(bookStatus.toString());
        return builder.toString();
    }
}
