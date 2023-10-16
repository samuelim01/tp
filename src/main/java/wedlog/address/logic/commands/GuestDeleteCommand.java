package wedlog.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import wedlog.address.commons.core.index.Index;
import wedlog.address.commons.util.ToStringBuilder;
import wedlog.address.logic.Messages;
import wedlog.address.logic.commands.exceptions.CommandException;
import wedlog.address.model.Model;
import wedlog.address.model.person.Guest;

/**
 * Deletes a Guest from the address book.
 */
public class GuestDeleteCommand extends Command {
    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the guest identified by the index number used in the displayed guest list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_GUEST_SUCCESS = "Deleted Guest: %1$s";

    private final Index targetIndex;

    public GuestDeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Guest> lastShownGuestList = model.getFilteredGuestList();

        if (targetIndex.getZeroBased() >= lastShownGuestList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Guest guestToDelete = lastShownGuestList.get(targetIndex.getZeroBased());
        model.deleteGuest(guestToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_GUEST_SUCCESS, Messages.format(guestToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GuestDeleteCommand)) {
            return false;
        }

        GuestDeleteCommand otherGuestDeleteCommand = (GuestDeleteCommand) other;
        return targetIndex.equals(otherGuestDeleteCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
