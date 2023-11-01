package wedlog.address.model.person;

import java.util.function.Predicate;

import wedlog.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Guest}'s {@code TableNumber} matches any of the keyword given.
 */
public class GuestTablePredicate implements Predicate<Guest> {
    private final String input;

    /**
     * Constructor for GuestTablePredicate.
     */
    public GuestTablePredicate(String input) {
        this.input = input;
    }

    @Override
    public boolean test(Guest guest) {
        return input.isEmpty()
                ? guest.getTableNumber().isEmpty() // if input is "", return if field is empty
                : guest.getTableNumber() // else check if input is contained in the field value
                .map(tn -> tn.value.equalsIgnoreCase(input)).orElse(false);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GuestTablePredicate)) {
            return false;
        }

        GuestTablePredicate otherGuestTablePredicate = (GuestTablePredicate) other;
        return input.equals(otherGuestTablePredicate.input);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("input", input).toString();
    }
}

