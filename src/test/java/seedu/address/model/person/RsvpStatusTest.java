package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Test class for RsvpStatus.
 *
 * @author Keagan
 * @version v1.2
 */
public class RsvpStatusTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RsvpStatus(null));
    }

    @Test
    public void constructor_invalidRsvpStatus_throwsIllegalArgumentException() {
        String invalidRsvpStatus = "";
        assertThrows(IllegalArgumentException.class, () -> new RsvpStatus(invalidRsvpStatus));
    }

    @Test
    public void isValidRsvpStatus() {
        // null rsvp status
        assertThrows(NullPointerException.class, () -> RsvpStatus.isValidRsvpStatus(null));

        // invalid rsvp status
        assertFalse(RsvpStatus.isValidRsvpStatus("")); // empty string
        assertFalse(RsvpStatus.isValidRsvpStatus(" ")); // spaces only
        assertFalse(RsvpStatus.isValidRsvpStatus("truefalse")); // more than 1 word
        assertFalse(RsvpStatus.isValidRsvpStatus("nonsense")); // invalid keyword

        // valid rsvp status
        assertTrue(RsvpStatus.isValidRsvpStatus("true"));
        assertTrue(RsvpStatus.isValidRsvpStatus("false"));
        assertTrue(RsvpStatus.isValidRsvpStatus("True")); // capitalised
        assertTrue(RsvpStatus.isValidRsvpStatus("False")); // capitalised
    }

    @Test
    public void equals() {
        RsvpStatus rsvpStatus = new RsvpStatus("true");

        // same values -> returns true
        assertTrue(rsvpStatus.equals(new RsvpStatus("true")));

        // same values but capitalised -> returns true
        assertTrue(rsvpStatus.equals(new RsvpStatus("True")));

        // same object -> returns true
        assertTrue(rsvpStatus.equals(rsvpStatus));

        // null -> returns false
        assertFalse(rsvpStatus.equals(null));

        // different types -> returns false
        assertFalse(rsvpStatus.equals(5.0f));

        // different values -> returns false
        assertFalse(rsvpStatus.equals(new RsvpStatus("false")));
    }

}
