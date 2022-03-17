package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BOOK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATRON;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ReturnAllBooksCommand;
import seedu.address.logic.commands.ReturnCommand;
import seedu.address.logic.commands.ReturnOneBookCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ReturnCommand object
 */
public class ReturnCommandParser implements Parser<ReturnCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ReturnCommand
     * and returns an ReturnCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ReturnCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PATRON, PREFIX_BOOK);
        if (!isValidPrefixes(argMultimap) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReturnCommand.MESSAGE_USAGE));
        }
        try {
            Index index = getPatronOrBookIndex(argMultimap);
            return getSpecificReturnCommand(argMultimap, index);
        } catch (ParseException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReturnCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Returns true if and only if the given {@code ArgumentMultimap} has exactly one of patron prefix or book prefix
     * with non-empty value, and the prefix with non-empty value has exactly one value.
     */
    private boolean isValidPrefixes(ArgumentMultimap argMultimap) {
        boolean hasOnlyOneTypeOfPrefix = argMultimap.hasExactlyOneQueriedPrefix(PREFIX_PATRON, PREFIX_BOOK);
        // Assume only one type of prefix is present from here onwards
        boolean hasExactlyOneIndex = argMultimap.hasExactlyOneValue(PREFIX_PATRON)
                || argMultimap.hasExactlyOneValue(PREFIX_BOOK);
        if (!hasOnlyOneTypeOfPrefix || !hasExactlyOneIndex) {
            return false;
        }
        return true;
    }

    private Index getPatronOrBookIndex(ArgumentMultimap argMultimap) throws ParseException {
        if (argMultimap.hasExactlyOneValue(PREFIX_PATRON)) {
            return ParserUtil.parseIndex(argMultimap.getValue(PREFIX_PATRON).get());
        }
        if (argMultimap.hasExactlyOneValue(PREFIX_BOOK)) {
            return ParserUtil.parseIndex(argMultimap.getValue(PREFIX_BOOK).get());
        }
        // The code below should not be reached unless there is no patron or book prefix
        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReturnCommand.MESSAGE_USAGE));
    }

    private ReturnCommand getSpecificReturnCommand(ArgumentMultimap argMultimap, Index index) throws ParseException {
        if (argMultimap.hasExactlyOneValue(PREFIX_PATRON)) {
            return new ReturnAllBooksCommand(index);
        }
        if (argMultimap.hasExactlyOneValue(PREFIX_BOOK)) {
            return new ReturnOneBookCommand(index);
        }
        // The code below should not be reached unless there is no patron or book prefix
        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReturnCommand.MESSAGE_USAGE));
    }
}
