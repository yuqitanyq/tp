package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

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
            + "RETURN_DATE (in dd-MMM-yyyy format)\n"
            + "Example: " + BORROW_COMMAND_WORD + " 1 1 28-Feb-2022";

    public static final String MESSAGE_BORROW_BOOK_SUCCESS = "Patron %1$s borrowed book %2$s until %3$s";

    private final Index patronIndex;
    private final Index bookIndex;
    private final String returnDate;

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
        if (bookToBorrow.isBorrowed()) {
            throw new CommandException(String.format(
                    Messages.MESSAGE_BOOK_ALREADY_BORROWED, bookToBorrow.getBookName()));
        }
        model.borrowBook(borrower, bookToBorrow, returnDate);

        return new CommandResult(String.format(MESSAGE_BORROW_BOOK_SUCCESS, borrower.getName(),
                bookToBorrow.getBookName(), returnDate));
    }
}
