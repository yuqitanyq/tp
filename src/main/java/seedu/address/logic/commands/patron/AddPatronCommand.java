package seedu.address.logic.commands.patron;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.patron.Patron;

/**
 * Adds a patron to LibTask.
 */
public class AddPatronCommand extends Command {

    public static final String MESSAGE_USAGE = PATRON_COMMAND_GROUP + ADD_COMMAND_WORD
            + ": Adds a patron to LibTask. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ID + "ID "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + PATRON_COMMAND_GROUP + ADD_COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ID + "A1234567X"
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney";

    public static final String MESSAGE_SUCCESS = "New patron added: %1$s";
    public static final String MESSAGE_DUPLICATE_PATRON = "This patron already exists in LibTask";

    private final Patron toAdd;

    /**
     * Creates an AddPatronCommand to add the specified {@code Patron}
     */
    public AddPatronCommand(Patron patron) {
        requireNonNull(patron);
        toAdd = patron;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPatron(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PATRON);
        }

        model.addPatron(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddPatronCommand // instanceof handles nulls
                && toAdd.equals(((AddPatronCommand) other).toAdd));
    }
}
