package wedlog.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import wedlog.address.testutil.PersonBuilder;

class EmailPredicateTest {

    @Test
    public void equals() {
        String firstPredicateString = "first@example.com";
        String secondPredicateString = "second@example.com";

        EmailPredicate firstPredicate = new EmailPredicate(firstPredicateString);
        EmailPredicate secondPredicate = new EmailPredicate(secondPredicateString);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        EmailPredicate firstPredicateCopy = new EmailPredicate(firstPredicateString);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different values -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));

        // different types -> returns false
        assertFalse(firstPredicate.equals(0.1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));
    }

    @Test
    public void test_emailContainsInput_returnsTrue() {
        // Exact match
        EmailPredicate predicate = new EmailPredicate("gina@example.com");
        assertTrue(predicate.test(new PersonBuilder().withEmail("gina@example.com").build()));

        // Partial match
        predicate = new EmailPredicate("example.com");
        assertTrue(predicate.test(new PersonBuilder().withEmail("gina@example.com").build()));

        // Mixed-case match
        predicate = new EmailPredicate("gInA@eXamPlE.coM");
        assertTrue(predicate.test(new PersonBuilder().withEmail("Gina@example.com").build()));
    }

    @Test
    public void test_emailAbsentInputEmpty_returnsTrue() {
        // empty input
        EmailPredicate predicate = new EmailPredicate("");
        assertTrue(predicate.test(new PersonBuilder().withoutEmail().build()));
    }

    @Test
    public void test_emailAbsentInputEmpty_returnsFalse() {
        // empty input
        EmailPredicate predicate = new EmailPredicate("");
        assertFalse(predicate.test(new PersonBuilder().withEmail("email@email.com").build()));
    }

    @Test
    public void test_emailDoesNotContainInput_returnsFalse() {
        // Email empty
        EmailPredicate predicate = new EmailPredicate("gina@example.com");
        assertFalse(predicate.test(new PersonBuilder().withoutEmail().build()));

        // Non-matching input
        predicate = new EmailPredicate("greg@example.com");
        assertFalse(predicate.test(new PersonBuilder().withEmail("gina@example.com").build()));
    }

    @Test
    public void toStringMethod() {
        String input = "random input";
        EmailPredicate predicate = new EmailPredicate(input);

        String expected = EmailPredicate.class.getCanonicalName() + "{input=" + input + "}";
        assertEquals(expected, predicate.toString());
    }
}
