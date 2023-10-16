package wedlog.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Simple test class for DietaryRequirements.
 *
 * @author Keagan
 * @version v1.2
 */
public class DietaryRequirementsTest {

    @Test
    public void equals() {
        DietaryRequirements dr = new DietaryRequirements("Halal");

        // same object -> returns true
        assertTrue(dr.equals(dr));

        // same values -> returns true
        DietaryRequirements drCopy = new DietaryRequirements(dr.value);
        assertTrue(dr.equals(drCopy));

        // different types -> returns false
        assertFalse(dr.equals(1));

        // null -> returns false
        assertFalse(dr.equals(null));

        // different dr -> returns false
        DietaryRequirements differentDr = new DietaryRequirements("Vegan");
        assertFalse(dr.equals(differentDr));

        // same none value -> returns true
        DietaryRequirements noneDr = new DietaryRequirements("");
        DietaryRequirements noneDrCopy = new DietaryRequirements("");
        assertTrue(noneDr.equals(noneDrCopy));

        // same null value -> returns true
        DietaryRequirements nullDr = new DietaryRequirements(null);
        DietaryRequirements nullDrCopy = new DietaryRequirements(null);
        assertTrue(noneDr.equals(noneDrCopy));

        // different status -> returns false
        assertFalse(noneDr.equals(nullDrCopy));
    }

    @Test
    void isNoneDietaryRequirement() {
        DietaryRequirements dr = new DietaryRequirements("Halal");
        assertFalse(dr.isNoneDietaryRequirement());

        DietaryRequirements noneDr = new DietaryRequirements("");
        assertTrue(noneDr.isNoneDietaryRequirement());

        DietaryRequirements nullDr = new DietaryRequirements(null);
        assertFalse(nullDr.isNoneDietaryRequirement());
    }

    @Test
    void isNullDietaryRequirement() {
        DietaryRequirements dr = new DietaryRequirements("Halal");
        assertFalse(dr.isNullDietaryRequirement());

        DietaryRequirements noneDr = new DietaryRequirements("");
        assertFalse(noneDr.isNullDietaryRequirement());

        DietaryRequirements nullDr = new DietaryRequirements(null);
        assertTrue(nullDr.isNullDietaryRequirement());
    }
}
