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
    Predicate<Patron> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
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
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Patron person);

    /**
     * Returns true if a book with the same identity as {@code book} exists in the address book.
     */
    boolean hasBook(Book book);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Patron target);

    /**
     * Deletes the given book.
     * The book must exist in the address book.
     */
    void deleteBook(Book target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Patron person);

    /**
     * Adds the given book.
     */
    void addBook(Book book);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Patron target, Patron editedPerson);

    /**
     * Replaces the given book {@code target} with {@code editedBook}.
     * {@code target} must exist in the address book.
     * The book identity of {@code editedBook} must not be the same as another existing book in the address book.
     */
    void setBook(Book target, Book editedBook);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Patron> getFilteredPersonList();

    /** Returns an unmodifiable view of the filtered book list */
    ObservableList<Book> getFilteredBookList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Patron> predicate);

    /**
     * Updates the filter of the filtered book list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredBookList(Predicate<Book> predicate);
}
