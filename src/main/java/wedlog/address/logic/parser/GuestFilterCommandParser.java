package wedlog.address.logic.parser;

import static wedlog.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static wedlog.address.logic.Messages.MESSAGE_NO_PREFIX_FOUND;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_DIETARY;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_NAME;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_RSVP;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_TABLE;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import wedlog.address.logic.commands.GuestFilterCommand;
import wedlog.address.logic.parser.exceptions.ParseException;
import wedlog.address.model.person.AddressPredicate;
import wedlog.address.model.person.EmailPredicate;
import wedlog.address.model.person.Guest;
import wedlog.address.model.person.GuestRsvpPredicate;
import wedlog.address.model.person.GuestTablePredicate;
import wedlog.address.model.person.NamePredicate;
import wedlog.address.model.person.PhonePredicate;
import wedlog.address.model.tag.GuestDietaryPredicate;
import wedlog.address.model.tag.TagPredicate;

/**
 * Parses user input for GuestFilter commands.
 */
public class GuestFilterCommandParser implements Parser<GuestFilterCommand> {
    // Prefixes for non-tag fields (everything except Dietary Requirement and Tag)
    private static final Prefix[] NON_TAG_PREFIXES = { PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
        PREFIX_RSVP, PREFIX_TABLE };

    // Prefixes for all fields
    private static final Prefix[] PREFIXES = { PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
        PREFIX_RSVP, PREFIX_TABLE, PREFIX_DIETARY, PREFIX_TAG };


    /**
     * Parses the given {@code String} of arguments in the context of the GuestFilterCommand
     * and returns an GuestFilterCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public GuestFilterCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIXES);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, GuestFilterCommand.MESSAGE_USAGE));
        }

        // Only DR and Tag fields are allowed to have multiple inputs
        argMultimap.verifyNoDuplicatePrefixesFor(NON_TAG_PREFIXES);

        List<Predicate<? super Guest>> predicates = new ArrayList<>();

        for (Prefix prefix : PREFIXES) {
            if (isNonTagFilter(prefix)) {
                parseNonTagFilters(argMultimap, prefix, predicates);
            } else {
                parseTagFilters(argMultimap, prefix, predicates);
            }
        }

        if (predicates.size() == 0) {
            throw new ParseException(String.format(MESSAGE_NO_PREFIX_FOUND, GuestFilterCommand.MESSAGE_USAGE));
        }

        return new GuestFilterCommand(predicates);
    }

    /**
     * Mandates the user to input non-empty {@code String}.
     * @throws ParseException if the user input does not conform the expected format
     */
    private void requireNonEmpty(String s) throws ParseException {
        if (s.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    "Cannot filter for empty compulsory field."));
        }
    }

    /**
     * Returns true if field values are not stored as tags,
     * and false otherwise.
     */
    private boolean isNonTagFilter(Prefix prefix) {
        return prefix == PREFIX_NAME || prefix == PREFIX_PHONE || prefix == PREFIX_EMAIL || prefix == PREFIX_ADDRESS
                || prefix == PREFIX_RSVP || prefix == PREFIX_TABLE;
    }

    /**
     * Parses user input for fields that do not utilise Tags and updates predicate list.
     */
    private void parseNonTagFilters(ArgumentMultimap argMultimap, Prefix prefix,
                                    List<Predicate<? super Guest>> predicates) throws ParseException {
        Optional<String> str = argMultimap.getValue(prefix);
        if (str.isEmpty()) { // skip the fields not included in the user's input
            return;
        }
        String trimmedInputString = str.get().trim();
        // all parameters will accept any kind of inputs: "", "anything", "123asd" etc.
        if (prefix.equals(PREFIX_NAME)) {
            // now accepts "" => but will return an empty guest list since name is never an empty string
            predicates.add(new NamePredicate(trimmedInputString));
        } else if (prefix.equals(PREFIX_PHONE)) {
            predicates.add(new PhonePredicate(trimmedInputString));
        } else if (prefix.equals(PREFIX_EMAIL)) {
            predicates.add(new EmailPredicate(trimmedInputString));
        } else if (prefix.equals(PREFIX_ADDRESS)) {
            predicates.add(new AddressPredicate(trimmedInputString));
        } else if (prefix.equals(PREFIX_RSVP)) {
            predicates.add(new GuestRsvpPredicate(trimmedInputString));
        } else if (prefix.equals(PREFIX_TABLE)) {
            predicates.add(new GuestTablePredicate(trimmedInputString));
        }
    }

    /**
     * Parses user input for fields that utilise Tags and updates predicate list.
     */
    private void parseTagFilters(ArgumentMultimap argMultimap, Prefix prefix,
                                    List<Predicate<? super Guest>> predicates) throws ParseException {
        Boolean isFieldIncludedInInput = !argMultimap.getValue(prefix).isEmpty();
        List<String> keywords = argMultimap.getAllValues(prefix);
        if (!isFieldIncludedInInput) { // skip the fields not included in the user's input
            return;
        }
        if (prefix.equals(PREFIX_DIETARY)) {
            predicates.add(new GuestDietaryPredicate(keywords));
        } else if (prefix.equals(PREFIX_TAG)) {
            predicates.add(new TagPredicate(keywords));
        }
    }
}
