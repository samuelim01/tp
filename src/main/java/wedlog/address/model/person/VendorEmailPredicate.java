package wedlog.address.model.person;

import java.util.function.Predicate;

import wedlog.address.commons.util.StringUtil;
import wedlog.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Vendor}'s {@code Email} matches any of the keyword given.
 */
public class VendorEmailPredicate implements Predicate<Vendor> {
    private final String keyword;

    public VendorEmailPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(Vendor vendor) {
        String VendorEmailValue = vendor.getEmail().value;
        if (VendorEmailValue == null) {
            if (keyword == "") {
                return true;
            }

            return false;
        }

        return StringUtil.containsWordIgnoreCase(VendorEmailValue, keyword);
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
        return keyword.equals(otherVendorEmailPredicate.keyword);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keyword", keyword).toString();
    }
}
