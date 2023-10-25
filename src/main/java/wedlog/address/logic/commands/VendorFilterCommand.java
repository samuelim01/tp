package wedlog.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_NAME;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static wedlog.address.logic.parser.VendorCommandParser.VENDOR_COMMAND_WORD;

import java.util.function.Predicate;

import wedlog.address.commons.util.ToStringBuilder;
import wedlog.address.logic.Messages;
import wedlog.address.model.Model;
import wedlog.address.model.person.Vendor;

/**
 * Filters and lsits all Vendors in address book whose fields contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class VendorFilterCommand extends Command {
    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all vendors whose fields contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS\n"
            + "Example: " + VENDOR_COMMAND_WORD + " " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 ";

    private final Predicate<? super Vendor> predicate;

    public VendorFilterCommand(Predicate<? super Vendor> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        model.updateFilteredVendorList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_VENDORS_LISTED_OVERVIEW, model.getFilteredVendorList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VendorFilterCommand)) {
            return false;
        }

        VendorFilterCommand otherFilterCommand = (VendorFilterCommand) other;
        return predicate.equals(otherFilterCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
