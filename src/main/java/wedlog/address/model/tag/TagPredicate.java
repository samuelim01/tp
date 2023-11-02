package wedlog.address.model.tag;

import java.util.List;
import java.util.function.Predicate;

import wedlog.address.commons.util.ToStringBuilder;
import wedlog.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Tags} matches any of the keywords given.
 */
public class TagPredicate implements Predicate<Person> {
    private final List<String> keywords;

    /**
     * Constructor for TagPredicate.
     */
    public TagPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    /**
     * Checks if the Tags(s) of the Guest parameter matches ALL keywords.
     * Empty keyword should match empty tag field.
     * Otherwise, matching should be conducted on exact match basis.
     */
    @Override
    public boolean test(Person person) {
        return !keywords.isEmpty() && keywords.get(0).isEmpty()
                ? person.getTags().isEmpty()
                : !keywords.isEmpty()
                    ? keywords.stream().allMatch(keyword -> person.getTags().stream() // all keywords must match a Tag
                          .anyMatch(tag -> Tag.isValidTagName(keyword) // checks if any tag matches the keyword
                                  && tag.equals(new Tag(keyword))))
                    : false;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TagPredicate)) {
            return false;
        }

        TagPredicate otherTagPredicate = (TagPredicate) other;
        return keywords.equals(otherTagPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
