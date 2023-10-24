package wedlog.address.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

public class DietaryRequirementStatisticsTest {

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
        HashMap<String, Integer> testMap = new HashMap<>();
        testMap.put("vegan", 1);
        testMap.put("vegetarian", 2);
        testMap.put("halal", 3);
        DietaryRequirementStatistics testDietaryRequirementStatistics = new DietaryRequirementStatistics(testMap);
        assertEquals(testDietaryRequirementStatistics.getMap(), testMap);
    }

    @Test
    public void equals_same_returnsTrue() {
        HashMap<String, Integer> testMap = new HashMap<>();
        testMap.put("vegan", 1);
        testMap.put("vegetarian", 2);
        testMap.put("halal", 3);
        DietaryRequirementStatistics expectedDietaryRequirementStatistics = new DietaryRequirementStatistics(testMap);
        DietaryRequirementStatistics testDietaryRequirementStatistics = new DietaryRequirementStatistics(testMap);
        assertEquals(testDietaryRequirementStatistics, expectedDietaryRequirementStatistics);
    }

    @Test
    public void equals_notSame_returnsFalse() {
        HashMap<String, Integer> testMap = new HashMap<>();
        testMap.put("Vegan", 1);
        testMap.put("Vegetarian", 2);
        testMap.put("Halal", 3);
        DietaryRequirementStatistics expectedDietaryRequirementStatistics = new DietaryRequirementStatistics(testMap);
        HashMap<String, Integer> testMap2 = new HashMap<>();
        testMap2.put("Vegan", 1);
        testMap2.put("Vegetarian", 2);
        testMap2.put("Halal", 4);
        DietaryRequirementStatistics testDietaryRequirementStatistics = new DietaryRequirementStatistics(testMap2);
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
        assertEquals(new DietaryRequirementStatistics(), new DietaryRequirementStatistics());
    }
}
