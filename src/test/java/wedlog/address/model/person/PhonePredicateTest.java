package wedlog.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import wedlog.address.testutil.PersonBuilder;

class PhonePredicateTest {

    @Test
    public void equals() {
        String firstPredicateString = "111";
        String secondPredicateString = "222";

        PhonePredicate firstPredicate = new PhonePredicate(firstPredicateString);
        PhonePredicate secondPredicate = new PhonePredicate(secondPredicateString);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PhonePredicate firstPredicateCopy = new PhonePredicate(firstPredicateString);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different values -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));

        // different types -> returns false
        assertFalse(firstPredicate.equals(0.1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));
    }

    @Test
    public void testPhoneContainsKeywords_returnsTrue() {
        // Exact match
        PhonePredicate predicate = new PhonePredicate("91423611");
        assertTrue(predicate.test(new PersonBuilder().withPhone("91423611").build()));

        // Partial match
        predicate = new PhonePredicate("11");
        assertTrue(predicate.test(new PersonBuilder().withPhone("91423611").build()));
    }

    @Test
    public void testPhoneAbsentKeywordEmpty_returnsTrue() {
        // empty keyword
        PhonePredicate predicate = new PhonePredicate("");
        assertTrue(predicate.test(new PersonBuilder().withoutPhone().build()));
    }

    @Test
    public void testPhoneAbsentKeywordEmpty_returnsFalse() {
        // Phone empty
        PhonePredicate predicate = new PhonePredicate("");
        assertFalse(predicate.test(new PersonBuilder().withPhone("91423611").build()));
    }

    @Test
    public void testPhoneDoesNotContainKeywords_returnsFalse() {
        // Phone empty
        PhonePredicate predicate = new PhonePredicate("91423611");
        assertFalse(predicate.test(new PersonBuilder().withoutPhone().build()));

        // Non-matching input
        predicate = new PhonePredicate("999");
        assertFalse(predicate.test(new PersonBuilder().withPhone("91423611").build()));
    }

    @Test
    public void toStringMethod() {
        String input = "random input";
        PhonePredicate predicate = new PhonePredicate(input);

        String expected = PhonePredicate.class.getCanonicalName() + "{input=" + input + "}";
        assertEquals(expected, predicate.toString());
    }
}
