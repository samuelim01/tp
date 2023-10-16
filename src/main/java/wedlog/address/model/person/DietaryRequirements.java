package wedlog.address.model.person;

/**
 * Represents a Guest's dietary requirements in WedLog.
 * Guarantees: immutable; is always valid
 */
public class DietaryRequirements {

    public static final String MESSAGE_CONSTRAINTS =
            "Dietary requirements should not be blank";

    /**
     * Dietary requirement status can only be one of the following values.
     */
    public enum PossibleDietaryRequirementsStatus {
        NONE, NULL, EXIST;
    }
    public final String value;
    public final PossibleDietaryRequirementsStatus dietaryRequirementsStatus;

    /**
     * Constructs a {@code DietaryRequirements}.
     *
     * @param remark A dietary requirement.
     */
    public DietaryRequirements(String remark) {
        value = remark; // dont trim as null cannot be trimmed; leave it to parser to trim

        if (value == "") {
            dietaryRequirementsStatus = PossibleDietaryRequirementsStatus.NONE;
        } else if (value == null) {
            dietaryRequirementsStatus = PossibleDietaryRequirementsStatus.NULL;
        } else {
            dietaryRequirementsStatus = PossibleDietaryRequirementsStatus.EXIST;
        }
    }

    /**
     * Returns true if a given string is a valid dietary requirement.
     */
    public static boolean isValidDietaryRequirement(String test) {
        return test.trim().length() > 0;
    }

    /**
     * Returns true if a user states he/she has no dietary requirement.
     */
    public boolean isNoneDietaryRequirement() {
        return dietaryRequirementsStatus == PossibleDietaryRequirementsStatus.NONE;
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
