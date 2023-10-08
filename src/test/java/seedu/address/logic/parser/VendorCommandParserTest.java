package seedu.address.logic.parser;

import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.*;
import seedu.address.logic.parser.exceptions.ParseException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;

public class VendorCommandParserTest {
    VendorCommandParser parser = new VendorCommandParser();

    // test vendor add
    @Test
    public void parseCommand_vendorAdd() throws Exception {
        String input = "vendor add /nvendor /p102391";
        Command command = parser.parseCommand(input);
        assertTrue(command instanceof VendorAddCommand);
    }

    // test vendor delete
    @Test
    public void parseCommand_vendorDelete() throws Exception {
        String input = "vendor delete 1";
        Command command = parser.parseCommand(input);
        assertTrue(command instanceof VendorDeleteCommand);
    }

    // test vendor list
    @Test
    public void parseCommand_vendorList() throws Exception {
        assertTrue(parser.parseCommand("vendor list") instanceof VendorListCommand);
    }

    // test vendor view
    @Test
    public void parseCommand_view() throws Exception {
        assertTrue(parser.parseCommand("vendor view 3") instanceof VendorViewCommand);
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
