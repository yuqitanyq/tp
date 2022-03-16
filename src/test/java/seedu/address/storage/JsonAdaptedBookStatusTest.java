package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.model.util.SampleDataUtil.SAMPLE_AVAILABLE_STATUS;
import static seedu.address.model.util.SampleDataUtil.getSampleBorrowedStatus;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPatrons.getTypicalPatrons;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.book.BookStatus;
import seedu.address.model.book.BookStatusType;

public class JsonAdaptedBookStatusTest {
    private static final String INVALID_TYPE = "invalid";
    private static final String INVALID_DATE_1 = "14Feb-2022";
    private static final String INVALID_DATE_2 = "11-MON-2022";

    private static final String VALID_TYPE_AVAILABLE = "Available";
    private static final String VALID_TYPE_BORROWED = "Borrowed";
    private static final JsonAdaptedPatron VALID_BORROWER = new JsonAdaptedPatron(getTypicalPatrons().get(0));
    private static final String VALID_BORROW_DATE = "14-Feb-2022";
    private static final String VALID_RETURN_DATE = "28-Feb-2022";
    private static final BookStatus VALID_AVAILABLE_BOOK_STATUS = SAMPLE_AVAILABLE_STATUS;
    private static final BookStatus VALID_BORROWED_BOOK_STATUS = getSampleBorrowedStatus();


    @Test
    public void toModelType_validBookStatusDetails_returnsBookStatus() throws Exception {
        // available status
        JsonAdaptedBookStatus bookStatus = new JsonAdaptedBookStatus(VALID_AVAILABLE_BOOK_STATUS);
        assertEquals(VALID_AVAILABLE_BOOK_STATUS, bookStatus.toModelType());

        // borrowed status
        bookStatus = new JsonAdaptedBookStatus(VALID_BORROWED_BOOK_STATUS);
        assertEquals(VALID_BORROWED_BOOK_STATUS, bookStatus.toModelType());
    }

    @Test
    public void toModelType_invalidBookStatusType_throwsIllegalValueException() {
        JsonAdaptedBookStatus bookStatus = new JsonAdaptedBookStatus(INVALID_TYPE,
                null, null, null);
        String expectedMessage = BookStatusType.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, bookStatus::toModelType);
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        // invalid borrow date
        JsonAdaptedBookStatus bookStatus =
                new JsonAdaptedBookStatus(VALID_TYPE_BORROWED, VALID_BORROWER, INVALID_DATE_1, VALID_RETURN_DATE);
        String expectedMessage = BookStatus.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, bookStatus::toModelType);

        // invalid return date tha does not match regex
        bookStatus =
                new JsonAdaptedBookStatus(VALID_TYPE_BORROWED, VALID_BORROWER, VALID_RETURN_DATE, INVALID_DATE_1);
        assertThrows(IllegalValueException.class, expectedMessage, bookStatus::toModelType);

        // invalid return date that matches regex but cannot be parsed
        bookStatus =
                new JsonAdaptedBookStatus(VALID_TYPE_BORROWED, VALID_BORROWER, VALID_RETURN_DATE, INVALID_DATE_2);
        assertThrows(IllegalValueException.class, expectedMessage, bookStatus::toModelType);
    }

    @Test
    public void toModelType_nullDetailsAvailableStatus_success() throws Exception {
        // null borrow date
        JsonAdaptedBookStatus bookStatus =
                new JsonAdaptedBookStatus(VALID_TYPE_AVAILABLE, null, null, null);
        assertEquals(VALID_AVAILABLE_BOOK_STATUS, bookStatus.toModelType());
    }

    @Test
    public void toModelType_nonNullDetailsAvailableStatus_throwsIllegalValueException() throws Exception {
        // borrower not null
        JsonAdaptedBookStatus bookStatus =
                new JsonAdaptedBookStatus(VALID_TYPE_AVAILABLE, VALID_BORROWER, null, null);
        String expectedMessage = BookStatus.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, bookStatus::toModelType);

        // borrowDate not null
        bookStatus =
                new JsonAdaptedBookStatus(VALID_TYPE_AVAILABLE, null, VALID_BORROW_DATE, null);
        assertThrows(IllegalValueException.class, expectedMessage, bookStatus::toModelType);

        // returnDate not null
        bookStatus =
                new JsonAdaptedBookStatus(VALID_TYPE_AVAILABLE, null, null, VALID_RETURN_DATE);
        assertThrows(IllegalValueException.class, expectedMessage, bookStatus::toModelType);
    }

    @Test
    public void toModelType_nullDetailsBorrowedStatus_throwsIllegalValueException() throws Exception {
        // borrower is null
        JsonAdaptedBookStatus bookStatus =
                new JsonAdaptedBookStatus(VALID_TYPE_BORROWED, null, VALID_BORROW_DATE, VALID_RETURN_DATE);
        String expectedMessage = BookStatus.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, bookStatus::toModelType);

        // borrowDate is null
        bookStatus =
                new JsonAdaptedBookStatus(VALID_TYPE_BORROWED, VALID_BORROWER, null, VALID_RETURN_DATE);
        assertThrows(IllegalValueException.class, expectedMessage, bookStatus::toModelType);

        // returnDate is null
        bookStatus =
                new JsonAdaptedBookStatus(VALID_TYPE_BORROWED, VALID_BORROWER, VALID_BORROW_DATE, null);
        assertThrows(IllegalValueException.class, expectedMessage, bookStatus::toModelType);
    }
}
