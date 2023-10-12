package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Adds a Vendor to the address book.
 */
public class VendorAddCommand extends Command {
    // implementation more or less copied from AddCommand
    public static final String COMMAND_WORD = "vendor add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to the address book. ";
    public static final String MESSAGE_SUCCESS = "New person added: %1$s";

    public VendorAddCommand(Person person) {
        // temporary empty constructor
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return null; // temporary returns a null
    }
}
