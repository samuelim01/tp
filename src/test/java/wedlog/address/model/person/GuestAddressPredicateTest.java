package wedlog.address.model.person;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import wedlog.address.testutil.GuestBuilder;

class GuestAddressPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        GuestAddressPredicate firstPredicate = new GuestAddressPredicate(firstPredicateKeywordList);
        GuestAddressPredicate secondPredicate = new GuestAddressPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        GuestAddressPredicate firstPredicateCopy = new GuestAddressPredicate(firstPredicateKeywordList);
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
        GuestAddressPredicate predicate = new GuestAddressPredicate(Collections.singletonList("Jurong"));
        assertTrue(predicate.test(new GuestBuilder().withAddress("Jurong West").build()));

        // Multiple keywords
        predicate = new GuestAddressPredicate(Arrays.asList("Jurong", "West"));
        assertTrue(predicate.test(new GuestBuilder().withAddress("Jurong West").build()));

        // Only one matching keyword
        predicate = new GuestAddressPredicate(Arrays.asList("West", "Singapore"));
        assertTrue(predicate.test(new GuestBuilder().withAddress("Jurong Singapore").build()));

        // Mixed-case keywords
        predicate = new GuestAddressPredicate(Arrays.asList("jUroNg", "wEst"));
        assertTrue(predicate.test(new GuestBuilder().withAddress("Jurong West").build()));
    }

    @Test
    public void test_addressAbsentKeywordEmpty_returnsTrue() {
        // empty keyword
        GuestAddressPredicate predicate = new GuestAddressPredicate(Collections.singletonList(""));
        assertTrue(predicate.test(new GuestBuilder().withoutAddress().build()));
    }

    @Test
    public void test_addressDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        GuestAddressPredicate predicate = new GuestAddressPredicate(Collections.emptyList());
        assertFalse(predicate.test(new GuestBuilder().withAddress("Jurong").build()));

        // Address empty
        predicate = new GuestAddressPredicate(Collections.singletonList("Jurong"));
        assertFalse(predicate.test(new GuestBuilder().withoutAddress().build()));

        // Non-matching keyword
        predicate = new GuestAddressPredicate(Collections.singletonList("Singapore"));
        assertFalse(predicate.test(new GuestBuilder().withAddress("Jurong West").build()));

        // Keywords match name, phone, and email, but does not match address
        predicate = new GuestAddressPredicate(Arrays.asList("Alice", "12345", "alice@email.com", "Jurong"));
        assertFalse(predicate.test(new GuestBuilder().withAddress("Bukit Timah").withName("Alice")
                .withPhone("12345").withEmail("alice@email.com").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        GuestAddressPredicate predicate = new GuestAddressPredicate(keywords);

        String expected = GuestAddressPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
