package wedlog.address.model.person;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static wedlog.address.model.person.PersonTest.VALID_ADDRESS;
import static wedlog.address.model.person.PersonTest.VALID_EMAIL;
import static wedlog.address.model.person.PersonTest.VALID_NAME;
import static wedlog.address.model.person.PersonTest.VALID_PHONE;
import static wedlog.address.model.person.PersonTest.VALID_TAGS;
import static wedlog.address.testutil.Assert.assertThrows;
import static wedlog.address.testutil.TypicalVendors.ANNE;
import static wedlog.address.testutil.TypicalVendors.BRYAN;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import wedlog.address.model.tag.Tag;
import wedlog.address.testutil.VendorBuilder;

/**
 * Simplified test class for Vendor.
 * More detailed tests to be written in the future.
 *
 * @author Shannon
 * @version v1.2
 */

public class VendorTest {

    @Test
    public void constructor() {
        // null name
        assertThrows(NullPointerException.class, () -> new Vendor(null, VALID_PHONE,
                VALID_EMAIL, VALID_ADDRESS, VALID_TAGS));

        // null phone
        assertDoesNotThrow(() -> new Vendor(VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS));

        // null email
        assertDoesNotThrow(() -> new Vendor(VALID_NAME, VALID_PHONE, null, VALID_ADDRESS, VALID_TAGS));

        // null address
        assertDoesNotThrow(() -> new Vendor(VALID_NAME, VALID_PHONE, VALID_EMAIL, null, VALID_TAGS));

        // null tags
        assertThrows(NullPointerException.class, () -> new Vendor(null, VALID_PHONE,
                VALID_EMAIL, VALID_ADDRESS, VALID_TAGS));
    }

    @Test
    public void isSameVendor() {

        Name name = new Name("ABC Catering");
        Phone phone = new Phone("61234567");
        Email email = new Email("abc@catering.com");
        Address address = new Address("Blk 123");
        Tag tag = new Tag("caterer");
        Set<Tag> tags = new HashSet<>();
        tags.add(tag);

        Vendor vendor = new Vendor(name, phone, email, address, tags);

        // same object -> returns true
        assertTrue(vendor.isSamePerson(vendor));

        // null -> returns false
        assertFalse(vendor.isSamePerson(null));

        // same name, all other attributes different -> returns true
        Phone phone1 = new Phone("6123456");
        Email email1 = new Email("diff@catering.com");
        Address address1 = new Address("Blk 321");

        Vendor editedVendor = new Vendor(name, phone1, email1, address1, tags);
        assertTrue(vendor.isSamePerson(editedVendor));

        // different name, all other attributes same -> returns false
        Name name1 = new Name("XYZ Catering");
        editedVendor = new Vendor(name1, phone, email, address, tags);
        assertFalse(vendor.isSamePerson(editedVendor));

        // name differs in case, all other attributes same -> returns false
        Name name2 = new Name("ABC CATERING");
        editedVendor = new Vendor(name2, phone, email, address, tags);
        assertFalse(vendor.isSamePerson(editedVendor));

        // name has trailing spaces, all other attributes same -> returns false
        Name name3 = new Name("ABC Catering ");
        editedVendor = new Vendor(name3, phone, email, address, tags);
        assertFalse(vendor.isSamePerson(editedVendor));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Vendor anneCopy = new VendorBuilder(ANNE).build();
        assertTrue(ANNE.equals(anneCopy));

        // same object -> returns true
        assertTrue(ANNE.equals(ANNE));

        // null -> returns false
        assertFalse(ANNE.equals(null));

        // different type -> returns false
        assertFalse(ANNE.equals(5));

        // different vendor -> returns false
        assertFalse(ANNE.equals(BRYAN));

        // different name -> returns false
        Vendor editedAnne = new VendorBuilder(ANNE).withName(VALID_NAME_BOB).build();
        assertFalse(ANNE.equals(editedAnne));

        // different phone -> returns false
        editedAnne = new VendorBuilder(ANNE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ANNE.equals(editedAnne));

        // different email -> returns false
        editedAnne = new VendorBuilder(ANNE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ANNE.equals(editedAnne));

        // different address -> returns false
        editedAnne = new VendorBuilder(ANNE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ANNE.equals(editedAnne));

        // different tags -> returns false
        editedAnne = new VendorBuilder(ANNE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ANNE.equals(editedAnne));

        // no phone -> returns false
        editedAnne = new VendorBuilder(ANNE).withoutPhone().build();
        assertFalse(ANNE.equals(editedAnne));

        // no email -> returns false
        editedAnne = new VendorBuilder(ANNE).withoutEmail().build();
        assertFalse(ANNE.equals(editedAnne));

        // no address -> returns false
        editedAnne = new VendorBuilder(ANNE).withoutAddress().build();
        assertFalse(ANNE.equals(editedAnne));
    }

    @Test
    public void toStringTest() {
        Name name = new Name("ABC Catering");
        Phone phone = new Phone("61234567");
        Email email = new Email("abc@catering.com");
        Address address = new Address("Blk 123");
        Tag tag = new Tag("caterer");
        Set<Tag> tags = new HashSet<>();
        tags.add(tag);

        Vendor vendor = new Vendor(name, phone, email, address, tags);

        String expected = Vendor.class.getCanonicalName() + "{name=" + vendor.getName() + ", phone=" + vendor.getPhone()
                + ", email=" + vendor.getEmail() + ", address=" + vendor.getAddress()
                + ", tags=" + vendor.getTags() + "}";
        assertEquals(expected, vendor.toString());
    }
}
