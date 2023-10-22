package wedlog.address.model.person;

import java.util.function.Predicate;

import wedlog.address.commons.util.StringUtil;
import wedlog.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Guest}'s {@code Email} matches any of the keyword given.
 */
public class GuestEmailPredicate implements Predicate<Guest> {
    private final String keyword;

    public GuestEmailPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(Guest guest) {
        String guestEmailValue = guest.getEmail().value;
        if (guestEmailValue == null) {
            if (keyword == "") {
                return true;
            }

            return false;
        }

        return StringUtil.containsWordIgnoreCase(guestEmailValue, keyword);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GuestEmailPredicate)) {
            return false;
        }

        GuestEmailPredicate otherGuestEmailPredicate = (GuestEmailPredicate) other;
        return keyword.equals(otherGuestEmailPredicate.keyword);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keyword", keyword).toString();
    }
}
