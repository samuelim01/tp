package wedlog.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import wedlog.address.testutil.GuestBuilder;

class GuestTablePredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("111");
        List<String> secondPredicateKeywordList = Arrays.asList("111", "222");

        GuestTablePredicate firstPredicate = new GuestTablePredicate(firstPredicateKeywordList);
        GuestTablePredicate secondPredicate = new GuestTablePredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        GuestTablePredicate firstPredicateCopy = new GuestTablePredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different values -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));

        // different types -> returns false
        assertFalse(firstPredicate.equals(0.1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));
    }

    @Test
    public void test_tableNumberContainsKeywords_returnsTrue() {
        // One keyword
        GuestTablePredicate predicate = new GuestTablePredicate(Collections.singletonList("111"));
        assertTrue(predicate.test(new GuestBuilder().withTableNumber("111").build()));

        // Only one matching keyword
        predicate = new GuestTablePredicate(Arrays.asList("111", "222"));
        assertTrue(predicate.test(new GuestBuilder().withTableNumber("111").build()));
    }

    @Test
    public void test_tableNumberAbsentKeywordEmpty_returnsTrue() {
        // empty keyword
        GuestTablePredicate predicate = new GuestTablePredicate(Collections.singletonList(""));
        assertTrue(predicate.test(new GuestBuilder().withoutTableNumber().build()));
    }

    @Test
    public void test_tableNumberDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        GuestTablePredicate predicate = new GuestTablePredicate(Collections.emptyList());
        assertFalse(predicate.test(new GuestBuilder().withTableNumber("111").build()));

        // Table number empty
        predicate = new GuestTablePredicate(Collections.singletonList("111"));
        assertFalse(predicate.test(new GuestBuilder().withoutTableNumber().build()));

        // Non-matching keyword
        predicate = new GuestTablePredicate(Collections.singletonList("222"));
        assertFalse(predicate.test(new GuestBuilder().withTableNumber("111").build()));

        // Keywords match name, phone, email, and address but does not match table number
        predicate = new GuestTablePredicate(Arrays.asList("Alice", "12345", "alice@email.com", "Jurong", "222"));
        assertFalse(predicate.test(new GuestBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Jurong West").withTableNumber("111").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        GuestTablePredicate predicate = new GuestTablePredicate(keywords);

        String expected = GuestTablePredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
