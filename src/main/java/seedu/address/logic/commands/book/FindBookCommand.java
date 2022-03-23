package seedu.address.logic.commands.book;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AUTHOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.function.Predicate;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.book.Book;



public class FindBookCommand extends Command {
    public static final String MESSAGE_USAGE = BOOK_COMMAND_GROUP + " " + FIND_COMMAND_WORD
            + ": Find a book based on the author, tag or book title "
            + "Parameters:"
            + "[" + PREFIX_NAME + "NAME] or "
            + "[" + PREFIX_AUTHOR + "AUTHOR] or "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example " + BOOK_COMMAND_GROUP + FIND_COMMAND_WORD
            + PREFIX_NAME + "Harry Potter";

    public static final String MESSAGE_FIND_BOOK_SUCCESS = "Found Books";
    public static final String MESSAGE_NOT_FOUND = "Only one category may be found";

    private final Predicate<Book> predicate;

    public FindBookCommand(Predicate<Book> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        requireNonNull(predicate);

        model.updateFilteredBookList(predicate);
        return new CommandResult(String.format(Messages.MESSAGE_BOOKS_LISTED_OVERVIEW,
                model.getFilteredBookList().size()));
    }
}
