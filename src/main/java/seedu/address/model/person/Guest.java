package seedu.address.model.person;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

import java.util.Objects;
import java.util.Set;

public class Guest extends Person {

    // Additional data fields
    private final RsvpStatus rsvpStatus;
    private final DietaryRequirements dietaryRequirements;

    /**
     * Every field must be present and not null.
     */
    public Guest(Name name, Phone phone, Email email, Address address, Set<Tag> tags, RsvpStatus rsvpStatus, DietaryRequirements dietaryRequirements) {
        super(name, phone, email, address, tags);
        this.rsvpStatus = rsvpStatus;
        this.dietaryRequirements = dietaryRequirements;
    }

    public RsvpStatus getRsvpStatus() {
        return rsvpStatus;
    }

    public DietaryRequirements getDietaryRequirements() {
        return dietaryRequirements;
    }

    /**
     * Returns true if both guests have the same identity and data fields.
     * This defines a stronger notion of equality between two guests.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Guest)) {
            return false;
        }

        Guest otherGuest = (Guest) other;
        return super.equals(otherGuest)
                && otherGuest.getRsvpStatus().equals(getRsvpStatus())
                && otherGuest.getDietaryRequirements().equals(getDietaryRequirements());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getPhone(), getEmail(), getAddress(), getTags(),
                rsvpStatus, dietaryRequirements);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", getName())
                .add("phone", getPhone())
                .add("email", getEmail())
                .add("address", getAddress())
                .add("rsvpStatus", getRsvpStatus())
                .add("dietaryRequirements", getDietaryRequirements())
                .add("tags", getTags())
                .toString();
    }

}
