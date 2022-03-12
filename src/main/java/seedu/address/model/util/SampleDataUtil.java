package seedu.address.model.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.book.Author;
import seedu.address.model.book.Book;
import seedu.address.model.book.BookName;
import seedu.address.model.book.Isbn;
import seedu.address.model.patron.Email;
import seedu.address.model.patron.Id;
import seedu.address.model.patron.Name;
import seedu.address.model.patron.Patron;
import seedu.address.model.patron.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    public static final long SAMPLE_BOOK_CREATED_TIME = 1646989653388L;

    public static Patron[] getSamplePatrons() {
        return new Patron[] {
            new Patron(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Id("A0123456H"),
                getTagSet("friends")),
            new Patron(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Id("A0123456H"),
                getTagSet("colleagues", "friends")),
            new Patron(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Id("A0123456H"),
                getTagSet("neighbours")),
            new Patron(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Id("A0123456H"),
                getTagSet("family")),
            new Patron(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Id("A0123456H"),
                getTagSet("classmates")),
            new Patron(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Id("A0123456H"),
                getTagSet("colleagues"))
        };
    }

    public static Book[] getSampleBooks() {
        return new Book[] {
            new Book(new BookName("Harry Potter and The Philosopher's Stone"), new Isbn("978-71617-018-8-5"),
                getAuthorList("J. K. Rowling"),
                getTagSet("Adventure", "Magic"), SAMPLE_BOOK_CREATED_TIME),
            new Book(new BookName("The Hunger Games: MockingJay"), new Isbn("9786029293883"),
                getAuthorList("Suzanne Collins"),
                getTagSet("Thriller", "Scifi", "Adventure"), SAMPLE_BOOK_CREATED_TIME),
            new Book(new BookName("Introduction to Algorithms"), new Isbn("978-03-71-88850-6"),
                getAuthorList("Cormen", "Leiserson", "Rivest", "Stein"),
                getTagSet("ComputerScience", "Mathematics"), SAMPLE_BOOK_CREATED_TIME),
            new Book(new BookName("The Little Book of Semaphores"), new Isbn("4992719864"),
                getAuthorList(),
                getTagSet("ComputerScience", "Technology"), SAMPLE_BOOK_CREATED_TIME),
            new Book(new BookName("The Maze Runner"), new Isbn("1-474-59282-1"),
                getAuthorList("James Dashner1", "James Dashner2", "James Dashner3"),
                getTagSet("Adventure", "Romance", "Scifi"), SAMPLE_BOOK_CREATED_TIME),
            new Book(new BookName("Artificial Intelligence: A Modern Approach"), new Isbn("9780131038059"),
                getAuthorList("Peter Norvig", "Stuart J. Russell"),
                getTagSet("Technology"), SAMPLE_BOOK_CREATED_TIME),
            new Book(new BookName("Cinderella"), new Isbn("9781409580454"),
                getAuthorList(),
                getTagSet(), SAMPLE_BOOK_CREATED_TIME)
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Patron samplePatron : getSamplePatrons()) {
            sampleAb.addPatron(samplePatron);
        }
        for (Book sampleBook : getSampleBooks()) {
            sampleAb.addBook(sampleBook);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a list of authors containing the list of strings given.
     */
    public static ArrayList<Author> getAuthorList(String... strings) {
        return Arrays.stream(strings)
                .map(Author::new)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
