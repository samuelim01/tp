package wedlog.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static wedlog.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static wedlog.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static wedlog.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import wedlog.address.logic.commands.Command;
import wedlog.address.logic.commands.HelpCommand;
import wedlog.address.logic.commands.VendorAddCommand;
import wedlog.address.logic.commands.VendorDeleteCommand;
import wedlog.address.logic.commands.VendorListCommand;
import wedlog.address.logic.commands.VendorViewCommand;
import wedlog.address.logic.parser.exceptions.ParseException;

public class VendorCommandParserTest {
    private VendorCommandParser parser = new VendorCommandParser();

    // test vendor add
    @Test
    public void parseCommand_vendorAdd() throws Exception {
        String input = "add n/vendor p/102391";

        Command command = parser.parseCommand(input);
        assertTrue(command instanceof VendorAddCommand);
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
