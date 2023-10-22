package wedlog.address.model.person;

import java.util.function.Predicate;

import wedlog.address.commons.util.StringUtil;
import wedlog.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Vendor}'s {@code Phone} matches any of the keyword given.
 */
public class VendorPhonePredicate implements Predicate<Vendor> {
    private final String keyword;

    public VendorPhonePredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(Vendor vendor) {
        String vendorPhoneValue = vendor.getPhone().value;
        if (vendorPhoneValue == null) {
            if (keyword == "") {
                return true;
            }

            return false;
        }

        return StringUtil.containsWordIgnoreCase(vendorPhoneValue, keyword);
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
        return keyword.equals(otherVendorPhonePredicate.keyword);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keyword", keyword).toString();
    }
}
