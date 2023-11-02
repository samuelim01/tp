package wedlog.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_NAME;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_TAG;
import static wedlog.address.logic.parser.VendorCommandParser.VENDOR_COMMAND_WORD;

import wedlog.address.commons.util.ToStringBuilder;
import wedlog.address.logic.Messages;
import wedlog.address.logic.commands.exceptions.CommandException;
import wedlog.address.model.Model;
import wedlog.address.model.person.Vendor;

/**
 * Adds a Vendor to Wedlog.
 */
public class VendorAddCommand extends Command {
    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = VENDOR_COMMAND_WORD + " " + COMMAND_WORD
            + ": Adds a vendor to the address book. "
            + "Compulsory Parameters: "
            + PREFIX_NAME + "NAME "
            + "Optional Parameters: "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + VENDOR_COMMAND_WORD + " " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_TAG + "florist";
    public static final String MESSAGE_SUCCESS = "New vendor added: %1$s";
    public static final String MESSAGE_DUPLICATE_VENDOR = "This vendor already exists in WedLog.";

    private final Vendor toAdd;

    /**
     * Adds a Vendor to WedLog.
     */
    public VendorAddCommand(Vendor vendor) {
        requireNonNull(vendor);
        this.toAdd = vendor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasVendor(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_VENDOR);
        }

        model.addVendor(toAdd);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // checks that Object is a VendorAddCommand
        if (!(other instanceof VendorAddCommand)) {
            return false;
        }

        VendorAddCommand otherCommand = (VendorAddCommand) other;
        return toAdd.equals(otherCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAddVendor", toAdd)
                .toString();
    }
}
