package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * View's a specific vendor in the address book to the user.
 */
public class VendorViewCommand extends Command {
    public static final String COMMAND_WORD = "view";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Views the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public VendorViewCommand(Index index) {
        // temporary empty constructor
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        return null; // temporary returns a null
    }
}
