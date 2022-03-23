package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.book.Author;
import seedu.address.model.book.Book;

/**
 * An UI component that displays information of a {@code Book}.
 */
public class BookCard extends UiPart<Region> {

    private static final String FXML = "BookListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on LibTask level 4</a>
     */

    public final Book book;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label bookCardDisplayId;
    @FXML
    private Label authors;
    @FXML
    private Label isbn;
    @FXML
    private FlowPane bookCategoryTags;
    @FXML
    private FlowPane bookAvailableTag;
    @FXML
    private FlowPane bookBorrowTag;
    @FXML
    private Label borrower;
    @FXML
    private Label bookReturnDate;
    @FXML
    private Label requestedBy;

    /**
     * Creates a {@code Book} with the given {@code Book} and index to display.
     */
    public BookCard(Book book, int displayedIndex) {
        super(FXML);
        requireNonNull(book);
        this.book = book;
        bookCardDisplayId.setText(displayedIndex + ". ");
        name.setText(book.getBookName().toString());
        setBookCategoryTags(book);
        setAuthor(book);
        isbn.setText("ISBN: " + book.getIsbn());
        setBookStatusDetails(book);
        setRequestsDetails(book);
    }

    /**
     * Sorts and sets the FXML book category tags with the category data from the supplied book object.
     *
     * @param book {@code Book} which the book object to obtain the category tags from.
     */
    public void setBookCategoryTags(Book book) {
        book.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> bookCategoryTags.getChildren().add(new Label(tag.tagName)));
    }

    /**
     * Sorts and sets the FXML author label with the author data from the supplied book object.
     *
     * @param book {@code Book} which the book object to obtain the author data from.
     */
    public void setAuthor(Book book) {
        // Author check needed since author is an optional parameter in book command
        if (!book.getAuthors().isEmpty()) {
            authors.setVisible(true);
            authors.setManaged(true);
            String authorsString = book.getAuthors().stream()
                    .sorted(Comparator.comparing(author -> author.toString()))
                    .map(Author::toString)
                    .collect(Collectors.joining(", "));
            authors.setText("Author: " + authorsString);
            return;
        }
        authors.setVisible(false);
        authors.setManaged(false);
    }

    /**
     * Sets the FXML book status labels based on the availability status of the supplied book object.
     *
     * @param book {@code Book} which the book object to obtain availability status, borrower and book return date.
     */
    public void setBookStatusDetails(Book book) {
        if (book.isAvailable()) {
            // hide borrower and book return date details as book is available
            borrower.setVisible(false);
            borrower.setManaged(false);
            bookReturnDate.setVisible(false);
            bookReturnDate.setManaged(false);
            bookAvailableTag.getChildren().add(new Label("Available"));
        } else {
            // borrower and book return date labels will be set to visible as by default its hidden
            borrower.setVisible(true);
            borrower.setManaged(true);
            bookReturnDate.setVisible(true);
            bookReturnDate.setManaged(true);

            Collections.addAll(bookBorrowTag.getChildren(), new Label("Borrowed"));
            borrower.setText("Borrower: " + book.getBorrowerName());
            bookReturnDate.setText("Return date: " + book.getReturnDateString());
        }
    }

    /**
     * Sorts and sets the FXML book requesters with the requester data from the supplied book object.
     *
     * @param book {@code Book} which the book object to obtain the requesters from.
     */
    public void setRequestsDetails(Book book) {
        // Requester check needed since it is optional
        if (!book.getRequesters().isEmpty()) {
            requestedBy.setVisible(true);
            requestedBy.setManaged(true);

            String requestersString = book.getRequesters().stream()
                    .sorted(Comparator.comparing(requester -> requester.getName().fullName))
                    .map(requester -> requester.getName().fullName)
                    .collect(Collectors.joining(", "));

            requestedBy.setText("Requested by: " + requestersString);
            return;
        }
        requestedBy.setVisible(false);
        requestedBy.setManaged(false);

    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof BookCard)) {
            return false;
        }

        // state check
        BookCard card = (BookCard) other;
        return bookCardDisplayId.getText().equals(card.bookCardDisplayId.getText())
                && book.equals(card.book);
    }
}
