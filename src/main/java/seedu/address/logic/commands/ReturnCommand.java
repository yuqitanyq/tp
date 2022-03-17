package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_BOOK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATRON;

public abstract class ReturnCommand extends Command {

    public static final String MESSAGE_USAGE = RETURN_COMMAND_WORD
            + ": Returns all books borrowed by a patron identified by the index number in the displayed patron list,"
            + " or return a specific book identified by the index number in the displayed book list.\n"
            + "Parameters: "
            + "PREFIX \"" + PREFIX_PATRON + "\" (return all books by patron) or \""
            + PREFIX_BOOK + "\" (return a specific book) "
            + "INDEX (must be a positive integer)\n"
            + "Example: " + RETURN_COMMAND_WORD + " "
            + PREFIX_PATRON + "1";

}
