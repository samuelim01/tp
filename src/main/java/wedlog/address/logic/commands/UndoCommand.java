package wedlog.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static wedlog.address.model.Model.PREDICATE_SHOW_ALL_GUESTS;
import static wedlog.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static wedlog.address.model.Model.PREDICATE_SHOW_ALL_VENDORS;

import wedlog.address.logic.commands.exceptions.CommandException;
import wedlog.address.model.Model;

//@@author samuelim01-reused
// Reused from Address Book(Level 4) with minor modifications.

/**
 * Restores the {@code Model}'s address book to its previous state.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Undoes the most recent change to WedLog. ";

    public static final String MESSAGE_SUCCESS = "Undo successful.";
    public static final String MESSAGE_FAILURE = "There is no change to undo!";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!model.canUndoAddressBook()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.undoAddressBook();
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.updateFilteredGuestList(PREDICATE_SHOW_ALL_GUESTS);
        model.updateFilteredVendorList(PREDICATE_SHOW_ALL_VENDORS);
        return new CommandResult(MESSAGE_SUCCESS);
    }

}

//@@author
