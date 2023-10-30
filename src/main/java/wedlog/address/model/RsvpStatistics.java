package wedlog.address.model;

/**
 * This class encapsulates the RSVP statistics of the guests.
 */
public class RsvpStatistics {
    private final int numGuestsRsvpYes;
    private final int numGuestsRsvpNo;
    private final int numGuestsRsvpUnknown;

    /**
     * Constructor for a {@code RsvpStatistics} object.
     * @param numGuestsRsvpYes The number of guests with RSVP status "yes".
     * @param numGuestsRsvpNo The number of guests with RSVP status "no".
     * @param numGuestsRsvpUnknown The number of guests with RSVP status "unknown".
     */
    public RsvpStatistics(int numGuestsRsvpYes, int numGuestsRsvpNo, int numGuestsRsvpUnknown) {
        this.numGuestsRsvpYes = numGuestsRsvpYes;
        this.numGuestsRsvpNo = numGuestsRsvpNo;
        this.numGuestsRsvpUnknown = numGuestsRsvpUnknown;
    }

    public int getNumGuestsRsvpYes() {
        return numGuestsRsvpYes;
    }

    public int getNumGuestsRsvpNo() {
        return numGuestsRsvpNo;
    }

    public int getNumGuestsRsvpUnknown() {
        return numGuestsRsvpUnknown;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof RsvpStatistics)) {
            return false;
        }

        RsvpStatistics otherRsvpStatistics = (RsvpStatistics) other;
        return this.numGuestsRsvpYes == otherRsvpStatistics.numGuestsRsvpYes
                && this.numGuestsRsvpNo == otherRsvpStatistics.numGuestsRsvpNo
                && this.numGuestsRsvpUnknown == otherRsvpStatistics.numGuestsRsvpUnknown;
    }

}
