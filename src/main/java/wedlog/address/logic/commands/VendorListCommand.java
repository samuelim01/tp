package wedlog.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static wedlog.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import wedlog.address.model.Model;

/**
 * Lists all Vendors in the address book to the user.
 */
public class VendorListCommand extends Command {
    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all Vendors. ";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredVendorList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
