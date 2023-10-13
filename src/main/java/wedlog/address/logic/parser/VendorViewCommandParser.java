package wedlog.address.logic.parser;

import static wedlog.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import wedlog.address.commons.core.index.Index;
import wedlog.address.logic.commands.VendorViewCommand;
import wedlog.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input specifically for VendorView commands.
 */
public class VendorViewCommandParser implements Parser<VendorViewCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public VendorViewCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new VendorViewCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, VendorViewCommand.MESSAGE_USAGE), pe);
        }
    }
}
