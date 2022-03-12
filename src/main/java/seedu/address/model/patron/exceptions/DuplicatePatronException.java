package seedu.address.model.patron.exceptions;

/**
 * Signals that the operation will result in duplicate Patrons (Patrons are considered duplicates if they have the same
 * identity).
 */
public class DuplicatePatronException extends RuntimeException {
    public DuplicatePatronException() {
        super("Operation would result in duplicate patrons");
    }
}
