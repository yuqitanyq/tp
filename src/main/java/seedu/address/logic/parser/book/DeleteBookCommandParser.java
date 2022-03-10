package seedu.address.logic.parser.book;

import java.util.stream.Stream;

import seedu.address.logic.commands.book.DeleteBookCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteBookCommand object
 */
public class DeleteBookCommandParser implements Parser<DeleteBookCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteBookCommand
     * and returns an DeleteBookCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteBookCommand parse(String args) throws ParseException {

        // TODO : Implement parse function

        return null;
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
