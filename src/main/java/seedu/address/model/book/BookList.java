package seedu.address.model.book;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.book.exceptions.BookNotFoundException;
import seedu.address.model.patron.Patron;

/**
 * A list of persons that does not allow nulls.
 *
 * Supports a minimal set of list operations.
 */
public class BookList implements Iterable<Book> {

    private final ObservableList<Book> internalList = FXCollections.observableArrayList();
    private final ObservableList<Book> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent book as the given argument.
     * Equivalence is defined based on {@link Book#isSameBook(Book)}
     *
     * @param toCheck The book to be checked.
     */
    public boolean contains(Book toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameBook);
    }

    /**
     * Adds a book to the list.
     */
    public void add(Book book) {
        requireNonNull(book);
        internalList.add(book);
    }

    /**
     * Replaces the book {@code target} in the list with {@code editedBook}.
     * {@code target} must exist in the list.
     */
    public void setBook(Book target, Book editedBook) {
        requireAllNonNull(target, editedBook);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new BookNotFoundException();
        }
        internalList.set(index, editedBook);
    }

    /**
     * Removes the equivalent book from the list.
     * The book must exist in the list.
     */
    public void remove(Book toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new BookNotFoundException();
        }
    }

    public void setBooks(BookList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code books}.
     * {@code books} must not contain duplicate books.
     */
    public void setBooks(List<Book> books) {
        requireAllNonNull(books);
        internalList.setAll(books);
    }

    /**
     * Replaces all books borrowed by {@code borrower} with the same book, but with available status in this book list.
     */
    public void returnAllBorrowedBooks(Patron borrower) {
        for (Book book : internalList) {
            if (!book.isBorrowedBy(borrower)) {
                continue;
            }
            Book updatedAvailableBook = new Book(book, BookStatus.createAvailableBookStatus());
            setBook(book, updatedAvailableBook);
        }
    }

    /**
     * Returns true if there is some book in this book list with the same isbn, but different book name or
     * different set of authors as {@code bookToCheck}.
     */
    public boolean hasSameIsbnDiffAuthorsOrName(Book bookToCheck) {
        return internalList.stream().anyMatch(book -> book.hasSameIsbn(bookToCheck) &&
                (!book.hasSameAuthors(bookToCheck) || !book.hasSameName(bookToCheck)));
    }

    /**
     * Removes all book requests from all books in this book list that has the same isbn as any book in {@param books}
     *
     * @return A message string representing the notifications for distinct book request that were deleted.
     */
    public String deleteAllRequests(Book ... books) {
        HashSet<Isbn> notifiedBooks = new HashSet<>(); // To prevent adding the same notification multiple times
        StringBuilder builder = new StringBuilder();
        List<Book> booksToProcess = Arrays.stream(books).collect(Collectors.toList());
        for (Book book : booksToProcess) {
            if (notifiedBooks.contains(book.getIsbn())) {
                continue;
            }
            notifiedBooks.add(book.getIsbn());
            builder.append(deleteRequestSingleBook(book));
        }
        return builder.toString();
    }

    /**
     * Returns true if the specified patron is currently borrowing at least one book.
     */
    public boolean isBorrowingSomeBook(Patron borrower) {
        requireNonNull(borrower);
        return internalList.stream().anyMatch(book -> book.isBorrowedBy(borrower));
    }

    /**
     * Replaces the given book {@code bookToBorrow} with a new book with all same fields except status.
     * The new status will be {@link seedu.address.model.book.BookStatusType#BORROWED} status
     * with {@code borrower} as the borrower and {@returnDate} as the returnDate.
     *
     * Both {@code borrower} {@code bookToBorrow} must exist in LibTask,
     * and {@code returnDate} must be in dd-MMM-yyyy format.
     */
    public void borrowBook(Patron borrower, Book bookToBorrow, String returnDate) {
        requireAllNonNull(borrower, bookToBorrow, returnDate);
        BookStatus borrowedStatus = new BookStatus(BookStatusType.BORROWED,
                Optional.of(borrower), Optional.of(BookStatus.getCurrentDateString()), Optional.of(returnDate));
        Book updatedBook = new Book(bookToBorrow, borrowedStatus);
        setBook(bookToBorrow, updatedBook);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Book> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Book> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BookList // instanceof handles nulls
                && internalList.equals(((BookList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    private String deleteRequestSingleBook(Book bookToDelete) {
        StringBuilder builder = new StringBuilder();
        boolean hasNotified = false; // flag to prevent appending the same notification many times
        for (Book book : internalList) {
            if (!book.hasSameIsbn(bookToDelete) || book.getRequesters().isEmpty()) {
                continue;
            }
            if (!hasNotified) {
                book.getRequesters().stream().forEach(requester ->
                        builder.append(String.format("You should notify %s about availability of %s\n",
                                requester.getName(), book.getBookName())));
                hasNotified = true;
            }
            Book updatedBookEmptyRequest = book.getBookWithEmptyRequest();
            setBook(book, updatedBookEmptyRequest);
        }
        return builder.toString();
    }
}
