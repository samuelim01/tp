package wedlog.address.model;

/**
 * This class encapsulates the RSVP statistics of the guests.
 */
public class RsvpStatistics {
    private final int numGuestsTotal;
    private final int numGuestsRsvpYes;
    private final int numGuestsRsvpNo;
    private final int numGuestsRsvpUnknown;
    private final int percentGuestsRsvpYes;
    private final int percentGuestsRsvpNo;
    private final int percentGuestsRsvpUnknown;

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
        this.numGuestsTotal = numGuestsRsvpYes + numGuestsRsvpNo + numGuestsRsvpUnknown;
        this.percentGuestsRsvpYes = (int) Math.round((double) numGuestsRsvpYes / numGuestsTotal * 100);
        this.percentGuestsRsvpNo = (int) Math.round((double) numGuestsRsvpNo / numGuestsTotal * 100);
        this.percentGuestsRsvpUnknown = (int) Math.round((double) numGuestsRsvpUnknown / numGuestsTotal * 100);
    }

    public int getPercentGuestsRsvpYes() {
        return percentGuestsRsvpYes;
    }

    public int getPercentGuestsRsvpNo() {
        return percentGuestsRsvpNo;
    }

    public int getPercentGuestsRsvpUnknown() {
        return percentGuestsRsvpUnknown;
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
        return this.numGuestsTotal == otherRsvpStatistics.numGuestsTotal
                && this.numGuestsRsvpYes == otherRsvpStatistics.numGuestsRsvpYes
                && this.numGuestsRsvpNo == otherRsvpStatistics.numGuestsRsvpNo
                && this.numGuestsRsvpUnknown == otherRsvpStatistics.numGuestsRsvpUnknown
                && this.percentGuestsRsvpYes == otherRsvpStatistics.percentGuestsRsvpYes
                && this.percentGuestsRsvpNo == otherRsvpStatistics.percentGuestsRsvpNo
                && this.percentGuestsRsvpUnknown == otherRsvpStatistics.percentGuestsRsvpUnknown;
    }

}
