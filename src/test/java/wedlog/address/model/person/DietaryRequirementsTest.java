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

        // same none status -> returns true
        DietaryRequirements noneDr = new DietaryRequirements("");
        DietaryRequirements noneDrCopy = new DietaryRequirements("");
        assertTrue(noneDr.equals(noneDrCopy));

        // same none status with spacing -> returns true
        DietaryRequirements noneSpacedDrCopy = new DietaryRequirements(" ");
        assertTrue(noneDr.equals(noneSpacedDrCopy));

        // same null value -> returns true
        DietaryRequirements nullDr = new DietaryRequirements(null);
        DietaryRequirements nullDrCopy = new DietaryRequirements(null);
        assertTrue(nullDr.equals(nullDrCopy));

        // different status -> returns false
        assertFalse(dr.equals(noneDr));
        assertFalse(noneDr.equals(nullDrCopy));
    }

    @Test
    void isNoneDietaryRequirement() {
        // present dietary requirement
        DietaryRequirements dr = new DietaryRequirements("Halal");
        assertFalse(dr.isNoneDietaryRequirement());

        // none dietary requirement
        DietaryRequirements noneDr = new DietaryRequirements("");
        assertTrue(noneDr.isNoneDietaryRequirement());

        // none dietary requirement with spacing
        DietaryRequirements noneSpacedDr = new DietaryRequirements(" ");
        assertTrue(noneSpacedDr.isNoneDietaryRequirement());

        // null dietary requirement
        DietaryRequirements nullDr = new DietaryRequirements(null);
        assertFalse(nullDr.isNoneDietaryRequirement());
    }

    @Test
    void isNullDietaryRequirement() {
        // present dietary requirement
        DietaryRequirements dr = new DietaryRequirements("Halal");
        assertFalse(dr.isNullDietaryRequirement());

        // none dietary requirement
        DietaryRequirements noneDr = new DietaryRequirements("");
        assertFalse(noneDr.isNullDietaryRequirement());

        // none dietary requirement with spacing
        DietaryRequirements noneSpacedDr = new DietaryRequirements(" ");
        assertFalse(noneSpacedDr.isNullDietaryRequirement());

        // null dietary requirement
        DietaryRequirements nullDr = new DietaryRequirements(null);
        assertTrue(nullDr.isNullDietaryRequirement());
    }
}
