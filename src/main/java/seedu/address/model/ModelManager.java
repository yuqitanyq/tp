package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.book.Book;
import seedu.address.model.patron.Patron;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Patron> filteredPatrons;
    private final FilteredList<Book> filteredBooks;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPatrons = new FilteredList<>(this.addressBook.getPatronList());
        filteredBooks = new FilteredList<>(this.addressBook.getBookList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasPatron(Patron patron) {
        requireNonNull(patron);
        return addressBook.hasPatron(patron);
    }

    @Override
    public boolean hasBook(Book book) {
        requireNonNull(book);
        return addressBook.hasBook(book);
    }

    @Override
    public void deletePatron(Patron target) {
        addressBook.removePatron(target);
    }

    @Override
    public void deleteBook(Book target) {
        addressBook.removeBook(target);
    }

    @Override
    public void addPatron(Patron patron) {
        addressBook.addPatron(patron);
        updateFilteredPatronList(PREDICATE_SHOW_ALL_PATRONS);
    }

    @Override
    public void addBook(Book book) {
        addressBook.addBook(book);
        updateFilteredBookList(PREDICATE_SHOW_ALL_BOOKS);
    }

    @Override
    public void setPatron(Patron target, Patron editedPatron) {
        requireAllNonNull(target, editedPatron);

        addressBook.setPatron(target, editedPatron);
    }

    @Override
    public void setBook(Book target, Book editedBook) {
        requireAllNonNull(target, editedBook);

        addressBook.setBook(target, editedBook);
    }

    //=========== Filtered Patron List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Patron} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Patron> getFilteredPatronList() {
        return filteredPatrons;
    }

    /**
     * Returns an unmodifiable view of the list of {@code Book} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Book> getFilteredBookList() {
        return filteredBooks;
    }

    @Override
    public void updateFilteredPatronList(Predicate<Patron> predicate) {
        requireNonNull(predicate);
        filteredPatrons.setPredicate(predicate);
    }

    @Override
    public void updateFilteredBookList(Predicate<Book> predicate) {
        requireNonNull(predicate);
        filteredBooks.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return addressBook.equals(other.addressBook)
                && userPrefs.equals(other.userPrefs)
                && filteredPatrons.equals(other.filteredPatrons)
                && filteredBooks.equals(other.filteredBooks);
    }

}
