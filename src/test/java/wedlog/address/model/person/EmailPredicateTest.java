package wedlog.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import wedlog.address.testutil.PersonBuilder;

class EmailPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first@example.com");
        List<String> secondPredicateKeywordList = Arrays.asList("first@example.com", "second@example.com");

        EmailPredicate firstPredicate = new EmailPredicate(firstPredicateKeywordList);
        EmailPredicate secondPredicate = new EmailPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        EmailPredicate firstPredicateCopy = new EmailPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different values -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));

        // different types -> returns false
        assertFalse(firstPredicate.equals(0.1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));
    }

    @Test
    public void test_emailContainsKeywords_returnsTrue() {
        // One keyword
        EmailPredicate predicate = new EmailPredicate(Collections.singletonList("gina@example.com"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("gina@example.com").build()));

        // Only one matching keyword
        predicate = new EmailPredicate(Arrays.asList("gina@example.com", "greg@example.com"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("gina@example.com").build()));

        // Mixed-case keywords
        predicate = new EmailPredicate(Arrays.asList("gInA@eXamPlE.coM"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("Gina@example.com").build()));
    }

    @Test
    public void test_emailAbsentKeywordEmpty_returnsTrue() {
        // empty keyword
        EmailPredicate predicate = new EmailPredicate(Collections.singletonList(""));
        assertTrue(predicate.test(new PersonBuilder().withoutEmail().build()));
    }

    @Test
    public void test_emailDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        EmailPredicate predicate = new EmailPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withEmail("gina@example.com").build()));

        // Email empty
        predicate = new EmailPredicate(Collections.singletonList("gina@example.com"));
        assertFalse(predicate.test(new PersonBuilder().withoutEmail().build()));

        // Non-matching keyword
        predicate = new EmailPredicate(Collections.singletonList("greg@example.com"));
        assertFalse(predicate.test(new PersonBuilder().withEmail("gina@example.com").build()));

        // Keywords match name, phone, and address, but does not match email
        predicate = new EmailPredicate(Arrays.asList("Alice", "12345", "Jurong", "West", "greg@example.com"));
        assertFalse(predicate.test(new PersonBuilder().withEmail("gina@example.com").withName("Alice")
                .withPhone("12345").withAddress("Jurong West").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        EmailPredicate predicate = new EmailPredicate(keywords);

        String expected = EmailPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
