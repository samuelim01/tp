package wedlog.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_NAME;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_TAG;
import static wedlog.address.logic.parser.VendorCommandParser.VENDOR_COMMAND_WORD;

import java.util.List;
import java.util.function.Predicate;

import wedlog.address.commons.util.ToStringBuilder;
import wedlog.address.logic.Messages;
import wedlog.address.model.Model;
import wedlog.address.model.person.Vendor;

/**
 * Filters and lists all Vendors in address book whose fields contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class VendorFilterCommand extends Command {
    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all vendors whose fields contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + VENDOR_COMMAND_WORD + " " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_TAG + "caterer";

    private final List<Predicate<? super Vendor>> predicates;

    /**
     * Creates a VendorFilterCommand which filters according to {@code predicates}
     */
    public VendorFilterCommand(List<Predicate<? super Vendor>> predicates) {
        assert predicates != null : "Predicates passed to VendorFilterCommand should not be null!";
        this.predicates = predicates;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        model.updateFilteredVendorList(preparePredicate(predicates));
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
        return predicates.equals(otherFilterCommand.predicates);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicates", predicates)
                .toString();
    }

    /**
     * Creates a predicate which returns true if all predicates return true,
     * and false otherwise.
     */
    private Predicate<Vendor> preparePredicate(List<Predicate<? super Vendor>> predicates) {
        // all match here make it that all parameters have an AND relationship
        return vendor -> predicates.stream().allMatch(predicate -> predicate.test(vendor));
    }
}
