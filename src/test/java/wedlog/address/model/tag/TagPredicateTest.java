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
    public void test_tagMatchesKeywords_returnsTrue() {
        // One matching keyword
        TagPredicate predicate = new TagPredicate(Collections.singletonList("friend"));
        assertTrue(predicate.test(new GuestBuilder().withTags("friend").build()));

        // Two matching keywords
        predicate = new TagPredicate(Arrays.asList("111", "222"));
        assertTrue(predicate.test(new GuestBuilder().withTags("111", "222").build()));

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
    public void test_tagDoesNotMatchKeywords_returnsFalse() {
        // Empty keyword with non-empty tag requirement
        TagPredicate predicate = new TagPredicate(Collections.singletonList(""));
        assertFalse(predicate.test(new GuestBuilder().withTags("friend").build()));

        // Non-empty keyword with empty tag
        predicate = new TagPredicate(Collections.singletonList("friend"));
        assertFalse(predicate.test(new GuestBuilder().withoutTags().build()));

        // Non-matching keyword
        predicate = new TagPredicate(Collections.singletonList("friend"));
        assertFalse(predicate.test(new GuestBuilder().withTags("family").build()));

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
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1");
        TagPredicate predicate = new TagPredicate(keywords);

        String expected = TagPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
