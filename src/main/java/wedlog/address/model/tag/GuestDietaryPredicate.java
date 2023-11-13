package wedlog.address.model.tag;

import java.util.List;
import java.util.function.Predicate;

import wedlog.address.commons.util.ToStringBuilder;
import wedlog.address.model.person.Guest;

/**
 * Tests that a {@code Person}'s {@code Dietary Requirement} matches any of the keywords given.
 */
public class GuestDietaryPredicate implements Predicate<Guest> {
    private final List<String> keywords;

    /**
     * Constructor for GuestDietaryPredicate.
     */
    public GuestDietaryPredicate(List<String> keywords) {
        this.keywords = keywords;
    }


    /**
     * Returns true if ALL keywords provided match at least one of the given {@code Guest}'s dietary requirement(s).
     * Keywords list should never be empty.
     * A keyword that is an empty string should match an empty {@code DietaryRequirement} field.
     * A non-empty keyword will match dietary requirement(s) on an exact match basis.
     */
    @Override
    public boolean test(Guest guest) {
        assert guest != null : "Guest passed to GuestDietaryPredicate should not be null!";
        assert !keywords.isEmpty() : "keywords list for GuestDietaryPredicate should not be empty";
        return keywords.get(0).isEmpty() // If a keyword is empty
                ? guest.getDietaryRequirements().isEmpty() // Return true if DR field is also empty
                // Else if there are no empty keywords
                // Check that all keywords match at least one DR
                : keywords.stream().allMatch(keyword -> guest.getDietaryRequirements().stream()
                    .anyMatch(dr -> DietaryRequirement.isValidDietaryRequirement(keyword)
                        && dr.equals(new DietaryRequirement(keyword))));
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
