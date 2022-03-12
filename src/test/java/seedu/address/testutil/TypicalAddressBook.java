package seedu.address.testutil;

import static seedu.address.testutil.TypicalBooks.getTypicalBooks;
import static seedu.address.testutil.TypicalPatrons.getTypicalPatrons;

import seedu.address.model.AddressBook;
import seedu.address.model.book.Book;
import seedu.address.model.patron.Patron;

public class TypicalAddressBook {
    /**
     * Returns an {@code AddressBook} with all the typical patrons and books.
     *
     * @return An {@code AddressBook} with all the typical patrons and books.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Patron patron : getTypicalPatrons()) {
            ab.addPatron(patron);
        }
        for (Book book : getTypicalBooks()) {
            ab.addBook(book);
        }
        return ab;
    }
}
