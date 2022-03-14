package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.book.Book;
import seedu.address.model.patron.Patron;

/**
 * Unmodifiable view of LibTask
 */
public interface ReadOnlyLibTask {

    /**
     * Returns an unmodifiable view of the patrons list.
     * This list will not contain any duplicate patrons.
     */
    ObservableList<Patron> getPatronList();

    /**
     * Returns an unmodifiable view of the books list.
     */
    ObservableList<Book> getBookList();
}
