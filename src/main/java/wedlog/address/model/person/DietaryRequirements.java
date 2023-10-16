package wedlog.address.model.person;

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
        value = remark; // dont trim as null cannot be trimmed; leave it to parser to trim

        if (value == "") {
            status = Status.NONE;
        } else if (value == null) {
            status = Status.NULL;
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
        return other == this // short circuit if same object
                || (other instanceof DietaryRequirements // instanceof handles nulls
                && value.equals(((DietaryRequirements) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
