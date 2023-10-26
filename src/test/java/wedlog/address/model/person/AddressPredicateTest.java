package wedlog.address.model.person;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import wedlog.address.testutil.PersonBuilder;

class AddressPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        AddressPredicate firstPredicate = new AddressPredicate(firstPredicateKeywordList);
        AddressPredicate secondPredicate = new AddressPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        AddressPredicate firstPredicateCopy = new AddressPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different values -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));

        // different types -> returns false
        assertFalse(firstPredicate.equals(0.1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));
    }

    @Test
    public void test_addressContainsKeywords_returnsTrue() {
        // One keyword
        AddressPredicate predicate = new AddressPredicate(Collections.singletonList("Jurong"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("Jurong West").build()));

        // Multiple keywords
        predicate = new AddressPredicate(Arrays.asList("Jurong", "West"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("Jurong West").build()));

        // Only one matching keyword
        predicate = new AddressPredicate(Arrays.asList("West", "Singapore"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("Jurong Singapore").build()));

        // Mixed-case keywords
        predicate = new AddressPredicate(Arrays.asList("jUroNg", "wEst"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("Jurong West").build()));
    }

    @Test
    public void test_addressAbsentKeywordEmpty_returnsTrue() {
        // empty keyword
        AddressPredicate predicate = new AddressPredicate(Collections.singletonList(""));
        assertTrue(predicate.test(new PersonBuilder().withoutAddress().build()));
    }

    @Test
    public void test_addressDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        AddressPredicate predicate = new AddressPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withAddress("Jurong").build()));

        // Address empty
        predicate = new AddressPredicate(Collections.singletonList("Jurong"));
        assertFalse(predicate.test(new PersonBuilder().withoutAddress().build()));

        // Non-matching keyword
        predicate = new AddressPredicate(Collections.singletonList("Singapore"));
        assertFalse(predicate.test(new PersonBuilder().withAddress("Jurong West").build()));

        // Keywords match name, phone, and email, but does not match address
        predicate = new AddressPredicate(Arrays.asList("Alice", "12345", "alice@email.com", "Jurong"));
        assertFalse(predicate.test(new PersonBuilder().withAddress("Bukit Timah").withName("Alice")
                .withPhone("12345").withEmail("alice@email.com").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        AddressPredicate predicate = new AddressPredicate(keywords);

        String expected = AddressPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
