package wedlog.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static wedlog.address.logic.Messages.MESSAGE_GUESTS_LISTED_OVERVIEW;
import static wedlog.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static wedlog.address.testutil.TypicalGuests.GINA;
import static wedlog.address.testutil.TypicalGuests.GREG;
import static wedlog.address.testutil.TypicalGuests.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import wedlog.address.model.Model;
import wedlog.address.model.ModelManager;
import wedlog.address.model.UserPrefs;
import wedlog.address.model.person.Guest;
import wedlog.address.model.person.GuestTablePredicate;
import wedlog.address.model.person.NamePredicate;

class GuestFilterCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        Predicate<? super Guest> firstPredicate = new GuestTablePredicate(Collections.singletonList("111"));
        Predicate<? super Guest> secondPredicate = new GuestTablePredicate(Collections.singletonList("222"));

        GuestFilterCommand filterFirstCommand = new GuestFilterCommand(firstPredicate);
        GuestFilterCommand filterSecondCommand = new GuestFilterCommand(secondPredicate);

        // same object -> returns true
        assertTrue(filterFirstCommand.equals(filterFirstCommand));

        // same values -> returns true
        GuestFilterCommand filterFirstCommandCopy = new GuestFilterCommand(firstPredicate);
        assertTrue(filterFirstCommand.equals(filterFirstCommandCopy));

        // different types -> returns false
        assertFalse(filterFirstCommand.equals(1));

        // null -> returns false
        assertFalse(filterFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(filterFirstCommand.equals(filterSecondCommand));
    }

    @Test
    public void execute_noKeywords_noGuestFound() {
        String expectedMessage = String.format(MESSAGE_GUESTS_LISTED_OVERVIEW, 0);
        NamePredicate predicate = prepareNamePredicate(" ");
        GuestFilterCommand command = new GuestFilterCommand(predicate);
        expectedModel.updateFilteredGuestList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredGuestList());
    }

    @Test
    public void execute_singleKeyword_singleGuestFound() {
        String expectedMessage = String.format(MESSAGE_GUESTS_LISTED_OVERVIEW, 1);
        NamePredicate predicate = prepareNamePredicate("gina");
        GuestFilterCommand command = new GuestFilterCommand(predicate);
        expectedModel.updateFilteredGuestList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(GINA), model.getFilteredGuestList());
    }

    @Test
    public void execute_multipleKeywords_multipleGuestFound() {
        String expectedMessage = String.format(MESSAGE_GUESTS_LISTED_OVERVIEW, 2);
        NamePredicate predicate = prepareNamePredicate("gina greg");
        GuestFilterCommand command = new GuestFilterCommand(predicate);
        expectedModel.updateFilteredGuestList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(GINA, GREG), model.getFilteredGuestList());
    }

    @Test
    public void toStringMethod() {
        Predicate<? super Guest> predicate = new GuestTablePredicate(Arrays.asList("111", "222"));
        GuestFilterCommand filterCommand = new GuestFilterCommand(predicate);
        String expected = GuestFilterCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, filterCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code NamePredicate}.
     */
    private NamePredicate prepareNamePredicate(String userInput) {
        return new NamePredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
