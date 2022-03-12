package seedu.address.testutil;

import static seedu.address.logic.commands.Command.PATRON_COMMAND_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.patron.EditPatronCommand;
import seedu.address.model.patron.Patron;
import seedu.address.model.tag.Tag;

/**
 * A utility class for Patron.
 */
public class PatronUtil {

    /**
     * Returns an add command string for adding the {@code patron}.
     */
    public static String getAddCommand(Patron patron) {
        return PATRON_COMMAND_GROUP + " " + Command.ADD_COMMAND_WORD + " " + getPatronDetails(patron);
    }

    /**
     * Returns the part of command string for the given {@code patron}'s details.
     */
    public static String getPatronDetails(Patron patron) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + patron.getName().fullName + " ");
        sb.append(PREFIX_PHONE + patron.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + patron.getEmail().value + " ");
        sb.append(PREFIX_ID + patron.getId().value + " ");
        patron.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPatronDescriptor}'s details.
     */
    public static String getEditPatronDescriptorDetails(EditPatronCommand.EditPatronDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getId().ifPresent(address -> sb.append(PREFIX_ID).append(address.value).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
