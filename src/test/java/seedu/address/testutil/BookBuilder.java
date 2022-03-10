package seedu.address.testutil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.model.book.Author;
import seedu.address.model.book.Book;
import seedu.address.model.book.BookName;
import seedu.address.model.book.Isbn;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Book objects.
 */
public class BookBuilder {

    public static final String DEFAULT_NAME = "Harry Potter";
    public static final String DEFAULT_ISBN = "9780747532743";

    private BookName bookName;
    private Isbn isbn;
    private List<Author> authors;
    private Set<Tag> tags;

    /**
     * Creates a {@code BookBuilder} with the default details.
     */
    public BookBuilder() {
        bookName = new BookName(DEFAULT_NAME);
        isbn = new Isbn(DEFAULT_ISBN);
        authors = new ArrayList<Author>();
        tags = new HashSet<Tag>();
    }

    /**
     * Initializes the BookBuilder with the data of {@code bookToCopy}.
     */
    public BookBuilder(Book bookToCopy) {
        bookName = bookToCopy.getBookName();
        isbn = bookToCopy.getIsbn();
        authors = new ArrayList<Author>(bookToCopy.getAuthors());
        tags = new HashSet<Tag>(bookToCopy.getTags());
    }

    /**
     * Sets the {@code BookName} of the {@code Book} that we are building.
     */
    public BookBuilder withName(String bookName) {
        this.bookName = new BookName(bookName);
        return this;
    }

    /**
     * Parses the {@code authors} into a {@code List<Author>} and set it to the {@code Book} that we are building.
     */
    public BookBuilder withAuthors(String ... authors) {
        this.authors = SampleDataUtil.getAuthorList(authors);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Book} that we are building.
     */
    public BookBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Isbn} of the {@code Book} that we are building.
     */
    public BookBuilder withIsbn(String isbn) {
        this.isbn = new Isbn(isbn);
        return this;
    }

    public Book build() {
        return new Book(bookName, isbn, authors, tags);
    }
}
