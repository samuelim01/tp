package wedlog.address.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

public class DietaryRequirementStatisticsTest {

    private static final HashMap<String, Integer> TEST_MAP_1 = new HashMap<>();
    private static final HashMap<String, Integer> TEST_MAP_2 = new HashMap<>();

    @Test
    public void constructor() {
        // Empty constructor
        assertDoesNotThrow(() -> new DietaryRequirementStatistics());

        // Constructor with HashMap
        assertDoesNotThrow(() -> new DietaryRequirementStatistics(new HashMap<>()));
    }

    @Test
    public void emptyMap_getter_returnsEmptyMap() {
        DietaryRequirementStatistics testDietaryRequirementStatistics = new DietaryRequirementStatistics();
        assert(testDietaryRequirementStatistics.getMap().isEmpty());
    }

    @Test
    public void nonEmptyMap_getter_returnsNonEmptyMap() {
        TEST_MAP_1.put("vegan", 1);
        TEST_MAP_1.put("vegetarian", 2);
        TEST_MAP_1.put("halal", 3);
        DietaryRequirementStatistics testDietaryRequirementStatistics = new DietaryRequirementStatistics(TEST_MAP_1);
        assertEquals(testDietaryRequirementStatistics.getMap(), TEST_MAP_1);
    }

    @Test
    public void equals_same_returnsTrue() {
        TEST_MAP_1.put("vegan", 1);
        TEST_MAP_1.put("vegetarian", 2);
        TEST_MAP_1.put("halal", 3);
        DietaryRequirementStatistics expectedDietaryRequirementStatistics =
                new DietaryRequirementStatistics(TEST_MAP_1);
        DietaryRequirementStatistics testDietaryRequirementStatistics = new DietaryRequirementStatistics(TEST_MAP_1);
        assertEquals(testDietaryRequirementStatistics, expectedDietaryRequirementStatistics);
    }

    @Test
    public void equals_notSame_returnsFalse() {
        TEST_MAP_1.put("Vegan", 1);
        TEST_MAP_1.put("Vegetarian", 2);
        TEST_MAP_1.put("Halal", 3);
        DietaryRequirementStatistics expectedDietaryRequirementStatistics =
                new DietaryRequirementStatistics(TEST_MAP_1);
        TEST_MAP_2.put("Vegan", 1);
        TEST_MAP_2.put("Vegetarian", 2);
        TEST_MAP_2.put("Halal", 4);
        DietaryRequirementStatistics testDietaryRequirementStatistics = new DietaryRequirementStatistics(TEST_MAP_2);
        assertNotEquals(testDietaryRequirementStatistics, expectedDietaryRequirementStatistics);
    }

    @Test
    public void equals_notDietaryRequirementStatistics_returnsFalse() {
        HashMap<String, Integer> testMap = new HashMap<>();
        testMap.put("Vegan", 1);
        testMap.put("Vegetarian", 2);
        testMap.put("Halal", 3);
        DietaryRequirementStatistics expectedDietaryRequirementStatistics = new DietaryRequirementStatistics(testMap);
        assertNotEquals(expectedDietaryRequirementStatistics, testMap);
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        DietaryRequirementStatistics testDietaryRequirementStatistics = new DietaryRequirementStatistics();
        assertTrue(testDietaryRequirementStatistics.equals(testDietaryRequirementStatistics));
    }
}
