package seedu.address.logic.parser.book;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BOOK;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.book.DeleteBookCommand;

public class DeleteBookCommandParserTest {
    private DeleteBookCommandParser parser = new DeleteBookCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new DeleteBookCommand(INDEX_FIRST_BOOK));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteBookCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "-999", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteBookCommand.MESSAGE_USAGE));
    }
}
