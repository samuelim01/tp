package wedlog.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import wedlog.address.commons.util.StringUtil;
import wedlog.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Guest}'s {@code Phone} matches any of the keywords given.
 */
public class GuestPhonePredicate implements Predicate<Guest> {
    private final List<String> keywords;

    /**
     * Constructor for GuestPhonePredicate.
     */
    public GuestPhonePredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Guest guest) {
        return keywords.get(0).isEmpty()
                ? guest.getPhone().isEmpty()
                : keywords.stream().anyMatch(keyword -> guest.getPhone()
                .map(a -> StringUtil.containsWordIgnoreCase(a.value, keyword))
                .orElse(false));
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
        return keywords.equals(otherGuestPhonePredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
