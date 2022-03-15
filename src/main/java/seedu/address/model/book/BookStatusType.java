package seedu.address.model.book;

public enum BookStatusType {
    AVAILABLE, BORROWED;

    public static final String MESSAGE_CONSTRAINTS = "Book status type must be Available or Borrowed.";

    @Override
    public String toString() {
        switch(this) {
        case AVAILABLE: return "Available";
        case BORROWED: return "Borrowed";
        default: throw new IllegalArgumentException();
        }
    }
}
