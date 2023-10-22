package wedlog.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import wedlog.address.commons.util.StringUtil;
import wedlog.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Guest}'s {@code DietaryRequirement} matches any of the keywords given.
 */
public class GuestDietaryPredicate implements Predicate<Guest> {
    private final List<String> keywords;

    public GuestDietaryPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Guest guest) {
        String dietaryRequirements = guest.getDietaryRequirements().value;

        // none values
        if (keywords.get(0) == "") {
            if (dietaryRequirements == "") {
                return true;
            }
            return false;
        }

        // null values
        if (dietaryRequirements == null) {
            // check if user input null, signalling that they want to filter dietary requirements that are null value
            return keywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase("null", keyword));
        }


        // filled values
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(dietaryRequirements, keyword));
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
