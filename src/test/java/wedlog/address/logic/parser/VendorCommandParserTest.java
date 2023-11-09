package wedlog.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static wedlog.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static wedlog.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static wedlog.address.testutil.Assert.assertThrows;
import static wedlog.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import wedlog.address.logic.commands.Command;
import wedlog.address.logic.commands.HelpCommand;
import wedlog.address.logic.commands.VendorAddCommand;
import wedlog.address.logic.commands.VendorDeleteCommand;
import wedlog.address.logic.commands.VendorEditCommand;
import wedlog.address.logic.commands.VendorFilterCommand;
import wedlog.address.logic.commands.VendorListCommand;
import wedlog.address.logic.parser.exceptions.ParseException;
import wedlog.address.model.person.Vendor;
import wedlog.address.testutil.EditVendorDescriptorBuilder;
import wedlog.address.testutil.VendorBuilder;
import wedlog.address.testutil.VendorUtil;

public class VendorCommandParserTest {
    private VendorCommandParser parser = new VendorCommandParser();

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
    public void parseCommand_vendorFilter() throws Exception {
        assertTrue(parser.parseCommand("filter n/name") instanceof VendorFilterCommand);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Vendor vendor = new VendorBuilder().build();
        VendorEditCommand.EditVendorDescriptor descriptor = new EditVendorDescriptorBuilder(vendor).build();
        VendorEditCommand command = (VendorEditCommand) parser.parseCommand(VendorEditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + VendorUtil.getEditVendorDescriptorDetails(descriptor));
        assertEquals(new VendorEditCommand(INDEX_FIRST_PERSON, descriptor), command);
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
