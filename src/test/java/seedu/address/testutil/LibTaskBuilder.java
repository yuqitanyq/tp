package seedu.address.testutil;

import seedu.address.model.LibTask;
import seedu.address.model.patron.Patron;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code LibTask ab = new LibTaskBuilder().withPatron("John", "Doe").build();}
 */
public class LibTaskBuilder {

    private LibTask libTask;

    public LibTaskBuilder() {
        libTask = new LibTask();
    }

    public LibTaskBuilder(LibTask libTask) {
        this.libTask = libTask;
    }

    /**
     * Adds a new {@code Patron} to the {@code LibTask} that we are building.
     */
    public LibTaskBuilder withPatron(Patron patron) {
        libTask.addPatron(patron);
        return this;
    }

    public LibTask build() {
        return libTask;
    }
}
