package wedlog.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_DIETARY;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_NAME;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_RSVP;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_TABLE;
import static wedlog.address.logic.parser.GuestCommandParser.GUEST_COMMAND_WORD;

import java.util.List;
import java.util.function.Predicate;

import wedlog.address.commons.util.ToStringBuilder;
import wedlog.address.logic.Messages;
import wedlog.address.model.Model;
import wedlog.address.model.person.Guest;

/**
 * Filters and lsits all Guests in address book whose fields contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class GuestFilterCommand extends Command {
    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all guests whose fields contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_RSVP + "RSVP "
            + PREFIX_DIETARY + "DIETARY "
            + PREFIX_TABLE + "TABLE_NUMBER\n"
            + "Example: " + GUEST_COMMAND_WORD + " " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_RSVP + "yes "
            + PREFIX_DIETARY + "vegetarian "
            + PREFIX_TABLE + "13";

    private final List<Predicate<? super Guest>> predicates;

    public GuestFilterCommand(List<Predicate<? super Guest>> predicates) {
        this.predicates = predicates;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        model.updateFilteredGuestList(preparePredicate(predicates));
        return new CommandResult(
                String.format(Messages.MESSAGE_GUESTS_LISTED_OVERVIEW, model.getFilteredGuestList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GuestFilterCommand)) {
            return false;
        }

        GuestFilterCommand otherFilterCommand = (GuestFilterCommand) other;
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
    private Predicate<Guest> preparePredicate(List<Predicate<? super Guest>> predicates) {
        return guest -> predicates.stream().allMatch(predicate -> predicate.test(guest));
    }
}
