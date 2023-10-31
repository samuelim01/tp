package wedlog.address.model.person;

import java.util.function.Predicate;

import wedlog.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Name} contains the given input.
 */
public class NamePredicate implements Predicate<Person> {
    private final String input;

    /**
     * Constructor for NamePredicate.
     */
    public NamePredicate(String input) {
        this.input = input;
    }

    @Override
    public boolean test(Person person) {
        return input.isEmpty()
                ? false // if input is "", return false
                : person.getName().fullName.toLowerCase().contains(input.toLowerCase());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NamePredicate)) {
            return false;
        }

        NamePredicate otherNamePredicate = (NamePredicate) other;
        return input.equals(otherNamePredicate.input);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("input", input).toString();
    }
}
