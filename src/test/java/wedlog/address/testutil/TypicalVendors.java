package wedlog.address.testutil;

import static wedlog.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_TAG_FLORIST;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import wedlog.address.model.AddressBook;
import wedlog.address.model.person.Vendor;

/**
 * A utility class containing a list of {@code Vendor} objects to be used in tests.
 */
public class TypicalVendors {

    public static final Vendor ANNE = new VendorBuilder().withName("Anne Chua")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("anne@example.com")
            .withPhone("94351253")
            .withTags("friends").build();
    public static final Vendor BRYAN = new VendorBuilder().withName("Bryan Lim")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("bryand@example.com").withPhone("98765432")
            .withTags("owesMoney", "friends").build();
    public static final Vendor CHLOE = new VendorBuilder().withName("Chloe Chua").withPhone("95352563")
            .withEmail("ckrz@example.com").withAddress("wall street").build();
    public static final Vendor DANIEL = new VendorBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").withTags("friends").build();
    public static final Vendor ETHAN = new VendorBuilder().withName("Ethan Meyer").withPhone("9482224")
            .withEmail("ethanmeyer@example.com").withAddress("michegan ave").build();
    public static final Vendor FELIZ = new VendorBuilder().withName("Feliz Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo").build();
    public static final Vendor GRACE = new VendorBuilder().withName("Grace Chan").withPhone("9482442")
            .withEmail("charis@example.com").withAddress("4th street").build();

    // Manually added
    public static final Vendor HENRY = new VendorBuilder().withName("Henry Meier").withPhone("8482424")
            .withEmail("hiimhenry@example.com").withAddress("little india").build();
    public static final Vendor IRENE = new VendorBuilder().withName("Irene Mueller").withPhone("8482131")
            .withEmail("muellerirene@example.com").withAddress("chicago ave").build();

    // Manually added - Vendor's details found in {@code CommandTestUtil}
    public static final Vendor AMY = new VendorBuilder(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).build();
    public static final Vendor BOB = new VendorBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_FLORIST)
            .build();

    private TypicalVendors() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical vendors.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Vendor vendor : getTypicalVendors()) {
            ab.addVendor(vendor);
        }
        return ab;
    }

    public static List<Vendor> getTypicalVendors() {
        return new ArrayList<>(Arrays.asList(ANNE, BRYAN, CHLOE, DANIEL, ETHAN, FELIZ, GRACE));
    }
}
