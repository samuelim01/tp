package wedlog.address.logic.commands;

import static wedlog.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static wedlog.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static wedlog.address.testutil.TypicalGuests.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import wedlog.address.logic.Messages;
import wedlog.address.model.AddressBook;
import wedlog.address.model.Model;
import wedlog.address.model.ModelManager;
import wedlog.address.model.UserPrefs;
import wedlog.address.model.person.Guest;
import wedlog.address.testutil.GuestBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code GuestAddCommand}.
 */
public class GuestAddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newGuestIntoEmptyAddressBook_success() {
        model = new ModelManager(new AddressBook(), new UserPrefs());
        Guest validGuest = new GuestBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addGuest(validGuest);

        assertCommandSuccess(new GuestAddCommand(validGuest), model,
                String.format(GuestAddCommand.MESSAGE_SUCCESS, Messages.format(validGuest)),
                expectedModel);
    }

    @Test
    public void execute_newGuestIntoPrefilledAddressBook_success() {
        Guest validGuest = new GuestBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addGuest(validGuest);

        assertCommandSuccess(new GuestAddCommand(validGuest), model,
                String.format(GuestAddCommand.MESSAGE_SUCCESS, Messages.format(validGuest)),
                expectedModel);
    }

    @Test
    public void execute_duplicateGuest_throwsCommandException() {
        Guest guestInList = model.getAddressBook().getGuestList().get(0);
        assertCommandFailure(new GuestAddCommand(guestInList), model,
                GuestAddCommand.MESSAGE_DUPLICATE_GUEST);
    }

}
