package wedlog.address.model.person;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import wedlog.address.testutil.PersonBuilder;

class AddressPredicateTest {

    @Test
    public void equals() {
        String firstPredicateString = "first";
        String secondPredicateString = "second";

        AddressPredicate firstPredicate = new AddressPredicate(firstPredicateString);
        AddressPredicate secondPredicate = new AddressPredicate(secondPredicateString);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        AddressPredicate firstPredicateCopy = new AddressPredicate(firstPredicateString);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different values -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));

        // different types -> returns false
        assertFalse(firstPredicate.equals(0.1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));
    }

    @Test
    public void test_addressContainsInput_returnsTrue() {
        // Exact match
        AddressPredicate predicate = new AddressPredicate("Jurong");
        assertTrue(predicate.test(new PersonBuilder().withAddress("Jurong").build()));

        // Partial Match
        predicate = new AddressPredicate("Jurong W");
        assertTrue(predicate.test(new PersonBuilder().withAddress("Jurong West").build()));

        // Mixed-case match
        predicate = new AddressPredicate("jUroNg");
        assertTrue(predicate.test(new PersonBuilder().withAddress("Jurong West").build()));
    }

    @Test
    public void test_addressAbsentInputEmpty_returnsTrue() {
        // empty input
        AddressPredicate predicate = new AddressPredicate("");
        assertTrue(predicate.test(new PersonBuilder().withoutAddress().build()));
    }

    @Test
    public void test_addressAbsentInputEmpty_returnsFalse() {
        // empty input
        AddressPredicate predicate = new AddressPredicate("");
        assertFalse(predicate.test(new PersonBuilder().withAddress("Jurong").build()));
    }

    @Test
    public void test_addressDoesNotContainInput_returnsFalse() {
        // Address empty
        AddressPredicate predicate = new AddressPredicate("Jurong");
        assertFalse(predicate.test(new PersonBuilder().withoutAddress().build()));

        // Non-matching input
        predicate = new AddressPredicate("Singapore");
        assertFalse(predicate.test(new PersonBuilder().withAddress("Jurong West").build()));
    }

    @Test
    public void toStringMethod() {
        String input = "random input";
        AddressPredicate predicate = new AddressPredicate(input);

        String expected = AddressPredicate.class.getCanonicalName() + "{input=" + input + "}";
        assertEquals(expected, predicate.toString());
    }
}
