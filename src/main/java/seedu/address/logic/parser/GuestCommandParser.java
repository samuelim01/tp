package seedu.address.logic.parser;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.GuestListCommand;
import seedu.address.logic.commands.GuestDeleteCommand;
import seedu.address.logic.commands.GuestViewCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

public class GuestCommandParser {
    /**
     * Used for initial separation of command word and args.
     */
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
            case GuestAddCommand.COMMAND_WORD:
                return new GuestAddCommandParser().parse(arguments);
            case GuestDeleteCommand.COMMAND_WORD:
                return new GuestDeleteCommandParser().parse(arguments);
            case GuestListCommand.COMMAND_WORD:
                return new GuestListCommand();
            case GuestViewCommand.COMMAND_WORD:
                return new GuestViewCommandParser().parse(arguments);
            default:
                logger.finer("This user input caused a ParseException: " + userInput);
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
