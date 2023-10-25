package wedlog.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import wedlog.address.commons.core.index.Index;
import wedlog.address.commons.util.StringUtil;
import wedlog.address.logic.parser.exceptions.ParseException;
import wedlog.address.model.person.Address;
import wedlog.address.model.person.Email;
import wedlog.address.model.person.Name;
import wedlog.address.model.person.Phone;
import wedlog.address.model.person.RsvpStatus;
import wedlog.address.model.person.TableNumber;
import wedlog.address.model.tag.DietaryRequirement;
import wedlog.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String rsvp} into a {@code RsvpStatus}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code rsvp} is invalid.
     */
    public static RsvpStatus parseRsvp(String rsvp) throws ParseException {
        // null inputs and empty string defaults to unknown status
        if (rsvp == null || rsvp == "") {
            return RsvpStatus.unknown();
        }

        requireNonNull(rsvp);
        String trimmedRsvp = rsvp.trim();

        // checks that value is either "yes", "no", or "unknown"
        if (!RsvpStatus.isValidRsvpStatus(trimmedRsvp)) {
            throw new ParseException(RsvpStatus.MESSAGE_CONSTRAINTS);
        }
        return new RsvpStatus(trimmedRsvp);
    }

    /**
     * Parses a {@code String dietaryRequirement} into a {@code DietaryRequirement}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code dietaryRequirement} is invalid.
     */
    public static DietaryRequirement parseDietaryRequirement(String dietaryRequirement) throws ParseException {
        requireNonNull(dietaryRequirement);
        String trimmedDietaryRequirement = dietaryRequirement.trim();
        if (!DietaryRequirement.isValidDietaryRequirement(trimmedDietaryRequirement)) {
            throw new ParseException(DietaryRequirement.MESSAGE_CONSTRAINTS);
        }
        return new DietaryRequirement(trimmedDietaryRequirement);
    }

    /**
     * Parses {@code Collection<String> dietaryRequirements} into a {@code Set<DietaryRequirement>}.
     */
    public static Set<DietaryRequirement> parseDietaryRequirements(Collection<String> dietaryRequirements)
            throws ParseException {
        requireNonNull(dietaryRequirements);
        final Set<DietaryRequirement> dietarySet = new HashSet<>();
        for (String dietary : dietaryRequirements) {
            // takes in a list of strings & parse then put into a hashset
            dietarySet.add(parseDietaryRequirement(dietary));
        }
        return dietarySet;
    }

    /**
     * Parses a {@code String table} into a {@code TableNumber}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code table} is invalid.
     */
    public static TableNumber parseTable(String table) throws ParseException {
        requireNonNull(table);
        String trimmedTable = table.trim();
        if (!TableNumber.isValidTableNumber(trimmedTable)) {
            throw new ParseException(TableNumber.MESSAGE_CONSTRAINTS);
        }
        return new TableNumber(trimmedTable);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName)); // takes in a list of strings & parse then put into a hashset
        }
        return tagSet;
    }

    //@@author samuelim01-reused
    // Reused from AY2324S1-CS2103T-W08-3
    // with minor modifications
    /**
     * Represents a function that parses the given {@code String} into the given result.
     */
    public interface ParserFunction<R> {
        R parse(String value) throws ParseException;
    }

    /**
     * Returns the result of parsing {@code optionalString} with the given
     * parser function if {@code optionalString} is present, else returns null.
     */
    public static <R> R parseOptionally(Optional<String> optionalString, ParserFunction<R> parserFunction)
            throws ParseException {

        if (optionalString.isPresent()) {
            return parserFunction.parse(optionalString.get());
        }
        return null;
    }
  
    //@@author

    /**
     * Returns the result of parsing {@code String} with the given
     * parser function if {@code string} is not empty, else returns null.
     */

    public static <R> R parseIfNotBlank(String string, ParserFunction<R> parserFunction)
            throws ParseException {

        if (!string.isBlank()) {
            return parserFunction.parse(string);
        }
        return null;
    }
}
