package wedlog.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import wedlog.address.testutil.GuestBuilder;

class GuestPhonePredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("111");
        List<String> secondPredicateKeywordList = Arrays.asList("111", "222");

        GuestPhonePredicate firstPredicate = new GuestPhonePredicate(firstPredicateKeywordList);
        GuestPhonePredicate secondPredicate = new GuestPhonePredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        GuestPhonePredicate firstPredicateCopy = new GuestPhonePredicate(firstPredicateKeywordList);
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
        // One keyword
        GuestPhonePredicate predicate = new GuestPhonePredicate(Collections.singletonList("91423611"));
        assertTrue(predicate.test(new GuestBuilder().withPhone("91423611").build()));

        // Only one matching keyword
        predicate = new GuestPhonePredicate(Arrays.asList("91423611", "999"));
        assertTrue(predicate.test(new GuestBuilder().withPhone("91423611").build()));
    }

    @Test
    public void testPhoneAbsentKeywordEmpty_returnsTrue() {
        // empty keyword
        GuestPhonePredicate predicate = new GuestPhonePredicate(Collections.singletonList(""));
        assertTrue(predicate.test(new GuestBuilder().withoutPhone().build()));
    }

    @Test
    public void testPhoneDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        GuestPhonePredicate predicate = new GuestPhonePredicate(Collections.emptyList());
        assertFalse(predicate.test(new GuestBuilder().withPhone("91423611").build()));

        // Phone empty
        predicate = new GuestPhonePredicate(Collections.singletonList("91423611"));
        assertFalse(predicate.test(new GuestBuilder().withoutEmail().build()));

        // Non-matching keyword
        predicate = new GuestPhonePredicate(Collections.singletonList("999"));
        assertFalse(predicate.test(new GuestBuilder().withPhone("91423611").build()));

        // Keywords match name, email, and address, but does not match phone
        predicate = new GuestPhonePredicate(Arrays.asList("Alice", "999", "alice@email.com", "Jurong", "West"));
        assertFalse(predicate.test(new GuestBuilder().withPhone("91423611").withName("Alice")
                .withEmail("alice@email.com").withAddress("Jurong West").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        GuestPhonePredicate predicate = new GuestPhonePredicate(keywords);

        String expected = GuestPhonePredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
