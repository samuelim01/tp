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

public class TagPredicateTest {
    @Test
    public void equals() {
        List<String> predicateKeywordList = Collections.singletonList("friend");
        TagPredicate predicate = new TagPredicate(predicateKeywordList);

        // same object -> returns true
        assertEquals(predicate, predicate);

        // same values -> returns true
        TagPredicate predicateCopy = new TagPredicate(predicateKeywordList);
        assertEquals(predicate, predicateCopy);

        // different values -> returns false
        List<String> secondPredicateKeywordList = Collections.singletonList("family");
        TagPredicate secondPredicate = new TagPredicate(secondPredicateKeywordList);
        assertNotEquals(predicate, secondPredicate);

        // different types -> returns false
        assertNotEquals(predicate, 0.1);

        // null -> returns false
        assertNotEquals(predicate, null);
    }

    @Test
    public void test_TagMatchesKeywords_returnsTrue() {
        // One keyword
        TagPredicate predicate = new TagPredicate(Collections.singletonList("friend"));
        assertTrue(predicate.test(new GuestBuilder().withTags("friend").build()));

        // Only one matching keyword
        // TODO: Remove/keep depending on implementation
        /*
        predicate = new TagPredicate(Arrays.asList("111", "222"));
        assertTrue(predicate.test(new GuestBuilder().withTags("111").build()));
        */
    }

    @Test
    public void test_TagAbsentKeywordEmpty_returnsTrue() {
        // empty keyword should match empty tag field
        TagPredicate predicate = new TagPredicate(Collections.singletonList(""));
        assertTrue(predicate.test(new GuestBuilder().withoutTags().build()));
    }

    @Test
    public void test_TagDoesNotMatchKeywords_returnsFalse() {
        // Empty keyword with non-empty tag requirement
        TagPredicate predicate = new TagPredicate(Collections.emptyList());
        assertFalse(predicate.test(new GuestBuilder().withTags("friend").build()));

        // Non-empty keyword with empty tag
        predicate = new TagPredicate(Collections.singletonList("friend"));
        assertFalse(predicate.test(new GuestBuilder().withoutTags().build()));

        // Non-matching keyword (tag filters by exact match)
        predicate = new TagPredicate(Collections.singletonList("friend"));
        assertFalse(predicate.test(new GuestBuilder().withTags("family").build()));

        // Keywords match name, phone, email, address and table number but does not match tag
        predicate = new TagPredicate(Arrays.asList(
                "Alice", "12345", "alice@email.com", "Jurong", "222", "12"));
        assertFalse(predicate.test(new GuestBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Jurong West").withTableNumber("12")
                .withoutTags().build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        TagPredicate predicate = new TagPredicate(keywords);

        String expected = TagPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
