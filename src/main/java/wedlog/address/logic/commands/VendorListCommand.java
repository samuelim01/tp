package wedlog.address.logic.commands;

import wedlog.address.logic.commands.exceptions.CommandException;
import wedlog.address.model.Model;

/**
 * Lists all Vendors in the address book to the user.
 */
public class VendorListCommand extends Command {
    public static final String COMMAND_WORD = "list";
    public VendorListCommand() {
        // temporary empty constructor
    }
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException("Command not created yet, wait for evolve for better testing");
    }
}
