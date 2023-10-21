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

class UndoCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    void execute_multipleUndoableStates_commandSuccess() {
        deleteFirstGuest(expectedModel);
        deleteFirstGuest(expectedModel);
        expectedModel.undoAddressBook();

        deleteFirstGuest(model);
        deleteFirstGuest(model);
        assertCommandSuccess(new UndoCommand(), model, UndoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    void execute_singleUndoableState_commandSuccess() {
        deleteFirstGuest(expectedModel);
        expectedModel.undoAddressBook();

        deleteFirstGuest(model);
        assertCommandSuccess(new UndoCommand(), model, UndoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    void execute_noUndoableStates_commandSuccess() {
        assertCommandFailure(new UndoCommand(), model, UndoCommand.MESSAGE_FAILURE);
    }

    @Test
    void execute_listIsFiltered_showsEverything() {
        deleteFirstGuest(expectedModel);
        expectedModel.undoAddressBook();

        deleteFirstGuest(model);
        showGuestAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(new UndoCommand(), model, UndoCommand.MESSAGE_SUCCESS, expectedModel);
    }

}

//@author
