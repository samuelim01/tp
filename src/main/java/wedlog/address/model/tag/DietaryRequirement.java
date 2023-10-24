package wedlog.address.model.tag;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Guest's dietary requirement in WedLog.
 * Guarantees: immutable; is always valid
 */
public class DietaryRequirement {

    public final String value;

    /**
     * Constructs a {@code DietaryRequirement}.
     *
     * @param remark A dietary requirement.
     */
    public DietaryRequirement(String remark) {
        requireNonNull(remark);
        this.value = remark;
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
        return '[' + value + ']';
    }

}
