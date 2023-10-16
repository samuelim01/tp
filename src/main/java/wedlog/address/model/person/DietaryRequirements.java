package wedlog.address.model.person;

import java.util.Objects;

/**
 * Represents a Guest's dietary requirements in WedLog.
 * Guarantees: immutable; is always valid
 */
public class DietaryRequirements {

    /**
     * Dietary requirement status can only be one of the following values.
     */
    public enum Status {
        NONE, NULL, PRESENT;
    }
    public final String value;
    public final Status status;

    /**
     * Constructs a {@code DietaryRequirements}.
     *
     * @param remark A dietary requirement.
     */
    public DietaryRequirements(String remark) {
        if (remark == null) {
            status = Status.NULL;
            value = null;
            return;
        }
        value = remark.trim();

        if (value.isEmpty()) {
            status = Status.NONE;
        } else {
            status = Status.PRESENT;
        }
    }

    /**
     * Returns true if there are no dietary requirements.
     */
    public boolean isNoneDietaryRequirement() {
        return status == Status.NONE;
    }

    /**
     * Returns true if dietary requirements are unspecified.
     */
    public boolean isNullDietaryRequirement() {
        return status == Status.NULL;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DietaryRequirements)) {
            return false;
        }

        DietaryRequirements otherDr = (DietaryRequirements) other;
        return status.equals(otherDr.status) && Objects.equals(value, otherDr.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
