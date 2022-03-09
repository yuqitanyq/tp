package seedu.address.ui;

import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.bookstub.Author;
import seedu.address.model.bookstub.BookStub;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class BookCard extends UiPart<Region> {

    private static final String FXML = "BookListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final BookStub bookStub;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
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

    /**
     * Creates a {@code Book} with the given {@code Book} and index to display.
     */
    public BookCard(BookStub bookStub, int displayedIndex) {
        super(FXML);
        this.bookStub = bookStub;
        id.setText(displayedIndex + ". ");
        name.setText(bookStub.getName().fullName);

        // Author check needed since author is an optional parameter in book command
        if (!bookStub.getAuthor().isEmpty()) {
            authors.setVisible(true);
            authors.setManaged(true);
            String authorsString = bookStub.getAuthor().stream()
                    .sorted(Comparator.comparing(author -> author.getName()))
                    .map(Author::getName)
                    .collect(Collectors.joining(", "));
            authors.setText("Author: " + authorsString);
        }

        isbn.setText("ISBN: " + bookStub.getIsbn());

        bookStub.getCategoryTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> bookCategoryTags.getChildren().add(new Label(tag.tagName)));

        // Book status check to properly render relevant tags
        if (bookStub.isAvailable()) {
            bookAvailableTag.getChildren().add(new Label("Available"));
        } else {
            Collections.addAll(bookBorrowTag.getChildren(), new Label("Borrowed"),
                    new Label(bookStub.getDate()));
        }
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
        return id.getText().equals(card.id.getText())
                && bookStub.equals(card.bookStub);
    }
}
