package wedlog.address.logic;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import wedlog.address.logic.parser.Prefix;
import wedlog.address.model.person.Guest;
import wedlog.address.model.person.Vendor;
import wedlog.address.model.tag.DietaryRequirement;
import wedlog.address.model.tag.Tag;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command.";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_NO_PREFIX_FOUND = "No prefix was found in the command! \n%1$s";
    public static final String MESSAGE_INVALID_GUEST_DISPLAYED_INDEX = "The guest index provided is invalid.";
    public static final String MESSAGE_INVALID_VENDOR_DISPLAYED_INDEX = "The vendor index provided is invalid.";
    public static final String MESSAGE_GUESTS_LISTED_OVERVIEW = "%1$d guest(s) listed.";
    public static final String MESSAGE_VENDORS_LISTED_OVERVIEW = "%1$d vendor(s) listed.";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code Guest} for display to the user.
     */
    public static String format(Guest guest) {
        final DisplayBuilder builder = new DisplayBuilder(guest.getName().fullName);
        builder.addOptional("Phone", guest.getPhone())
                .addOptional("Email", guest.getEmail())
                .addOptional("Address", guest.getAddress())
                .add("RSVP Status", guest.getRsvpStatus())
                .addDietaryRequirements(guest.getDietaryRequirements())
                .addOptional("Table Number", guest.getTableNumber())
                .addTags(guest.getTags());

        return builder.toString();
    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String format(Vendor vendor) {
        final DisplayBuilder builder = new DisplayBuilder(vendor.getName().fullName);
        builder.addOptional("Phone", vendor.getPhone())
                .addOptional("Email", vendor.getEmail())
                .addOptional("Address", vendor.getAddress())
                .addTags(vendor.getTags());
        return builder.toString();
    }

    private static class DisplayBuilder {
        private static final String FIELD_SEPARATOR = ", ";
        private static final String FIELD_NAME_VALUE_SEPARATOR = ": ";
        private final StringBuilder stringBuilder = new StringBuilder();

        DisplayBuilder(String objectName) {
            stringBuilder.append(objectName);
        }

        public DisplayBuilder add(String fieldName, Object fieldValue) {
            if (fieldValue.equals(null)) {
                // Prevents null dietary requirements from being added
                return this;
            }
            stringBuilder.append(FIELD_SEPARATOR)
                    .append(fieldName)
                    .append(FIELD_NAME_VALUE_SEPARATOR).append(fieldValue);
            return this;
        }

        public DisplayBuilder addOptional(String fieldName, Optional<? extends Object> fieldValue) {
            if (!fieldValue.isPresent()) {
                return this;
            }
            stringBuilder.append(FIELD_SEPARATOR)
                    .append(fieldName)
                    .append(FIELD_NAME_VALUE_SEPARATOR).append(fieldValue.get());
            return this;
        }


        /**
         * Appends dietary requirements to the {@code Guest} to be displayed, if they are present.
         */
        private DisplayBuilder addDietaryRequirements(Set<DietaryRequirement> dietaryRequirements) {
            if (dietaryRequirements.isEmpty()) {
                return this;
            }
            stringBuilder.append(FIELD_SEPARATOR).append("Dietary Requirements").append(FIELD_NAME_VALUE_SEPARATOR);
            dietaryRequirements.forEach(stringBuilder::append);
            return this;
        }

        /**
         * Appends tags to the {@code Guest} to be displayed, if they are present.
         */
        private DisplayBuilder addTags(Set<Tag> tags) {
            if (tags.isEmpty()) {
                return this;
            }
            stringBuilder.append(FIELD_SEPARATOR).append("Tags").append(FIELD_NAME_VALUE_SEPARATOR);
            tags.forEach(stringBuilder::append);
            return this;
        }

        @Override
        public String toString() {
            return stringBuilder.toString();
        }
    }
}
