package wedlog.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import wedlog.address.testutil.GuestBuilder;

class GuestTablePredicateTest {

    @Test
    public void equals() {
        String firstPredicateString = "111";
        String secondPredicateString = "222";

        GuestTablePredicate firstPredicate = new GuestTablePredicate(firstPredicateString);
        GuestTablePredicate secondPredicate = new GuestTablePredicate(secondPredicateString);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        GuestTablePredicate firstPredicateCopy = new GuestTablePredicate(firstPredicateString);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different values -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));

        // different types -> returns false
        assertFalse(firstPredicate.equals(0.1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));
    }

    @Test
    public void testAssertionGuestNonNull() {
        GuestTablePredicate pred = new GuestTablePredicate("111");

        // Non null scenario
        Guest guest = new GuestBuilder().withTableNumber("111").build();
        assertTrue(pred.test(guest));

        // Heuristic: No more than 1 invalid input in a test case
        // Null scenario
        Guest nullGuest = null;
        assertThrows(AssertionError.class, () -> pred.test(nullGuest));
    }

    @Test
    public void test_tableNumberContainsInput_returnsTrue() {
        // Exact match
        GuestTablePredicate predicate = new GuestTablePredicate("111");
        assertTrue(predicate.test(new GuestBuilder().withTableNumber("111").build()));
    }

    @Test
    public void test_tableNumberAbsentInputEmpty_returnsTrue() {
        // Empty input
        GuestTablePredicate predicate = new GuestTablePredicate("");
        assertTrue(predicate.test(new GuestBuilder().withoutTableNumber().build()));
    }

    @Test
    public void test_tableNumberAbsentInputEmpty_returnsFalse() {
        // Empty input
        GuestTablePredicate predicate = new GuestTablePredicate("");
        assertFalse(predicate.test(new GuestBuilder().withTableNumber("111").build()));
    }

    @Test
    public void test_tableNumberDoesNotContainInput_returnsFalse() {
        // Partial match
        GuestTablePredicate predicate = new GuestTablePredicate("11");
        assertFalse(predicate.test(new GuestBuilder().withTableNumber("111").build()));

        // Table number empty
        predicate = new GuestTablePredicate("111");
        assertFalse(predicate.test(new GuestBuilder().withoutTableNumber().build()));

        // Non-matching input
        predicate = new GuestTablePredicate("222");
        assertFalse(predicate.test(new GuestBuilder().withTableNumber("111").build()));
    }

    @Test
    public void toStringMethod() {
        String input = "random input";
        GuestTablePredicate predicate = new GuestTablePredicate(input);

        String expected = GuestTablePredicate.class.getCanonicalName() + "{input=" + input + "}";
        assertEquals(expected, predicate.toString());
    }
}
