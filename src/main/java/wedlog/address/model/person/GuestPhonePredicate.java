package wedlog.address.model.person;

import java.util.function.Predicate;

import wedlog.address.commons.util.StringUtil;
import wedlog.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Guest}'s {@code Phone} matches any of the keyword given.
 */
public class GuestPhonePredicate implements Predicate<Guest> {
    private final String keyword;

    public GuestPhonePredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(Guest guest) {
        String guestPhoneValue = guest.getPhone().value;
        if (guestPhoneValue == null) {
            if (keyword == "") {
                return true;
            }

            return false;
        }

        return StringUtil.containsWordIgnoreCase(guestPhoneValue, keyword);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GuestPhonePredicate)) {
            return false;
        }

        GuestPhonePredicate otherGuestPhonePredicate = (GuestPhonePredicate) other;
        return keyword.equals(otherGuestPhonePredicate.keyword);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keyword", keyword).toString();
    }
}
