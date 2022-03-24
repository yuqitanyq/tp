package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.book.Author;
import seedu.address.model.book.Book;
import seedu.address.model.book.BookName;
import seedu.address.model.book.BookStatus;
import seedu.address.model.book.Isbn;
import seedu.address.model.patron.Patron;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Book}.
 */
class JsonAdaptedBook {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Book's %s field is missing!";
    private final String isbn;
    private final String bookName;
    private final List<JsonAdaptedAuthor> authors = new ArrayList<>();
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();
    private final String timeAdded;
    private final JsonAdaptedBookStatus bookStatus;
    private final List<JsonAdaptedPatron> requesters = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedBook} with the given book details.
     */
    @JsonCreator
    public JsonAdaptedBook(@JsonProperty("bookName") String bookName, @JsonProperty("isbn") String isbn,
        @JsonProperty("tagged") List<JsonAdaptedTag> tagged,
        @JsonProperty("authors") List<JsonAdaptedAuthor> authors,
        @JsonProperty("timeAdded") String timeAdded,
        @JsonProperty("bookStatus") JsonAdaptedBookStatus bookStatus,
        @JsonProperty("requesters") List<JsonAdaptedPatron> requesters) {
        this.bookName = bookName;
        this.isbn = isbn;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
        if (authors != null) {
            this.authors.addAll(authors);
        }
        this.bookStatus = bookStatus;
        this.timeAdded = timeAdded;
        if (requesters != null) {
            this.requesters.addAll(requesters);
        }
    }

    /**
     * Converts a given {@code Book} into this class for Jackson use.
     */
    public JsonAdaptedBook(Book source) {
        bookName = source.getBookName().fullBookName;
        isbn = source.getIsbn().toString();
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        authors.addAll(source.getAuthors().stream()
                .map(JsonAdaptedAuthor::new)
                .collect(Collectors.toList()));
        timeAdded = Long.toString(source.getTimeAdded());
        bookStatus = new JsonAdaptedBookStatus(source.getBookStatus());
        requesters.addAll(source.getRequesters().stream()
                .map(JsonAdaptedPatron::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted book object into the model's {@code Book} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted book.
     */
    public Book toModelType() throws IllegalValueException {
        final List<Tag> bookTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            bookTags.add(tag.toModelType());
        }
        final List<Patron> bookRequesters = new ArrayList<>();
        for (JsonAdaptedPatron requester : requesters) {
            bookRequesters.add(requester.toModelType());
        }

        final List<Author> bookAuthors = new ArrayList<>();
        for (JsonAdaptedAuthor author : authors) {
            bookAuthors.add(author.toModelType());
        }

        if (bookName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                BookName.class.getSimpleName()));
        }
        if (!BookName.isValidBookName(bookName)) {
            throw new IllegalValueException(BookName.MESSAGE_CONSTRAINTS);
        }
        final BookName modelName = new BookName(bookName);

        if (isbn == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Isbn.class.getSimpleName()));
        }
        if (!Isbn.isValidIsbn(isbn)) {
            throw new IllegalValueException(Isbn.MESSAGE_CONSTRAINTS);
        }
        final Isbn modelIsbn = new Isbn(isbn);

        final Set<Tag> modelTags = new HashSet<>(bookTags);
        final Set<Patron> modelRequesters = new HashSet<>(bookRequesters);

        if (bookStatus == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    BookStatus.class.getSimpleName()));
        }
        final BookStatus modelBookStatus = bookStatus.toModelType();

        if (timeAdded == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Long.class.getSimpleName()));
        }
        try {
            final long timeBookAdded = Long.parseLong(timeAdded);
            return new Book(modelName, modelIsbn, bookAuthors, modelTags, timeBookAdded, modelBookStatus,
                    modelRequesters);
        } catch (NumberFormatException e) {
            throw new IllegalValueException(Book.TIME_ADDED_MESSAGE_CONSTRAINTS);
        }
    }
}
