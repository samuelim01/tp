package wedlog.address.model.person;

import java.util.function.Predicate;

import wedlog.address.commons.util.StringUtil;
import wedlog.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Guest}'s {@code Rsvp} matches any of the keywords given.
 */
public class GuestRsvpPredicate implements Predicate<Guest> {
    private final String keyword;

    public GuestRsvpPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(Guest guest) {
        return StringUtil.containsWordIgnoreCase(guest.getRsvpStatus().value, keyword);
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
        return keyword.equals(otherRsvpPredicate.keyword);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keyword).toString();
    }
}
