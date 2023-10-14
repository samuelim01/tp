package wedlog.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import wedlog.address.model.tag.Tag;

/**
 * Simplified test class for Vendor.
 * More detailed tests to be written in the future.
 *
 * @author Shannon
 * @version v1.2
 */

public class VendorTest {
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
        Name name = new Name("ABC Catering");
        Phone phone = new Phone("61234567");
        Email email = new Email("abc@catering.com");
        Address address = new Address("Blk 123");
        Tag tag = new Tag("caterer");
        Set<Tag> tags = new HashSet<>();
        tags.add(tag);

        Vendor vendor = new Vendor(name, phone, email, address, tags);

        // same values -> returns true
        Person vendorCopy = new Vendor(name, phone, email, address, tags);
        assertTrue(vendor.equals(vendorCopy));

        // same object -> returns true
        assertTrue(vendor.equals(vendor));

        // null -> returns false
        assertFalse(vendor.equals(null));

        // different type -> returns false
        assertFalse(vendor.equals(5));
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
