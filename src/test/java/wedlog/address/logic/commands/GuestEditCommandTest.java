package wedlog.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static wedlog.address.logic.commands.CommandTestUtil.DESC_GIA;
import static wedlog.address.logic.commands.CommandTestUtil.DESC_BOB_GUEST;
import static wedlog.address.logic.commands.CommandTestUtil.EMPTY_STRING;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static wedlog.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static wedlog.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static wedlog.address.logic.commands.CommandTestUtil.showGuestAtIndex;
import static wedlog.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static wedlog.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static wedlog.address.testutil.TypicalGuests.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import wedlog.address.commons.core.index.Index;
import wedlog.address.logic.Messages;
import wedlog.address.logic.commands.GuestEditCommand.EditGuestDescriptor;
import wedlog.address.model.AddressBook;
import wedlog.address.model.Model;
import wedlog.address.model.ModelManager;
import wedlog.address.model.UserPrefs;
import wedlog.address.model.person.Guest;
import wedlog.address.testutil.EditGuestDescriptorBuilder;
import wedlog.address.testutil.GuestBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for GuestEditCommand.
 */
public class GuestEditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Guest editedGuest = new GuestBuilder().build();
        EditGuestDescriptor descriptor = new EditGuestDescriptorBuilder(editedGuest).build();
        GuestEditCommand guestEditCommand = new GuestEditCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(GuestEditCommand.MESSAGE_EDIT_GUEST_SUCCESS, Messages.format(editedGuest));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setGuest(model.getFilteredGuestList().get(0), editedGuest);
        expectedModel.commitAddressBook();

        assertCommandSuccess(guestEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastGuest = Index.fromOneBased(model.getFilteredGuestList().size());
        Guest lastGuest = model.getFilteredGuestList().get(indexLastGuest.getZeroBased());

        GuestBuilder guestInList = new GuestBuilder(lastGuest);
        Guest editedGuest = guestInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        // TODO: REMOVE
        // EditGuestDescriptor descriptor = new EditGuestDescriptorBuilder().withName(VALID_NAME_BOB)
        //         .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditGuestDescriptor descriptor = new EditGuestDescriptorBuilder(editedGuest).build();

        GuestEditCommand guestEditCommand = new GuestEditCommand(indexLastGuest, descriptor);

        String expectedMessage = String.format(GuestEditCommand.MESSAGE_EDIT_GUEST_SUCCESS, Messages.format(editedGuest));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setGuest(lastGuest, editedGuest);
        expectedModel.commitAddressBook();

        assertCommandSuccess(guestEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        GuestEditCommand guestEditCommand = new GuestEditCommand(INDEX_FIRST_PERSON, new EditGuestDescriptor());
        Guest editedGuest = model.getFilteredGuestList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(GuestEditCommand.MESSAGE_EDIT_GUEST_SUCCESS, Messages.format(editedGuest));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.commitAddressBook();

        assertCommandSuccess(guestEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_emptyFieldSpecifiedUnfilteredList_success() {
        Guest guestToEdit = model.getFilteredGuestList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(GuestEditCommand.MESSAGE_EDIT_GUEST_SUCCESS, Messages.format(guestToEdit));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        GuestBuilder guestInList = new GuestBuilder(guestToEdit);
        Guest editedGuest = guestInList.withoutPhone().withoutEmail().withoutAddress().withUnknownRsvpStatus()
                .withNullDietaryRequirements().withoutTableNumber().build();
        expectedModel.setGuest(guestToEdit, editedGuest);
        expectedModel.commitAddressBook();

        EditGuestDescriptor descriptor = new EditGuestDescriptorBuilder(guestToEdit).withPhone(EMPTY_STRING)
                .withEmail(EMPTY_STRING).withAddress(EMPTY_STRING).withRsvp(EMPTY_STRING).withDietary(EMPTY_STRING)
                .withTable(EMPTY_STRING).build();
        GuestEditCommand guestEditCommand = new GuestEditCommand(INDEX_FIRST_PERSON, descriptor);
        assertCommandSuccess(guestEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showGuestAtIndex(model, INDEX_FIRST_PERSON);

        Guest guestInFilteredList = model.getFilteredGuestList().get(INDEX_FIRST_PERSON.getZeroBased());
        Guest editedGuest = new GuestBuilder(guestInFilteredList).withName(VALID_NAME_BOB).build();
        GuestEditCommand guestEditCommand = new GuestEditCommand(INDEX_FIRST_PERSON,
                new EditGuestDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(GuestEditCommand.MESSAGE_EDIT_GUEST_SUCCESS, Messages.format(editedGuest));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setGuest(model.getFilteredGuestList().get(0), editedGuest);
        expectedModel.commitAddressBook();

        assertCommandSuccess(guestEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateGuestUnfilteredList_failure() {
        Guest firstGuest = model.getFilteredGuestList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditGuestDescriptor descriptor = new EditGuestDescriptorBuilder(firstGuest).build();
        GuestEditCommand guestEditCommand = new GuestEditCommand(INDEX_SECOND_PERSON, descriptor);

        assertCommandFailure(guestEditCommand, model, GuestEditCommand.MESSAGE_DUPLICATE_GUEST);
    }

    @Test
    public void execute_duplicateGuestFilteredList_failure() {
        showGuestAtIndex(model, INDEX_FIRST_PERSON);

        // edit guest in filtered list into a duplicate in address book
        Guest guestInList = model.getAddressBook().getGuestList().get(INDEX_SECOND_PERSON.getZeroBased());
        GuestEditCommand guestEditCommand = new GuestEditCommand(INDEX_FIRST_PERSON,
                new EditGuestDescriptorBuilder(guestInList).build());

        assertCommandFailure(guestEditCommand, model, GuestEditCommand.MESSAGE_DUPLICATE_GUEST);
    }

    @Test
    public void execute_invalidGuestIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredGuestList().size() + 1);
        EditGuestDescriptor descriptor = new EditGuestDescriptorBuilder().withName(VALID_NAME_BOB).build();
        GuestEditCommand guestEditCommand = new GuestEditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(guestEditCommand, model, Messages.MESSAGE_INVALID_GUEST_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidGuestIndexFilteredList_failure() {
        showGuestAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getGuestList().size());

        GuestEditCommand guestEditCommand = new GuestEditCommand(outOfBoundIndex,
                new EditGuestDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(guestEditCommand, model, Messages.MESSAGE_INVALID_GUEST_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        // TODO: Replace AMY with GIA once PR merged
        final GuestEditCommand standardCommand = new GuestEditCommand(INDEX_FIRST_PERSON, DESC_GIA);

        // same values -> returns true
        EditGuestDescriptor copyDescriptor = new EditGuestDescriptor(DESC_GIA);
        GuestEditCommand commandWithSameValues = new GuestEditCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new GuestEditCommand(INDEX_SECOND_PERSON, DESC_GIA)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new GuestEditCommand(INDEX_FIRST_PERSON, DESC_BOB_GUEST)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditGuestDescriptor editGuestDescriptor = new EditGuestDescriptor();
        GuestEditCommand guestEditCommand = new GuestEditCommand(index, editGuestDescriptor);
        String expected = GuestEditCommand.class.getCanonicalName() + "{index=" + index + ", editGuestDescriptor="
                + editGuestDescriptor + "}";
        assertEquals(expected, guestEditCommand.toString());
    }

}
