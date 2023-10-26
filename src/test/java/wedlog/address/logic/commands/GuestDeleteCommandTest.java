package wedlog.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static wedlog.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static wedlog.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static wedlog.address.logic.commands.CommandTestUtil.showGuestAtIndex;
import static wedlog.address.testutil.TypicalGuests.getTypicalAddressBook;
import static wedlog.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static wedlog.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import wedlog.address.commons.core.index.Index;
import wedlog.address.logic.Messages;
import wedlog.address.model.Model;
import wedlog.address.model.ModelManager;
import wedlog.address.model.UserPrefs;
import wedlog.address.model.person.Guest;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code GuestDeleteCommand}.
 */
public class GuestDeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredGuestList_success() {
        Guest guestToDelete = model.getFilteredGuestList().get(INDEX_FIRST_PERSON.getZeroBased());
        GuestDeleteCommand guestDeleteCommand = new GuestDeleteCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(GuestDeleteCommand.MESSAGE_DELETE_GUEST_SUCCESS,
                Messages.format(guestToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteGuest(guestToDelete);
        expectedModel.commitAddressBook();

        assertCommandSuccess(guestDeleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredGuestList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredGuestList().size() + 1);
        GuestDeleteCommand guestDeleteCommand = new GuestDeleteCommand(outOfBoundIndex);

        assertCommandFailure(guestDeleteCommand, model, Messages.MESSAGE_INVALID_GUEST_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredGuestList_success() {
        showGuestAtIndex(model, INDEX_FIRST_PERSON);

        Guest guestToDelete = model.getFilteredGuestList().get(INDEX_FIRST_PERSON.getZeroBased());
        GuestDeleteCommand guestDeleteCommand = new GuestDeleteCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(GuestDeleteCommand.MESSAGE_DELETE_GUEST_SUCCESS,
                Messages.format(guestToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteGuest(guestToDelete);
        expectedModel.commitAddressBook();
        showNoPerson(expectedModel);

        assertCommandSuccess(guestDeleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredGuestList_throwsCommandException() {
        showGuestAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getGuestList().size());

        GuestDeleteCommand guestDeleteCommand = new GuestDeleteCommand(outOfBoundIndex);

        assertCommandFailure(guestDeleteCommand, model, Messages.MESSAGE_INVALID_GUEST_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        GuestDeleteCommand guestDeleteFirstCommand = new GuestDeleteCommand(INDEX_FIRST_PERSON);
        GuestDeleteCommand guestDeleteSecondCommand = new GuestDeleteCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(guestDeleteFirstCommand.equals(guestDeleteFirstCommand));

        // same values -> returns true
        GuestDeleteCommand guestDeleteFirstCommandCopy = new GuestDeleteCommand(INDEX_FIRST_PERSON);
        assertTrue(guestDeleteFirstCommand.equals(guestDeleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(guestDeleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(guestDeleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(guestDeleteFirstCommand.equals(guestDeleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        GuestDeleteCommand guestDeleteCommand = new GuestDeleteCommand(targetIndex);
        String expected = GuestDeleteCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, guestDeleteCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered guest list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredGuestList(p -> false);

        assertTrue(model.getFilteredGuestList().isEmpty());
    }
}
