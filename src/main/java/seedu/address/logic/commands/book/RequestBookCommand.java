package seedu.address.logic.commands.book;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_BOOKS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.book.Book;
import seedu.address.model.patron.Patron;

/**
 * Requests for a book by a patron, both identified using their displayed index from LibTask.
 */
public class RequestBookCommand extends Command {

    public static final String MESSAGE_USAGE = REQUEST_COMMAND_WORD
            + ": Establishes a request relationship between the patron identified by the index number "
            + " in the displayed patron list and the book identified the index number in the displayed book list.\n"
            + "Parameters: PATRON_INDEX BOOK_INDEX (both must be positive integers)\n"
            + "Example: " + BOOK_COMMAND_GROUP  + " " + REQUEST_COMMAND_WORD + " 1 1";

    public static final String MESSAGE_REQUEST_BOOK_SUCCESS = "Patron %1$s requested for all books with isbn %2$s";
    public static final String MESSAGE_ALREADY_REQUESTED = "Patron %1$s already requested for %2$s";
    public static final String MESSAGE_MAX_REQUEST = "%1$s already has a maximum of 3 requesters!";
    public static final String MESSAGE_HAS_AVAILABLE = "There is at least one copy of %1$s that is available.\n"
            + "There is no need to request to be notified about this book's availability.";
    public static final String MESSAGE_IS_BORROWING = "Patron %1$s already borrowing a copy of the same book!\n"
            + "There is no need to for the patron to request for a book he/she has already borrowed.";

    private static final int MAX_REQUESTS_PER_BOOK = 3;

    private final Index patronIndex;
    private final Index bookIndex;

    /**
     * Creates an RequestBookCommand to establish a request relationship between the
     * specified {@code Patron} and the specified {@code Book}
     *
     * @param patronIndex The index of the requester patron in the visible patron list.
     * @param bookIndex The index of the requester book in the visible book list.
     */
    public RequestBookCommand(Index patronIndex, Index bookIndex) {
        this.patronIndex = patronIndex;
        this.bookIndex = bookIndex;
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
        Patron requester = lastShownPatronList.get(patronIndex.getZeroBased());
        Book bookToRequest = lastShownBookList.get(bookIndex.getZeroBased());
        if (bookToRequest.isRequestedBy(requester)) {
            throw new CommandException(String.format(
                    MESSAGE_ALREADY_REQUESTED, requester.getName(), bookToRequest.getBookName()));
        }
        if (model.isBorrowing(requester, bookToRequest)) {
            throw new CommandException(String.format(MESSAGE_IS_BORROWING, requester.getName()));
        }
        if (model.hasAvailableCopy(bookToRequest)) {
            // Not sure if it will result in better UX if book list is reset to show all books
            throw new CommandException(String.format(MESSAGE_HAS_AVAILABLE, bookToRequest.getBookName()));
        }
        if (bookToRequest.getRequesters().size() >= MAX_REQUESTS_PER_BOOK) {
            throw new CommandException(String.format(MESSAGE_MAX_REQUEST, bookToRequest.getBookName()));
        }
        model.addRequest(bookToRequest, requester);
        model.updateFilteredBookList(PREDICATE_SHOW_ALL_BOOKS);
        return new CommandResult(String.format(MESSAGE_REQUEST_BOOK_SUCCESS, requester.getName(),
                bookToRequest.getBookName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RequestBookCommand // instanceof handles nulls
                && patronIndex.equals(((RequestBookCommand) other).patronIndex)
                && bookIndex.equals(((RequestBookCommand) other).bookIndex)); // state check
    }
}
