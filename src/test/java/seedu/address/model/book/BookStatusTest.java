package seedu.address.model.book;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.book.BookStatus.createAvailableBookStatus;
import static seedu.address.model.book.BookStatus.isValidStatus;
import static seedu.address.model.book.BookStatusType.AVAILABLE;
import static seedu.address.model.book.BookStatusType.BORROWED;
import static seedu.address.model.util.SampleDataUtil.getSampleBorrowedStatus;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPatrons.ALICE;
import static seedu.address.testutil.TypicalPatrons.getTypicalPatrons;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.model.patron.Patron;

public class BookStatusTest {

    private static final Optional<Patron> VALID_BORROWER = Optional.of(getTypicalPatrons().get(0));
    private static final Optional<String> VALID_BORROW_DATE = Optional.of("14-Feb-2022");
    private static final Optional<String> VALID_RETURN_DATE = Optional.of("28-Feb-2022");

    @Test
    public void constructor_someFieldsNull_throwsNullPointerException() {
        // null book status type
        assertThrows(NullPointerException.class, () ->
                new BookStatus(null, VALID_BORROWER, VALID_BORROW_DATE, VALID_RETURN_DATE));

        // null borrower
        assertThrows(NullPointerException.class, () ->
                new BookStatus(AVAILABLE, null, VALID_BORROW_DATE, VALID_RETURN_DATE));

        // null borrow date
        assertThrows(NullPointerException.class, () ->
                new BookStatus(AVAILABLE, VALID_BORROWER, null, VALID_RETURN_DATE));

        // null return date
        assertThrows(NullPointerException.class, () ->
                new BookStatus(AVAILABLE, VALID_BORROWER, VALID_BORROW_DATE, null));
    }

    @Test
    public void editBorrower_nullEditedBorrower_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> getSampleBorrowedStatus().editBorrower(null));
    }

    @Test
    public void editBorrower_statusNotBorrowed_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> createAvailableBookStatus().editBorrower(ALICE));
    }

    @Test
    public void isValidStatus_invalidFields_returnsFalse() {

        // Available status with some fields present
        assertFalse(isValidStatus(AVAILABLE, Optional.empty(), Optional.empty(), VALID_RETURN_DATE));
        assertFalse(isValidStatus(AVAILABLE, Optional.empty(), VALID_BORROW_DATE, Optional.empty()));
        assertFalse(isValidStatus(AVAILABLE, VALID_BORROWER, Optional.empty(), Optional.empty()));

        // Borrowed status with some fields empty
        assertFalse(isValidStatus(BORROWED, Optional.empty(), VALID_BORROW_DATE, VALID_RETURN_DATE));
        assertFalse(isValidStatus(BORROWED, VALID_BORROWER, Optional.empty(), VALID_RETURN_DATE));
        assertFalse(isValidStatus(BORROWED, VALID_BORROWER, VALID_BORROW_DATE, Optional.empty()));

        // Borrowed status with invalid date format
        assertFalse(isValidStatus(BORROWED, VALID_BORROWER, VALID_BORROW_DATE, Optional.of("24/Feb/2021")));
        assertFalse(isValidStatus(BORROWED, VALID_BORROWER, VALID_BORROW_DATE, Optional.of("24--Feb-2021")));
        assertFalse(isValidStatus(BORROWED, VALID_BORROWER, VALID_BORROW_DATE, Optional.of("24--MON-2021")));
        assertFalse(isValidStatus(BORROWED, VALID_BORROWER, VALID_BORROW_DATE, Optional.of("24--SUN-2021")));
        assertFalse(isValidStatus(BORROWED, VALID_BORROWER, Optional.of("24/Feb/2021"), VALID_RETURN_DATE));
        assertFalse(isValidStatus(BORROWED, VALID_BORROWER, Optional.of("24--Feb-2021"), VALID_RETURN_DATE));
        assertFalse(isValidStatus(BORROWED, VALID_BORROWER, Optional.of("24--MON-2021"), VALID_RETURN_DATE));
        assertFalse(isValidStatus(BORROWED, VALID_BORROWER, Optional.of("24--SUN-2021"), VALID_RETURN_DATE));
    }

    @Test
    public void isValidStatus_validFields_returnsTrue() {
        // valid available status
        assertTrue(isValidStatus(AVAILABLE, Optional.empty(), Optional.empty(),
                Optional.empty()));

        // valid borrowed status
        assertTrue(isValidStatus(BORROWED, VALID_BORROWER, VALID_BORROW_DATE, VALID_RETURN_DATE));
    }

}
