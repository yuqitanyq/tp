package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.book.Book;
import seedu.address.model.person.Patron;

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
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a patron with the same identity as {@code patron} exists in the address book.
     */
    boolean hasPatron(Patron patron);

    /**
     * Returns true if a book with the same identity as {@code book} exists in the address book.
     */
    boolean hasBook(Book book);

    /**
     * Deletes the given patron.
     * The patron must exist in the address book.
     */
    void deletePatron(Patron target);

    /**
     * Deletes the given book.
     * The book must exist in the address book.
     */
    void deleteBook(Book target);

    /**
     * Adds the given patron.
     * {@code patron} must not already exist in the address book.
     */
    void addPatron(Patron patron);

    /**
     * Adds the given book.
     */
    void addBook(Book book);

    /**
     * Replaces the given patron {@code target} with {@code editedPatron}.
     * {@code target} must exist in the address book.
     * The patron identity of {@code editedPatron} must not be the same as another existing patron in the address book.
     */
    void setPatron(Patron target, Patron editedPatron);

    /**
     * Replaces the given book {@code target} with {@code editedBook}.
     * {@code target} must exist in the address book.
     * The book identity of {@code editedBook} must not be the same as another existing book in the address book.
     */
    void setBook(Book target, Book editedBook);

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
