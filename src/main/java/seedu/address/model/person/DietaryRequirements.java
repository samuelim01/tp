package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Guest's dietary requirements in WedLog.
 * Guarantees: immutable; is always valid
 */
public class DietaryRequirements {

    public static final String MESSAGE_CONSTRAINTS =
            "Dietary requirements should not be blank";

    public final String value;

    /**
     * Constructs a {@code DietaryRequirements}.
     *
     * @param remark A dietary requirement.
     */
    public DietaryRequirements(String remark) {
        requireNonNull(remark);
        value = remark;
    }

    /**
     * Returns true if a given string is a valid dietary requirement.
     */
    public static boolean isValidDietaryRequirement(String test) {
        return test.trim().length() > 0;
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
