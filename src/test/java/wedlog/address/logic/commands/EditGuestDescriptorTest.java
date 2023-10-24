package wedlog.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static wedlog.address.logic.commands.CommandTestUtil.DESC_GIA;
import static wedlog.address.logic.commands.CommandTestUtil.DESC_BOB_GUEST;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import org.junit.jupiter.api.Test;

import wedlog.address.logic.commands.GuestEditCommand.EditGuestDescriptor;
import wedlog.address.testutil.EditGuestDescriptorBuilder;

public class EditGuestDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditGuestDescriptor descriptorWithSameValues = new EditGuestDescriptor(DESC_GIA);
        assertTrue(DESC_GIA.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_GIA.equals(DESC_GIA));

        // null -> returns false
        assertFalse(DESC_GIA.equals(null));

        // different types -> returns false
        assertFalse(DESC_GIA.equals(5));

        // different values -> returns false
        assertFalse(DESC_GIA.equals(DESC_BOB_GUEST));

        // different name -> returns false
        EditGuestDescriptor editedAmy = new EditGuestDescriptorBuilder(DESC_GIA).withName(VALID_NAME_BOB).build();
        assertFalse(DESC_GIA.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditGuestDescriptorBuilder(DESC_GIA).withPhone(VALID_PHONE_BOB).build();
        assertFalse(DESC_GIA.equals(editedAmy));

        // different email -> returns false
        editedAmy = new EditGuestDescriptorBuilder(DESC_GIA).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(DESC_GIA.equals(editedAmy));

        // different address -> returns false
        editedAmy = new EditGuestDescriptorBuilder(DESC_GIA).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(DESC_GIA.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditGuestDescriptorBuilder(DESC_GIA).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(DESC_GIA.equals(editedAmy));
    }

    @Test
    public void toStringMethod() {
        EditGuestDescriptor editGuestDescriptor = new EditGuestDescriptor();
        String expected = EditGuestDescriptor.class.getCanonicalName() + "{name="
                + editGuestDescriptor.getName().orElse(null) + ", phone="
                + editGuestDescriptor.getPhone().orElse(null) + ", email="
                + editGuestDescriptor.getEmail().orElse(null) + ", address="
                + editGuestDescriptor.getAddress().orElse(null) + ", rsvp status="
                + editGuestDescriptor.getRsvp() + ", dietary requirement="
                + editGuestDescriptor.getDietary() + ", table number="
                + editGuestDescriptor.getTable().orElse(null) + ", tags="
                + editGuestDescriptor.getTags().orElse(null) + "}";
        assertEquals(expected, editGuestDescriptor.toString());
    }
}
