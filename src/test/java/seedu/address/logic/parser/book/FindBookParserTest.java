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
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_ADVENTURE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_MAGIC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AUTHOR_JK_ROWLING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AUTHOR_SUZANNE_COLLINS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BOOK_NAME_HARRY_POTTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ISBN_HARRY_POTTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_ADVENTURE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_MAGIC;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalBooks.HARRY_POTTER;

import org.junit.jupiter.api.Test;

import seedu.address.model.book.Book;
import seedu.address.model.book.BookName;
import seedu.address.model.book.Isbn;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.BookBuilder;

class FindBookParserTest {

    private FindBookParser findBookParser = new FindBookParser();

    @Test
    void parseOnlyOnePrefix() {
        Book book = new BookBuilder(HARRY_POTTER).withAuthors(VALID_AUTHOR_JK_ROWLING).withTags(VALID_TAG_MAGIC).build();


    }
}