package wedlog.address.model.person;

import static wedlog.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;
import java.util.Set;

import wedlog.address.commons.util.ToStringBuilder;
import wedlog.address.model.tag.Tag;

/**
 * Represents a Guest in the address book. Inherits from Person.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Guest extends Person {

    // Additional data fields
    private final RsvpStatus rsvpStatus;
    private final DietaryRequirements dietaryRequirements;

    /**
     * Name, rsvp status, dietary requirements and tags must be present and not null.
     */
    public Guest(Name name, Phone phone, Email email, Address address, RsvpStatus rsvpStatus,
                 DietaryRequirements dietaryRequirements, Set<Tag> tags) {
        super(name, phone, email, address, tags);
        requireAllNonNull(rsvpStatus);
        this.rsvpStatus = rsvpStatus;
        this.dietaryRequirements =
                Objects.requireNonNullElseGet(dietaryRequirements, () -> new DietaryRequirements(null));
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
                && rsvpStatus.equals(otherGuest.rsvpStatus)
                && dietaryRequirements.equals(otherGuest.dietaryRequirements);
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
