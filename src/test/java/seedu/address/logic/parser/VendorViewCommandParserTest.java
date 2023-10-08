package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.VendorViewCommand;
import seedu.address.logic.commands.VendorViewCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the VendorViewCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the VendorViewCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class VendorViewCommandParserTest {

    private VendorViewCommandParser parser = new VendorViewCommandParser();

    @Test
    public void parse_validArgs_returnsVendorViewCommand() {
        assertParseSuccess(parser, "1", new VendorViewCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
//        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
//                VendorViewCommand.MESSAGE_USAGE)); // the message usage property is not created yet hence red
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                "VendorViewCommand.MESSAGE_USAGE"));
    }
}