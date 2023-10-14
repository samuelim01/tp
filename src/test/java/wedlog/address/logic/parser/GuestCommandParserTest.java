package wedlog.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static wedlog.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static wedlog.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static wedlog.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import wedlog.address.logic.commands.Command;
import wedlog.address.logic.commands.GuestDeleteCommand;
import wedlog.address.logic.commands.GuestListCommand;
import wedlog.address.logic.commands.GuestViewCommand;
import wedlog.address.logic.commands.HelpCommand;
import wedlog.address.logic.parser.exceptions.ParseException;

public class GuestCommandParserTest {
    private static final String GUEST_ADD_PARSE_EXCEPTION_MSG =
            "Guest not created in GuestAddCommand due to un-evolved classes";
    private GuestCommandParser parser = new GuestCommandParser();

    @Test
    public void parseCommand_guestAdd() throws Exception {
        String input = "add n/guest p/102391";
        // expected type, expected string, executable
        assertThrows(ParseException.class, GUEST_ADD_PARSE_EXCEPTION_MSG, () -> parser.parseCommand(input));

        // test for when Guest/Vendor/Person is evolved
        // Command command = parser.parseCommand(input);
        // assertTrue(command instanceof GuestAddCommand);
    }

    @Test
    public void parseCommand_guestDelete() throws Exception {
        String input = "delete 1";
        Command command = parser.parseCommand(input);
        assertTrue(command instanceof GuestDeleteCommand);
    }

    @Test
    public void parseCommand_guestList() throws Exception {
        assertTrue(parser.parseCommand("list") instanceof GuestListCommand);
    }

    @Test
    public void parseCommand_view() throws Exception {
        assertTrue(parser.parseCommand("view 3") instanceof GuestViewCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
                -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
