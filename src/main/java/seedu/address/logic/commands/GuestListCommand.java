package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Lists all Guests in the address book to the user.
 */
public class GuestListCommand extends Command {
    public static final String COMMAND_WORD = "list";
    public GuestListCommand() {
        // temporary empty constructor
    }
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException("Command not created yet, wait for evolve for better testing");
    }
}
