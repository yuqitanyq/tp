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

    private final LibTask libTask;
    private final UserPrefs userPrefs;
    private final FilteredList<Patron> filteredPatrons;
    private final FilteredList<Book> filteredBooks;

    /**
     * Initializes a ModelManager with the given libTask and userPrefs.
     */
    public ModelManager(ReadOnlyLibTask addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.libTask = new LibTask(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPatrons = new FilteredList<>(this.libTask.getPatronList());
        filteredBooks = new FilteredList<>(this.libTask.getBookList());
    }

    public ModelManager() {
        this(new LibTask(), new UserPrefs());
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

    //=========== LibTask ================================================================================

    @Override
    public void setAddressBook(ReadOnlyLibTask addressBook) {
        this.libTask.resetData(addressBook);
    }

    @Override
    public ReadOnlyLibTask getAddressBook() {
        return libTask;
    }

    @Override
    public boolean hasPatron(Patron patron) {
        requireNonNull(patron);
        return libTask.hasPatron(patron);
    }

    @Override
    public boolean hasBook(Book book) {
        requireNonNull(book);
        return libTask.hasBook(book);
    }

    @Override
    public void deletePatron(Patron target) {
        libTask.removePatron(target);
    }

    @Override
    public void deleteBook(Book target) {
        libTask.removeBook(target);
    }

    @Override
    public void addPatron(Patron patron) {
        libTask.addPatron(patron);
        updateFilteredPatronList(PREDICATE_SHOW_ALL_PATRONS);
    }

    @Override
    public void addBook(Book book) {
        libTask.addBook(book);
        updateFilteredBookList(PREDICATE_SHOW_ALL_BOOKS);
    }

    @Override
    public void setPatron(Patron target, Patron editedPatron) {
        requireAllNonNull(target, editedPatron);

        libTask.setPatron(target, editedPatron);
    }

    @Override
    public void setBook(Book target, Book editedBook) {
        requireAllNonNull(target, editedBook);

        libTask.setBook(target, editedBook);
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
        return libTask.equals(other.libTask)
                && userPrefs.equals(other.userPrefs)
                && filteredPatrons.equals(other.filteredPatrons)
                && filteredBooks.equals(other.filteredBooks);
    }

}
