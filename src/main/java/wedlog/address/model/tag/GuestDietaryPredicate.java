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
     * Checks if the Dietary Requirement(s) of the Guest parameter matches ALL keywords.
     * Empty keyword should match empty dietary requirement field.
     * Otherwise, matching should be conducted on exact match basis.
     */
    @Override
    public boolean test(Guest guest) {
        return !keywords.isEmpty() && keywords.get(0).isEmpty()
                ? guest.getDietaryRequirements().isEmpty()
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
