package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AUTHOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BOOK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATRON;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BOOK;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PATRON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ReturnAllBooksCommand;
import seedu.address.logic.commands.ReturnCommand;
import seedu.address.logic.commands.ReturnOneBookCommand;

public class ReturnCommandParserTest {
    private ReturnCommandParser parser = new ReturnCommandParser();

    @Test
    public void parse_onePrefixOneValue_success() {
        // only has p/
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + " " + PREFIX_PATRON + "1",
                new ReturnAllBooksCommand(INDEX_FIRST_PATRON));
        // only has b/
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + " " + PREFIX_BOOK + "1",
                new ReturnOneBookCommand(INDEX_FIRST_BOOK));
    }

    @Test
    public void parse_noPatronOrBookPrefix_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReturnCommand.MESSAGE_USAGE);
        assertParseFailure(parser, PREAMBLE_WHITESPACE + " " + PREFIX_AUTHOR + "1", expectedMessage);
    }

    @Test
    public void parse_hasBothPatronOrBookPrefix_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReturnCommand.MESSAGE_USAGE);
        assertParseFailure(parser, PREAMBLE_WHITESPACE + " " + PREFIX_PATRON + "1" + " "
                + PREFIX_BOOK + "1", expectedMessage);
    }

    @Test
    public void parse_hasExtraPrefix_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReturnCommand.MESSAGE_USAGE);
        assertParseFailure(parser, PREAMBLE_WHITESPACE + " " + PREFIX_PATRON + "1" + " "
                + PREFIX_AUTHOR + "1", expectedMessage);
    }

    @Test
    public void parse_hasNonEmptyPreamble_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReturnCommand.MESSAGE_USAGE);
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + " " + PREFIX_PATRON + "1" + " ", expectedMessage);
    }

    @Test
    public void parse_onePrefixMultipleValues_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReturnCommand.MESSAGE_USAGE);

        // multiple /p
        assertParseFailure(parser, PREAMBLE_WHITESPACE + " " + PREFIX_PATRON + "1" + " "
                + PREFIX_PATRON + "2", expectedMessage);

        // multiple /b
        assertParseFailure(parser, PREAMBLE_WHITESPACE + " " + PREFIX_BOOK + "1" + " "
                + PREFIX_BOOK + "2", expectedMessage);
    }
}
