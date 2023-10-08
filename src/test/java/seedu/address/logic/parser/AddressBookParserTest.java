package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    // test for vendor
    @Test
    public void parseCommand_vendor() throws Exception {
        String input = "vendor list"; // a valid command
        // no split between Vendor & Guest type, so just test that the output is a command
        Command command = parser.parseCommand(input);
        assertEquals(command instanceof Command, true);
    }

    // test for guest
    @Test
    public void parseCommand_guest() throws Exception {
        String input = "guest list"; // a valid command
        // no split between Vendor & Guest type, so just test that the output is a command
        Command command = parser.parseCommand(input);
        assertEquals(command instanceof Command, true);
    }

    // test for unrecognised input -> invalid command format
    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    // test for unknown command
    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
