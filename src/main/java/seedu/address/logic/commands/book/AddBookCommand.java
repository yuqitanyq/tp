package seedu.address.logic.commands.book;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AUTHOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ISBN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.book.Book;

/**
 * Adds a book to LibTask.
 */
public class AddBookCommand extends Command {

    public static final String MESSAGE_USAGE = BOOK_COMMAND_GROUP + " " + ADD_COMMAND_WORD
            + ": Adds a book to LibTask. "
            + "Parameters: "
            + PREFIX_NAME + "BOOK_NAME "
            + PREFIX_ISBN + "ISBN "
            + "[" + PREFIX_TAG + "TAG]..."
            + "[" + PREFIX_AUTHOR + "AUTHOR]...\n"
            + "Example: " + BOOK_COMMAND_GROUP + " " + ADD_COMMAND_WORD + " "
            + PREFIX_NAME + "Harry Potter "
            + PREFIX_ISBN + "978-71617-018-8-5 "
            + PREFIX_AUTHOR + "J. K. Rowling "
            + PREFIX_TAG + "Adventure "
            + PREFIX_TAG + "Magic";

    public static final String MESSAGE_SUCCESS = "New book added: %1$s.\n";

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
        if (model.hasSameIsbnDiffAuthorsOrName(toAdd)) {
            throw new CommandException(String.format(Messages.MESSAGE_SAME_ISBN_INCONSISTENT, toAdd.getIsbn()));
        }
        // Remove all requests for this isbn and notify all requesters who requested for a book with toAdd's isbn
        String notification = model.deleteAllRequests(toAdd);
        model.addBook(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd) + notification);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof AddBookCommand)) {
            return false;
        }
        Book otherToAdd = ((AddBookCommand) other).toAdd;
        // Compare without timeAdded attribute of Book
        return otherToAdd.getBookName().equals(toAdd.getBookName())
                && otherToAdd.getIsbn().equals(toAdd.getIsbn())
                && otherToAdd.getTags().equals(toAdd.getTags())
                && otherToAdd.getAuthors().equals(toAdd.getAuthors());
    }
}
