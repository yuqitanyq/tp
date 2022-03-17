package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_BOOKS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.patron.Patron;

/**
 * Returns all books borrowed by a patron identified using its displayed index from LibTask.
 */
public class ReturnAllBooksCommand extends ReturnCommand {

    public static final String MESSAGE_SUCCESS = "Returned all books borrowed by: %1$s";

    private final Index patronIndex;

    /**
     * Creates an ReturnAllBookCommand to return all books borrowed by the specified {@code Patron}
     */
    public ReturnAllBooksCommand(Index patronIndex) {
        this.patronIndex = patronIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Patron> lastShownList = model.getFilteredPatronList();

        if (patronIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PATRON_DISPLAYED_INDEX);
        }
        Patron borrower = lastShownList.get(patronIndex.getZeroBased());
        if (!model.isBorrowingSomeBook(borrower)) {
            throw new CommandException(String.format(Messages.MESSAGE_NO_BOOKS_BORROWED, borrower.getName()));
        }
        model.returnAllBorrowedBooks(borrower);

        model.updateFilteredBookList(PREDICATE_SHOW_ALL_BOOKS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, borrower.getName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReturnAllBooksCommand // instanceof handles nulls
                && patronIndex.equals(((ReturnAllBooksCommand) other).patronIndex)); // state check
    }
}
