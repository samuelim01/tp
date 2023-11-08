package wedlog.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static wedlog.address.testutil.Assert.assertThrows;

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
        // EP: null rsvp status
        assertThrows(NullPointerException.class, () -> RsvpStatus.isValidRsvpStatus(null));

        // EP: invalid rsvp status
        assertFalse(RsvpStatus.isValidRsvpStatus("")); // empty string
        assertFalse(RsvpStatus.isValidRsvpStatus(" ")); // spaces only
        assertFalse(RsvpStatus.isValidRsvpStatus("yesno")); // more than 1 word
        assertFalse(RsvpStatus.isValidRsvpStatus("nonsense")); // invalid keyword

        // EP: valid rsvp status
        assertTrue(RsvpStatus.isValidRsvpStatus("yes"));
        assertTrue(RsvpStatus.isValidRsvpStatus("no"));
        assertTrue(RsvpStatus.isValidRsvpStatus("Yes")); // capitalised
        assertTrue(RsvpStatus.isValidRsvpStatus("No")); // capitalised
        assertTrue(RsvpStatus.isValidRsvpStatus("unknown"));
        assertTrue(RsvpStatus.isValidRsvpStatus("Unknown")); // capitalised
    }

    @Test
    public void equals() {
        RsvpStatus rsvpStatus = new RsvpStatus("yes");

        // EP: same rsvp status

        // same values -> returns true
        assertTrue(rsvpStatus.equals(new RsvpStatus("yes")));

        // same values but capitalised -> returns true
        assertTrue(rsvpStatus.equals(new RsvpStatus("Yes")));

        // same object -> returns true
        assertTrue(rsvpStatus.equals(rsvpStatus));

        // EP: null rsvp status

        // null -> returns false
        assertFalse(rsvpStatus.equals(null));

        // EP: different rsvp status

        // different types -> returns false
        assertFalse(rsvpStatus.equals(5.0f));

        // different values -> returns false
        assertFalse(rsvpStatus.equals(new RsvpStatus("no")));
        assertFalse(rsvpStatus.equals(new RsvpStatus("unknown")));

        // different constructor -> return true
        RsvpStatus unknownRsvpStatus = new RsvpStatus("unknown");
        assertTrue(unknownRsvpStatus.equals(RsvpStatus.unknown()));
    }

}
