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
     * Returns true if ALL keywords provided match at least one of the given {@code Person}'s Tags.
     * {@code keywords} list should never be empty.
     * A keyword that is an empty string should match an empty tag field.
     * A non-empty keyword will match Tag(s) on an exact match basis.
     */
    @Override
    public boolean test(Person person) {
        assert !keywords.isEmpty(): "keywords list for TagPredicate should not be empty";
        return !keywords.isEmpty() && keywords.get(0).isEmpty() // If a keyword is empty
                ? person.getTags().isEmpty() // Return true if Tag field is also empty
                : !keywords.isEmpty() // Else if there are no empty keywords
                    ? keywords.stream().allMatch(keyword -> person.getTags().stream() // all keywords must match a Tag
                          .anyMatch(tag -> Tag.isValidTagName(keyword) // checks if any Tag matches the keyword
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
