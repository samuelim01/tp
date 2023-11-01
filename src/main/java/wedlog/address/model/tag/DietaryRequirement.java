package wedlog.address.model.tag;

import static java.util.Objects.requireNonNull;
import static wedlog.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Guest's dietary requirement in WedLog.
 * Guarantees: immutable; is always valid
 */
public class DietaryRequirement {

    public static final String MESSAGE_CONSTRAINTS =
            "Dietary requirements should only contain alphanumeric characters and spaces, and it should not be blank";
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String value;

    /**
     * Constructs a {@code DietaryRequirement}.
     *
     * @param remark A dietary requirement.
     */
    public DietaryRequirement(String remark) {
        requireNonNull(remark);
        checkArgument(isValidDietaryRequirement(remark), MESSAGE_CONSTRAINTS);
        this.value = remark;
    }

    /**
     * Returns true if a given string is a valid dietary requirement.
     */
    public static boolean isValidDietaryRequirement(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DietaryRequirement)) {
            return false;
        }

        DietaryRequirement otherDietaryRequirement = (DietaryRequirement) other;
        return value.equals(otherDietaryRequirement.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value;
    }

}
