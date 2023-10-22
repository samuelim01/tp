package wedlog.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static wedlog.address.logic.commands.CommandTestUtil.DESC_ANNE;
import static wedlog.address.logic.commands.CommandTestUtil.DESC_BOB;
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
        EditVendorDescriptor descriptorWithSameValues = new EditVendorDescriptor(DESC_ANNE);
        assertTrue(DESC_ANNE.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_ANNE.equals(DESC_ANNE));

        // null -> returns false
        assertFalse(DESC_ANNE.equals(null));

        // different types -> returns false
        assertFalse(DESC_ANNE.equals(5));

        // different values -> returns false
        assertFalse(DESC_ANNE.equals(DESC_BOB));

        // different name -> returns false
        EditVendorDescriptor editedAnne = new EditVendorDescriptorBuilder(DESC_ANNE).withName(VALID_NAME_BOB).build();
        assertFalse(DESC_ANNE.equals(editedAnne));

        // different phone -> returns false
        editedAnne = new EditVendorDescriptorBuilder(DESC_ANNE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(DESC_ANNE.equals(editedAnne));

        // different email -> returns false
        editedAnne = new EditVendorDescriptorBuilder(DESC_ANNE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(DESC_ANNE.equals(editedAnne));

        // different address -> returns false
        editedAnne = new EditVendorDescriptorBuilder(DESC_ANNE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(DESC_ANNE.equals(editedAnne));

        // different tags -> returns false
        editedAnne = new EditVendorDescriptorBuilder(DESC_ANNE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(DESC_ANNE.equals(editedAnne));

        // absent phone -> returns false
        editedAnne = new EditVendorDescriptorBuilder(DESC_ANNE).withoutPhone().build();
        assertFalse(DESC_ANNE.equals(editedAnne));

        // absent email -> returns false
        editedAnne = new EditVendorDescriptorBuilder(DESC_ANNE).withoutEmail().build();
        assertFalse(DESC_ANNE.equals(editedAnne));

        // absent address -> returns false
        editedAnne = new EditVendorDescriptorBuilder(DESC_ANNE).withoutAddress().build();
        assertFalse(DESC_ANNE.equals(editedAnne));

        // absent tags -> returns false
        editedAnne = new EditVendorDescriptorBuilder(DESC_ANNE).withTags().build();
        assertFalse(DESC_ANNE.equals(editedAnne));
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
