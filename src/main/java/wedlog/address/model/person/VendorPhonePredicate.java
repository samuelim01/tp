package wedlog.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import wedlog.address.commons.util.StringUtil;
import wedlog.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Vendor}'s {@code Phone} matches any of the keywords given.
 */
public class VendorPhonePredicate implements Predicate<Vendor> {
    private final List<String> keywords;

    /**
     * Constructor for VendorPhonePredicate.
     */
    public VendorPhonePredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Vendor vendor) {
        return keywords.get(0).isEmpty()
                ? vendor.getPhone().isEmpty()
                : keywords.stream().anyMatch(keyword -> vendor.getPhone()
                .map(a -> StringUtil.containsWordIgnoreCase(a.value, keyword))
                .orElse(false));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VendorPhonePredicate)) {
            return false;
        }

        VendorPhonePredicate otherVendorPhonePredicate = (VendorPhonePredicate) other;
        return keywords.equals(otherVendorPhonePredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
