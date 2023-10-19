package wedlog.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class RsvpStatisticsTest {

    private final RsvpStatistics testRsvpStatistics = new RsvpStatistics(50, 20, 30);

    @Test
    public void constructor() {
        assertEquals(new RsvpStatistics(50, 20, 30), testRsvpStatistics);
    }

    @Test
    public void testEquals_same() {
        assertEquals(new RsvpStatistics(50, 20, 30), new RsvpStatistics(50, 20, 30));
    }

    @Test
    public void testEquals_notSame() {
        assertNotEquals(new RsvpStatistics(50, 20, 30), new RsvpStatistics(50, 30, 20));
    }
}
