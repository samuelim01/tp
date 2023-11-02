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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import wedlog.address.logic.commands.VendorFilterCommand;
import wedlog.address.logic.parser.exceptions.ParseException;
import wedlog.address.model.person.AddressPredicate;
import wedlog.address.model.person.EmailPredicate;
import wedlog.address.model.person.Guest;
import wedlog.address.model.person.GuestRsvpPredicate;
import wedlog.address.model.person.GuestTablePredicate;
import wedlog.address.model.person.NamePredicate;
import wedlog.address.model.person.PhonePredicate;
import wedlog.address.model.person.Vendor;
import wedlog.address.model.tag.GuestDietaryPredicate;
import wedlog.address.model.tag.TagPredicate;

/**
 * Parses user input for VendorFilter commands.
 */
public class VendorFilterCommandParser implements Parser<VendorFilterCommand> {
    // Prefixes for non-tag fields (everything except Dietary Requirement and Tag)
    private static final Prefix[] NON_TAG_PREFIXES = { PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS };

    // Prefixes for all fields
    private static final Prefix[] PREFIXES = { PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG };

    /**
     * Parses the given {@code String} of arguments in the context of the VendorFilterCommand
     * and returns an VendorFilterCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public VendorFilterCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIXES);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, VendorFilterCommand.MESSAGE_USAGE));
        }

        // Only DR and Tag fields are allowed to have multiple inputs
        argMultimap.verifyNoDuplicatePrefixesFor(NON_TAG_PREFIXES);

        List<Predicate<? super Vendor>> predicates = new ArrayList<>();

        for (Prefix prefix : PREFIXES) {
            if (isNonTagFilter(prefix)) {
                parseNonTagFilters(argMultimap, prefix, predicates);
            } else {
                parseTagFilters(argMultimap, prefix, predicates);
            }
        }

        if (predicates.size() == 0) {
            throw new ParseException(String.format(MESSAGE_NO_PREFIX_FOUND, VendorFilterCommand.MESSAGE_USAGE));
        }

        return new VendorFilterCommand(predicates);
    }

    /**
     * Returns true if field values are not stored as tags,
     * and false otherwise.
     */
    private boolean isNonTagFilter(Prefix prefix) {
        return Arrays.asList(NON_TAG_PREFIXES).contains(prefix);
    }

    /**
     * Parses user input for fields that do not utilise Tags and updates predicate list.
     */
    private void parseNonTagFilters(ArgumentMultimap argMultimap, Prefix prefix,
                                    List<Predicate<? super Vendor>> predicates) throws ParseException {
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
        }
    }

    /**
     * Parses user input for Tags and updates predicate list.
     */
    private void parseTagFilters(ArgumentMultimap argMultimap, Prefix prefix,
                                 List<Predicate<? super Vendor>> predicates) throws ParseException {
        List<String> keywords = argMultimap.getAllValues(prefix);
        predicates.add(new TagPredicate(keywords));
    }
}
