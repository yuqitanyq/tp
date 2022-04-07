package seedu.address.logic.commands.book;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.book.BookRelatedToPatronPredicate;
import seedu.address.model.patron.Patron;

/**
 * Lists all books related to an existing patron in libTask.
 */
public class RelatedBookCommand extends Command {

    public static final String MESSAGE_USAGE = BOOK_COMMAND_GROUP + " " + RELATED_COMMAND_WORD
            + ": Lists all the books borrowed or requested by the patron at a specified index "
            + "by the index number used in the displayed patron list. \n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "PATRON_INDEX must be a positive integer and "
            + "does not exceed the largest index number in the displayed patron list\n"
            + "Example: " + BOOK_COMMAND_GROUP + " " + RELATED_COMMAND_WORD + " 1 ";

    public static final String MESSAGE_RELATED_BOOK_SUCCESS = "Listed all books related to patron %1$s";

    private final Index patronIndex;

    /**
     * @param index of patron in the filtered patron list to list all its related books
     */
    public RelatedBookCommand(Index index) {
        requireNonNull(index);
        this.patronIndex = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Patron> lastShownPatronList = model.getFilteredPatronList();

        if (patronIndex.getZeroBased() >= lastShownPatronList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PATRON_DISPLAYED_INDEX);
        }

        Patron patronToRelate = lastShownPatronList.get(patronIndex.getZeroBased());
        BookRelatedToPatronPredicate predicate = new BookRelatedToPatronPredicate(patronToRelate);
        model.updateFilteredBookList(predicate);
        return new CommandResult(String.format(MESSAGE_RELATED_BOOK_SUCCESS, patronToRelate));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof RelatedBookCommand) // instanceof handles null
            && patronIndex.equals(((RelatedBookCommand) other).patronIndex);
    }
}
