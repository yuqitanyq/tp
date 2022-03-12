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

import seedu.address.logic.commands.book.AddBookCommand;
import seedu.address.model.book.Book;
import seedu.address.model.book.BookName;
import seedu.address.model.book.Isbn;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.BookBuilder;

public class AddBookCommandParserTest {
    private AddBookCommandParser parser = new AddBookCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Book expectedBook = new BookBuilder(HARRY_POTTER).withTags(VALID_TAG_MAGIC).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + BOOK_NAME_DESC_HARRY_POTTER + ISBN_DESC_HARRY_POTTER
                + AUTHOR_DESC_JK_ROWLING + TAG_DESC_MAGIC, new AddBookCommand(expectedBook));

        // multiple names - last name accepted
        assertParseSuccess(parser, BOOK_NAME_DESC_HUNGER_GAMES + BOOK_NAME_DESC_HARRY_POTTER
                + ISBN_DESC_HARRY_POTTER + AUTHOR_DESC_JK_ROWLING
                + TAG_DESC_MAGIC, new AddBookCommand(expectedBook));

        // multiple isbn - last isbn accepted
        assertParseSuccess(parser, BOOK_NAME_DESC_HARRY_POTTER + ISBN_DESC_HUNGER_GAMES + ISBN_DESC_HARRY_POTTER
                + AUTHOR_DESC_JK_ROWLING + TAG_DESC_MAGIC, new AddBookCommand(expectedBook));

        // multiple tags - all accepted
        Book expectedBookMultipleTags = new BookBuilder(HARRY_POTTER).withTags(VALID_TAG_ADVENTURE, VALID_TAG_MAGIC)
                .build();
        assertParseSuccess(parser, BOOK_NAME_DESC_HARRY_POTTER + ISBN_DESC_HARRY_POTTER
                + AUTHOR_DESC_JK_ROWLING
                + TAG_DESC_ADVENTURE + TAG_DESC_MAGIC, new AddBookCommand(expectedBookMultipleTags));

        // multiple authors - all accepted
        Book expectedBookMultipleAuthors = new BookBuilder(HARRY_POTTER)
                .withAuthors(VALID_AUTHOR_JK_ROWLING, VALID_AUTHOR_SUZANNE_COLLINS)
                .withTags()
                .build();
        assertParseSuccess(parser, BOOK_NAME_DESC_HARRY_POTTER + ISBN_DESC_HARRY_POTTER
                + AUTHOR_DESC_JK_ROWLING + AUTHOR_DESC_SUZANNE_COLLINS,
                new AddBookCommand(expectedBookMultipleAuthors));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Book expectedBook = new BookBuilder(HARRY_POTTER).withTags().build();
        assertParseSuccess(parser, BOOK_NAME_DESC_HARRY_POTTER + ISBN_DESC_HARRY_POTTER
                + AUTHOR_DESC_JK_ROWLING,
                new AddBookCommand(expectedBook));

        // zero authors
        expectedBook = new BookBuilder(HARRY_POTTER).withAuthors().build();
        assertParseSuccess(parser, BOOK_NAME_DESC_HARRY_POTTER + ISBN_DESC_HARRY_POTTER
                        + TAG_DESC_ADVENTURE + TAG_DESC_MAGIC,
                new AddBookCommand(expectedBook));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddBookCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_BOOK_NAME_HARRY_POTTER + ISBN_DESC_HARRY_POTTER, expectedMessage);

        // missing isbn prefix
        assertParseFailure(parser, BOOK_NAME_DESC_HARRY_POTTER + VALID_ISBN_HARRY_POTTER, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_BOOK_NAME_HARRY_POTTER + VALID_ISBN_HARRY_POTTER, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_BOOK_NAME_DESC + ISBN_DESC_HARRY_POTTER + AUTHOR_DESC_JK_ROWLING
                + TAG_DESC_ADVENTURE + TAG_DESC_MAGIC, BookName.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, BOOK_NAME_DESC_HARRY_POTTER + INVALID_ISBN_DESC + AUTHOR_DESC_JK_ROWLING
                + TAG_DESC_ADVENTURE + TAG_DESC_MAGIC, Isbn.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, BOOK_NAME_DESC_HARRY_POTTER + ISBN_DESC_HARRY_POTTER + AUTHOR_DESC_JK_ROWLING
                + INVALID_TAG_DESC + TAG_DESC_MAGIC, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_BOOK_NAME_DESC + ISBN_DESC_HARRY_POTTER + INVALID_AUTHOR_DESC,
                BookName.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + BOOK_NAME_DESC_HARRY_POTTER + ISBN_DESC_HARRY_POTTER
                        + AUTHOR_DESC_JK_ROWLING + TAG_DESC_ADVENTURE + TAG_DESC_MAGIC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddBookCommand.MESSAGE_USAGE));
    }
}
