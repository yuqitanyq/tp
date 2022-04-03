package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.model.book.BookStatus.isValidDateString;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.BorrowCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new BorrowCommand object
 */
public class BorrowCommandParser implements Parser<BorrowCommand> {

    private static final String VALIDATION_REGEX = "^\\d+\\s+\\d+\\s+\\d{2}-[a-zA-Z]{3}-\\d{4}$";

    /**
     * Parses the given {@code String} of arguments in the context of the BorrowCommand
     * and returns an BorrowCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public BorrowCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (!trimmedArgs.matches(VALIDATION_REGEX)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, BorrowCommand.MESSAGE_USAGE));
        }
        String[] borrowInfo = trimmedArgs.split("\\s+");
        String returnDate = borrowInfo[2];

        // Ensures date can be parsed because regex does not check for all invalid cases.
        // Check is done here instead of BookStatus class so that invalid date error is not
        // thrown from BookStatus, therefore hiding BookStatus from end user.
        if (!isValidDateString(returnDate)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, BorrowCommand.MESSAGE_USAGE));
        }

        try {
            Index patronIndex = ParserUtil.parseIndex(borrowInfo[0]);
            Index bookIndex = ParserUtil.parseIndex(borrowInfo[1]);
            return new BorrowCommand(patronIndex, bookIndex, returnDate);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, BorrowCommand.MESSAGE_USAGE), pe);
        }
    }
}
