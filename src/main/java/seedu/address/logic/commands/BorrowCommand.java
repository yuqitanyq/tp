package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_BOOKS;
import static seedu.address.model.book.BookStatus.STATUS_DATE_FORMAT;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.book.Book;
import seedu.address.model.patron.Patron;

/**
 * Parses input arguments and creates a new BorrowCommand object
 */
public class BorrowCommand extends Command {

    public static final String MESSAGE_USAGE = BORROW_COMMAND_WORD
            + ": Establishes a borrow relationship between the patron identified by the index number "
            + " in the displayed patron list and the book identified the index number in the displayed book list.\n"
            + "Parameters: PATRON_INDEX BOOK_INDEX (both must be positive integers) "
            + "RETURN_DATE (in dd-MMM-yyyy format and after today's date)\n"
            + "Example: " + BORROW_COMMAND_WORD + " 1 1 28-Feb-2023";

    public static final String MESSAGE_BORROW_BOOK_SUCCESS = "Patron %1$s borrowed book %2$s until %3$s";

    private final Index patronIndex;
    private final Index bookIndex;
    private final String returnDate;

    /**
     * Creates an BorrowCommand to establish a borrow relationship between the
     * specified {@code Patron} and the specified {@code Book}
     *
     * @param patronIndex The index of the borrower patron in the visible patron list.
     * @param bookIndex The index of the borrower book in the visible book list.
     * @param returnDate The return date of this book loan. It must be after the current date.
     */
    public BorrowCommand(Index patronIndex, Index bookIndex, String returnDate) {
        this.patronIndex = patronIndex;
        this.bookIndex = bookIndex;
        this.returnDate = returnDate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Patron> lastShownPatronList = model.getFilteredPatronList();
        if (patronIndex.getZeroBased() >= lastShownPatronList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PATRON_DISPLAYED_INDEX);
        }

        List<Book> lastShownBookList = model.getFilteredBookList();
        if (bookIndex.getZeroBased() >= lastShownBookList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
        }

        Patron borrower = lastShownPatronList.get(patronIndex.getZeroBased());
        Book bookToBorrow = lastShownBookList.get(bookIndex.getZeroBased());
        if (!isAfterCurrentDate(returnDate)) {
            throw new CommandException(Messages.MESSAGE_RETURN_DATE_TOO_EARLY);
        }
        if (bookToBorrow.isBorrowed()) {
            throw new CommandException(String.format(
                    Messages.MESSAGE_BOOK_ALREADY_BORROWED, bookToBorrow.getBookName()));
        }
        model.borrowBook(borrower, bookToBorrow, returnDate);
        model.updateFilteredBookList(PREDICATE_SHOW_ALL_BOOKS);

        return new CommandResult(String.format(MESSAGE_BORROW_BOOK_SUCCESS, borrower.getName(),
                bookToBorrow.getBookName(), returnDate));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BorrowCommand // instanceof handles nulls
                && patronIndex.equals(((BorrowCommand) other).patronIndex)
                && bookIndex.equals(((BorrowCommand) other).bookIndex)
                && returnDate.equals(((BorrowCommand) other).returnDate)); // state check
    }

    private boolean isAfterCurrentDate(String returnDate) {
        Date currentDate = new Date();
        try {
            Date commandReturnDate = STATUS_DATE_FORMAT.parse(returnDate);
            return commandReturnDate.after(currentDate);
        } catch (ParseException e) {
            return false;
        }
    }
}