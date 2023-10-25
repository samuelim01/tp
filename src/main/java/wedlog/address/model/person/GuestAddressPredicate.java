package wedlog.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import wedlog.address.commons.util.StringUtil;
import wedlog.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Guest}'s {@code Address} matches any of the keywords given.
 */
public class GuestAddressPredicate implements Predicate<Guest> {
    private final List<String> keywords;

    /**
     * Constructor for GuestAddressPredicate.
     */
    public GuestAddressPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Guest guest) {
        // if keyword is empty, the only time it matches is when val is empty
        // but if keyword not empty, check if it matches? => returns false
        return !keywords.isEmpty() && keywords.get(0).isEmpty()
                ? guest.getAddress().isEmpty()
                : keywords.stream().anyMatch(keyword -> guest.getAddress()
                        .map(a -> StringUtil.containsWordIgnoreCase(a.value, keyword))
                        .orElse(false));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GuestAddressPredicate)) {
            return false;
        }

        GuestAddressPredicate otherGuestAddressPredicate = (GuestAddressPredicate) other;
        return keywords.equals(otherGuestAddressPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
