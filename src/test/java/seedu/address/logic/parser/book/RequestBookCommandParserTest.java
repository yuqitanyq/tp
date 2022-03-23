package seedu.address.logic.parser.book;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BOOK;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PATRON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.book.RequestBookCommand;

public class RequestBookCommandParserTest {

    private RequestBookCommandParser parser = new RequestBookCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, " 1 1", new RequestBookCommand(INDEX_FIRST_PATRON, INDEX_FIRST_BOOK));
        assertParseSuccess(parser, "1 1", new RequestBookCommand(INDEX_FIRST_PATRON, INDEX_FIRST_BOOK));
        assertParseSuccess(parser, "1   1 ", new RequestBookCommand(INDEX_FIRST_PATRON, INDEX_FIRST_BOOK));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RequestBookCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "-999", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RequestBookCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "11", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RequestBookCommand.MESSAGE_USAGE));
    }
}
