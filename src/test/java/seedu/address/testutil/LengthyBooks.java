package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.LibTask;
import seedu.address.model.book.Book;

/**
 * A utility class containing a list of {@code Book} objects to be used in tests.
 */
public class LengthyBooks {

    // Book with 800 characters in name, 800 characters in author name, 800 characters in tag label.
    public static final Book EIGHT_HUNDRED_LARGE_BOOK = new BookBuilder().withName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
            + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
            + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
            + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
            + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
            + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
            + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
            + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
            + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
            .withIsbn("0-545-01022-5")
            .withAuthors("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
            + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
            + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
            + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
            + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
            + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
            + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
            + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
            + "aaaaa")
            .withTags("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
            + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
            + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
            + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
            + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
            + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
            + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
            + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
            + "aaaaa")
            .build();

    // LARGE_BOOK contains lengthy book titles, lengthy author names, multiple tags
    public static final Book LARGE_BOOK = new BookBuilder().withName("Lorem Ipsum comes from a latin text written"
            + " in 45BC by Roman statesman Lorem Ipsum comes from a latin text written in 45BC by Roman statesman"
            + " Lorem Ipsum comes from a latin text written in 45BC by Roman statesman Lorem Ipsum comes from a "
            + "latin text written in 45BC by Roman statesman Lorem Ipsum comes from a latin text written in 45BC by"
            + " Roman statesman Lorem Ipsum comes from a latin text written in 45BC by Roman statesman")
            .withIsbn("0-545-01022-5")
            .withAuthors("accusamus acutum aegritudine alios aliquip allevatio antiqua"
                    + " arbitramur arbitrium arcu augeri captet censes cetero cogitavisse comparare conscientiam"
                    + " contentus cupiditatum deditum democritus depravata deserunt desiderant desistunt detracta"
                    + " didicerimus dirigentur displicet dissensio disseretur distinguique eget elit eo epicurei equos"
                    + " exercitus exorsus fastidium faucibus felis foedus fuissent generis geometrica gravida illam"
                    + " impetus incursione indignae inhaererent initia inquam inter ipsas ipsi iriure lacinia licet"
                    + " mel mi minim mors muniti netus noster nostro occultarum odioque omnino oratoribus oriantur"
                    + " ornateque percipit plura pluribus possint praetulerit privamur probamus procedat propemodum"
                    + " pulchraeque quaerendi quanti quisque rudem sensum sole stultorum successerit successionem"
                    + " suspicor tranquilli unde varias videre voce vulgo ")
            .withTags("Detective", "Mystery", "Fantasy", "Classics", "Historical",
                    "Horror", "Literary", "Romance", "Crime", "SciFi", "Thrillers", "Drama", "Poetry", "Media",
                    "Nonfiction", "Horror1", "Literary1", "Romance1", "Crime1", "SciFi1", "Thrillers1", "Drama1",
                    "Poetry1", "Media1" , "Media2", "Media3", "Media4", "Media5", "Media6", "Media7", "Media8",
                    "Media9", "Media10")
            .build();

    private LengthyBooks() {} // prevents instantiation

    /**
     * Returns an {@code LibTask} with all the lengthy books.
     */
    public static LibTask getLengthyAddressBook() {
        LibTask ab = new LibTask();
        for (Book book : getLengthyBooks()) {
            ab.addBook(book);
        }
        return ab;
    }

    public static List<Book> getLengthyBooks() {
        return new ArrayList<>(Arrays.asList(EIGHT_HUNDRED_LARGE_BOOK, LARGE_BOOK));
    }
}
