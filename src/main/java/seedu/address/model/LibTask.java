package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.address.model.book.Book;
import seedu.address.model.book.BookList;
import seedu.address.model.patron.Patron;
import seedu.address.model.patron.UniquePatronList;

/**
 * Wraps all data at the LibTask level
 * Duplicates are not allowed (by .isSamePatron comparison)
 */
public class LibTask implements ReadOnlyLibTask {

    private final UniquePatronList patrons;
    private final BookList books;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        patrons = new UniquePatronList();
        books = new BookList();
    }

    public LibTask() {}

    /**
     * Creates an LibTask using the Patrons in the {@code toBeCopied}
     */
    public LibTask(ReadOnlyLibTask toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the patron list with {@code patrons}.
     * {@code patrons} must not contain duplicate patrons.
     */
    public void setPatrons(List<Patron> patrons) {
        this.patrons.setPatrons(patrons);
    }

    /**
     * Replaces the contents of the book list with {@code books}.
     */
    public void setBooks(List<Book> books) {
        this.books.setBooks(books);
    }

    /**
     * Resets the existing data of this {@code LibTask} with {@code newData}.
     */
    public void resetData(ReadOnlyLibTask newData) {
        requireNonNull(newData);

        setPatrons(newData.getPatronList());
        setBooks(newData.getBookList());
    }

    //// patron-level operations

    /**
     * Returns true if a patron with the same identity as {@code patron} exists in LibTask.
     */
    public boolean hasPatron(Patron patron) {
        requireNonNull(patron);
        return patrons.contains(patron);
    }

    /**
     * Returns true if a book with the same identity as {@code book} exists in LibTask.
     */
    public boolean hasBook(Book book) {
        requireNonNull(book);
        return books.contains(book);
    }

    /**
     * Returns true if there is some book in this LibTask's book list with the same isbn, but different book name or
     * different set of authors as {@code bookToCheck}.
     */
    public boolean hasSameIsbnDiffAuthorsOrName(Book bookToCheck) {
        requireNonNull(bookToCheck);
        return books.hasSameIsbnDiffAuthorsOrName(bookToCheck);
    }

    /**
     * Returns true if there is some book in this LibTask's book list with the same isbn as {@code bookToCheck}.
     */
    public boolean hasSameIsbn(Book bookToCheck) {
        requireNonNull(bookToCheck);
        return books.hasSameIsbn(bookToCheck);
    }

    /**
     * Removes all book requests from all books in this LibTask's book list that has the same isbn as any book in
     * {@param booksToDelete}.
     *
     * @return A message string representing the notifications for distinct book request that were deleted.
     */
    public String deleteAllRequests(Book ... booksToDelete) {
        return books.deleteAllRequests(booksToDelete);
    }

    /**
     * Adds a patron to LibTask.
     * The patron must not already exist in LibTask.
     */
    public void addPatron(Patron p) {
        patrons.add(p);
    }

    /**
     * Adds a book to LibTask.
     */
    public void addBook(Book book) {
        books.add(book);
    }

    /**
     * Replaces the given patron {@code target} in the list with {@code editedPatron}.
     * {@code target} must exist in LibTask.
     * The patron identity of {@code editedPatron} must not be the same as another existing patron in LibTask.
     */
    public void setPatron(Patron target, Patron editedPatron) {
        requireNonNull(editedPatron);

        patrons.setPatron(target, editedPatron);
    }

    /**
     * Replaces the given book {@code target} in the list with {@code editedBook}.
     * {@code target} must exist in LibTask.
     */
    public void setBook(Book target, Book editedBook) {
        requireNonNull(editedBook);

        books.setBook(target, editedBook);
    }

    /**
     * Replaces the given book {@code target} with {@code editedBook}. If there are any other books with the same isbn
     * as {@code target}, update the authors and names of all those books to ensure consistency about books.
     * {@code target} must exist in LibTask.
     *
     * @return True if some other book other than target is modified for the sake of consistency.
     */
    public boolean setAndEditBook(Book target, Book editedBook) {
        requireAllNonNull(target, editedBook);
        return books.setAndEditBook(target, editedBook);
    }

    /**
     * Replaces all books that are borrowed or requested by {@code target} with new book objects such that
     * {@code target} is replaced by {@code editedPatron} in the new book's requesters list and borrower.
     *
     * @return A message string representing the notifications for the book updates.
     */
    public String updateBookAfterPatronEdit(Patron target, Patron editedPatron) {
        requireAllNonNull(target, editedPatron);
        return books.updateBookAfterPatronEdit(target, editedPatron);
    }

    /**
     * Replaces all books that are borrowed or requested by {@code deletedPatron} with new book objects such that
     * {@code deletedPatron} is removed from the new book's requesters list.
     *
     * @return A message string representing the notifications for the book updates.
     */
    public String updateBookAfterPatronDelete(Patron deletedPatron) {
        requireNonNull(deletedPatron);
        return books.updateBookAfterPatronDelete(deletedPatron);
    }

    /**
     * Replaces all books that have the same isbn as {@code bookToRequest} with new book objects such that
     * {@code requester} is added to the new book's requesters list.
     */
    public void addRequest(Book bookToRequest, Patron requester) {
        requireAllNonNull(bookToRequest, requester);
        books.addRequest(bookToRequest, requester);
    }

    /**
     * Returns true if there is at least one available copy of book with the same isbn as {@code book}
     */
    public boolean hasAvailableCopy(Book book) {
        requireNonNull(book);
        return books.hasAvailableCopy(book);
    }

    /**
     * Returns true if the specified {@code patron} is currently borrowing a copy book with the same isbn as
     * the specified {@code book}.
     */
    public boolean isBorrowing(Patron patron, Book book) {
        requireAllNonNull(patron, book);
        return books.isBorrowing(patron, book);
    }

    /**
     * Replaces all books borrowed by {@code borrower} with the same book, but with available status in LibTask.
     *
     * @return The list of available books that were returned by {@code borrower}
     */
    public List<Book> returnAllBorrowedBooks(Patron borrower) {
        requireNonNull(borrower);

        return books.returnAllBorrowedBooks(borrower);
    }

    /**
     * Returns true if the specified patron is currently borrowing at least one book.
     */
    public boolean isBorrowingSomeBook(Patron borrower) {
        requireNonNull(borrower);
        return books.isBorrowingSomeBook(borrower);
    }

    /**
     * Returns true if a specified patron has overdue books.
     */
    public boolean hasOverdueBooks(Patron patron) {
        requireNonNull(patron);
        return books.hasOverdueBooks(patron);
    }

    /**
     * Returns true if this model contains a model that is not the same as {@code oldPatron} based on
     * {@link Patron#equals(Object)}, but same as {@code editedPatron} based on {@link Patron#isSamePatron(Patron)}.
     */
    boolean hasEditedPatron(Patron oldPatron, Patron editedPatron) {
        requireAllNonNull(oldPatron, editedPatron);
        return patrons.hasEditedPatron(oldPatron, editedPatron);
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

        books.borrowBook(borrower, bookToBorrow, returnDate);
    }

    /**
     * Removes {@code key} from this {@code LibTask}.
     * {@code key} must exist in LibTask.
     */
    public void removePatron(Patron key) {
        patrons.remove(key);
    }

    /**
     * Removes {@code key} from this {@code LibTask}.
     * {@code key} must exist in LibTask.
     */
    public void removeBook(Book key) {
        books.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return patrons.asUnmodifiableObservableList().size() + " patrons";
        // TODO: refine later
    }

    @Override
    public ObservableList<Patron> getPatronList() {
        return patrons.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Book> getBookList() {
        return books.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LibTask // instanceof handles nulls
                && patrons.equals(((LibTask) other).patrons)
                && books.equals(((LibTask) other).books));
    }

    @Override
    public int hashCode() {
        return Objects.hash(patrons, books);
    }
}
