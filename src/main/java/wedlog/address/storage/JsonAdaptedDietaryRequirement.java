package wedlog.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import wedlog.address.commons.exceptions.IllegalValueException;
import wedlog.address.model.tag.DietaryRequirement;

/**
 * Jackson-friendly version of {@link DietaryRequirement}.
 */
public class JsonAdaptedDietaryRequirement {

    private final String dietaryRequirement;

    /**
     * Constructs a {@code JsonAdaptedDietaryRequirement} with the given {@code dietaryRequirement}.
     */
    @JsonCreator
    public JsonAdaptedDietaryRequirement(String dietaryRequirement) {
        this.dietaryRequirement = dietaryRequirement;
    }

    /**
     * Converts a given {@code DietaryRequirement} into this class for Jackson use.
     */
    public JsonAdaptedDietaryRequirement(DietaryRequirement source) {
        dietaryRequirement = source.value;
    }

    @JsonValue
    public String getDietaryRequirement() {
        return dietaryRequirement;
    }

    /**
     * Converts this Jackson-friendly adapted dietary requirement object into the model's {@code DietaryRequirement}
     * object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted dietary requirement.
     */
    public DietaryRequirement toModelType() throws IllegalValueException {
        return new DietaryRequirement(dietaryRequirement);
    }

}
