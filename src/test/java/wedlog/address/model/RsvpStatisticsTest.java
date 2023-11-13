package wedlog.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class RsvpStatisticsTest {

    private final RsvpStatistics testRsvpStatistics = new RsvpStatistics(50, 20, 30);

    @Test
    public void constructor() {
        assertEquals(new RsvpStatistics(50, 20, 30), testRsvpStatistics);
    }

    @Test
    public void getNumGuestsRsvpYes() {
        int result = testRsvpStatistics.getNumGuestsRsvpYes();
        assertEquals(50, result);
    }

    @Test
    public void testGetNumGuestsRsvpNo() {
        int result = testRsvpStatistics.getNumGuestsRsvpNo();
        assertEquals(20, result);
    }

    @Test
    public void getNumGuestsRsvpUnknown() {
        int result = testRsvpStatistics.getNumGuestsRsvpUnknown();
        assertEquals(30, result);
    }

    @Test
    public void testEquals_same() {
        assertEquals(new RsvpStatistics(50, 20, 30), new RsvpStatistics(50, 20, 30));
    }

    @Test
    public void testEquals_notSame() {
        assertNotEquals(new RsvpStatistics(50, 20, 30), new RsvpStatistics(50, 30, 20));
    }

    @Test
    public void equals_notRsvpStatistics_returnsFalse() {
        assertNotEquals(testRsvpStatistics, 0);
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        assertTrue(testRsvpStatistics.equals(testRsvpStatistics));
    }
}
