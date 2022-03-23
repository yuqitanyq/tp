package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CommandResultTest {
    @Test
    public void equals() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns true
        assertTrue(commandResult.equals(new CommandResult("feedback")));
        assertTrue(commandResult.equals(new CommandResult("feedback", false, false, false)));

        // same object -> returns true
        assertTrue(commandResult.equals(commandResult));

        // null -> returns false
        assertFalse(commandResult.equals(null));

        // different types -> returns false
        assertFalse(commandResult.equals(0.5f));

        // different feedbackToUser value -> returns false
        assertFalse(commandResult.equals(new CommandResult("different")));

        // different showHelp value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", true, false, false)));

        // different exit value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", false, true, false)));
    }

    @Test
    public void test_isShowHelp_returnsTrue() {
        CommandResult commandResult = new CommandResult("Opened help window.", true, false, false);
        assertTrue(commandResult.isShowHelp());
    }

    @Test
    public void test_isShowHelp_returnFalse() {
        CommandResult commandResult = new CommandResult("feedback", false, false, false);
        assertFalse(commandResult.isShowHelp());
    }

    @Test
    public void test_isExit_returnsTrue() {
        CommandResult commandResult = new CommandResult("Exiting LibTask as requested ...", false,
                true, false);
        assertTrue(commandResult.isExit());
    }

    @Test
    public void test_isExit_returnFalse() {
        CommandResult commandResult = new CommandResult("feedback", false, false, false);
        assertFalse(commandResult.isExit());
    }

    @Test
    public void test_isPrevious_returnsTrue() {
        CommandResult commandResult = new CommandResult("Here is your previous command.", false,
                false, true);
        assertTrue(commandResult.isPrevious());
    }

    @Test
    public void test_isPrevious_returnFalse() {
        CommandResult commandResult = new CommandResult("feedback", false, false, false);
        assertFalse(commandResult.isPrevious());
    }

    @Test
    public void hashcode() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns same hashcode
        assertEquals(commandResult.hashCode(), new CommandResult("feedback").hashCode());

        // different feedbackToUser value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("different").hashCode());

        // different showHelp value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", true, false, false).hashCode());

        // different exit value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", false, true, false).hashCode());
    }
}
