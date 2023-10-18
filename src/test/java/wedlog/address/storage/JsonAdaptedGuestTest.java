package wedlog.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static wedlog.address.storage.JsonAdaptedGuest.MISSING_FIELD_MESSAGE_FORMAT;
import static wedlog.address.testutil.Assert.assertThrows;
import static wedlog.address.testutil.TypicalGuests.GABRIEL;
import static wedlog.address.testutil.TypicalGuests.GINA;
import static wedlog.address.testutil.TypicalGuests.GREG;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import wedlog.address.commons.exceptions.IllegalValueException;
import wedlog.address.model.person.Address;
import wedlog.address.model.person.Email;
import wedlog.address.model.person.Guest;
import wedlog.address.model.person.Name;
import wedlog.address.model.person.Phone;
import wedlog.address.model.person.RsvpStatus;
import wedlog.address.testutil.GuestBuilder;

public class JsonAdaptedGuestTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_RSVP_STATUS = "idk";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = GINA.getName().toString();
    private static final String VALID_PHONE = GINA.getPhone().toString();
    private static final String VALID_EMAIL = GINA.getEmail().toString();
    private static final String VALID_ADDRESS = GINA.getAddress().toString();
    private static final String VALID_RSVP_STATUS = GINA.getRsvpStatus().toString();
    private static final String VALID_YES_RSVP_STATUS = GINA.getRsvpStatus().toString();
    private static final String VALID_NO_RSVP_STATUS = GREG.getRsvpStatus().toString();
    private static final String VALID_UNKNOWN_RSVP_STATUS = GABRIEL.getRsvpStatus().toString();
    private static final String VALID_DIETARY_REQUIREMENTS = GINA.getDietaryRequirements().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = GINA.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validGuestDetails_returnsGuest() throws Exception {
        JsonAdaptedGuest guest = new JsonAdaptedGuest(GINA);
        assertEquals(GINA, guest.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedGuest guest =
                new JsonAdaptedGuest(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_RSVP_STATUS,
                        VALID_DIETARY_REQUIREMENTS, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, guest::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedGuest guest =
                new JsonAdaptedGuest(null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_RSVP_STATUS,
                        VALID_DIETARY_REQUIREMENTS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, guest::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedGuest guest =
                new JsonAdaptedGuest(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_RSVP_STATUS,
                        VALID_DIETARY_REQUIREMENTS, VALID_TAGS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, guest::toModelType);
    }

    @Test
    public void toModelType_nullPhone_returnsGuest() throws Exception {
        Guest expectedGuest = new GuestBuilder(GINA).withoutPhone().build();
        JsonAdaptedGuest guest = new JsonAdaptedGuest(expectedGuest);
        assertEquals(expectedGuest, guest.toModelType());
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedGuest guest =
                new JsonAdaptedGuest(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS, VALID_RSVP_STATUS,
                        VALID_DIETARY_REQUIREMENTS, VALID_TAGS);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, guest::toModelType);
    }

    @Test
    public void toModelType_nullEmail_returnsGuest() throws Exception {
        Guest expectedGuest = new GuestBuilder(GINA).withoutEmail().build();
        JsonAdaptedGuest guest = new JsonAdaptedGuest(expectedGuest);
        assertEquals(expectedGuest, guest.toModelType());
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedGuest guest =
                new JsonAdaptedGuest(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS, VALID_RSVP_STATUS,
                        VALID_DIETARY_REQUIREMENTS, VALID_TAGS);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, guest::toModelType);
    }

    @Test
    public void toModelType_nullAddress_returnsGuest() throws Exception {
        Guest expectedGuest = new GuestBuilder(GINA).withoutAddress().build();
        JsonAdaptedGuest guest = new JsonAdaptedGuest(expectedGuest);
        assertEquals(expectedGuest, guest.toModelType());
    }

    @Test
    public void toModelType_invalidRsvpStatus_throwsIllegalValueException() {
        JsonAdaptedGuest guest =
                new JsonAdaptedGuest(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, INVALID_RSVP_STATUS,
                        VALID_DIETARY_REQUIREMENTS, VALID_TAGS);
        String expectedMessage = RsvpStatus.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, guest::toModelType);
    }

    @Test
    public void toModelType_nullRsvpStatus_throwsIllegalValueException() throws Exception {
        JsonAdaptedGuest guest =
                new JsonAdaptedGuest(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, null,
                        VALID_DIETARY_REQUIREMENTS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
                RsvpStatus.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, guest::toModelType);
    }

    @Test
    public void toModelType_validRsvpStatus_returnsGuest() throws Exception {
        // yes rsvp status
        JsonAdaptedGuest guest =
                new JsonAdaptedGuest(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_YES_RSVP_STATUS, VALID_DIETARY_REQUIREMENTS, VALID_TAGS);
        Guest expectedGuest = new GuestBuilder(GINA).withRsvpStatus(VALID_YES_RSVP_STATUS).build();
        assertEquals(expectedGuest, guest.toModelType());

        // no rsvp status
        guest = new JsonAdaptedGuest(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_NO_RSVP_STATUS, VALID_DIETARY_REQUIREMENTS, VALID_TAGS);
        expectedGuest = new GuestBuilder(GINA).withRsvpStatus(VALID_NO_RSVP_STATUS).build();
        assertEquals(expectedGuest, guest.toModelType());

        // unknown rsvp status
        guest = new JsonAdaptedGuest(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_UNKNOWN_RSVP_STATUS, VALID_DIETARY_REQUIREMENTS, VALID_TAGS);
        expectedGuest = new GuestBuilder(GINA).withRsvpStatus(VALID_UNKNOWN_RSVP_STATUS).build();
        assertEquals(expectedGuest, guest.toModelType());
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedGuest guest =
                new JsonAdaptedGuest(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_RSVP_STATUS,
                        VALID_DIETARY_REQUIREMENTS, invalidTags);
        assertThrows(IllegalValueException.class, guest::toModelType);
    }

}
