package seedu.address.model.book;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.model.book.exceptions.BookNotFoundException;
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
    private final Set<Patron> requesters = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Book(BookName bookName, Isbn isbn, List<Author> authors, Set<Tag> tags, long timeAdded,
                BookStatus bookStatus, Set<Patron> requesters) {
        requireAllNonNull(bookName, isbn, authors, tags, bookStatus, requesters);
        this.bookName = bookName;
        this.isbn = isbn;
        this.authors.addAll(authors);
        this.tags.addAll(tags);
        this.timeAdded = timeAdded;
        this.bookStatus = bookStatus;
        this.requesters.addAll(requesters);
    }

    /**
     * Constructs a Book that is same as {@code originalBook} in every aspect except book status.
     */
    public Book(Book originalBook, BookStatus updatedBookStatus) {
        this (originalBook.getBookName(), originalBook.getIsbn(), originalBook.getAuthors(),
                originalBook.getTags(), originalBook.getTimeAdded(), updatedBookStatus, originalBook.getRequesters());
    }

    /**
     * Returns a Book that is same as {@code originalBook} in every aspect, but with no requesters.
     */
    public Book getBookWithEmptyRequest() {
        return new Book(getBookName(), getIsbn(), getAuthors(), getTags(), getTimeAdded(), getBookStatus(),
                new HashSet<>());
    }

    /**
     * Returns a Book that is consistent with {@code editedBook}, but has all other fields same as this book.
     * Two books are consistent if they either do not have the same isbn, or have the same isbn, book name, and authors.
     */
    public Book getConsistentReplacement(Book editedBook) {
        requireNonNull(editedBook);
        return new Book(editedBook.getBookName(), editedBook.getIsbn(), editedBook.getAuthors(), getTags(),
                getTimeAdded(), getBookStatus(), getRequesters());
    }

    /**
     * Returns a Book with the exact same fields as this book, but with the specified old requester replaced with
     * the edited requester in its list of requesters.
     *
     * @param oldRequester The old requester in this book's requester list to be replaced. It must not be null.
     * @param editedRequester The edited version of the old requester to replace the old requester in this book's
     *                        requester list. It must not be null.
     */
    public Book editRequester(Patron oldRequester, Patron editedRequester) {
        requireAllNonNull(oldRequester, editedRequester);
        return updateRequester(oldRequester, Optional.of(editedRequester));
    }

    /**
     * Returns a Book with the exact same fields as this book, but with the specified old requester deleted from its
     * list of requesters.
     *
     * @param oldRequester The old requester in this book's requester list to be deleted. It must not be null.
     */
    public Book deleteRequester(Patron oldRequester) {
        requireNonNull(oldRequester);
        return updateRequester(oldRequester, Optional.empty());
    }

    /**
     * Returns a Book with the exact same fields as this book, but with its borrower replaced with the edited borrower.
     * This book must be borrowed before {@code editBorrower} is called.
     *
     * @param editedBorrower The updated version of borrower. It must not be null
     */
    public Book editBorrower(Patron editedBorrower) {
        requireNonNull(editedBorrower);
        assert isBorrowed();
        BookStatus newStatus = bookStatus.editBorrower(editedBorrower);
        return new Book(this, newStatus);
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

    /**
     * Returns an immutable requester set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Patron> getRequesters() {
        return Collections.unmodifiableSet(requesters);
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
     * Returns true if this book is requested by the specified patron.
     */
    public boolean isRequestedBy(Patron patron) {
        return requesters.contains(patron);
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
     * Returns true if both books have the same book name.
     * Book names are considered to be equal based on {@link BookName#equals(Object)}
     */
    public boolean hasSameName(Book other) {
        return bookName.equals(other.bookName);
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
                && otherBook.bookStatus.equals(bookStatus)
                && otherBook.requesters.equals(requesters);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(bookName, isbn, authors, tags, timeAdded, bookStatus, requesters);
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
            authors.forEach(author -> builder.append(author.toString() + " "));
        }

        Set<Tag> tags = getTags();
        if (!tags.isEmpty()) {
            builder.append("; Tags: ");
            tags.forEach(builder::append);
        }
        builder.append("; Status: ").append(bookStatus.toString());
        if (!requesters.isEmpty()) {
            builder.append("; Requested by: ");
            requesters.forEach(r -> builder.append(r.getName()));
        }
        return builder.toString();
    }

    private Book updateRequester(Patron oldRequester, Optional<Patron> editedRequester) {
        requireAllNonNull(oldRequester, editedRequester);
        assert isRequestedBy(oldRequester);
        HashSet<Patron> newRequesters = new HashSet<>();
        newRequesters.addAll(getRequesters());
        newRequesters.remove(oldRequester);
        editedRequester.ifPresent(newRequesters::add);
        return new Book(getBookName(), getIsbn(), getAuthors(), getTags(), getTimeAdded(), getBookStatus(),
                newRequesters);
    }
}
