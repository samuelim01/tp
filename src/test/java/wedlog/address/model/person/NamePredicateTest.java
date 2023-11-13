package wedlog.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import wedlog.address.testutil.Assert;
import wedlog.address.testutil.PersonBuilder;

class NamePredicateTest {

    @Test
    public void equals() {
        String firstPredicateString = "first";
        String secondPredicateString = "second";

        NamePredicate firstPredicate = new NamePredicate(firstPredicateString);
        NamePredicate secondPredicate = new NamePredicate(secondPredicateString);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NamePredicate firstPredicateCopy = new NamePredicate(firstPredicateString);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(0.1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void testAssertionPersonNonNull() {
        NamePredicate pred = new NamePredicate("Alice");

        // EP1: Non null scenario
        Person person = new PersonBuilder().withName("Alice").build();
        assertTrue(pred.test(person));

        // Heuristic: No more than 1 invalid input in a test case
        // EP2: Null scenario
        Person nullPerson = null;
        Assert.assertThrows(AssertionError.class,
                "Person passed to NamePredicate should not be null!", () -> pred.test(nullPerson));
    }

    @Test
    public void test_nameContainsInput_returnsTrue() {
        // EP1: Exact match
        NamePredicate predicate = new NamePredicate("Alice");
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").build()));

        // EP2: Partial match
        predicate = new NamePredicate("Alice C");
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").build()));

        // EP3: Mixed-case input
        predicate = new NamePredicate("aLIce bOB");
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // EP4: Empty input
        NamePredicate predicate = new NamePredicate("");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // EP5: Non-matching input
        predicate = new NamePredicate("Carol");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void toStringMethod() {
        String input = "random input";
        NamePredicate predicate = new NamePredicate(input);

        String expected = NamePredicate.class.getCanonicalName() + "{input=" + input + "}";
        assertEquals(expected, predicate.toString());
    }
}
