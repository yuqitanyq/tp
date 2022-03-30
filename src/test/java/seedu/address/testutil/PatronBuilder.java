package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.patron.Email;
import seedu.address.model.patron.Id;
import seedu.address.model.patron.Name;
import seedu.address.model.patron.Patron;
import seedu.address.model.patron.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Patron objects.
 */
public class PatronBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ID = "A6666666H";

    private Name name;
    private Phone phone;
    private Email email;
    private Id id;
    private Set<Tag> tags;

    /**
     * Creates a {@code PatronBuilder} with the default details.
     */
    public PatronBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        id = new Id(DEFAULT_ID);
        tags = new HashSet<>();
    }

    /**
     * Initializes the PatronBuilder with the data of {@code patronToCopy}.
     */
    public PatronBuilder(Patron patronToCopy) {
        name = patronToCopy.getName();
        phone = patronToCopy.getPhone();
        email = patronToCopy.getEmail();
        id = patronToCopy.getId();
        tags = new HashSet<>(patronToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Patron} that we are building.
     */
    public PatronBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Patron} that we are building.
     */
    public PatronBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Id} of the {@code Patron} that we are building.
     */
    public PatronBuilder withId(String id) {
        this.id = new Id(id);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Patron} that we are building.
     */
    public PatronBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Patron} that we are building.
     */
    public PatronBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    public Patron build() {
        return new Patron(name, phone, email, id, tags);
    }

}
