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
            + ": Deletes the vendor identified by the index number used in the displayed vendor list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_VENDOR_SUCCESS = "Deleted Vendor: %1$s";

    // private final Index targetIndex;

    public VendorDeleteCommand(Index targetIndex) {
        // temporary empty constructor
        // this.targetIndex = targetIndex;
    }
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException("Command not created yet, wait for evolve for better testing");
    }
}
