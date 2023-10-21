package wedlog.address.logic.commands;

import wedlog.address.commons.util.ToStringBuilder;
import wedlog.address.logic.Messages;
import wedlog.address.model.Model;
import wedlog.address.model.person.NameContainsKeywordsPredicate;

import static java.util.Objects.requireNonNull;

/**
 * Filters and lsits all Guests in address book whose fields contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class GuestFilterCommand extends Command {
    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all guests whose fields contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: specifier/KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: guest" + COMMAND_WORD + " n/alice d/seafood";

    private final NameContainsKeywordsPredicate predicate;

    public GuestFilterCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        while () {

        }
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
