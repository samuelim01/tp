package wedlog.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import wedlog.address.commons.util.StringUtil;
import wedlog.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Vendor}'s {@code Address} matches any of the keywords given.
 */
public class VendorAddressPredicate implements Predicate<Vendor> {
    private final List<String> keywords;

    public VendorAddressPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Vendor vendor) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(vendor.getAddress().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VendorAddressPredicate)) {
            return false;
        }

        VendorAddressPredicate otherVendorAddressPredicate = (VendorAddressPredicate) other;
        return keywords.equals(otherVendorAddressPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
