package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class PreviousCommand extends Command {

    public static final String MESSAGE_SUCCESS = "Here is your previous command.";
    public static final String NO_PREVIOUS_COMMAND = "No previous commands";

    private final String previousCommand;

    public PreviousCommand(String previousCommand) {
        this.previousCommand = previousCommand;
    }

    public String getMessageForUi() {
        return String.format("%s|%s", previousCommand, MESSAGE_SUCCESS);
    }

    public String getMessageForUiNoPreviousCommand() {
        return String.format("%s", NO_PREVIOUS_COMMAND);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (this.previousCommand.equals("")) {
            return new CommandResult(getMessageForUiNoPreviousCommand(), false, false, true);
        } else {
            return new CommandResult(getMessageForUi(), false, false, true);
        }
    }
}

