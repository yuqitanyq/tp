package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.address.model.book.Book;
import seedu.address.model.book.BookList;
import seedu.address.model.person.Patron;
import seedu.address.model.person.UniquePersonList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList patrons;
    private final BookList books;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        patrons = new UniquePersonList();
        books = new BookList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Patrons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code patrons}.
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
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPatrons(newData.getPatronList());
        setBooks(newData.getBookList());
    }

    //// person-level operations

    /**
     * Returns true if a patron with the same identity as {@code patron} exists in the address book.
     */
    public boolean hasPatron(Patron patron) {
        requireNonNull(patron);
        return patrons.contains(patron);
    }

    /**
     * Returns true if a book with the same identity as {@code book} exists in the address book.
     */
    public boolean hasBook(Book book) {
        requireNonNull(book);
        return books.contains(book);
    }

    /**
     * Adds a patron to the address book.
     * The patron must not already exist in the address book.
     */
    public void addPatron(Patron p) {
        patrons.add(p);
    }

    /**
     * Adds a book to the address book.
     */
    public void addBook(Book book) {
        books.add(book);
    }

    /**
     * Replaces the given patron {@code target} in the list with {@code editedPatron}.
     * {@code target} must exist in the address book.
     * The patron identity of {@code editedPatron} must not be the same as another existing patron in the address book.
     */
    public void setPatron(Patron target, Patron editedPatron) {
        requireNonNull(editedPatron);

        patrons.setPatron(target, editedPatron);
    }

    /**
     * Replaces the given book {@code target} in the list with {@code editedBook}.
     * {@code target} must exist in the address book.
     */
    public void setBook(Book target, Book editedBook) {
        requireNonNull(editedBook);

        books.setBook(target, editedBook);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePatron(Patron key) {
        patrons.remove(key);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeBook(Book key) {
        books.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return patrons.asUnmodifiableObservableList().size() + " persons";
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
                || (other instanceof AddressBook // instanceof handles nulls
                && patrons.equals(((AddressBook) other).patrons)
                && books.equals(((AddressBook) other).books));
    }

    @Override
    public int hashCode() {
        return Objects.hash(patrons, books);
    }
}
