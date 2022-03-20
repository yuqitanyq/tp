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

public class EditBookCommandParserTest {
    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            EditBookCommand.MESSAGE_USAGE);

    private EditBookCommandParser parser = new EditBookCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, PREFIX_NAME + VALID_BOOK_NAME_HARRY_POTTER, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditBookCommand.MESSAGE_NOT_EDITED);

        // no index and not field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + BOOK_NAME_DESC_HARRY_POTTER, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + BOOK_NAME_DESC_HUNGER_GAMES, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 random string here", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 x/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid book name
        assertParseFailure(parser, "1" + INVALID_BOOK_NAME_DESC, BookName.MESSAGE_CONSTRAINTS);

        // invalid isbn
        assertParseFailure(parser, "1" + INVALID_ISBN_DESC, Isbn.MESSAGE_CONSTRAINTS);

        // invalid author
        assertParseFailure(parser, "1" + INVALID_AUTHOR_DESC, Author.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS);

        // invalid isbn followed by valid author
        assertParseFailure(parser, "1" + INVALID_ISBN_DESC + AUTHOR_DESC_JK_ROWLING, Isbn.MESSAGE_CONSTRAINTS);

        // valid isbn followed by invalid isbn. The test case for invalid isbn followed by valid isbn
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + ISBN_DESC_HARRY_POTTER + INVALID_ISBN_DESC, Isbn.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Book} being edited
        // parsing it together with a valid tag results in an error
        assertParseFailure(parser, "1" + TAG_DESC_SCIFI + TAG_DESC_THRILLER + TAG_EMPTY,
                Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_SCIFI + TAG_EMPTY + TAG_DESC_THRILLER,
                Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_SCIFI + TAG_DESC_THRILLER,
                Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_BOOK_NAME_DESC + INVALID_ISBN_DESC + AUTHOR_DESC_JK_ROWLING
                + TAG_DESC_ADVENTURE, BookName.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_BOOK;
        String userInput = targetIndex.getOneBased() + BOOK_NAME_DESC_HUNGER_GAMES + TAG_DESC_THRILLER
                + ISBN_DESC_HUNGER_GAMES + AUTHOR_DESC_SUZANNE_COLLINS + TAG_DESC_SCIFI;

        EditBookCommand.EditBookDescriptor bookDescriptor = new EditBookDescriptorBuilder()
                .withBookName(VALID_BOOK_NAME_HUNGER_GAMES)
                .withIsbn(VALID_ISBN_HUNGER_GAMES)
                .withAuthors(VALID_AUTHOR_SUZANNE_COLLINS)
                .withTags(VALID_TAG_SCIFI, VALID_TAG_THRILLER)
                .build();
        EditBookCommand expectedCommand = new EditBookCommand(targetIndex, bookDescriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_BOOK;
        String userInput = targetIndex.getOneBased() + BOOK_NAME_DESC_HARRY_POTTER + AUTHOR_DESC_JK_ROWLING;

        EditBookCommand.EditBookDescriptor bookDescriptor = new EditBookDescriptorBuilder()
                .withBookName(VALID_BOOK_NAME_HARRY_POTTER)
                .withAuthors(VALID_AUTHOR_JK_ROWLING).build();
        EditBookCommand expectedCommand = new EditBookCommand(targetIndex, bookDescriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // book name
        Index targetIndex = INDEX_FOURTH_BOOK;
        String userInput = targetIndex.getOneBased() + BOOK_NAME_DESC_HARRY_POTTER;
        EditBookCommand.EditBookDescriptor bookDescriptor = new EditBookDescriptorBuilder()
                .withBookName(VALID_BOOK_NAME_HARRY_POTTER).build();
        EditBookCommand expectedCommand = new EditBookCommand(targetIndex, bookDescriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // isbn
        userInput = targetIndex.getOneBased() + ISBN_DESC_HARRY_POTTER;
        bookDescriptor = new EditBookDescriptorBuilder().withIsbn(VALID_ISBN_HARRY_POTTER).build();
        expectedCommand = new EditBookCommand(targetIndex, bookDescriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // author
        userInput = targetIndex.getOneBased() + AUTHOR_DESC_JK_ROWLING;
        bookDescriptor = new EditBookDescriptorBuilder().withAuthors(VALID_AUTHOR_JK_ROWLING).build();
        expectedCommand = new EditBookCommand(targetIndex, bookDescriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_THRILLER;
        bookDescriptor = new EditBookDescriptorBuilder().withTags(VALID_TAG_THRILLER).build();
        expectedCommand = new EditBookCommand(targetIndex, bookDescriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_BOOK;
        String userInput = targetIndex.getOneBased() + BOOK_NAME_DESC_HARRY_POTTER + ISBN_DESC_HARRY_POTTER
                + TAG_DESC_THRILLER + BOOK_NAME_DESC_HARRY_POTTER + ISBN_DESC_HARRY_POTTER
                + TAG_DESC_THRILLER + BOOK_NAME_DESC_HUNGER_GAMES + ISBN_DESC_HUNGER_GAMES
                + AUTHOR_DESC_SUZANNE_COLLINS + TAG_DESC_SCIFI;

        EditBookCommand.EditBookDescriptor bookDescriptor = new EditBookDescriptorBuilder()
                .withBookName(VALID_BOOK_NAME_HUNGER_GAMES)
                .withIsbn(VALID_ISBN_HUNGER_GAMES)
                .withAuthors(VALID_AUTHOR_SUZANNE_COLLINS)
                .withTags(VALID_TAG_THRILLER, VALID_TAG_SCIFI)
                .build();
        EditBookCommand expectedCommand = new EditBookCommand(targetIndex, bookDescriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        //no other valid values specified
        Index targetIndex = INDEX_FIRST_BOOK;
        String userInput = targetIndex.getOneBased() + INVALID_ISBN_DESC + ISBN_DESC_HUNGER_GAMES;
        EditBookCommand.EditBookDescriptor bookDescriptor = new EditBookDescriptorBuilder()
                .withIsbn(VALID_ISBN_HUNGER_GAMES).build();
        EditBookCommand expectedCommand = new EditBookCommand(targetIndex, bookDescriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        //other valid values specified
        userInput = targetIndex.getOneBased() + BOOK_NAME_DESC_HUNGER_GAMES + INVALID_ISBN_DESC
                + ISBN_DESC_HUNGER_GAMES + AUTHOR_DESC_SUZANNE_COLLINS;
        bookDescriptor = new EditBookDescriptorBuilder().withBookName(VALID_BOOK_NAME_HUNGER_GAMES)
                .withIsbn(VALID_ISBN_HUNGER_GAMES)
                .withAuthors(VALID_AUTHOR_SUZANNE_COLLINS)
                .build();
        expectedCommand = new EditBookCommand(targetIndex, bookDescriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_FOURTH_BOOK;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditBookCommand.EditBookDescriptor bookDescriptor = new EditBookDescriptorBuilder().withTags().build();
        EditBookCommand expectedCommand = new EditBookCommand(targetIndex, bookDescriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
