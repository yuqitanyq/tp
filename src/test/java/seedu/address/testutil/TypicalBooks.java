package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_AUTHOR_JK_ROWLING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AUTHOR_SUZANNE_COLLINS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BOOK_NAME_HARRY_POTTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BOOK_NAME_HUNGER_GAMES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ISBN_HARRY_POTTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ISBN_HUNGER_GAMES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_ADVENTURE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_MAGIC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_SCIFI;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_THRILLER;
import static seedu.address.model.book.BookStatusType.BORROWED;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import seedu.address.model.book.Book;
import seedu.address.model.book.BookStatus;

/**
 * A utility class containing a list of {@code Patron} objects to be used in tests.
 */
public class TypicalBooks {

    public static final Book ALGORITHM = new BookBuilder().withName("Introduction to Algorithms")
            .withIsbn("978-03-71-88850-6").withAuthors("Cormen", "Leiserson", "Rivest", "Stein")
            .withTags("ComputerScience", "Mathematics").build();
    public static final Book SEMAPHORE = new BookBuilder().withName("The Little Book of Semaphores")
            .withIsbn("4992719864")
            .withTags("ComputerScience", "Technology").build();
    public static final Book MAZE_RUNNER = new BookBuilder().withName("The Maze Runner")
            .withIsbn("1-474-59282-1")
            .withAuthors("James Dashner1", "James Dashner2", "James Dashner3").build();
    public static final Book AI = new BookBuilder().withName("Artificial Intelligence: A Modern Approach")
            .withIsbn("9780131038059")
            .withAuthors("Peter Norvig", "Stuart J. Russell")
            .withTags("Technology")
            .withBookStatus(getSampleBorrowedStatus())
            .build();
    public static final Book CINDERELLA = new BookBuilder().withName("Cinderella")
            .withIsbn("9781409580454")
            .build();

    // Manually added - Book's details found in {@code CommandTestUtil}
    public static final Book HARRY_POTTER = new BookBuilder().withName(VALID_BOOK_NAME_HARRY_POTTER)
            .withIsbn(VALID_ISBN_HARRY_POTTER)
            .withAuthors(VALID_AUTHOR_JK_ROWLING)
            .withTags(VALID_TAG_ADVENTURE, VALID_TAG_MAGIC).build();
    public static final Book HUNGER_GAMES = new BookBuilder().withName(VALID_BOOK_NAME_HUNGER_GAMES)
            .withIsbn(VALID_ISBN_HUNGER_GAMES)
            .withAuthors(VALID_AUTHOR_SUZANNE_COLLINS)
            .withTags(VALID_TAG_THRILLER, VALID_TAG_SCIFI, VALID_TAG_ADVENTURE).build();

    private TypicalBooks() {} // prevents instantiation

    public static List<Book> getTypicalBooks() {
        return new ArrayList<>(Arrays.asList(ALGORITHM, SEMAPHORE, MAZE_RUNNER, AI, CINDERELLA));
    }

    private static BookStatus getSampleBorrowedStatus() {
        return new BookStatus(BORROWED,
                Optional.ofNullable(TypicalPatrons.getTypicalPatrons().get(0)),
                Optional.ofNullable("14-Feb-2022"),
                Optional.ofNullable("28-Feb-2022"));
    }
}
