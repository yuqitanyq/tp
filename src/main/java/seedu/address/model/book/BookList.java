package seedu.address.model.book;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
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
     * Replaces the given book {@code target} with {@code editedBook}. If there are any other books with the same isbn
     * as {@code target}, update the authors and names of all those books to ensure consistency about books.
     * {@code target} must exist in LibTask.
     *
     * @return True if some other book other than target is modified for the sake of consistency.
     */
    public boolean setAndEditBook(Book target, Book editedBook) {
        // setBook must be done first, otherwise tags will not be updated
        setBook(target, editedBook);
        boolean hasModifiedOtherBooks = false;
        for (Book book : internalList) {
            if (!book.hasSameIsbn(target) || book.equals(editedBook)) {
                continue;
            }
            if (!book.hasSameIsbn(editedBook) || !book.hasSameName(editedBook) || !book.hasSameAuthors(editedBook)) {
                Book updatedConsistentBook = book.getConsistentReplacement(editedBook);
                setBook(book, updatedConsistentBook);
                hasModifiedOtherBooks = true;
            }
        }
        return hasModifiedOtherBooks;
    }

    /**
     * Replaces all books that are borrowed or requested by {@code target} with new book objects such that
     * {@code target} is replaced by {@code editedPatron} in the new book's requesters list and borrower.
     *
     * @return A message string representing the notifications for the book updates.
     */
    public String updateBookAfterPatronEdit(Patron target, Patron editedPatron) {
        requireAllNonNull(target, editedPatron);
        return updatePatronBorrowedBooks(target, editedPatron) + updatePatronRequestedBooks(target, editedPatron);
    }

    /**
     * Replaces all books that are borrowed or requested by {@code deletedPatron} with new book objects such that
     * {@code deletedPatron} is removed from the new book's requesters list.
     *
     * @return A message string representing the notifications for the book updates.
     */
    public String updateBookAfterPatronDelete(Patron deletedPatron) {
        requireNonNull(deletedPatron);
        boolean hasModifiedSomeBooks = false;
        for (Book book : internalList) {
            if (!book.isRequestedBy(deletedPatron)) {
                continue;
            }
            Book updatedBook = book.deleteRequester(deletedPatron);
            setBook(book, updatedBook);
            hasModifiedSomeBooks = true;
        }
        return hasModifiedSomeBooks
                ? String.format("%s is also deleted from the requesters list of some books\n", deletedPatron.getName())
                : "";
    }

    /**
     * Replaces all books that have the same isbn as {@code bookToRequest} with new book objects such that
     * {@code requester} is added to the new book's requesters list.
     */
    public void addRequest(Book bookToRequest, Patron requester) {
        requireAllNonNull(bookToRequest, requester);
        for (Book book : internalList) {
            if (!book.hasSameIsbn(bookToRequest)) {
                continue;
            }
            Book updatedBook = book.addRequester(requester);
            setBook(book, updatedBook);
        }
    }

    /**
     * Returns true if there is at least one available copy of book with the same isbn as {@code book}
     */
    public boolean hasAvailableCopy(Book book) {
        requireNonNull(book);
        return internalList.stream().anyMatch(b -> b.hasSameIsbn(book) && b.isAvailable());
    }

    /**
     * Returns true if the specified {@code patron} is currently borrowing a copy book with the same isbn as
     * the specified {@code book}.
     */
    public boolean isBorrowing(Patron patron, Book book) {
        requireAllNonNull(patron, book);
        return internalList.stream().anyMatch(b -> b.hasSameIsbn(book) && b.isBorrowedBy(patron));
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
     *
     * @return The list of available books that were returned by {@code borrower}
     */
    public List<Book> returnAllBorrowedBooks(Patron borrower) {
        ArrayList<Book> returnedBooks = new ArrayList<>();
        for (Book book : internalList) {
            if (!book.isBorrowedBy(borrower)) {
                continue;
            }
            Book updatedAvailableBook = new Book(book, BookStatus.createAvailableBookStatus());
            setBook(book, updatedAvailableBook);
            returnedBooks.add(updatedAvailableBook);
        }
        return returnedBooks;
    }

    /**
     * Returns true if there is some other book in this book list with the same isbn, but different book name or
     * different set of authors as {@code bookToCheck}.
     */
    public boolean hasSameIsbnDiffAuthorsOrName(Book bookToCheck) {
        return internalList.stream().anyMatch(book -> book.hasSameIsbn(bookToCheck)
                && (!book.hasSameAuthors(bookToCheck) || !book.hasSameName(bookToCheck)));
    }

    /**
     * Returns true if there is some book in this book list with the same isbn as {@code bookToCheck}.
     */
    public boolean hasSameIsbn(Book bookToCheck) {
        return internalList.stream().anyMatch(book -> book.hasSameIsbn(bookToCheck));
    }

    /**
     * Returns the number of maximum requests allowed for the given {@link Book}
     */
    public int getMaxRequests(Book bookToRequest) {
        requireNonNull(bookToRequest);
        return (int) internalList.stream().filter(book -> book.hasSameIsbn(bookToRequest)).count();
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
     * Returns true if a specified patron has overdue books.
     */
    public boolean hasOverdueBooks(Patron patron) {
        requireNonNull(patron);
        return internalList.stream().anyMatch(book -> book.isBorrowedBy(patron) && book.getBookStatus().isOverdue());
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

    private String updatePatronBorrowedBooks(Patron target, Patron editedPatron) {
        requireAllNonNull(target, editedPatron);
        boolean hasModifiedSomeBooks = false;
        for (Book book : internalList) {
            if (!book.isBorrowedBy(target)) {
                continue;
            }
            Book updatedBook = book.editBorrower(editedPatron);
            setBook(book, updatedBook);
            hasModifiedSomeBooks = true;
        }
        return hasModifiedSomeBooks
                ? String.format("Borrower information of %s is also edited in some books\n", editedPatron.getName())
                : "";
    }

    private String updatePatronRequestedBooks(Patron target, Patron editedPatron) {
        requireAllNonNull(target, editedPatron);
        boolean hasModifiedSomeBooks = false;
        for (Book book : internalList) {
            if (!book.isRequestedBy(target)) {
                continue;
            }
            Book updatedBook = book.editRequester(target, editedPatron);
            setBook(book, updatedBook);
            hasModifiedSomeBooks = true;
        }
        return hasModifiedSomeBooks
                ? String.format("Requester information of %s is also edited in some books\n", editedPatron.getName())
                : "";
    }
}
