package seedu.address.logic.parser.patron;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.patron.ListPatronCommand;
import seedu.address.logic.commands.patron.OverduePatronCommand;
import seedu.address.logic.parser.LibTaskParser;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input that is already processed by LibTaskParser into a Command object
 *
 * @see LibTaskParser
 */
public class PatronCommandParser implements Parser<Command> {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput user input string that is alaready processed by {@code LibTaskParser}
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parse(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        switch (commandWord) {

        case Command.ADD_COMMAND_WORD:
            return new AddPatronCommandParser().parse(arguments);

        case Command.EDIT_COMMAND_WORD:
            return new EditPatronCommandParser().parse(arguments);

        case Command.DELETE_COMMAND_WORD:
            return new DeletePatronCommandParser().parse(arguments);

        case Command.FIND_COMMAND_WORD:
            return new FindPatronCommandParser().parse(arguments);

        case Command.OVERDUE_COMMAND_WORD:
            return new OverduePatronCommand();

        case Command.LIST_COMMAND_WORD:
            return new ListPatronCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
