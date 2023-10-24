package wedlog.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import wedlog.address.commons.util.StringUtil;
import wedlog.address.commons.util.ToStringBuilder;
import wedlog.address.logic.commands.GuestFilterCommand;
import wedlog.address.logic.parser.exceptions.ParseException;

import static wedlog.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Tests that a {@code Guest}'s {@code Rsvp} matches any of the keywords given.
 */
public class GuestRsvpPredicate implements Predicate<Guest> {
    private final List<String> keywords;

    /**
     * Constructor for GuestRsvpPredicate.
     */
    public GuestRsvpPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Guest guest) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(guest.getRsvpStatus().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GuestRsvpPredicate)) {
            return false;
        }

        GuestRsvpPredicate otherRsvpPredicate = (GuestRsvpPredicate) other;
        return keywords.equals(otherRsvpPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
