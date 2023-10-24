package wedlog.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import wedlog.address.commons.util.StringUtil;
import wedlog.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Vendor}'s {@code Email} matches any of the keywords given.
 */
public class VendorEmailPredicate implements Predicate<Vendor> {
    private final List<String> keywords;

    /**
     * Constructor for GuestAddressPredicate.
     */
    public VendorEmailPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Vendor vendor) {
        return keywords.get(0).isEmpty()
                ? vendor.getAddress().isEmpty()
                : keywords.stream().anyMatch(keyword -> vendor.getAddress()
                .map(a -> StringUtil.containsWordIgnoreCase(a.value, keyword))
                .orElse(false));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VendorEmailPredicate)) {
            return false;
        }

        VendorEmailPredicate otherVendorEmailPredicate = (VendorEmailPredicate) other;
        return keywords.equals(otherVendorEmailPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
