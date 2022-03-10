package seedu.address.logic.commands.book;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.book.Book;

/**
 * Adds a book to the address book.
 */
public class AddBookCommand extends Command {

    // TODO : Improve messages
    public static final String MESSAGE_USAGE = PATRON_COMMAND_GROUP + " " + ADD_COMMAND_WORD
            + ": Adds a book to LibTask. ";

    public static final String MESSAGE_SUCCESS = "New book added: %1$s";

    private final Book toAdd;

    /**
     * Creates an AddBookCommand to add the specified {@code Book}
     */
    public AddBookCommand(Book book) {
        requireNonNull(book);
        toAdd = book;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.addBook(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddBookCommand // instanceof handles nulls
                && toAdd.equals(((AddBookCommand) other).toAdd));
    }
}
