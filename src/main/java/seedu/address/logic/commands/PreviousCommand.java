package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.model.Model;

public class PreviousCommand extends Command {

    public static final String MESSAGE_SUCCESS = "Here is your previous command";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult(AddressBookParser.getPreviousCommands(), false, false);
    }
}
