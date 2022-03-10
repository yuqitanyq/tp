package seedu.address.logic.parser.book;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AUTHOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ISBN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.book.EditBookCommand;
import seedu.address.logic.commands.book.EditBookCommand.EditBookDescriptor;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.book.Author;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and create a new EditBookCommand object
 */
public class EditBookCommandParser implements Parser<EditBookCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditBookCommand
     * and returns an EditBookCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditBookCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_ISBN, PREFIX_AUTHOR, PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditBookCommand.MESSAGE_USAGE), pe);
        }

        EditBookDescriptor editBookDescriptor = new EditBookDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editBookDescriptor.setBookName(ParserUtil.parseBookName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_ISBN).isPresent()) {
            editBookDescriptor.setIsbn(ParserUtil.parseIsbn(argMultimap.getValue(PREFIX_ISBN).get()));
        }

        parseAuthorsForEdit(argMultimap.getAllValues(PREFIX_AUTHOR)).ifPresent(editBookDescriptor::setAuthors);

        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editBookDescriptor::setTags);

        if (!editBookDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditBookCommand.MESSAGE_NOT_EDITED);
        }

        return new EditBookCommand(index, editBookDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

    /**
     * Parses {@code Collection<String> authors} into a {@code Optional<List<Author>>} if {@code authors} is non-empty.
     * If {@code authors} contain only one element which is an empty string, it will be parsed into a
     * {@code List<Author>} containing zero authors.
     */
    private Optional<List<Author>> parseAuthorsForEdit(Collection<String> authors) throws ParseException {
        assert authors != null;

        if (authors.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> authorList = authors.size() == 1 && authors.contains("") ? Collections.emptyList() : authors;
        return Optional.of(ParserUtil.parseAuthors(authorList));
    }
}

