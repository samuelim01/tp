package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Guest's RsvpStatus status in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidRsvpStatus(String)}
 */
public class RsvpStatus {

    public static final String MESSAGE_CONSTRAINTS =
            "RsvpStatus Status should only have one of two values. true or false.";

    /*
     * RSVP status should only consist of true or false values. Not case-sensitive.
     */
    public static final String VALIDATION_REGEX = "^(?i)(true|false)$";

    public static final String TRUE_REGEX = "^(?i)(true)$";

    public final boolean rsvpStatus;

    /**
     * Constructs a {@code RsvpStatus}.
     *
     * @param rsvp A valid RSVP status.
     */
    public RsvpStatus(String rsvp) {
        requireNonNull(rsvp);
        checkArgument(isValidRsvpStatus(rsvp), MESSAGE_CONSTRAINTS);
        this.rsvpStatus = rsvp.matches(TRUE_REGEX);
    }

    /**
     * Returns true if a given string is a valid RSVP status.
     */
    public static boolean isValidRsvpStatus(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return this.rsvpStatus ? "RSVP'd" : "Not RSVP'd";
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RsvpStatus)) {
            return false;
        }

        RsvpStatus otherName = (RsvpStatus) other;
        return rsvpStatus == (otherName.rsvpStatus);
    }

}
