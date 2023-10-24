package wedlog.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import wedlog.address.commons.util.StringUtil;
import wedlog.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Guest}'s {@code DietaryRequirement} matches any of the keywords given.
 */
public class GuestDietaryPredicate implements Predicate<Guest> {
    private final List<String> keywords;

    /**
     * Constructor for GuestDietaryPredicate.
     */
    public GuestDietaryPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Guest guest) {
        return false; // temporary value
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GuestDietaryPredicate)) {
            return false;
        }

        GuestDietaryPredicate otherGuestDietaryPredicate = (GuestDietaryPredicate) other;
        return keywords.equals(otherGuestDietaryPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
