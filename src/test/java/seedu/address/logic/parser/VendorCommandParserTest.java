package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.VendorDeleteCommand;
import seedu.address.logic.commands.VendorListCommand;
import seedu.address.logic.commands.VendorViewCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class VendorCommandParserTest {
    private static final String VENDOR_ADD_PARSE_EXCEPTION_MSG =
            "Vendor not created in VendorAddCommand due to un-evolved classes";
    private VendorCommandParser parser = new VendorCommandParser();

    // test vendor add
    @Test
    public void parseCommand_vendorAdd() throws Exception {
        String input = "add n/vendor p/102391";
        // expected type, expected string, executable
        assertThrows(ParseException.class, VENDOR_ADD_PARSE_EXCEPTION_MSG, () -> parser.parseCommand(input));

        // test for when Guest/Vendor/Person is evolved
        // Command command = parser.parseCommand(input);
        // assertTrue(command instanceof VendorAddCommand);
    }

    @Test
    public void parseCommand_vendorDelete() throws Exception {
        String input = "delete 1";
        Command command = parser.parseCommand(input);
        assertTrue(command instanceof VendorDeleteCommand);
    }

    @Test
    public void parseCommand_vendorList() throws Exception {
        assertTrue(parser.parseCommand("list") instanceof VendorListCommand);
    }

    @Test
    public void parseCommand_view() throws Exception {
        assertTrue(parser.parseCommand("view 3") instanceof VendorViewCommand);
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
