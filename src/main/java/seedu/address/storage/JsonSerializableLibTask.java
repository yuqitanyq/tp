package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.LibTask;
import seedu.address.model.ReadOnlyLibTask;
import seedu.address.model.book.Book;
import seedu.address.model.patron.Patron;

/**
 * An Immutable LibTask that is serializable to JSON format.
 */
@JsonRootName(value = "libTask")
class JsonSerializableLibTask {

    public static final String MESSAGE_DUPLICATE_PATRON = "Patrons list contains duplicate patron(s).";

    private final List<JsonAdaptedPatron> patrons = new ArrayList<>();
    private final List<JsonAdaptedBook> books = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableLibTask} with the given patrons.
     */
    @JsonCreator
    public JsonSerializableLibTask(@JsonProperty("patrons") List<JsonAdaptedPatron> patrons,
                                   @JsonProperty("books") List<JsonAdaptedBook> books) {
        this.patrons.addAll(patrons);
        this.books.addAll(books);
    }

    /**
     * Converts a given {@code ReadOnlyLibTask} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableLibTask}.
     */
    public JsonSerializableLibTask(ReadOnlyLibTask source) {
        patrons.addAll(source.getPatronList().stream().map(JsonAdaptedPatron::new).collect(Collectors.toList()));
        books.addAll(source.getBookList().stream().map(JsonAdaptedBook::new).collect(Collectors.toList()));
    }

    /**
     * Converts this LibTask into the model's {@code LibTask} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public LibTask toModelType() throws IllegalValueException {
        LibTask libTask = new LibTask();
        for (JsonAdaptedPatron jsonAdaptedPatron : patrons) {
            Patron patron = jsonAdaptedPatron.toModelType();
            if (libTask.hasPatron(patron)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PATRON);
            }
            libTask.addPatron(patron);
        }
        for (JsonAdaptedBook jsonAdaptedBook : books) {
            Book book = jsonAdaptedBook.toModelType();
            libTask.addBook(book);
        }
        return libTask;
    }

}
