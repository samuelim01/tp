package wedlog.address.model.person;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_DIETARY_REQUIREMENTS_AMY;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_RSVP_STATUS_AMY;
import static wedlog.address.model.person.PersonTest.VALID_ADDRESS;
import static wedlog.address.model.person.PersonTest.VALID_EMAIL;
import static wedlog.address.model.person.PersonTest.VALID_NAME;
import static wedlog.address.model.person.PersonTest.VALID_PHONE;
import static wedlog.address.model.person.PersonTest.VALID_TAGS;
import static wedlog.address.testutil.Assert.assertThrows;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import wedlog.address.model.tag.Tag;

/**
 * Simplified test class for Guest.
 * More detailed tests to be written in the future.
 *
 * @author Keagan
 * @version v1.2
 */
public class GuestTest {

    public static final DietaryRequirements VALID_DIETARY_REQUIREMENTS =
            new DietaryRequirements(VALID_DIETARY_REQUIREMENTS_AMY);

    public static final RsvpStatus VALID_RSVP_STATUS = new RsvpStatus(VALID_RSVP_STATUS_AMY);

    @Test
    public void constructor() {
        // Name Null
        assertThrows(NullPointerException.class, () -> new Guest(null, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_RSVP_STATUS, VALID_DIETARY_REQUIREMENTS, VALID_TAGS));

        // Phone Null
        assertDoesNotThrow(() -> new Guest(VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS,
                VALID_RSVP_STATUS, VALID_DIETARY_REQUIREMENTS, VALID_TAGS));

        // Email Null
        assertDoesNotThrow(() -> new Guest(VALID_NAME, VALID_PHONE, null, VALID_ADDRESS,
                VALID_RSVP_STATUS, VALID_DIETARY_REQUIREMENTS, VALID_TAGS));

        // Address Null
        assertDoesNotThrow(() -> new Guest(VALID_NAME, VALID_PHONE, VALID_EMAIL, null,
                VALID_RSVP_STATUS, VALID_DIETARY_REQUIREMENTS, VALID_TAGS));

        // Rsvp Status Null
        assertThrows(NullPointerException.class, () -> new Guest(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, null, VALID_DIETARY_REQUIREMENTS, VALID_TAGS));

        // Dietary Requirements Null
        assertThrows(NullPointerException.class, () -> new Guest(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_RSVP_STATUS, null, VALID_TAGS));

        // Tags Null
        assertThrows(NullPointerException.class, () -> new Guest(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_RSVP_STATUS, VALID_DIETARY_REQUIREMENTS, null));
    }

    @Test
    public void equals() {
        Name name = new Name("Bob");
        Phone phone = new Phone("91234567");
        Email email = new Email("bob@bob.com");
        Address address = new Address("Blk 123");
        DietaryRequirements dietaryRequirements = new DietaryRequirements("Halal");
        RsvpStatus rsvpStatus = new RsvpStatus("yes");
        Tag tag = new Tag("friend");
        Set<Tag> tags = new HashSet<>();
        tags.add(tag);

        Guest guest = new Guest(name, phone, email, address, rsvpStatus, dietaryRequirements, tags);

        // same values -> returns true
        Person guestCopy = new Guest(name, phone, email, address, rsvpStatus, dietaryRequirements, tags);
        assertTrue(guest.equals(guestCopy));

        // same object -> returns true
        assertTrue(guest.equals(guest));

        // null -> returns false
        assertFalse(guest.equals(null));

        // different type -> returns false
        assertFalse(guest.equals(5));
    }

    @Test
    public void toStringTest() {
        Name name = new Name("Bob");
        Phone phone = new Phone("91234567");
        Email email = new Email("bob@bob.com");
        Address address = new Address("Blk 123");
        DietaryRequirements dietaryRequirements = new DietaryRequirements("Halal");
        RsvpStatus rsvpStatus = new RsvpStatus("yes");
        Tag tag = new Tag("friend");
        Set<Tag> tags = new HashSet<>();
        tags.add(tag);

        Guest guest = new Guest(name, phone, email, address, rsvpStatus, dietaryRequirements, tags);

        String expected = Guest.class.getCanonicalName() + "{name=" + guest.getName() + ", phone=" + guest.getPhone()
                + ", email=" + guest.getEmail() + ", address=" + guest.getAddress()
                + ", rsvpStatus=" + guest.getRsvpStatus() + ", dietaryRequirements=" + guest.getDietaryRequirements()
                + ", tags=" + guest.getTags() + "}";
        assertEquals(expected, guest.toString());
    }
}
