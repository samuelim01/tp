package wedlog.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import wedlog.address.testutil.GuestBuilder;

public class GuestDietaryPredicateTest {
    @Test
    public void equals() {
        List<String> predicateKeywordList = Collections.singletonList("no seafood");
        GuestDietaryPredicate predicate = new GuestDietaryPredicate(predicateKeywordList);

        // same object -> returns true
        assertEquals(predicate, predicate);

        // same values -> returns true
        GuestDietaryPredicate predicateCopy = new GuestDietaryPredicate(predicateKeywordList);
        assertEquals(predicate, predicateCopy);

        // different values -> returns false
        List<String> secondPredicateKeywordList = Collections.singletonList("no beef");
        GuestDietaryPredicate secondPredicate = new GuestDietaryPredicate(secondPredicateKeywordList);
        assertNotEquals(predicate, secondPredicate);

        // different types -> returns false
        assertNotEquals(predicate, 0.1);

        // null -> returns false
        assertNotEquals(predicate, null);
    }

    @Test
    public void test_DietaryMatchesKeywords_returnsTrue() {
        // One keyword
        GuestDietaryPredicate predicate = new GuestDietaryPredicate(Collections.singletonList("111"));
        assertTrue(predicate.test(new GuestBuilder().withDietaryRequirements("111").build()));

        // Only one matching keyword
        // TODO: Remove/keep depending on implementation
        /*
        predicate = new GuestDietaryPredicate(Arrays.asList("111", "222"));
        assertTrue(predicate.test(new GuestBuilder().withDietaryRequirements("111").build()));
        */
    }

    @Test
    public void test_DietaryAbsentKeywordEmpty_returnsTrue() {
        // empty keyword should match empty dietary field
        GuestDietaryPredicate predicate = new GuestDietaryPredicate(Collections.singletonList(""));
        assertTrue(predicate.test(new GuestBuilder().withoutDietaryRequirements().build()));
    }

    @Test
    public void test_DietaryDoesNotMatchKeywords_returnsFalse() {
        // Empty keyword with non-empty dietary requirement
        GuestDietaryPredicate predicate = new GuestDietaryPredicate(Collections.emptyList());
        assertFalse(predicate.test(new GuestBuilder().withDietaryRequirements("no beef").build()));

        // Non-empty keyword with empty dietary requirement
        predicate = new GuestDietaryPredicate(Collections.singletonList("no beef"));
        assertFalse(predicate.test(new GuestBuilder().withoutDietaryRequirements().build()));

        // Non-matching keyword (DR filters by exact match)
        predicate = new GuestDietaryPredicate(Collections.singletonList("no beef"));
        assertFalse(predicate.test(new GuestBuilder().withDietaryRequirements("no pork").build()));

        // Keywords match name, phone, email, address and table number but does not match dietary requirement
        predicate = new GuestDietaryPredicate(Arrays.asList(
                "Alice", "12345", "alice@email.com", "Jurong", "222", "12"));
        assertFalse(predicate.test(new GuestBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Jurong West").withTableNumber("12")
                .withoutDietaryRequirements().build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        GuestDietaryPredicate predicate = new GuestDietaryPredicate(keywords);

        String expected = GuestDietaryPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
