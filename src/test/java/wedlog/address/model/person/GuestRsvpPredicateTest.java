package wedlog.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import wedlog.address.testutil.GuestBuilder;

class GuestRsvpPredicateTest {

    @Test
    public void equals() {
        String firstPredicateString = "yes";
        String secondPredicateString = "no";

        GuestRsvpPredicate firstPredicate = new GuestRsvpPredicate(firstPredicateString);
        GuestRsvpPredicate secondPredicate = new GuestRsvpPredicate(secondPredicateString);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        GuestRsvpPredicate firstPredicateCopy = new GuestRsvpPredicate(firstPredicateString);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different values -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));

        // different types -> returns false
        assertFalse(firstPredicate.equals(0.1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));
    }

    @Test
    public void test_rsvpContainsInput_returnsTrue() {
        // Exact match
        GuestRsvpPredicate predicate = new GuestRsvpPredicate("yes");
        assertTrue(predicate.test(new GuestBuilder().withRsvpStatus("yes").build()));

        // Mixed-case keywords
        predicate = new GuestRsvpPredicate("yEs");
        assertTrue(predicate.test(new GuestBuilder().withRsvpStatus("yes").build()));
    }

    @Test
    public void test_rsvpAbsentInput_returnsTrue() {
        // Empty input
        GuestRsvpPredicate predicate = new GuestRsvpPredicate("");
        assertTrue(predicate.test(new GuestBuilder().withRsvpStatus("unknown").build()));
    }

    @Test
    public void test_rsvpAbsentInput_returnsFalse() {
        // Empty input
        GuestRsvpPredicate predicate = new GuestRsvpPredicate("");
        assertFalse(predicate.test(new GuestBuilder().withRsvpStatus("yes").build()));
    }

    @Test
    public void test_rsvpDoesNotContainInput_returnsFalse() {
        // Rsvp Status unknown
        GuestRsvpPredicate predicate = new GuestRsvpPredicate("yes");
        assertFalse(predicate.test(new GuestBuilder().withUnknownRsvpStatus().build()));

        // Non-exact match keyword
        predicate = new GuestRsvpPredicate("ye");
        assertFalse(predicate.test(new GuestBuilder().withRsvpStatus("yes").build()));
    }

    @Test
    public void toStringMethod() {
        String input = "random input";
        GuestRsvpPredicate predicate = new GuestRsvpPredicate(input);

        String expected = GuestRsvpPredicate.class.getCanonicalName() + "{input=" + input + "}";
        assertEquals(expected, predicate.toString());
    }
}
