package wedlog.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static wedlog.address.logic.Messages.MESSAGE_INVALID_VENDOR_DISPLAYED_INDEX;
import static wedlog.address.logic.commands.CommandTestUtil.DESC_BRYAN;
import static wedlog.address.logic.commands.CommandTestUtil.DESC_VAL;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static wedlog.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static wedlog.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static wedlog.address.logic.commands.CommandTestUtil.showVendorAtIndex;
import static wedlog.address.logic.commands.VendorEditCommand.MESSAGE_EDIT_VENDOR_SUCCESS;
import static wedlog.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static wedlog.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static wedlog.address.testutil.TypicalVendors.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import wedlog.address.commons.core.index.Index;
import wedlog.address.logic.Messages;
import wedlog.address.logic.commands.VendorEditCommand.EditVendorDescriptor;
import wedlog.address.model.AddressBook;
import wedlog.address.model.Model;
import wedlog.address.model.ModelManager;
import wedlog.address.model.UserPrefs;
import wedlog.address.model.person.Vendor;
import wedlog.address.testutil.EditVendorDescriptorBuilder;
import wedlog.address.testutil.VendorBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for VendorEditCommand.
 */
public class VendorEditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Vendor editedVendor = new VendorBuilder().build();
        EditVendorDescriptor descriptor = new EditVendorDescriptorBuilder(editedVendor).build();
        VendorEditCommand editCommand = new VendorEditCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(MESSAGE_EDIT_VENDOR_SUCCESS, Messages.format(editedVendor));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setVendor(model.getFilteredVendorList().get(0), editedVendor);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastVendor = Index.fromOneBased(model.getFilteredVendorList().size());
        Vendor lastVendor = model.getFilteredVendorList().get(indexLastVendor.getZeroBased());

        VendorBuilder vendorInList = new VendorBuilder(lastVendor);
        Vendor editedVendor = vendorInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditVendorDescriptor descriptor = new EditVendorDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        VendorEditCommand editCommand = new VendorEditCommand(indexLastVendor, descriptor);

        String expectedMessage = String.format(MESSAGE_EDIT_VENDOR_SUCCESS, Messages.format(editedVendor));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setVendor(lastVendor, editedVendor);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        VendorEditCommand editCommand = new VendorEditCommand(INDEX_FIRST_PERSON, new EditVendorDescriptor());
        Vendor editedVendor = model.getFilteredVendorList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(MESSAGE_EDIT_VENDOR_SUCCESS, Messages.format(editedVendor));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedEmptyUnfilteredList_success() {
        Vendor firstVendor = model.getFilteredVendorList().get(0);
        Vendor editedVendor = new VendorBuilder().withName("Anne Chua")
                .withoutPhone().withoutAddress().withoutEmail().withTags().build();
        EditVendorDescriptor descriptor = new EditVendorDescriptorBuilder()
                .withoutPhone().withoutAddress().withoutEmail().withTags().build();
        VendorEditCommand editCommand = new VendorEditCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(MESSAGE_EDIT_VENDOR_SUCCESS, Messages.format(editedVendor));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setVendor(firstVendor, editedVendor);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showVendorAtIndex(model, INDEX_FIRST_PERSON);

        Vendor vendorInFilteredList = model.getFilteredVendorList().get(INDEX_FIRST_PERSON.getZeroBased());
        Vendor editedVendor = new VendorBuilder(vendorInFilteredList).withName(VALID_NAME_BOB).build();
        VendorEditCommand editCommand = new VendorEditCommand(INDEX_FIRST_PERSON,
                new EditVendorDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(MESSAGE_EDIT_VENDOR_SUCCESS, Messages.format(editedVendor));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setVendor(model.getFilteredVendorList().get(0), editedVendor);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateVendorUnfilteredList_failure() {
        Vendor firstVendor = model.getFilteredVendorList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditVendorDescriptor descriptor = new EditVendorDescriptorBuilder(firstVendor).build();
        VendorEditCommand editCommand = new VendorEditCommand(INDEX_SECOND_PERSON, descriptor);

        assertCommandFailure(editCommand, model, VendorEditCommand.MESSAGE_DUPLICATE_VENDOR);
    }

    @Test
    public void execute_duplicateVendorFilteredList_failure() {
        showVendorAtIndex(model, INDEX_FIRST_PERSON);

        // edit vendor in filtered list into a duplicate in address book
        Vendor vendorInList = model.getAddressBook().getVendorList().get(INDEX_SECOND_PERSON.getZeroBased());
        VendorEditCommand editCommand = new VendorEditCommand(INDEX_FIRST_PERSON,
                new EditVendorDescriptorBuilder(vendorInList).build());

        assertCommandFailure(editCommand, model, VendorEditCommand.MESSAGE_DUPLICATE_VENDOR);
    }

    @Test
    public void execute_invalidVendorIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredVendorList().size() + 1);
        EditVendorDescriptor descriptor = new EditVendorDescriptorBuilder().withName(VALID_NAME_BOB).build();
        VendorEditCommand editCommand = new VendorEditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, MESSAGE_INVALID_VENDOR_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidVendorIndexFilteredList_failure() {
        showVendorAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getVendorList().size());

        VendorEditCommand editCommand = new VendorEditCommand(outOfBoundIndex,
                new EditVendorDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, MESSAGE_INVALID_VENDOR_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final VendorEditCommand standardCommand = new VendorEditCommand(INDEX_FIRST_PERSON, DESC_VAL);

        // same values -> returns true
        EditVendorDescriptor copyDescriptor = new EditVendorDescriptor(DESC_VAL);
        VendorEditCommand commandWithSameValues = new VendorEditCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new VendorEditCommand(INDEX_SECOND_PERSON, DESC_VAL)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new VendorEditCommand(INDEX_FIRST_PERSON, DESC_BRYAN)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditVendorDescriptor editVendorDescriptor = new EditVendorDescriptor();
        VendorEditCommand editCommand = new VendorEditCommand(index, editVendorDescriptor);
        String expected = VendorEditCommand.class.getCanonicalName() + "{index=" + index + ", editVendorDescriptor="
                + editVendorDescriptor + "}";
        assertEquals(expected, editCommand.toString());
    }

}
