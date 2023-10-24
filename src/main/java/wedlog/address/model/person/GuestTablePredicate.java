package wedlog.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import wedlog.address.commons.util.StringUtil;
import wedlog.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Guest}'s {@code TableNumber} matches any of the keyword given.
 */
public class GuestTablePredicate implements Predicate<Guest> {
    private final List<String> keywords;

    /**
     * Constructor for GuestTablePredicate.
     */
    public GuestTablePredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Guest guest) {
        return keywords.get(0).isEmpty()
                ? guest.getTableNumber().isEmpty()
                : keywords.stream().anyMatch(keyword -> guest.getTableNumber()
                .map(a -> StringUtil.containsWordIgnoreCase(a.value, keyword))
                .orElse(false));
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
        return keywords.equals(otherGuestTablePredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keyword", keywords).toString();
    }
}

