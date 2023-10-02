package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Guest's dietary requirements in WedLog.
 * Guarantees: immutable; is always valid
 */
public class DietaryRequirements {
    public final String value;

    public DietaryRequirements(String remark) {
        requireNonNull(remark);
        value = remark;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DietaryRequirements // instanceof handles nulls
                && value.equals(((DietaryRequirements) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}