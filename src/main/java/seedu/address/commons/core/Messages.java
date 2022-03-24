package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_EMPTY_COMMAND = "Command cannot be blank";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_BOOK_DISPLAYED_INDEX = "The book index provided is invalid";
    public static final String MESSAGE_BOOKS_LISTED_OVERVIEW = "%1$d books listed!";
    public static final String MESSAGE_INVALID_PATRON_DISPLAYED_INDEX = "The patron index provided is invalid";
    public static final String MESSAGE_PATRONS_LISTED_OVERVIEW = "%1$d patrons listed!";
    public static final String MESSAGE_BOOK_NOT_BORROWED = "You cannot return %1$s if is not borrowed!";
    public static final String MESSAGE_NO_BOOKS_BORROWED = "Patron %1$s did not borrow any book!";
    public static final String MESSAGE_BOOK_ALREADY_BORROWED = "%1$s is already borrowed!";
    public static final String MESSAGE_RETURN_DATE_TOO_EARLY = "Return date must be later than today's date!";
    public static final String MESSAGE_SAME_ISBN_INCONSISTENT = "There already exists a book with isbn %1$s, "
            + "but has different authors or name!\n";
}
