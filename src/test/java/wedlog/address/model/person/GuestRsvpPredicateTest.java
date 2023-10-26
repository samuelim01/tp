package wedlog.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import wedlog.address.testutil.GuestBuilder;

class GuestRsvpPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("yes");
        List<String> secondPredicateKeywordList = Arrays.asList("yes", "no");

        GuestRsvpPredicate firstPredicate = new GuestRsvpPredicate(firstPredicateKeywordList);
        GuestRsvpPredicate secondPredicate = new GuestRsvpPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        GuestRsvpPredicate firstPredicateCopy = new GuestRsvpPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different values -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));

        // different types -> returns false
        assertFalse(firstPredicate.equals(0.1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));
    }

    @Test
    public void test_rsvpContainsKeywords_returnsTrue() {
        // One keyword
        GuestRsvpPredicate predicate = new GuestRsvpPredicate(Collections.singletonList("yes"));
        assertTrue(predicate.test(new GuestBuilder().withRsvpStatus("yes").build()));

        // Only one matching keyword
        predicate = new GuestRsvpPredicate(Arrays.asList("no", "yes"));
        assertTrue(predicate.test(new GuestBuilder().withRsvpStatus("yes").build()));

        // Mixed-case keywords
        predicate = new GuestRsvpPredicate(Arrays.asList("yEs", "nO"));
        assertTrue(predicate.test(new GuestBuilder().withRsvpStatus("yes").build()));
    }

    @Test
    public void test_rsvpDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        GuestRsvpPredicate predicate = new GuestRsvpPredicate(Collections.emptyList());
        assertFalse(predicate.test(new GuestBuilder().withRsvpStatus("yes").build()));

        // Rsvp Status unknown
        predicate = new GuestRsvpPredicate(Collections.singletonList("yes"));
        assertFalse(predicate.test(new GuestBuilder().withUnknownRsvpStatus().build()));

        // Non-matching keyword
        predicate = new GuestRsvpPredicate(Collections.singletonList("yes"));
        assertFalse(predicate.test(new GuestBuilder().withRsvpStatus("no").build()));

        // Keywords match name, phone, email, and address, but does not match rsvp status
        predicate = new GuestRsvpPredicate(Arrays.asList("Alice", "12345", "alice@email.com", "Jurong", "yes"));
        assertFalse(predicate.test(new GuestBuilder().withRsvpStatus("no").withName("Alice")
                .withPhone("12345").withEmail("alice@email.com").withAddress("Jurong West").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        GuestRsvpPredicate predicate = new GuestRsvpPredicate(keywords);

        String expected = GuestRsvpPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
