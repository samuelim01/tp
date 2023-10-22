package wedlog.address.logic.commands;

import static wedlog.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static wedlog.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static wedlog.address.logic.commands.CommandTestUtil.deleteFirstGuest;
import static wedlog.address.logic.commands.CommandTestUtil.showGuestAtIndex;
import static wedlog.address.testutil.TypicalGuests.getTypicalAddressBook;
import static wedlog.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import wedlog.address.model.Model;
import wedlog.address.model.ModelManager;
import wedlog.address.model.UserPrefs;

//@@author samuelim01-reused
// Reused from Address Book(Level 4) with minor modifications.

class RedoCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    void execute_multipleRedoableStates_commandSuccess() {
        deleteFirstGuest(expectedModel);
        deleteFirstGuest(expectedModel);
        expectedModel.undoAddressBook();
        expectedModel.undoAddressBook();
        expectedModel.redoAddressBook();

        deleteFirstGuest(model);
        deleteFirstGuest(model);
        model.undoAddressBook();
        model.undoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    void execute_singleRedoableState_commandSuccess() {
        deleteFirstGuest(expectedModel);
        expectedModel.undoAddressBook();
        expectedModel.redoAddressBook();

        deleteFirstGuest(model);
        model.undoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    void execute_noRedoableStates_commandSuccess() {
        assertCommandFailure(new RedoCommand(), model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    void execute_listIsFiltered_showsEverything() {
        deleteFirstGuest(expectedModel);
        expectedModel.undoAddressBook();
        expectedModel.redoAddressBook();

        deleteFirstGuest(model);
        model.undoAddressBook();
        showGuestAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(new RedoCommand(), model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }
}

//@author
