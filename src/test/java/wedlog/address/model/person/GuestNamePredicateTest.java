package wedlog.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import wedlog.address.testutil.GuestBuilder;

class GuestNamePredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        GuestNamePredicate firstPredicate = new GuestNamePredicate(firstPredicateKeywordList);
        GuestNamePredicate secondPredicate = new GuestNamePredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        GuestNamePredicate firstPredicateCopy = new GuestNamePredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(0.1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        GuestNamePredicate predicate = new GuestNamePredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new GuestBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new GuestNamePredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new GuestBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new GuestNamePredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new GuestBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new GuestNamePredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new GuestBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        GuestNamePredicate predicate = new GuestNamePredicate(Collections.emptyList());
        assertFalse(predicate.test(new GuestBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new GuestNamePredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new GuestBuilder().withName("Alice Bob").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new GuestNamePredicate(Arrays.asList("12345", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new GuestBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        GuestNamePredicate predicate = new GuestNamePredicate(keywords);

        String expected = GuestNamePredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
