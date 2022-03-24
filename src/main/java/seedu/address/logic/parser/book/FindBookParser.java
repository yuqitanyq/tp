package seedu.address.logic.parser.book;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AUTHOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.book.FindBookCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.book.Book;
import seedu.address.model.book.BookAuthorContainsKeywordsPredicate;
import seedu.address.model.book.BookNameContainsKeywordsPredicate;
import seedu.address.model.book.BookTagContainsKeywordsPredicate;


public class FindBookParser implements Parser<Command> {
    /**
     * Parses user input into command for execution.
     *
     * @param args user input string that is already processed by {@code LibTaskParser}
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_AUTHOR, PREFIX_TAG);
        if (!argMultimap.hasExactlyOneQueriedPrefix(PREFIX_NAME, PREFIX_AUTHOR, PREFIX_TAG)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindBookCommand.MESSAGE_USAGE));
        }

        List<Prefix> prefixes = argMultimap.getNonEmptyPrefixes(PREFIX_NAME, PREFIX_AUTHOR, PREFIX_TAG);

        Prefix queriedPrefix = prefixes.get(0);
        if (!argMultimap.hasExactlyOneValue(queriedPrefix)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindBookCommand.MESSAGE_USAGE));
        }
        String argument = (argMultimap.getValue(queriedPrefix)).get();
        return new FindBookCommand(parsePredicate(queriedPrefix, List.of(argument)));
    }

    private static Predicate<Book> parsePredicate(Prefix prefix, List<String> args) throws ParseException {
        if (PREFIX_AUTHOR.equals(prefix)) {
            return new BookAuthorContainsKeywordsPredicate(args);
        } else if (PREFIX_NAME.equals(prefix)) {
            return new BookNameContainsKeywordsPredicate(args);
        } else if (PREFIX_TAG.equals(prefix)) {
            return new BookTagContainsKeywordsPredicate(args);
        }
        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindBookCommand.MESSAGE_USAGE));
    }
}
