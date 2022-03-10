package seedu.address.logic.commands.patron;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Patron;

/**
 * Deletes a patron identified using it's displayed index from the address book.
 */
public class DeletePatronCommand extends Command {

    public static final String MESSAGE_USAGE = DELETE_COMMAND_WORD
            + ": Deletes the patron identified by the index number used in the displayed patron list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + DELETE_COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Patron: %1$s";

    private final Index targetIndex;

    public DeletePatronCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Patron> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Patron personToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deletePerson(personToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.logic.commands.patron.DeletePatronCommand // instanceof handles nulls
                && targetIndex.equals(((seedu.address.logic.commands.patron.DeletePatronCommand) other).targetIndex));
    }
}
