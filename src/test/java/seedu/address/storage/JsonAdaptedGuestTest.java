package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Address;
import seedu.address.model.person.DietaryRequirements;
import seedu.address.model.person.Email;
import seedu.address.model.person.Guest;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.RsvpStatus;
import seedu.address.model.tag.Tag;

public class JsonAdaptedGuestTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_RSVP = "lol";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_RSVP = "true";
    private static final String VALID_DIETARY = "Halal";
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    private final Name validName = new Name(VALID_NAME);
    private final Phone validPhone = new Phone(VALID_PHONE);
    private final Email validEmail = new Email(VALID_EMAIL);
    private final Address validAddress = new Address(VALID_ADDRESS);
    private final DietaryRequirements validDietaryRequirements = new DietaryRequirements(VALID_DIETARY);
    private final RsvpStatus validRsvpStatus = new RsvpStatus(VALID_RSVP);
    private final Set<Tag> tags = new HashSet<>();

    private final Guest guest = new Guest(validName, validPhone, validEmail, validAddress, validRsvpStatus,
            validDietaryRequirements, tags);

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedGuest jsonGuest = new JsonAdaptedGuest(guest);
        assertEquals(guest, jsonGuest.toModelType());
    }

}
