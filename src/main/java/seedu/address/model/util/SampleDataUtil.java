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
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"))
        };
    }

    public static Book[] getSampleBooks() {
        return new Book[] {
            new Book(new BookName("Harry Potter and The Philosopher's Stone"), new Isbn("978-71617-018-8-5"),
                getAuthorList("J. K. Rowling"),
                getTagSet("Adventure", "Magic")),
            new Book(new BookName("The Hunger Games: MockingJay"), new Isbn("9786029293883"),
                getAuthorList("Suzanne Collins"),
                getTagSet("Thriller", "Scifi", "Adventure")),
            new Book(new BookName("Introduction to Algorithms"), new Isbn("978-03-71-88850-6"),
                getAuthorList("Cormen", "Leiserson", "Rivest", "Stein"),
                getTagSet("ComputerScience", "Mathematics")),
            new Book(new BookName("The Little Book of Semaphores"), new Isbn("4992719864"),
                getAuthorList(),
                getTagSet("ComputerScience", "Technology")),
            new Book(new BookName("The Maze Runner"), new Isbn("1-474-59282-1"),
                getAuthorList("James Dashner1", "James Dashner2", "James Dashner3"),
                getTagSet("Adventure", "Romance", "Scifi")),
            new Book(new BookName("Artificial Intelligence: A Modern Approach"), new Isbn("9780131038059"),
                getAuthorList("Peter Norvig", "Stuart J. Russell"),
                getTagSet("Technology")),
            new Book(new BookName("Cinderella"), new Isbn("9781409580454"),
                getAuthorList(),
                getTagSet())
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
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
