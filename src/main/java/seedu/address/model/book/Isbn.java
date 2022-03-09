package seedu.address.model.book;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Isbn {

    public static final String SPECIAL_CHARACTERS = "-";
    public static final String MESSAGE_CONSTRAINTS = "Isbn should adhere to the following constraints:\n"
            + "1. Contain only digits and at most one \"" + SPECIAL_CHARACTERS + "\" character between digits,"
            + " and must start and end with digit characters.\n"
            + "2. Contain exactly 10 digits or 13 digits\n"
            + "3. For 10-digit Isbn, the sum of [position]x[digit at position] must be divisible by 11\n"
            + "4. For 13-digit Isbn, the digits must start with 978 or 979, and have correct check sum.\n"
            + "The check sum:\n"
            + "    - is defined as [weight of position]x[digit at position] and must be divisible by 10\n"
            + "    - weight is 1 if position is odd and 3 otherwise\n"
            + "5. Position is the 1-based index of the digit in the isbn input after removal of \""
            + SPECIAL_CHARACTERS + "\" character.\n";

    public static final String VALIDATION_REGEX_ISBN = "^[0-9]+(-?[0-9]+)+$";

    private static final int ISBN10_CHECKSUM_DIVIDER = 11;
    private static final int ISBN13_CHECKSUM_DIVIDER = 10;
    private static final int ISBN10_LENGTH = 10;
    private static final int ISBN13_LENGTH = 13;
    private static final int ISBN13_VALID_PREFIX1 = 978;
    private static final int ISBN13_VALID_PREFIX2 = 979;

    private final String isbn;

    /**
     * Constructs a {@code Isbn}
     *
     * @param isbn A valid isbn.
     */
    public Isbn(String isbn) {
        requireNonNull(isbn);
        checkArgument(isValidIsbn(isbn), MESSAGE_CONSTRAINTS);
        this.isbn = isbn;
    }

    /**
     * Returns true if a given string is a valid isbn.
     */
    public static boolean isValidIsbn(String test) {
        if (!test.matches(VALIDATION_REGEX_ISBN)) {
            System.out.println("regex problem");
            return false;
        }
        String isbnWithoutHyphen = removeHyphen(test);
        if (isbnWithoutHyphen.length() == ISBN10_LENGTH) {
            return isValidIsbn10(isbnWithoutHyphen);
        }
        if (isbnWithoutHyphen.length() == ISBN13_LENGTH) {
            return isValidIsbn13(isbnWithoutHyphen);
        }
        return false;
    }

    private static String removeHyphen(String fullIsbn) {
        String[] isbnParts = fullIsbn.split("-");
        StringBuilder isbnWithoutHyphen = new StringBuilder();
        for (String substring: isbnParts) {
            isbnWithoutHyphen.append(substring);
        }
        return isbnWithoutHyphen.toString();
    }

    private static boolean isValidIsbn10(String isbnWithoutHyphen) {
        assert isbnWithoutHyphen.length() == ISBN10_LENGTH : "isbn length is not correct.";
        int checkSum = 0;
        for (int i = 0; i < ISBN10_LENGTH; i++) {
            int weight = ISBN10_LENGTH - i;
            int digitAtPosition = Integer.parseInt(isbnWithoutHyphen.substring(i, i + 1));
            checkSum += weight * digitAtPosition;
        }
        return checkSum % ISBN10_CHECKSUM_DIVIDER == 0;
    }

    private static boolean isValidIsbn13(String isbnWithoutHyphen) {
        assert isbnWithoutHyphen.length() == ISBN13_LENGTH : "isbn length is not correct.";

        // Checks for valid prefix
        int prefix = Integer.parseInt(isbnWithoutHyphen.substring(0, 3));
        boolean hasValidPrefix = prefix == ISBN13_VALID_PREFIX1 || prefix == ISBN13_VALID_PREFIX2;

        // Checks for valid check sum
        int checkSum = 0;
        for (int i = 0; i < ISBN13_LENGTH; i++) {
            // weights alternate between 1 and 3 according to Isbn standards
            int weight = i % 2 == 0 ? 1 : 3;
            int digitAtPosition = Integer.parseInt(isbnWithoutHyphen.substring(i, i + 1));
            checkSum += weight * digitAtPosition;
        }
        boolean hasValidCheckSum = checkSum % ISBN13_CHECKSUM_DIVIDER == 0;

        return hasValidPrefix && hasValidCheckSum;
    }

    @Override
    public String toString() {
        return isbn;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Isbn // instanceof handles nulls
                && removeHyphen(isbn).equals(removeHyphen(((Isbn) other).isbn))); // state check
    }


}
