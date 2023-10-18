package wedlog.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static wedlog.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static wedlog.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static wedlog.address.logic.commands.CommandTestUtil.showVendorAtIndex;
import static wedlog.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static wedlog.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static wedlog.address.testutil.TypicalVendors.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import wedlog.address.commons.core.index.Index;
import wedlog.address.logic.Messages;
import wedlog.address.model.Model;
import wedlog.address.model.ModelManager;
import wedlog.address.model.UserPrefs;
import wedlog.address.model.person.Vendor;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code GuestDeleteCommand}.
 */
public class VendorDeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredVendorList_success() {
        Vendor vendorToDelete = model.getFilteredVendorList().get(INDEX_FIRST_PERSON.getZeroBased());
        VendorDeleteCommand vendorDeleteCommand = new VendorDeleteCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(VendorDeleteCommand.MESSAGE_DELETE_VENDOR_SUCCESS,
                Messages.format(vendorToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteVendor(vendorToDelete);

        assertCommandSuccess(vendorDeleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredVendorList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredVendorList().size() + 1);
        VendorDeleteCommand vendorDeleteCommand = new VendorDeleteCommand(outOfBoundIndex);

        assertCommandFailure(vendorDeleteCommand, model, Messages.MESSAGE_INVALID_VENDOR_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredVendorList_success() {
        showVendorAtIndex(model, INDEX_FIRST_PERSON);

        Vendor vendorToDelete = model.getFilteredVendorList().get(INDEX_FIRST_PERSON.getZeroBased());
        VendorDeleteCommand vendorDeleteCommand = new VendorDeleteCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(VendorDeleteCommand.MESSAGE_DELETE_VENDOR_SUCCESS,
                Messages.format(vendorToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteVendor(vendorToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(vendorDeleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredVendorList_throwsCommandException() {
        showVendorAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getVendorList().size());

        VendorDeleteCommand vendorDeleteCommand = new VendorDeleteCommand(outOfBoundIndex);

        assertCommandFailure(vendorDeleteCommand, model, Messages.MESSAGE_INVALID_VENDOR_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        VendorDeleteCommand vendorDeleteFirstCommand = new VendorDeleteCommand(INDEX_FIRST_PERSON);
        VendorDeleteCommand vendorDeleteSecondCommand = new VendorDeleteCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(vendorDeleteFirstCommand.equals(vendorDeleteFirstCommand));

        // same values -> returns true
        VendorDeleteCommand vendorDeleteFirstCommandCopy = new VendorDeleteCommand(INDEX_FIRST_PERSON);
        assertTrue(vendorDeleteFirstCommand.equals(vendorDeleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(vendorDeleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(vendorDeleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(vendorDeleteFirstCommand.equals(vendorDeleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        VendorDeleteCommand vendorDeleteCommand = new VendorDeleteCommand(targetIndex);
        String expected = VendorDeleteCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, vendorDeleteCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered guest list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredVendorList(p -> false);

        assertTrue(model.getFilteredVendorList().isEmpty());
    }
}
