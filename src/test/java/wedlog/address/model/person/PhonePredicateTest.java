package wedlog.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import wedlog.address.testutil.PersonBuilder;

class PhonePredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("111");
        List<String> secondPredicateKeywordList = Arrays.asList("111", "222");

        PhonePredicate firstPredicate = new PhonePredicate(firstPredicateKeywordList);
        PhonePredicate secondPredicate = new PhonePredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PhonePredicate firstPredicateCopy = new PhonePredicate(firstPredicateKeywordList);
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
        PhonePredicate predicate = new PhonePredicate(Collections.singletonList("91423611"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("91423611").build()));

        // Only one matching keyword
        predicate = new PhonePredicate(Arrays.asList("91423611", "999"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("91423611").build()));
    }

    @Test
    public void testPhoneAbsentKeywordEmpty_returnsTrue() {
        // empty keyword
        PhonePredicate predicate = new PhonePredicate(Collections.singletonList(""));
        assertTrue(predicate.test(new PersonBuilder().withoutPhone().build()));
    }

    @Test
    public void testPhoneDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        PhonePredicate predicate = new PhonePredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withPhone("91423611").build()));

        // Phone empty
        predicate = new PhonePredicate(Collections.singletonList("91423611"));
        assertFalse(predicate.test(new PersonBuilder().withoutEmail().build()));

        // Non-matching keyword
        predicate = new PhonePredicate(Collections.singletonList("999"));
        assertFalse(predicate.test(new PersonBuilder().withPhone("91423611").build()));

        // Keywords match name, email, and address, but does not match phone
        predicate = new PhonePredicate(Arrays.asList("Alice", "999", "alice@email.com", "Jurong", "West"));
        assertFalse(predicate.test(new PersonBuilder().withPhone("91423611").withName("Alice")
                .withEmail("alice@email.com").withAddress("Jurong West").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        PhonePredicate predicate = new PhonePredicate(keywords);

        String expected = PhonePredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
