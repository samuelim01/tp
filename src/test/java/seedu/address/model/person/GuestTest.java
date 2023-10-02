package seedu.address.model.person;

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
    public void isSameGuest() {

        Name name = new Name("Bob");
        Phone phone = new Phone("91234567");
        Email email = new Email("bob@bob.com");
        Address address = new Address("Blk 123");
        DietaryRequirements dietaryRequirements = new DietaryRequirements("Halal");
        RsvpStatus rsvpStatus = new RsvpStatus("true");
        Tag tag = new Tag("friend");
        Set<Tag> tags = new HashSet<>();
        tags.add(tag);

        Guest guest = new Guest(name, phone, email, address, rsvpStatus, dietaryRequirements, tags);

        // same object -> returns true
        assertTrue(guest.isSamePerson(guest));

        // null -> returns false
        assertFalse(guest.isSamePerson(null));

        // same name, all other attributes different -> returns true
        Phone phone1 = new Phone("9123456");
        Email email1 = new Email("diff@bob.com");
        Address address1 = new Address("Blk 321");
        DietaryRequirements dietaryRequirements1 = new DietaryRequirements("Vegan");
        RsvpStatus rsvpStatus1 = new RsvpStatus("false");

        Guest editedGuest = new Guest(name, phone1, email1, address1, rsvpStatus1, dietaryRequirements1, tags);
        assertTrue(guest.isSamePerson(editedGuest));

        // different name, all other attributes same -> returns false
        Name name1 = new Name("Alice");
        editedGuest = new Guest(name1, phone, email, address, rsvpStatus, dietaryRequirements, tags);
        assertFalse(guest.isSamePerson(editedGuest));

        // name differs in case, all other attributes same -> returns false
        Name name2 = new Name("BOB");
        editedGuest = new Guest(name2, phone, email, address, rsvpStatus, dietaryRequirements, tags);
        assertFalse(guest.isSamePerson(editedGuest));

        // name has trailing spaces, all other attributes same -> returns false
        Name name3 = new Name("Bob ");
        editedGuest = new Guest(name3, phone, email, address, rsvpStatus, dietaryRequirements, tags);
        assertFalse(guest.isSamePerson(editedGuest));
    }

}
