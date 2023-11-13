package wedlog.address.model.person;

import java.util.function.Predicate;

import wedlog.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Email} matches any of the input given.
 */
public class EmailPredicate implements Predicate<Person> {
    private final String input;

    /**
     * Constructor for EmailPredicate.
     */
    public EmailPredicate(String input) {
        this.input = input;
    }

    @Override
    public boolean test(Person person) {
        assert person != null : "Person passed to EmailPredicate should not be null!";
        return input.isEmpty()
                ? person.getEmail().isEmpty() // if input is "", return if field is empty
                : person.getEmail() // else check if input is contained in the field value
                .map(e -> e.value.toLowerCase().contains(input.toLowerCase())).orElse(false);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EmailPredicate)) {
            return false;
        }

        EmailPredicate otherEmailPredicate = (EmailPredicate) other;
        return input.equals(otherEmailPredicate.input);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("input", input).toString();
    }
}
