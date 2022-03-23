package seedu.address.logic.parser.book;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.book.RequestBookCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RequestBookCommand object
 */
public class RequestBookCommandParser implements Parser<RequestBookCommand> {

    private static final String VALIDATION_REGEX = "^\\d+\\s+\\d+$";

    /**
     * Parses the given {@code String} of arguments in the context of the RequestBookCommand
     * and returns an RequestBookCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RequestBookCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (!trimmedArgs.matches(VALIDATION_REGEX)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RequestBookCommand.MESSAGE_USAGE));
        }
        String[] requestInfo = trimmedArgs.split("\\s+");
        try {
            Index patronIndex = ParserUtil.parseIndex(requestInfo[0]);
            Index bookIndex = ParserUtil.parseIndex(requestInfo[1]);
            return new RequestBookCommand(patronIndex, bookIndex);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RequestBookCommand.MESSAGE_USAGE), pe);
        }
    }
}
