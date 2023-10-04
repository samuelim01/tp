package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.tag.Tag;

/**
 * Simplified test class for Guest.
 * More detailed tests to be written in the future.
 *
 * @author Keagan
 * @version v1.2
 */
public class GuestTest {

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
