package wedlog.address.logic.commands;

import static wedlog.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static wedlog.address.testutil.TypicalVendors.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import wedlog.address.model.Model;
import wedlog.address.model.ModelManager;
import wedlog.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for VendorListCommand.
 */
public class VendorListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_vendorListIsNotFiltered_showsSameVendorList() {
        assertCommandSuccess(new VendorListCommand(), model, VendorListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    // TODO: execute_listIsFiltered_showsEverything
}
