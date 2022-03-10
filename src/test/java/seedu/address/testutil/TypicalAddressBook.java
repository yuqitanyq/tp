package seedu.address.testutil;

import static seedu.address.testutil.TypicalBooks.getTypicalBooks;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;

import seedu.address.model.AddressBook;
import seedu.address.model.book.Book;
import seedu.address.model.person.Person;

public class TypicalAddressBook {
    /**
     * Returns an {@code AddressBook} with all the typical persons and books.
     *
     * @return An {@code AddressBook} with all the typical persons and books.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        for (Book book : getTypicalBooks()) {
            ab.addBook(book);
        }
        return ab;
    }
}
