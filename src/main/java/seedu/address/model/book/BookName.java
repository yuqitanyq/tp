package seedu.address.model.book;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Book's name in LibTask.
 * Guarantees: immutable; is valid as declared in {@link #isValidBookName(String)}
 */
public class BookName {
    public static final String MESSAGE_CONSTRAINTS =
            "Book names should only contain alphanumeric characters and spaces, "
        + "and it should not be blank, and it must start with an alphanumeric character.";

    /*
     * The first character of the name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     * The name should also contain at least one alphanumeric character,
     * and contains no non-alphanumeric characters other than ' character and : character.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} \\'\\:]*";

    public final String fullBookName;

    /**
     * Constructs a {@code BookName}.
     *
     * @param bookname A valid book name.
     */
    public BookName(String bookname) {
        requireNonNull(bookname);
        checkArgument(isValidBookName(bookname), MESSAGE_CONSTRAINTS);
        fullBookName = bookname;
    }

    /**
     * Returns true if a given string is a valid book name.
     */
    public static boolean isValidBookName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return fullBookName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof BookName) //instanceof handle nulls
                && fullBookName.equals(((BookName) other).fullBookName);
    }

    @Override
    public int hashCode() {
        return fullBookName.hashCode();
    }
}
