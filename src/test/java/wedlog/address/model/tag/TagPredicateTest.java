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

import wedlog.address.model.person.Person;
import wedlog.address.testutil.GuestBuilder;
import wedlog.address.testutil.PersonBuilder;

public class TagPredicateTest {
    @Test
    public void equals() {
        List<String> predicateKeywordList = Collections.singletonList("friend");
        TagPredicate predicate = new TagPredicate(predicateKeywordList);

        // EP1: same object -> returns true
        assertEquals(predicate, predicate);

        // EP2: different object but same values -> returns true
        TagPredicate predicateCopy = new TagPredicate(predicateKeywordList);
        assertEquals(predicate, predicateCopy);

        // EP3: different values -> returns false
        List<String> secondPredicateKeywordList = Collections.singletonList("family");
        TagPredicate secondPredicate = new TagPredicate(secondPredicateKeywordList);
        assertNotEquals(predicate, secondPredicate);

        // EP4: null -> returns false
        assertNotEquals(predicate, null);
    }

    @Test
    public void testAssertionPersonNonNull() {
        TagPredicate pred = new TagPredicate(Collections.singletonList("friend"));

        // Non null scenario
        Person person = new PersonBuilder().withTags("friend").build();
        assertTrue(pred.test(person));

        // Heuristic: No more than 1 invalid input in a test case
        // Null scenario
        Person nullPerson = null;
        assertThrows(AssertionError.class, () -> pred.test(nullPerson));
    }

    @Test
    public void test_tagMatchesKeywords_returnsTrue() {
        // EP1: Exact match
        // One matching keyword
        TagPredicate predicate = new TagPredicate(Collections.singletonList("friend"));
        assertTrue(predicate.test(new GuestBuilder().withTags("friend").build()));

        // Two matching keywords
        predicate = new TagPredicate(Arrays.asList("111", "222"));
        assertTrue(predicate.test(new GuestBuilder().withTags("111", "222").build()));

        // EP2: Match with additional non-matching values
        // One matching keyword (with additional non-matching value)
        predicate = new TagPredicate(Collections.singletonList("friend"));
        assertTrue(predicate.test(new GuestBuilder().withTags("friend", "family").build()));

        // Two matching keywords (with additional non-matching value)
        predicate = new TagPredicate(Arrays.asList("111", "222"));
        assertTrue(predicate.test(new GuestBuilder().withTags("111", "222", "333").build()));
    }

    @Test
    public void test_tagAbsentKeywordEmpty_returnsTrue() {
        // empty keyword should match empty tag field
        TagPredicate predicate = new TagPredicate(Collections.singletonList(""));
        assertTrue(predicate.test(new GuestBuilder().withoutTags().build()));
    }

    @Test
    public void test_emptyKeywordList_returnsFalse() {
        // empty keyword list should result in Assertion Error
        TagPredicate predicate = new TagPredicate(Collections.emptyList());
        assertThrows(AssertionError.class, () -> predicate.test(new GuestBuilder().withTags("friend").build()));
    }

    @Test
    public void test_tagDoesNotMatchKeywords_returnsFalse() {
        // EP1: No match
        // Empty keyword with non-empty tag requirement
        TagPredicate predicate = new TagPredicate(Collections.singletonList(""));
        assertFalse(predicate.test(new GuestBuilder().withTags("friend").build()));

        // Non-empty keyword with empty tag
        predicate = new TagPredicate(Collections.singletonList("friend"));
        assertFalse(predicate.test(new GuestBuilder().withoutTags().build()));

        // Non-matching keyword
        predicate = new TagPredicate(Collections.singletonList("friend"));
        assertFalse(predicate.test(new GuestBuilder().withTags("family").build()));

        // EP2: Partial match
        // Only one matching keyword
        predicate = new TagPredicate(Arrays.asList("friends", "family"));
        assertFalse(predicate.test(new GuestBuilder().withTags("family").build()));

        // Keywords match name, phone, email, address and table number but does not match tag
        predicate = new TagPredicate(Arrays.asList(
                "Alice", "12345", "alice@email.com", "Jurong", "222", "12"));
        assertFalse(predicate.test(new GuestBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Jurong West").withTableNumber("12")
                .withoutTags().build()));
    }

    @Test
    public void toString_success() {
        List<String> keywords = List.of("keyword1");
        TagPredicate predicate = new TagPredicate(keywords);

        String expected = TagPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
