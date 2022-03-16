package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {

    public static final String PATRON_COMMAND_GROUP = "patron";
    public static final String BOOK_COMMAND_GROUP = "book";

    public static final String ADD_COMMAND_WORD = "add";
    public static final String HELP_COMMAND_WORD = "help";
    public static final String EXIT_COMMAND_WORD = "exit";
    public static final String CLEAR_COMMAND_WORD = "clear";
    public static final String EDIT_COMMAND_WORD = "edit";
    public static final String DELETE_COMMAND_WORD = "delete";
    public static final String FIND_COMMAND_WORD = "find";
    public static final String LIST_COMMAND_WORD = "list";
    public static final String PREVIOUS_COMMAND_WORD = "u";
    public static final String RETURN_COMMAND_WORD = "return";

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute(Model model) throws CommandException;

}
