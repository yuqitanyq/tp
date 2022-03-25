package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.book.Book;
import seedu.address.model.patron.Patron;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Patron> PREDICATE_SHOW_ALL_PATRONS = unused -> true;
    Predicate<Book> PREDICATE_SHOW_ALL_BOOKS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user preLibTask file path.
     */
    Path getLibTaskFilePath();

    /**
     * Sets the user preLibTask file path.
     */
    void setLibTaskFilePath(Path libTaskFilePath);

    /**
     * Replace LibTask data with the data in {@code libTask}.
     */
    void setLibTask(ReadOnlyLibTask libTask);

    /** Returns the LibTask */
    ReadOnlyLibTask getLibTask();

    /**
     * Returns true if a patron with the same identity as {@code patron} exists in LibTask.
     */
    boolean hasPatron(Patron patron);

    /**
     * Returns true if a book with the same identity as {@code book} exists in LibTask.
     */
    boolean hasBook(Book book);

    /**
     * Deletes the given patron.
     * The patron must exist in LibTask.
     */
    void deletePatron(Patron target);

    /**
     * Deletes the given book.
     * The book must exist in LibTask.
     */
    void deleteBook(Book target);

    /**
     * Adds the given patron.
     * {@code patron} must not already exist in LibTask.
     */
    void addPatron(Patron patron);

    /**
     * Adds the given book.
     */
    void addBook(Book book);

    /**
     * Replaces the given patron {@code target} with {@code editedPatron}.
     * {@code target} must exist in LibTask.
     * The patron identity of {@code editedPatron} must not be the same as another existing patron in LibTask.
     */
    void setPatron(Patron target, Patron editedPatron);

    /**
     * Replaces the given book {@code target} with {@code editedBook}.
     * {@code target} must exist in LibTask.
     * The book identity of {@code editedBook} must not be the same as another existing book in LibTask.
     */
    void setBook(Book target, Book editedBook);

    /**
     * Replaces all books borrowed by {@code borrower} with the same book, but with available status in LibTask.
     */
    void returnAllBorrowedBooks(Patron borrower);

    /**
     * Returns true if the specified patron is currently borrowing at least one book.
     */
    boolean isBorrowingSomeBook(Patron borrower);

    boolean hasOverdueBooks(Patron patron);

    /**
     * Returns true if this model containt a model that is not the same as {@code oldPatron} based on
     * {@link Patron#equals(Object), but same as {@code editedPatron} based on {@link Patron#isSamePatron(Patron)}.
     */
    boolean hasEditedPatron(Patron oldPatron, Patron editedPatron);

    /**
     * Replaces the given book {@code bookToBorrow} with a new book with all same fields except status.
     * The new status will be {@link seedu.address.model.book.BookStatusType#BORROWED} status
     * with {@code borrower} as the borrower and {@returnDate} as the returnDate.
     *
     * Both {@code borrower} {@code bookToBorrow} must exist in LibTask,
     * and {@code returnDate} must be in dd-MMM-yyyy format.
     */
    void borrowBook(Patron borrower, Book bookToBorrow, String returnDate);

    /** Returns an unmodifiable view of the filtered patron list */
    ObservableList<Patron> getFilteredPatronList();

    /** Returns an unmodifiable view of the filtered book list */
    ObservableList<Book> getFilteredBookList();

    /**
     * Updates the filter of the filtered patron list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPatronList(Predicate<Patron> predicate);

    /**
     * Updates the filter of the filtered book list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredBookList(Predicate<Book> predicate);

}
