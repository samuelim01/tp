package wedlog.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

        // EP1: same object -> returns true
        assertEquals(predicate, predicate);

        // EP2: same values -> returns true
        GuestDietaryPredicate predicateCopy = new GuestDietaryPredicate(predicateKeywordList);
        assertEquals(predicate, predicateCopy);

        // EP3: different values -> returns false
        List<String> secondPredicateKeywordList = Collections.singletonList("no beef");
        GuestDietaryPredicate secondPredicate = new GuestDietaryPredicate(secondPredicateKeywordList);
        assertNotEquals(predicate, secondPredicate);

        // EP4: null -> returns false
        assertNotEquals(predicate, null);
    }

    @Test
    public void test_dietaryRequirementMatchesKeywords_returnsTrue() {
        // EP1: Exact match
        // One keyword
        GuestDietaryPredicate predicate = new GuestDietaryPredicate(Collections.singletonList("111"));
        assertTrue(predicate.test(new GuestBuilder().withDietaryRequirements("111").build()));

        // Two matching keywords
        predicate = new GuestDietaryPredicate(Arrays.asList("vegan", "no peanuts"));
        assertTrue(predicate.test(new GuestBuilder().withDietaryRequirements("vegan", "no peanuts").build()));

        // EP2: Match with additional non-matching values
        // Two matching keywords (with additional non-matching value)
        predicate = new GuestDietaryPredicate(Arrays.asList("vegan", "no peanuts"));
        assertTrue(predicate.test(new GuestBuilder().withDietaryRequirements("vegan", "no peanuts", "111").build()));
    }

    @Test
    public void test_dietaryRequirementAbsentKeywordEmpty_returnsTrue() {
        // empty keyword should match empty dietary field
        GuestDietaryPredicate predicate = new GuestDietaryPredicate(Collections.singletonList(""));
        assertTrue(predicate.test(new GuestBuilder().withoutDietaryRequirements().build()));
    }

    @Test
    public void test_emptyKeywordList_returnsFalse() {
        // empty keyword list should result in Assertion Error
        GuestDietaryPredicate predicate = new GuestDietaryPredicate(Collections.emptyList());
        assertThrows(
                AssertionError.class, () -> predicate.test(new GuestBuilder()
                        .withDietaryRequirements("vegan").build()));
    }

    @Test
    public void test_dietaryRequirementDoesNotMatchKeywords_returnsFalse() {
        // EP1: No match
        // Empty keyword with non-empty dietary requirement
        GuestDietaryPredicate predicate = new GuestDietaryPredicate(Collections.singletonList(""));
        assertFalse(predicate.test(new GuestBuilder().withDietaryRequirements("no beef").build()));

        // Non-empty keyword with empty dietary requirement
        predicate = new GuestDietaryPredicate(Collections.singletonList("no beef"));
        assertFalse(predicate.test(new GuestBuilder().withoutDietaryRequirements().build()));

        // Non-matching keyword
        predicate = new GuestDietaryPredicate(Collections.singletonList("no beef"));
        assertFalse(predicate.test(new GuestBuilder().withDietaryRequirements("no pork").build()));

        // EP2: Partial match
        // Only one matching keyword
        predicate = new GuestDietaryPredicate(Arrays.asList("vegan", "no peanut"));
        assertFalse(predicate.test(new GuestBuilder().withTags("vegan").build()));

        // Keywords match name, phone, email, address and table number but does not match dietary requirement
        predicate = new GuestDietaryPredicate(Arrays.asList(
                "Alice", "12345", "alice@email.com", "Jurong", "222", "12"));
        assertFalse(predicate.test(new GuestBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Jurong West").withTableNumber("12")
                .withoutDietaryRequirements().build()));
    }

    @Test
    public void toString_success() {
        List<String> keywords = List.of("keyword");
        GuestDietaryPredicate predicate = new GuestDietaryPredicate(keywords);

        String expected = GuestDietaryPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
