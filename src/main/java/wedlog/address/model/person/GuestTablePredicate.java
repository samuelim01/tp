package wedlog.address.model.person;

import java.util.function.Predicate;

import wedlog.address.commons.util.StringUtil;
import wedlog.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Guest}'s {@code TableNumber} matches any of the keyword given.
 */
public class GuestTablePredicate implements Predicate<Guest> {
    private final String keyword;

    public GuestTablePredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(Guest guest) {
        String guestTableNumValue = guest.getTableNumber().value;
        if (guestTableNumValue == null) {
            if (keyword == "") {
                return true;
            }

            return false;
        }

        return StringUtil.containsWordIgnoreCase(guestTableNumValue, keyword);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GuestTablePredicate)) {
            return false;
        }

        GuestTablePredicate otherGuestTablePredicate = (GuestTablePredicate) other;
        return keyword.equals(otherGuestTablePredicate.keyword);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keyword", keyword).toString();
    }
}

