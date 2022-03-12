package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.patron.EditPatronCommand;
import seedu.address.model.patron.Email;
import seedu.address.model.patron.Id;
import seedu.address.model.patron.Name;
import seedu.address.model.patron.Patron;
import seedu.address.model.patron.Phone;
import seedu.address.model.tag.Tag;


/**
 * A utility class to help with building EditPatronDescriptor objects.
 */
public class EditPatronDescriptorBuilder {

    private EditPatronCommand.EditPatronDescriptor descriptor;

    public EditPatronDescriptorBuilder() {
        descriptor = new EditPatronCommand.EditPatronDescriptor();
    }

    public EditPatronDescriptorBuilder(EditPatronCommand.EditPatronDescriptor descriptor) {
        this.descriptor = new EditPatronCommand.EditPatronDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPatronDescriptor} with fields containing {@code patron}'s details
     */
    public EditPatronDescriptorBuilder(Patron patron) {
        descriptor = new EditPatronCommand.EditPatronDescriptor();
        descriptor.setName(patron.getName());
        descriptor.setPhone(patron.getPhone());
        descriptor.setEmail(patron.getEmail());
        descriptor.setId(patron.getId());
        descriptor.setTags(patron.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditPatronDescriptor} that we are building.
     */
    public EditPatronDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditPatronDescriptor} that we are building.
     */
    public EditPatronDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditPatronDescriptor} that we are building.
     */
    public EditPatronDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Id} of the {@code EditPatronDescriptor} that we are building.
     */
    public EditPatronDescriptorBuilder withId(String id) {
        descriptor.setId(new Id(id));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPatronDescriptor}
     * that we are building.
     */
    public EditPatronDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditPatronCommand.EditPatronDescriptor build() {
        return descriptor;
    }
}
