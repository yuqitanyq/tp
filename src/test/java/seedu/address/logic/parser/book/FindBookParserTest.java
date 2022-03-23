package seedu.address.logic.parser.book;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.AUTHOR_DESC_JK_ROWLING;
import static seedu.address.logic.commands.CommandTestUtil.AUTHOR_DESC_SUZANNE_COLLINS;
import static seedu.address.logic.commands.CommandTestUtil.BOOK_NAME_DESC_HARRY_POTTER;
import static seedu.address.logic.commands.CommandTestUtil.BOOK_NAME_DESC_HUNGER_GAMES;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_AUTHOR_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_BOOK_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ISBN_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.ISBN_DESC_HARRY_POTTER;
import static seedu.address.logic.commands.CommandTestUtil.ISBN_DESC_HUNGER_GAMES;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_ADVENTURE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_SCIFI;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_THRILLER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AUTHOR_JK_ROWLING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AUTHOR_SUZANNE_COLLINS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BOOK_NAME_HARRY_POTTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BOOK_NAME_HUNGER_GAMES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ISBN_HARRY_POTTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ISBN_HUNGER_GAMES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_SCIFI;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_THRILLER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BOOK;
import static seedu.address.testutil.TypicalIndexes.INDEX_FOURTH_BOOK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_BOOK;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.book.EditBookCommand;
import seedu.address.model.book.Author;
import seedu.address.model.book.BookName;
import seedu.address.model.book.Isbn;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EditBookDescriptorBuilder;

class FindBookParserTest {
    private FindBookParser findBookParser = new FindBookParser();

    @Test
    void parse() {
    }
}