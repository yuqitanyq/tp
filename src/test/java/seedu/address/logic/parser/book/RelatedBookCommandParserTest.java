package seedu.address.logic.parser.book;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PATRON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.book.RelatedBookCommand;

public class RelatedBookCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            RelatedBookCommand.MESSAGE_USAGE);

    private RelatedBookCommandParser parser = new RelatedBookCommandParser();

    @Test
    public void parse_missingParts_failure() {
        //no index specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidIndex_failure() {
        //negative index
        assertParseFailure(parser, "-200", MESSAGE_INVALID_FORMAT);

        //invalid index input
        assertParseFailure(parser, "a", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validIndex_success() {
        Index targetIndex = INDEX_FIRST_PATRON;
        RelatedBookCommand expectedCommand = new RelatedBookCommand(targetIndex);

        //valid index
        assertParseSuccess(parser, "1", expectedCommand);
    }
}
