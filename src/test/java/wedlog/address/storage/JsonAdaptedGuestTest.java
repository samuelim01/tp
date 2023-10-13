package wedlog.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static wedlog.address.storage.JsonAdaptedGuest.MISSING_FIELD_MESSAGE_FORMAT;
import static wedlog.address.testutil.Assert.assertThrows;
import static wedlog.address.testutil.TypicalGuests.GINA;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import wedlog.address.commons.exceptions.IllegalValueException;
import wedlog.address.model.person.Address;
import wedlog.address.model.person.Email;
import wedlog.address.model.person.Name;
import wedlog.address.model.person.Phone;

public class JsonAdaptedGuestTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_RSVP_STATUS = "idk";
    private static final String INVALID_DIETARY_REQUIREMENTS = "";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = GINA.getName().toString();
    private static final String VALID_PHONE = GINA.getPhone().toString();
    private static final String VALID_EMAIL = GINA.getEmail().toString();
    private static final String VALID_ADDRESS = GINA.getAddress().toString();
    private static final String VALID_RSVP_STATUS = GINA.getRsvpStatus().toString();
    private static final String VALID_DIETARY_REQUIREMENT = GINA.getDietaryRequirements().toString();
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
                        VALID_DIETARY_REQUIREMENT, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, guest::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedGuest guest =
                new JsonAdaptedGuest(null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_RSVP_STATUS,
                        VALID_DIETARY_REQUIREMENT, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, guest::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedGuest guest =
                new JsonAdaptedGuest(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_RSVP_STATUS,
                        VALID_DIETARY_REQUIREMENT, VALID_TAGS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, guest::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedGuest guest =
                new JsonAdaptedGuest(VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS, VALID_RSVP_STATUS,
                        VALID_DIETARY_REQUIREMENT, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, guest::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedGuest guest =
                new JsonAdaptedGuest(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS, VALID_RSVP_STATUS,
                        VALID_DIETARY_REQUIREMENT, VALID_TAGS);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, guest::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedGuest guest =
                new JsonAdaptedGuest(VALID_NAME, VALID_PHONE, null, VALID_ADDRESS, VALID_RSVP_STATUS,
                        VALID_DIETARY_REQUIREMENT, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, guest::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedGuest guest =
                new JsonAdaptedGuest(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS, VALID_RSVP_STATUS,
                        VALID_DIETARY_REQUIREMENT, VALID_TAGS);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, guest::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedGuest guest =
                new JsonAdaptedGuest(VALID_NAME, VALID_PHONE, VALID_EMAIL, null, VALID_RSVP_STATUS,
                        VALID_DIETARY_REQUIREMENT, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, guest::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedGuest guest =
                new JsonAdaptedGuest(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_RSVP_STATUS,
                        VALID_DIETARY_REQUIREMENT, invalidTags);
        assertThrows(IllegalValueException.class, guest::toModelType);
    }

}
