package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.VendorDeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the VendorDeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the VendorDeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class VendorDeleteCommandParserTest {

    private VendorDeleteCommandParser parser = new VendorDeleteCommandParser();

    @Test
    public void parse_validArgs_returnsVendorDeleteCommand() throws ParseException {
        assertTrue(parser.parse("1") instanceof VendorDeleteCommand);

        // code below invalid due to un-evolved VendorDeleteCommand (equals method)
        // assertParseSuccess(parser, "1", new VendorDeleteCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                VendorDeleteCommand.MESSAGE_USAGE));
    }
}
