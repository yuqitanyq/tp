package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.model.Model;

public class PreviousCommand extends Command {

    public static final String COMMAND_WORD = "u";
    public static final String MESSAGE_SUCCESS = "Here is your previous command";

    /**
     * Returns a String of the last commands that does not include prev
     *
     * @return a String of past commands
     */
    String getPreviousCommands() {
        return AddressBookParser.getpreviousCommands();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult(getPreviousCommands(), false, false);
    }
}
