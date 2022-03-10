package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.Command.CLEAR_COMMAND_WORD;
import static seedu.address.logic.commands.Command.EXIT_COMMAND_WORD;
import static seedu.address.logic.commands.Command.HELP_COMMAND_WORD;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.book.BookCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case Command.PATRON_COMMAND_GROUP:
            // TODO : ADITI please do something like "return new PatronCommandParser().parse(arguments);"
            return null;

        case Command.BOOK_COMMAND_GROUP:
            return new BookCommandParser().parse(arguments);

        // TODO : ADITI please move add, edit, del, find, list into your new PatronCommandParser class in new package
        // TODO : CHANGE TO "case ADD_COMMAND_WORD", import static "ADD_COMMAND_WORD" from Command class
        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        // TODO : CHANGE TO "case EDIT_COMMAND_WORD", import static "EDIT_COMMAND_WORD" from Command class
        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        // TODO : CHANGE TO "case DELETE_COMMAND_WORD", import static "DELETE_COMMAND_WORD" from Command class
        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        // TODO : CHANGE TO "case FIND_COMMAND_WORD", import static "FIND_COMMAND_WORD" from Command class
        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        // TODO : CHANGE TO "case LIST_COMMAND_WORD", import static "LIST_COMMAND_WORD" from Command class
        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case CLEAR_COMMAND_WORD:
            return new ClearCommand();

        case EXIT_COMMAND_WORD:
            return new ExitCommand();

        case HELP_COMMAND_WORD:
            return new HelpCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
