package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.model.util.SampleDataUtil.SAMPLE_AVAILABLE_STATUS;
import static seedu.address.model.util.SampleDataUtil.SAMPLE_BOOK_CREATED_TIME;
import static seedu.address.storage.JsonAdaptedBook.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalBooks.ALGORITHM;
import static seedu.address.testutil.TypicalPatrons.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.book.Book;
import seedu.address.model.book.BookName;
import seedu.address.model.book.BookStatus;
import seedu.address.model.book.Isbn;

public class JsonAdaptedBookTest {
    private static final String INVALID_BOOK_NAME = "H@rryPotter";
    private static final String INVALID_ISBN = "+97829812";
    private static final String INVALID_TAG = "#Adventure";
    private static final String INVALID_AUTHOR = "J!KRowling";
    private static final String INVALID_TIME_ADDED = "askdj";
    private static final String INVALID_REQUESTER_NAME = "R@chel";

    private static final String VALID_BOOK_NAME = ALGORITHM.getBookName().fullBookName;
    private static final String VALID_ISBN = ALGORITHM.getIsbn().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = ALGORITHM.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());
    private static final List<JsonAdaptedAuthor> VALID_AUTHORS = ALGORITHM.getAuthors().stream()
            .map(JsonAdaptedAuthor::new)
            .collect(Collectors.toList());
    private static final String SAMPLE_CREATED_TIME = Long.toString(SAMPLE_BOOK_CREATED_TIME);
    private static final JsonAdaptedBookStatus SAMPLE_AVAILABLE_BOOK_STATUS =
            new JsonAdaptedBookStatus(SAMPLE_AVAILABLE_STATUS);
    private static final List<JsonAdaptedPatron> VALID_REQUESTERS = ALGORITHM.getRequesters().stream()
            .map(JsonAdaptedPatron::new)
            .collect(Collectors.toList());
    private static final String VALID_REQUESTER_PHONE = BENSON.getPhone().toString();
    private static final String VALID_REQUESTER_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_REQUESTER_ID = BENSON.getId().toString();
    private static final List<JsonAdaptedTag> VALID_REQUESTER_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validBookDetails_returnsBook() throws Exception {
        JsonAdaptedBook book = new JsonAdaptedBook(ALGORITHM);
        assertEquals(ALGORITHM, book.toModelType());
    }

    @Test
    public void toModelType_invalidBookName_throwsIllegalValueException() {
        JsonAdaptedBook book =
                new JsonAdaptedBook(INVALID_BOOK_NAME, VALID_ISBN, VALID_TAGS, VALID_AUTHORS, SAMPLE_CREATED_TIME,
                        SAMPLE_AVAILABLE_BOOK_STATUS, VALID_REQUESTERS);
        String expectedMessage = BookName.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, book::toModelType);
    }

    @Test
    public void toModelType_nullBookName_throwsIllegalValueException() {
        JsonAdaptedBook book = new JsonAdaptedBook(null, VALID_ISBN, VALID_TAGS, VALID_AUTHORS,
                SAMPLE_CREATED_TIME, SAMPLE_AVAILABLE_BOOK_STATUS, VALID_REQUESTERS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, BookName.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, book::toModelType);
    }

    @Test
    public void toModelType_invalidIsbn_throwsIllegalValueException() {
        JsonAdaptedBook book =
                new JsonAdaptedBook(VALID_BOOK_NAME, INVALID_ISBN, VALID_TAGS, VALID_AUTHORS, SAMPLE_CREATED_TIME,
                        SAMPLE_AVAILABLE_BOOK_STATUS, VALID_REQUESTERS);
        String expectedMessage = Isbn.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, book::toModelType);
    }

    @Test
    public void toModelType_nullIsbn_throwsIllegalValueException() {
        JsonAdaptedBook book = new JsonAdaptedBook(VALID_BOOK_NAME, null, VALID_TAGS, VALID_AUTHORS,
                SAMPLE_CREATED_TIME, SAMPLE_AVAILABLE_BOOK_STATUS, VALID_REQUESTERS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Isbn.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, book::toModelType);
    }

    @Test
    public void toModelType_invalidRequesters_throwsIllegalValueException() {
        List<JsonAdaptedPatron> invalidRequesters = new ArrayList<>(VALID_REQUESTERS);
        invalidRequesters.add(new JsonAdaptedPatron(INVALID_REQUESTER_NAME, VALID_REQUESTER_PHONE,
                VALID_REQUESTER_EMAIL, VALID_REQUESTER_ID, VALID_REQUESTER_TAGS));
        JsonAdaptedBook book =
                new JsonAdaptedBook(VALID_BOOK_NAME, VALID_ISBN, VALID_TAGS, VALID_AUTHORS, SAMPLE_CREATED_TIME,
                        SAMPLE_AVAILABLE_BOOK_STATUS, invalidRequesters);
        assertThrows(IllegalValueException.class, book::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedBook book =
                new JsonAdaptedBook(VALID_BOOK_NAME, VALID_ISBN, invalidTags, VALID_AUTHORS, SAMPLE_CREATED_TIME,
                        SAMPLE_AVAILABLE_BOOK_STATUS, VALID_REQUESTERS);
        assertThrows(IllegalValueException.class, book::toModelType);
    }

    @Test
    public void toModelType_invalidAuthors_throwsIllegalValueException() {
        List<JsonAdaptedAuthor> invalidAuthors = new ArrayList<>(VALID_AUTHORS);
        invalidAuthors.add(new JsonAdaptedAuthor(INVALID_AUTHOR));
        JsonAdaptedBook book =
                new JsonAdaptedBook(VALID_BOOK_NAME, VALID_ISBN, VALID_TAGS, invalidAuthors, SAMPLE_CREATED_TIME,
                        SAMPLE_AVAILABLE_BOOK_STATUS, VALID_REQUESTERS);
        assertThrows(IllegalValueException.class, book::toModelType);
    }

    @Test
    public void toModelType_invalidTimeAdded_throwsIllegalValueException() {
        JsonAdaptedBook book =
                new JsonAdaptedBook(VALID_BOOK_NAME, VALID_ISBN, VALID_TAGS, VALID_AUTHORS, INVALID_TIME_ADDED,
                        SAMPLE_AVAILABLE_BOOK_STATUS, VALID_REQUESTERS);
        String expectedMessage = Book.TIME_ADDED_MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, book::toModelType);
    }

    @Test
    public void toModelType_nullBookStatus_throwsIllegalValueException() {
        JsonAdaptedBook book = new JsonAdaptedBook(VALID_BOOK_NAME, VALID_ISBN, VALID_TAGS, VALID_AUTHORS,
                SAMPLE_CREATED_TIME, null, VALID_REQUESTERS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, BookStatus.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, book::toModelType);
    }
}
