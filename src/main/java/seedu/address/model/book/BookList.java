package seedu.address.model.book;

import static java.util.Objects.requireNonNull;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BookList {

    private final ObservableList<Book> internalList = FXCollections.observableArrayList();
    private final ObservableList<Book> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Adds a book to the list.
     */
    public void add(Book book) {
        requireNonNull(book);
        internalList.add(book);
    }
}
