package wedlog.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import wedlog.address.commons.util.StringUtil;
import wedlog.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class RsvpPredicate implements Predicate<Guest> {
    private final String keyword;

    public RsvpPredicate(String keyword) {
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
        if (!(other instanceof RsvpPredicate)) {
            return false;
        }

        RsvpPredicate otherRsvpPredicate = (RsvpPredicate) other;
        return keyword.equals(otherRsvpPredicate.keyword);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keyword).toString();
    }

//        @Override
//        public boolean equals(Object other) {
//            return other == this // short circuit if same object
//                    || (other instanceof StudentIdPredicate // instanceof handles nulls
//                    && keyword.equals(((StudentIdPredicate) other).keyword)); // state check
//        }

    }
}
