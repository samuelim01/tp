package wedlog.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import wedlog.address.commons.util.StringUtil;
import wedlog.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Guest}'s {@code Email} matches any of the keywords given.
 */
public class GuestEmailPredicate implements Predicate<Guest> {
    private final List<String> keywords;

    /**
     * Constructor for GuestEmailPredicate.
     */
    public GuestEmailPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Guest guest) {
        return !keywords.isEmpty() && keywords.get(0).isEmpty()
                ? guest.getEmail().isEmpty()
                : keywords.stream().anyMatch(keyword -> guest.getEmail()
                    .map(a -> StringUtil.containsWordIgnoreCase(a.value, keyword))
                    .orElse(false));
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
        return keywords.equals(otherGuestEmailPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
