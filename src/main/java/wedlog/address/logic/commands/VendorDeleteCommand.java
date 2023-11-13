package wedlog.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static wedlog.address.logic.parser.VendorCommandParser.VENDOR_COMMAND_WORD;

import java.util.List;
import java.util.logging.Logger;

import wedlog.address.commons.core.LogsCenter;
import wedlog.address.commons.core.index.Index;
import wedlog.address.commons.util.ToStringBuilder;
import wedlog.address.logic.Messages;
import wedlog.address.logic.commands.exceptions.CommandException;
import wedlog.address.model.Model;
import wedlog.address.model.person.Vendor;

/**
 * Deletes a Vendor from the address book.
 */
public class VendorDeleteCommand extends Command {
    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = VENDOR_COMMAND_WORD + " " + COMMAND_WORD
            + ": Deletes the vendor identified by the index number used in the displayed vendor list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + VENDOR_COMMAND_WORD + " " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_VENDOR_SUCCESS = "Deleted vendor: %1$s";

    private final Index targetIndex;

    private final Logger logger = LogsCenter.getLogger(getClass());

    /**
     * Creates a {@code VendorDeleteCommand} with the given {@code Index}.
     *
     * @param targetIndex index of the vendor in the filtered vendor list to delete
     */
    public VendorDeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Vendor> lastShownVendorList = model.getFilteredVendorList();

        if (targetIndex.getZeroBased() >= lastShownVendorList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_VENDOR_DISPLAYED_INDEX);
        }

        Vendor vendorToDelete = lastShownVendorList.get(targetIndex.getZeroBased());
        logger.fine("Deleted Vendor: " + vendorToDelete);

        model.deleteVendor(vendorToDelete);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_DELETE_VENDOR_SUCCESS, Messages.format(vendorToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VendorDeleteCommand)) {
            return false;
        }

        VendorDeleteCommand otherVendorDeleteCommand = (VendorDeleteCommand) other;
        return targetIndex.equals(otherVendorDeleteCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
