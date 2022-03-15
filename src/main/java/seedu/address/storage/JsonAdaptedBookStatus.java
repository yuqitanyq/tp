package seedu.address.storage;

import static seedu.address.model.book.BookStatus.VALIDATION_REGEX;
import static seedu.address.model.book.BookStatus.isValidStatus;
import static seedu.address.model.book.BookStatusType.AVAILABLE;
import static seedu.address.model.book.BookStatusType.BORROWED;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.book.BookStatus;
import seedu.address.model.book.BookStatusType;
import seedu.address.model.patron.Patron;

/**
 * Jackson-friendly version of {@link BookStatus}.
 */
public class JsonAdaptedBookStatus {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "BookStatus's %s field is missing!";
    private final String bookStatusType;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final JsonAdaptedPatron borrower;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String borrowDate;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String returnDate;

    /**
     * Constructs a {@code JsonAdaptedBookStatus} with the given book status details.
     */
    @JsonCreator
    public JsonAdaptedBookStatus(@JsonProperty("bookStatusType") String bookStatusType,
                                 @JsonProperty("borrower") JsonAdaptedPatron borrower,
                                 @JsonProperty("borrowDate") String borrowDate,
                                 @JsonProperty("returnDate") String returnDate) {
        this.bookStatusType = bookStatusType;
        this.borrower = borrower;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }

    /**
     * Converts a given {@code Book} into this class for Jackson use.
     */
    public JsonAdaptedBookStatus(BookStatus source) {
        bookStatusType = source.getBookStatusTypeString();
        borrower = source.getBorrower().map(JsonAdaptedPatron::new).orElse(null);
        borrowDate = source.isBorrowed() ? source.getBorrowDateString() : null;
        returnDate = source.isBorrowed() ? source.getReturnDateString() : null;
    }

    /**
     * Converts this Jackson-friendly adapted book status object into the model's {@code BookStatus} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted book status.
     */
    public BookStatus toModelType() throws IllegalValueException {
        final BookStatusType modelStatusType;
        if (bookStatusType == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    BookStatusType.class.getSimpleName()));
        }
        if (bookStatusType.equalsIgnoreCase(AVAILABLE.toString())) {
            modelStatusType = AVAILABLE;
        } else if (bookStatusType.equalsIgnoreCase(BORROWED.toString())) {
            modelStatusType = BORROWED;
        } else {
            throw new IllegalValueException(BookStatusType.MESSAGE_CONSTRAINTS);
        }

        final Optional<Patron> modelBorrower;
        if (borrower == null) {
            modelBorrower = Optional.empty();
        } else {
            modelBorrower = Optional.of(borrower.toModelType());
        }

        final Optional<String> modelBorrowDate;
        final Optional<String> modelReturnDate;
        if (borrowDate == null) {
            modelBorrowDate = Optional.empty();
        } else if (!borrowDate.matches(VALIDATION_REGEX)) {
            throw new IllegalValueException(BookStatus.MESSAGE_CONSTRAINTS);
        } else {
            modelBorrowDate = Optional.of(borrowDate);
        }

        if (returnDate == null) {
            modelReturnDate = Optional.empty();
        } else if (!returnDate.matches(VALIDATION_REGEX)) {
            throw new IllegalValueException(BookStatus.MESSAGE_CONSTRAINTS);
        } else {
            modelReturnDate = Optional.of(returnDate);
        }

        if (!isValidStatus(modelStatusType, modelBorrower, modelBorrowDate, modelReturnDate)) {
            throw new IllegalValueException(BookStatus.MESSAGE_CONSTRAINTS);
        }

        return new BookStatus(modelStatusType, modelBorrower, modelBorrowDate, modelReturnDate);
    }
}
