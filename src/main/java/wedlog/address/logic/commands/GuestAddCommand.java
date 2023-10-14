package wedlog.address.logic.commands;

import wedlog.address.logic.commands.exceptions.CommandException;
import wedlog.address.model.Model;
import wedlog.address.model.person.Guest;

/**
 * Adds a Guest to the address book.
 */
public class GuestAddCommand extends Command {
    // below implementation more or less copied from AddCommand
    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a guest to the address book. ";
    public static final String MESSAGE_SUCCESS = "New guest added: %1$s";

    public GuestAddCommand(Guest guest) {
        // temporary empty constructor
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException("Command not created yet, wait for evolve for better testing");
    }
}
