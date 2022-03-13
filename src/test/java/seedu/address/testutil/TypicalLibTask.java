package seedu.address.testutil;

import static seedu.address.testutil.TypicalBooks.getTypicalBooks;
import static seedu.address.testutil.TypicalPatrons.getTypicalPatrons;

import seedu.address.model.LibTask;
import seedu.address.model.book.Book;
import seedu.address.model.patron.Patron;

public class TypicalLibTask {
    /**
     * Returns an {@code LibTask} with all the typical patrons and books.
     *
     * @return An {@code LibTask} with all the typical patrons and books.
     */
    public static LibTask getTypicalLibTask() {
        LibTask ab = new LibTask();
        for (Patron patron : getTypicalPatrons()) {
            ab.addPatron(patron);
        }
        for (Book book : getTypicalBooks()) {
            ab.addBook(book);
        }
        return ab;
    }
}
