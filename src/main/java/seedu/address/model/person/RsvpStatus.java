package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Guest's RsvpStatus status in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidRsvpStatus(String)}
 */
public class RsvpStatus {

    public static final String MESSAGE_CONSTRAINTS =
            "RsvpStatus Status should only have one of three values. yes, no, or unknown.";

    /**
     * RSVP status can only be one of the following values.
     */
    public enum PossibleRsvpStatus {
        YES, NO, UNKNOWN
    }

    /*
     * RSVP status should only consist of yes, no, or unknown values. Not case-sensitive.
     */
    public static final String VALIDATION_REGEX = "^(?i)(yes|no|unknown)$";

    public static final String RSVP_YES_REGEX = "^(?i)(yes)$";

    public static final String RSVP_NO_REGEX = "^(?i)(no)$";

    public final PossibleRsvpStatus rsvpStatus;

    /*
     * Value is stored in lower case.
     * Possible values are: "yes", "no", "unknown".
     */
    public final String value;

    /**
     * Constructs a {@code RsvpStatus}.
     *
     * @param rsvp A valid RSVP status.
     */
    public RsvpStatus(String rsvp) {
        requireNonNull(rsvp);
        checkArgument(isValidRsvpStatus(rsvp), MESSAGE_CONSTRAINTS);
        this.rsvpStatus = rsvp.matches(RSVP_YES_REGEX)
            ? PossibleRsvpStatus.YES
            : rsvp.matches(RSVP_NO_REGEX)
            ? PossibleRsvpStatus.NO
            : PossibleRsvpStatus.UNKNOWN;
        this.value = rsvp.toLowerCase();
    }

    /**
     * Returns true if a given string is a valid RSVP status.
     */
    public static boolean isValidRsvpStatus(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return this.value;
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
