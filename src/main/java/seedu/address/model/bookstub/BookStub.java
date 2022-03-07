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
    private final Set<Tag> categoryTags = new HashSet<>();
    private final Set<Tag> availabilityTags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public BookStub(Name name, String author, String isbn, Set<Tag> categoryTags, Set<Tag> availabilityTags) {
        requireAllNonNull(name, author, isbn, categoryTags, availabilityTags);
        this.name = name;
        this.author = author;
        this.isbn = isbn;
        this.categoryTags.addAll(categoryTags);
        this.availabilityTags.addAll(availabilityTags);
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

    public Set<Tag> getCategoryTags() {
        return Collections.unmodifiableSet(categoryTags);
    }

    public Set<Tag> getAvailabilityTags() { return Collections.unmodifiableSet(availabilityTags);
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
                && otherBookStub.getCategoryTags().equals(getCategoryTags())
                && otherBookStub.getAvailabilityTags().equals(getAvailabilityTags());
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
        Set<Tag> availabilityTags = getAvailabilityTags();

        if (!categoryTags.isEmpty()) {
            builder.append("; Category Tags: ");
            categoryTags.forEach(builder::append);
        }

        if (!availabilityTags.isEmpty()) {
            builder.append("; Availability Tags: ");
            availabilityTags.forEach(builder::append);
        }

        return builder.toString();
    }
}
