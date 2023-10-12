package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Deletes a Vendor from the address book.
 */
public class VendorDeleteCommand extends Command { // this is still the old DeleteCommand Implementation
    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";

    // private final Index targetIndex;

    public VendorDeleteCommand(Index targetIndex) {
        // temporary empty constructor
        // this.targetIndex = targetIndex;
    }
    public CommandResult execute(Model model) throws CommandException {
        return null; // temporary returns a null
    }
}
