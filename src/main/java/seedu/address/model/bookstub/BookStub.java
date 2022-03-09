package seedu.address.model.bookstub;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * BookStub is a temporarily class for testing GUI.
 * It is not safe for deployment and needed to be removed during integration.
 */
public class BookStub {

    // Identity fields
    private final Name name;
    private final List<Author> authors;
    private final String isbn;
    private final boolean isAvailable;
    private final String date;

    // Data fields
    private final Set<Tag> categoryTags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public BookStub(Name name, List<Author> authors, String isbn, Set<Tag> categoryTags, boolean isAvailable,
                    String date) {
        requireAllNonNull(name, authors, isbn, categoryTags, isAvailable, date);
        this.name = name;
        this.authors = authors;
        this.isbn = isbn;
        this.categoryTags.addAll(categoryTags);
        this.isAvailable = isAvailable;
        this.date = date;
    }

    public Name getName() {
        return name;
    }

    public List<Author> getAuthor() {
        return authors;
    }

    public String getIsbn() {
        return isbn;
    }

    public Set<Tag> getCategoryTags() {
        return Collections.unmodifiableSet(categoryTags);
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public String getDate() {
        return date;
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
                && otherBookStub.getCategoryTags().equals(getCategoryTags());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append("; Author: ")
                .append(getAuthor())
                .append("; Isbn: ")
                .append(getIsbn());

        Set<Tag> categoryTags = getCategoryTags();

        if (!categoryTags.isEmpty()) {
            builder.append("; Category Tags: ");
            categoryTags.forEach(builder::append);
        }

        return builder.toString();
    }
}
