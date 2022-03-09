package seedu.address.model.bookstub;

/**
 * BookStub depends on the Author class for Author name.
 * It is not safe for deployment and needed to be removed during integration.
 */
public class Author {

    private String name;

    public Author(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
