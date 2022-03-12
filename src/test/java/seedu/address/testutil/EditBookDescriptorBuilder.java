package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.book.EditBookCommand.EditBookDescriptor;
import seedu.address.model.book.Book;
import seedu.address.model.book.BookName;
import seedu.address.model.book.Isbn;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building EditBookDescriptor objects.
 */
public class EditBookDescriptorBuilder {
    private EditBookDescriptor bookDescriptor;

    public EditBookDescriptorBuilder() {
        bookDescriptor = new EditBookDescriptor();
    }

    public EditBookDescriptorBuilder(EditBookDescriptor bookDescriptor) {
        this.bookDescriptor = new EditBookDescriptor(bookDescriptor);
    }

    /**
     * Returns an {@code EditBookDescriptor} with fields containing {@code book}'s details
     */
    public EditBookDescriptorBuilder(Book book) {
        bookDescriptor = new EditBookDescriptor();
        bookDescriptor.setBookName(book.getBookName());
        bookDescriptor.setIsbn(book.getIsbn());
        bookDescriptor.setAuthors(book.getAuthors());
        bookDescriptor.setTags(book.getTags());
    }

    /**
     * Sets the {@code BookName} of the {@code EditBookDescriptor} that we are building.
     */
    public EditBookDescriptorBuilder withBookName(String name) {
        bookDescriptor.setBookName(new BookName(name));
        return this;
    }

    /**
     * Sets the {@code Isbn} of the {@code EditBookDescriptor} that we are building.
     */
    public EditBookDescriptorBuilder withIsbn(String isbn) {
        bookDescriptor.setIsbn(new Isbn(isbn));
        return this;
    }

    /**
     * Sets the {@code authors} of the {@code EditBookDescriptor} that we are building.
     */
    public EditBookDescriptorBuilder withAuthors(String ... authors) {
        bookDescriptor.setAuthors(SampleDataUtil.getAuthorList(authors));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditBookDescriptor}
     * that we are building.
     */
    public EditBookDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        bookDescriptor.setTags(tagSet);
        return this;
    }

    public EditBookDescriptor build() {
        return bookDescriptor;
    }
}




