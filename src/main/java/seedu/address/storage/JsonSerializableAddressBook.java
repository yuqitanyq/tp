package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.book.Book;
import seedu.address.model.patron.Patron;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PATRON = "Patrons list contains duplicate patron(s).";

    private final List<JsonAdaptedPatron> patrons = new ArrayList<>();
    private final List<JsonAdaptedBook> books = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given patrons.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("patrons") List<JsonAdaptedPatron> patrons,
                                       @JsonProperty("books") List<JsonAdaptedBook> books) {
        this.patrons.addAll(patrons);
        this.books.addAll(books);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        patrons.addAll(source.getPatronList().stream().map(JsonAdaptedPatron::new).collect(Collectors.toList()));
        books.addAll(source.getBookList().stream().map(JsonAdaptedBook::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (JsonAdaptedPatron jsonAdaptedPatron : patrons) {
            Patron patron = jsonAdaptedPatron.toModelType();
            if (addressBook.hasPatron(patron)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PATRON);
            }
            addressBook.addPatron(patron);
        }
        for (JsonAdaptedBook jsonAdaptedBook : books) {
            Book book = jsonAdaptedBook.toModelType();
            addressBook.addBook(book);
        }
        return addressBook;
    }

}
