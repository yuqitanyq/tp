package seedu.address.model.book;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Author in LibTask.
 * Guarantees: immutable; is valid as declared in {@link #isValidAuthor(String)}
 */
public class Author {

    public static final String MESSAGE_CONSTRAINTS =
            "Author names should only contain alphanumeric characters, spaces and dot, "
            + "and it should not be blank, and it must start with an alphanumeric character.";

    /*
     * The first character of the name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     * The name should also contain at least one alphanumeric character,
     * and contains no non-alphanumeric characters other than the dot character.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} .]*";

    public final String fullAuthorName;

    /**
     * Constructs a {@code AuthorName}.
     *
     * @param name A valid author name.
     */
    public Author(String name) {
        requireNonNull(name);
        checkArgument(isValidAuthor(name), MESSAGE_CONSTRAINTS);
        fullAuthorName = name;
    }

    /**
     * Returns true if a given string can be converted to a valid author.
     */
    public static boolean isValidAuthor(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return fullAuthorName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Author // instanceof handles nulls
            && fullAuthorName.equals(((Author) other).fullAuthorName)); // state check
    }
}
