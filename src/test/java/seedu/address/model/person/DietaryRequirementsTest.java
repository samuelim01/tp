package seedu.address.model.person;

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
    }
}
