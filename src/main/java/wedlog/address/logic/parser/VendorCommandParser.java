package wedlog.address.logic.parser;

import static wedlog.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static wedlog.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import wedlog.address.commons.core.LogsCenter;
import wedlog.address.logic.commands.Command;
import wedlog.address.logic.commands.HelpCommand;
import wedlog.address.logic.commands.VendorAddCommand;
import wedlog.address.logic.commands.VendorDeleteCommand;
import wedlog.address.logic.commands.VendorListCommand;
import wedlog.address.logic.commands.VendorViewCommand;
import wedlog.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input specifically for Vendor commands.
 */
public class VendorCommandParser {

    /**
     * Used for initial separation of command word and args.
     */
    public static final String COMMAND_WORD = "vendor";
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(AddressBookParser.class);

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim()); // trims the back and end of the string
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }
        // any way the "vendor command is removed, should have the same format as previous"

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        // Note to developers: Change the log level in config.json to enable lower level (i.e., FINE, FINER and lower)
        // log messages such as the one below.
        // Lower level log messages are used sparingly to minimize noise in the code.
        logger.fine("Command word: " + commandWord + "; Arguments: " + arguments);

        switch (commandWord) {
        case VendorAddCommand.COMMAND_WORD:
            return new VendorAddCommandParser().parse(arguments);
        case VendorDeleteCommand.COMMAND_WORD:
            return new VendorDeleteCommandParser().parse(arguments);
        case VendorListCommand.COMMAND_WORD:
            return new VendorListCommand();
        case VendorViewCommand.COMMAND_WORD:
            return new VendorViewCommandParser().parse(arguments);
        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
