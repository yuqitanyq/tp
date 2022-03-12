package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.Command.PATRON_COMMAND_GROUP;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PATRON;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.patron.AddPatronCommand;
import seedu.address.logic.commands.patron.DeletePatronCommand;
import seedu.address.logic.commands.patron.EditPatronCommand;
import seedu.address.logic.commands.patron.EditPatronCommand.EditPatronDescriptor;
import seedu.address.logic.commands.patron.FindPatronCommand;
import seedu.address.logic.commands.patron.ListPatronCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.patron.NameContainsKeywordsPredicate;
import seedu.address.model.patron.Patron;
import seedu.address.testutil.EditPatronDescriptorBuilder;
import seedu.address.testutil.PatronBuilder;
import seedu.address.testutil.PatronUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Patron patron = new PatronBuilder().build();
        AddPatronCommand command = (AddPatronCommand) parser.parseCommand(PatronUtil.getAddCommand(patron));
        assertEquals(new AddPatronCommand(patron), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeletePatronCommand command = (DeletePatronCommand) parser.parseCommand(
                PATRON_COMMAND_GROUP + " " + Command.DELETE_COMMAND_WORD + " "
                        + INDEX_FIRST_PATRON.getOneBased());
        assertEquals(new DeletePatronCommand(INDEX_FIRST_PATRON), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Patron patron = new PatronBuilder().build();
        EditPatronDescriptor descriptor = new EditPatronDescriptorBuilder(patron).build();
        EditPatronCommand command = (EditPatronCommand) parser.parseCommand(PATRON_COMMAND_GROUP + " "
                + Command.EDIT_COMMAND_WORD + " "
                + INDEX_FIRST_PATRON.getOneBased() + " " + PatronUtil.getEditPatronDescriptorDetails(descriptor));
        assertEquals(new EditPatronCommand(INDEX_FIRST_PATRON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindPatronCommand command = (FindPatronCommand) parser.parseCommand(
                PATRON_COMMAND_GROUP + " " + Command.FIND_COMMAND_WORD + " "
                        + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindPatronCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(PATRON_COMMAND_GROUP + " "
                + Command.LIST_COMMAND_WORD) instanceof ListPatronCommand);
        assertTrue(parser.parseCommand(PATRON_COMMAND_GROUP + " "
                + Command.LIST_COMMAND_WORD + " 3") instanceof ListPatronCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () ->
            parser.parseCommand("unknownCommand"));
    }
}
