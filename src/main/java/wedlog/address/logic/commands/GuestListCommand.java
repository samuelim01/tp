package wedlog.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static wedlog.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import wedlog.address.model.Model;

/**
 * Lists all Guests in the address book to the user.
 */
public class GuestListCommand extends Command {
    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all Guests";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredGuestList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
