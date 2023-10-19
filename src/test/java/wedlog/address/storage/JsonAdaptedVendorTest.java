package wedlog.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static wedlog.address.testutil.Assert.assertThrows;
import static wedlog.address.testutil.TypicalVendors.BRYAN;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import wedlog.address.commons.exceptions.IllegalValueException;
import wedlog.address.model.person.Address;
import wedlog.address.model.person.Email;
import wedlog.address.model.person.Name;
import wedlog.address.model.person.Phone;
import wedlog.address.model.person.Vendor;
import wedlog.address.testutil.VendorBuilder;

public class JsonAdaptedVendorTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BRYAN.getName().toString();
    private static final String VALID_PHONE = BRYAN.getPhone().get().toString();
    private static final String VALID_EMAIL = BRYAN.getEmail().toString();
    private static final String VALID_ADDRESS = BRYAN.getAddress().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BRYAN.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validVendorDetails_returnsVendor() throws Exception {
        JsonAdaptedVendor vendor = new JsonAdaptedVendor(BRYAN);
        assertEquals(BRYAN, vendor.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedVendor vendor =
                new JsonAdaptedVendor(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, vendor::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedVendor vendor = new JsonAdaptedVendor(null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = String.format(JsonAdaptedVendor.MISSING_FIELD_MESSAGE_FORMAT,
                Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, vendor::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedVendor vendor =
                new JsonAdaptedVendor(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, vendor::toModelType);
    }

    @Test
    public void toModelType_nullPhone_returnsVendor() throws Exception {
        Vendor expectedVendor = new VendorBuilder(BRYAN).withoutPhone().build();
        JsonAdaptedVendor vendor = new JsonAdaptedVendor(expectedVendor);
        assertEquals(expectedVendor, vendor.toModelType());
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedVendor vendor =
                new JsonAdaptedVendor(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, vendor::toModelType);
    }

    @Test
    public void toModelType_nullEmail_returnsVendor() throws Exception {
        Vendor expectedVendor = new VendorBuilder(BRYAN).withoutEmail().build();
        JsonAdaptedVendor vendor = new JsonAdaptedVendor(expectedVendor);
        assertEquals(expectedVendor, vendor.toModelType());
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedVendor vendor =
                new JsonAdaptedVendor(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS, VALID_TAGS);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, vendor::toModelType);
    }

    @Test
    public void toModelType_nullAddress_returnsVendor() throws Exception {
        Vendor expectedVendor = new VendorBuilder(BRYAN).withoutAddress().build();
        JsonAdaptedVendor vendor = new JsonAdaptedVendor(expectedVendor);
        assertEquals(expectedVendor, vendor.toModelType());
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedVendor vendor =
                new JsonAdaptedVendor(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, invalidTags);
        assertThrows(IllegalValueException.class, vendor::toModelType);
    }

}
