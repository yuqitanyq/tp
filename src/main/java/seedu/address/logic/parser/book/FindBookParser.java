package seedu.address.logic.parser.book;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AUTHOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.text.ParseException;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.book.FindBookCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.book.Book;
import seedu.address.model.book.BookNameContainsKeywordsPredicate;
import seedu.address.model.book.BookTagContainsKeywordsPredicate;
import seedu.address.model.patron.NameContainsKeywordsPredicate;


public class FindBookParser {

    public Command parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_AUTHOR, PREFIX_TAG);
        if (!argMultimap.hasExactlyOneQueriedPrefix(PREFIX_NAME, PREFIX_AUTHOR, PREFIX_TAG)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindBookCommand.MESSAGE_USAGE));
        }
        List<Prefix> prefixes = argMultimap.getNonEmptyPrefixes();
        assert(prefixes.size() == 1);

        Prefix queriedPrefix = prefixes.get(0);
        String argument =


    }

    private static Predicate<Book> parsePredicate(Prefix prefix, List<String> args) throws ParseException {
        switch (prefix.toString()) {
        case PREFIX_AUTHOR:
            return new NameContainsKeywordsPredicate(args);
        case PREFIX_NAME:
            return new BookNameContainsKeywordsPredicate(args);
        case PREFIX_TAG:
            return new BookTagContainsKeywordsPredicate(args);
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindBookCommand.MESSAGE_USAGE));
        }

    }
}
