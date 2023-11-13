package wedlog.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static wedlog.address.logic.Messages.MESSAGE_VENDORS_LISTED_OVERVIEW;
import static wedlog.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static wedlog.address.testutil.TypicalVendors.ANNE;
import static wedlog.address.testutil.TypicalVendors.getTypicalAddressBook;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import wedlog.address.model.Model;
import wedlog.address.model.ModelManager;
import wedlog.address.model.UserPrefs;
import wedlog.address.model.person.NamePredicate;
import wedlog.address.model.person.Vendor;
import wedlog.address.testutil.Assert;

class VendorFilterCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        List<Predicate<? super Vendor>> firstPredicate =
                Collections.singletonList(new NamePredicate("keyword1"));
        List<Predicate<? super Vendor>> secondPredicate =
                Collections.singletonList(new NamePredicate("keyword2"));

        VendorFilterCommand filterFirstCommand = new VendorFilterCommand(firstPredicate);
        VendorFilterCommand filterSecondCommand = new VendorFilterCommand(secondPredicate);

        // same object -> returns true
        assertTrue(filterFirstCommand.equals(filterFirstCommand));

        // same values -> returns true
        List<Predicate<? super Vendor>> firstPredicateCopy =
                Collections.singletonList(new NamePredicate("keyword1"));
        VendorFilterCommand filterFirstCommandCopy = new VendorFilterCommand(firstPredicateCopy);
        assertTrue(filterFirstCommand.equals(filterFirstCommandCopy));

        // different types -> returns false
        assertFalse(filterFirstCommand.equals(1));

        // null -> returns false
        assertFalse(filterFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(filterFirstCommand.equals(filterSecondCommand));
    }

    @Test
    public void testAssertionPersonNonNull() {
        NamePredicate predicate = prepareNamePredicate("Alice");
        List<Predicate<? super Vendor>> predicates = Collections.singletonList(predicate);

        // Non null scenario
        assertTrue(new VendorFilterCommand(predicates) instanceof VendorFilterCommand);

        // Heuristic: No more than 1 invalid input in a test case
        // Null scenario
        List<Predicate<? super Vendor>> nullPredicates = null;
        String expectedErrMsg = "Predicates passed to VendorFilterCommand should not be null!";
        Assert.assertThrows(AssertionError.class,
                expectedErrMsg, () -> new VendorFilterCommand(nullPredicates));
    }

    @Test
    public void execute_noKeywords_noVendorFound() {
        String expectedMessage = String.format(MESSAGE_VENDORS_LISTED_OVERVIEW, 0);
        NamePredicate predicate = prepareNamePredicate(" ");
        VendorFilterCommand command = new VendorFilterCommand(Collections.singletonList(predicate));
        expectedModel.updateFilteredVendorList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredVendorList());
    }

    @Test
    public void execute_singleKeyword_singleVendorFound() {
        String expectedMessage = String.format(MESSAGE_VENDORS_LISTED_OVERVIEW, 1);
        NamePredicate predicate = prepareNamePredicate("anne");
        VendorFilterCommand command = new VendorFilterCommand(Collections.singletonList(predicate));
        expectedModel.updateFilteredVendorList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(ANNE), model.getFilteredVendorList());
    }

    @Test
    public void toStringMethod() {
        List<Predicate<? super Vendor>> predicates = Collections.singletonList(new NamePredicate("keyword1"));
        VendorFilterCommand filterCommand = new VendorFilterCommand(predicates);
        String expected = VendorFilterCommand.class.getCanonicalName() + "{predicates=" + predicates + "}";
        assertEquals(expected, filterCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code NamePredicate}.
     */
    private NamePredicate prepareNamePredicate(String userInput) {
        return new NamePredicate(userInput.trim());
    }
}
