package wedlog.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import wedlog.address.commons.exceptions.IllegalValueException;
import wedlog.address.model.person.Address;
import wedlog.address.model.person.Email;
import wedlog.address.model.person.Guest;
import wedlog.address.model.person.Name;
import wedlog.address.model.person.Phone;
import wedlog.address.model.person.RsvpStatus;
import wedlog.address.model.person.TableNumber;
import wedlog.address.model.tag.DietaryRequirement;
import wedlog.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Guest}.
 */
class JsonAdaptedGuest extends JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Guest's %s field is missing!";

    private final String rsvpStatus;
    private final List<JsonAdaptedDietaryRequirement> dietaryRequirements = new ArrayList<>();
    private final String tableNumber;

    /**
     * Constructs a {@code JsonAdaptedGuest} with the given guest details.
     */
    @JsonCreator
    public JsonAdaptedGuest(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                            @JsonProperty("email") String email, @JsonProperty("address") String address,
                            @JsonProperty("rsvpStatus") String rsvpStatus,
                            @JsonProperty("dietaryRequirements") List<JsonAdaptedDietaryRequirement> dietaryRequirements,
                            @JsonProperty("tableNumber") String tableNumber,
                            @JsonProperty("tags") List<JsonAdaptedTag> tags) {
        super(name, phone, email, address, tags);

        this.rsvpStatus = rsvpStatus;
        this.tableNumber = tableNumber;
        if (dietaryRequirements != null) {
            this.dietaryRequirements.addAll(dietaryRequirements);
        }
    }

    /**
     * Converts a given {@code Guest} into this class for Jackson use.
     */
    public JsonAdaptedGuest(Guest source) {
        super(source);

        rsvpStatus = source.getRsvpStatus().value;
        tableNumber = source.getTableNumber().map(tn -> tn.value).orElse(null);
        dietaryRequirements.addAll(source.getDietaryRequirements().stream()
                .map(JsonAdaptedDietaryRequirement::new)
                .collect(java.util.stream.Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted guest object into the model's {@code Guest} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Guest toModelType() throws IllegalValueException {
        final List<Tag> guestTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            guestTags.add(tag.toModelType());
        }
        final List<DietaryRequirement> guestDietaryRequirements = new ArrayList<>();
        for (JsonAdaptedDietaryRequirement dietaryRequirement : dietaryRequirements) {
            guestDietaryRequirements.add(dietaryRequirement.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        final Phone modelPhone;
        if (phone == null) {
            modelPhone = null;
        } else if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        } else {
            modelPhone = new Phone(phone);
        }

        final Email modelEmail;
        if (email == null) {
            modelEmail = null;
        } else if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        } else {
            modelEmail = new Email(email);
        }

        final Address modelAddress;
        if (address == null) {
            modelAddress = null;
        } else if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        } else {
            modelAddress = new Address(address);
        }

        final RsvpStatus modelRsvpStatus;
        if (rsvpStatus == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    RsvpStatus.class.getSimpleName()));
        } else if (!RsvpStatus.isValidRsvpStatus(rsvpStatus)) {
            throw new IllegalValueException(RsvpStatus.MESSAGE_CONSTRAINTS);
        } else {
            modelRsvpStatus = new RsvpStatus(rsvpStatus);
        }

        final TableNumber modelTableNumber;
        if (tableNumber == null) {
            modelTableNumber = null;
        } else if (!TableNumber.isValidTableNumber(tableNumber)) {
            throw new IllegalValueException(TableNumber.MESSAGE_CONSTRAINTS);
        } else {
            modelTableNumber = new TableNumber(tableNumber);
        }

        final Set<Tag> modelTags = new HashSet<>(guestTags);
        final Set<DietaryRequirement> modelDietaryRequirements = new HashSet<>(guestDietaryRequirements);
        return new Guest(modelName, modelPhone, modelEmail, modelAddress, modelRsvpStatus,
                modelDietaryRequirements, modelTableNumber, modelTags);
    }

}
