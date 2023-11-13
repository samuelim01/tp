package wedlog.address.model;

import java.util.HashMap;

/**
 * This class encapsulates the dietary requirement statistics of the guests.
 * It stores each combination of dietary requirements as a key
 * and the number of guests with that combination as the value.
 * @author Keagan
 */
public class DietaryRequirementStatistics {
    private final HashMap<String, Integer> dietaryRequirementMap;

    /**
     * Constructor for an empty {@code DietaryRequirementStatistics} object.
     */
    public DietaryRequirementStatistics() {
        this.dietaryRequirementMap = new HashMap<>();
    }

    /**
     * Constructor for a {@code DietaryRequirementStatistics} object.
     * @param dietaryRequirementMap The map of dietary requirements to the number of guests
     *                              with that combination.
     */
    public DietaryRequirementStatistics(HashMap<String, Integer> dietaryRequirementMap) {
        this.dietaryRequirementMap = dietaryRequirementMap;
    }

    public HashMap<String, Integer> getMap() {
        return this.dietaryRequirementMap;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DietaryRequirementStatistics)) {
            return false;
        }

        DietaryRequirementStatistics otherDietaryRequirementStatistics = (DietaryRequirementStatistics) other;
        return this.dietaryRequirementMap.equals(otherDietaryRequirementStatistics.dietaryRequirementMap);
    }
}
