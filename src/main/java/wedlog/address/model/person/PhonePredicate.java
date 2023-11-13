package wedlog.address.model.person;

import java.util.function.Predicate;

import wedlog.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Phone} matches any of the input given.
 */
public class PhonePredicate implements Predicate<Person> {
    private final String input;

    /**
     * Constructor for PhonePredicate.
     */
    public PhonePredicate(String input) {
        this.input = input;
    }

    @Override
    public boolean test(Person person) {
        assert person != null : "Person passed to PhonePredicate should not be null!";
        return input.isEmpty()
                ? person.getPhone().isEmpty() // if input is "", return if field is empty
                : person.getPhone() // else check if input is contained in the field value
                .map(p -> p.value.toLowerCase().contains(input.toLowerCase())).orElse(false);
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
        return input.equals(otherPhonePredicate.input);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("input", input).toString();
    }
}
