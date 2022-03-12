package seedu.address.logic.commands.patron;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PATRONS;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * Lists all patrons in the address book to the user.
 */
public class ListPatronCommand extends Command {

    public static final String MESSAGE_SUCCESS = "Listed all patrons";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPatronList(PREDICATE_SHOW_ALL_PATRONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
