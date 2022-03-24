package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_BOOKS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.book.Book;
import seedu.address.model.book.BookStatus;

/**
 * Returns a book identified using its displayed index from LibTask.
 */
public class ReturnOneBookCommand extends ReturnCommand {

    public static final String MESSAGE_SUCCESS = "Returned book: %1$s\n";

    private final Index bookIndex;

    /**
     * Creates an ReturnOneBookCommand to return the specified {@code Book}
     */
    public ReturnOneBookCommand(Index bookIndex) {
        this.bookIndex = bookIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Book> lastShownList = model.getFilteredBookList();

        if (bookIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
        }

        Book bookToReturn = lastShownList.get(bookIndex.getZeroBased());
        if (!bookToReturn.isBorrowed()) {
            throw new CommandException(String.format(Messages.MESSAGE_BOOK_NOT_BORROWED, bookToReturn.getBookName()));
        }
        Book updatedAvailableBook = new Book(bookToReturn, BookStatus.createAvailableBookStatus());
        model.setBook(bookToReturn, updatedAvailableBook);
        String notification = model.deleteAllRequests(bookToReturn);
        model.updateFilteredBookList(PREDICATE_SHOW_ALL_BOOKS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, bookToReturn.getBookName()) + notification);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReturnOneBookCommand // instanceof handles nulls
                && bookIndex.equals(((ReturnOneBookCommand) other).bookIndex)); // state check
    }

}
