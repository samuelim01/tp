package wedlog.address.model.person;

import static wedlog.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import wedlog.address.commons.util.ToStringBuilder;
import wedlog.address.model.tag.DietaryRequirement;
import wedlog.address.model.tag.Tag;

/**
 * Represents a Guest in the address book. Inherits from Person.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Guest extends Person {

    // Additional data fields
    private final RsvpStatus rsvpStatus;
    private final Set<DietaryRequirement> dietaryRequirements = new HashSet<>();
    private final Optional<TableNumber> tableNumber;

    /**
     * Name, rsvp status, dietary requirements and tags must be present and not null.
     */
    public Guest(Name name, Phone phone, Email email, Address address, RsvpStatus rsvpStatus,
                 Set<DietaryRequirement> dietaryRequirements, TableNumber tableNumber, Set<Tag> tags) {
        super(name, phone, email, address, tags);
        requireAllNonNull(rsvpStatus, dietaryRequirements);
        this.rsvpStatus = rsvpStatus;
        this.dietaryRequirements.addAll(dietaryRequirements);
        this.tableNumber = Optional.ofNullable(tableNumber);
    }

    public RsvpStatus getRsvpStatus() {
        return rsvpStatus;
    }

    /**
     * Returns an immutable dietary requirement set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<DietaryRequirement> getDietaryRequirements() {
        return Collections.unmodifiableSet(dietaryRequirements);
    }

    public Optional<TableNumber> getTableNumber() {
        return tableNumber;
    }

    /**
     * Returns the comma separated dietary requirements of the guest.
     * String of dietary requirements is lexicographically ordered.
     */
    public String getDietaryRequirementsString() {
        if (this.dietaryRequirements.isEmpty()) {
            return "";
        }

        // Add all dietary requirements to an array and sort it
        DietaryRequirement[] dietaryRequirementsArray = this.dietaryRequirements.toArray(DietaryRequirement[]::new);
        Arrays.sort(dietaryRequirementsArray, Comparator.comparing(DietaryRequirement::toString));

        StringBuilder dietaryRequirementsStringBuilder = new StringBuilder();
        for (DietaryRequirement dietaryRequirement : dietaryRequirementsArray) {
            dietaryRequirementsStringBuilder.append(dietaryRequirement.value.toLowerCase());
            dietaryRequirementsStringBuilder.append(", ");
        }
        return dietaryRequirementsStringBuilder.substring(0, dietaryRequirementsStringBuilder.length() - 2);
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
                && dietaryRequirements.equals(otherGuest.dietaryRequirements)
                && Objects.equals(tableNumber, otherGuest.tableNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getPhone(), getEmail(), getAddress(), getTags(),
                rsvpStatus, dietaryRequirements, tableNumber);
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
                .add("tableNumber", getTableNumber())
                .add("tags", getTags())
                .toString();
    }

}
