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
 * Restores the {@code Model}'s address book to its previous state before undo.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Reverses the most recent undo command to WedLog. ";

    public static final String MESSAGE_SUCCESS = "Redo successful.";
    public static final String MESSAGE_FAILURE = "There is no change to redo!";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!model.canRedoAddressBook()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.redoAddressBook();
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.updateFilteredGuestList(PREDICATE_SHOW_ALL_GUESTS);
        model.updateFilteredVendorList(PREDICATE_SHOW_ALL_VENDORS);
        return new CommandResult(MESSAGE_SUCCESS);
    }

}

//@author
