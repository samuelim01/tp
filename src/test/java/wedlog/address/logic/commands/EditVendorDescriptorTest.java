package wedlog.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static wedlog.address.logic.commands.CommandTestUtil.DESC_BOB;
import static wedlog.address.logic.commands.CommandTestUtil.DESC_VAL;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import org.junit.jupiter.api.Test;

import wedlog.address.logic.commands.VendorEditCommand.EditVendorDescriptor;
import wedlog.address.testutil.EditVendorDescriptorBuilder;

public class EditVendorDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditVendorDescriptor descriptorWithSameValues = new EditVendorDescriptor(DESC_VAL);
        assertTrue(DESC_VAL.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_VAL.equals(DESC_VAL));

        // null -> returns false
        assertFalse(DESC_VAL.equals(null));

        // different types -> returns false
        assertFalse(DESC_VAL.equals(5));

        // different values -> returns false
        assertFalse(DESC_VAL.equals(DESC_BOB));

        // different name -> returns false
        EditVendorDescriptor editedVal = new EditVendorDescriptorBuilder(DESC_VAL).withName(VALID_NAME_BOB).build();
        assertFalse(DESC_VAL.equals(editedVal));

        // different phone -> returns false
        editedVal = new EditVendorDescriptorBuilder(DESC_VAL).withPhone(VALID_PHONE_BOB).build();
        assertFalse(DESC_VAL.equals(editedVal));

        // different email -> returns false
        editedVal = new EditVendorDescriptorBuilder(DESC_VAL).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(DESC_VAL.equals(editedVal));

        // different address -> returns false
        editedVal = new EditVendorDescriptorBuilder(DESC_VAL).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(DESC_VAL.equals(editedVal));

        // different tags -> returns false
        editedVal = new EditVendorDescriptorBuilder(DESC_VAL).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(DESC_VAL.equals(editedVal));

        // absent phone -> returns false
        editedVal = new EditVendorDescriptorBuilder(DESC_VAL).withoutPhone().build();
        assertFalse(DESC_VAL.equals(editedVal));

        // absent email -> returns false
        editedVal = new EditVendorDescriptorBuilder(DESC_VAL).withoutEmail().build();
        assertFalse(DESC_VAL.equals(editedVal));

        // absent address -> returns false
        editedVal = new EditVendorDescriptorBuilder(DESC_VAL).withoutAddress().build();
        assertFalse(DESC_VAL.equals(editedVal));

        // absent tags -> returns false
        editedVal = new EditVendorDescriptorBuilder(DESC_VAL).withTags().build();
        assertFalse(DESC_VAL.equals(editedVal));
    }

    @Test
    public void toStringMethod() {
        EditVendorDescriptor editVendorDescriptor = new EditVendorDescriptor();
        String expected = EditVendorDescriptor.class.getCanonicalName() + "{name="
                + editVendorDescriptor.getName() + ", phone="
                + editVendorDescriptor.getPhone() + ", email="
                + editVendorDescriptor.getEmail() + ", address="
                + editVendorDescriptor.getAddress() + ", tags="
                + editVendorDescriptor.getTags() + ", isNameEdited="
                + editVendorDescriptor.isNameEdited() + ", isPhoneEdited="
                + editVendorDescriptor.isPhoneEdited() + ", isEmailEdited="
                + editVendorDescriptor.isEmailEdited() + ", isAddressEdited="
                + editVendorDescriptor.isAddressEdited() + ", isTagsEdited="
                + editVendorDescriptor.isTagsEdited() + "}";
        assertEquals(expected, editVendorDescriptor.toString());
    }
}
