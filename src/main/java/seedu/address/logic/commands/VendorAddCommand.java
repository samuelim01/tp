package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Vendor;

/**
 * Adds a Vendor to the address book.
 */
public class VendorAddCommand extends Command {
    // implementation more or less copied from AddCommand
    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a vendor to the address book. ";
    public static final String MESSAGE_SUCCESS = "New vendor added: %1$s";

    public VendorAddCommand(Vendor vendor) {
        // temporary empty constructor
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException("Command not created yet, wait for evolve for better testing");
    }
}
