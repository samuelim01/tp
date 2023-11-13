package wedlog.address.logic.commands;

import static wedlog.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import wedlog.address.model.AddressBook;
import wedlog.address.model.Model;
import wedlog.address.model.ModelManager;
import wedlog.address.model.UserPrefs;
import wedlog.address.testutil.TypicalGuests;
import wedlog.address.testutil.TypicalVendors;

public class ClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitAddressBook();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(TypicalGuests.getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(TypicalGuests.getTypicalAddressBook(), new UserPrefs());
        expectedModel.setAddressBook(new AddressBook());
        expectedModel.commitAddressBook();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);

        model = new ModelManager(TypicalVendors.getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(TypicalVendors.getTypicalAddressBook(), new UserPrefs());
        expectedModel.setAddressBook(new AddressBook());
        expectedModel.commitAddressBook();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
