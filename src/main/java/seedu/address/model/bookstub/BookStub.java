package seedu.address.model.bookstub;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.tag.Tag;

public class BookStub {

    // Identity fields
    private final Name name;
    private final String author;
    private final String isbn;

    // Data fields
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public BookStub(Name name, String author, String isbn, Set<Tag> tags) {
        requireAllNonNull(name, author, isbn, tags);
        this.name = name;
        this.author = author;
        this.isbn = isbn;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof BookStub)) {
            return false;
        }

        BookStub otherBookStub = (BookStub) other;
        return otherBookStub.getName().equals(getName())
                && otherBookStub.getAuthor().equals(getAuthor())
                && otherBookStub.getIsbn().equals(getIsbn())
                && otherBookStub.getTags().equals(getTags());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append("; Author: ")
                .append(getAuthor())
                .append("; Isbn: ")
                .append(getIsbn());

        Set<Tag> tags = getTags();
        if (!tags.isEmpty()) {
            builder.append("; Tags: ");
            tags.forEach(builder::append);
        }
        return builder.toString();
    }
}
