package wedlog.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import wedlog.address.commons.util.StringUtil;
import wedlog.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Phone} matches any of the keywords given.
 */
public class PhonePredicate implements Predicate<Person> {
    private final List<String> keywords;

    /**
     * Constructor for PhonePredicate.
     */
    public PhonePredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return !keywords.isEmpty() && keywords.get(0).isEmpty()
                ? person.getPhone().isEmpty()
                : keywords.stream().anyMatch(keyword -> person.getPhone()
                    .map(a -> StringUtil.containsWordIgnoreCase(a.value, keyword))
                    .orElse(false));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PhonePredicate)) {
            return false;
        }

        PhonePredicate otherPhonePredicate = (PhonePredicate) other;
        return keywords.equals(otherPhonePredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
