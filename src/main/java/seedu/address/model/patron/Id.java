package seedu.address.model.patron;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Patron's id in LibTask.
 * Guarantees: immutable; is valid as declared in {@link #isValidId(String)}
 */
public class Id {

    public static final String MESSAGE_CONSTRAINTS = "Ids must be of format AXXXXXXXX, and should not be blank. Last "
            + "character of an ID must be a capital letter.";

    public static final String VALIDATION_REGEX = "A[0-9]{7}[A-Z]{1}";

    public final String value;

    /**
     * Constructs an {@code Id}.
     *
     * @param id A valid id.
     */
    public Id(String id) {
        requireNonNull(id);
        checkArgument(isValidId(id), MESSAGE_CONSTRAINTS);
        value = id;
    }

    /**
     * Returns true if a given string is a valid id.
     */
    public static boolean isValidId(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Id // instanceof handles nulls
                && getIdForComparison().equals(((Id) other).getIdForComparison())); // state check
    }

    /**
     * Returns the id of the patron, converted to lowercase.
     */
    private String getIdForComparison() {
        return value.toLowerCase();
    }

    @Override
    public int hashCode() {
        return getIdForComparison().hashCode();
    }

}
