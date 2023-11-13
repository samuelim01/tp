package wedlog.address.model.person;

import java.util.function.Predicate;

import wedlog.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Address} contains the given input.
 */
public class AddressPredicate implements Predicate<Person> {
    private final String input;

    /**
     * Constructor for AddressPredicate.
     */
    public AddressPredicate(String input) {
        this.input = input;
    }

    @Override
    public boolean test(Person person) {
        assert person != null : "Person passed to AddressPredicate should not be null!";
        return input.isEmpty()
                ? person.getAddress().isEmpty() // if input is "", return if field is empty
                : person.getAddress() // else check if input is contained in the field value
                        .map(a -> a.value.toLowerCase().contains(input.toLowerCase())).orElse(false);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddressPredicate)) {
            return false;
        }

        AddressPredicate otherAddressPredicate = (AddressPredicate) other;
        return input.equals(otherAddressPredicate.input);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("input", input).toString();
    }
}
