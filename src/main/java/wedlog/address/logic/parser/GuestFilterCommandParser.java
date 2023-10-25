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
import java.util.Optional;
import java.util.function.Predicate;

import wedlog.address.logic.commands.GuestFilterCommand;
import wedlog.address.logic.parser.exceptions.ParseException;
import wedlog.address.model.person.Guest;
import wedlog.address.model.person.GuestAddressPredicate;
import wedlog.address.model.person.GuestEmailPredicate;
import wedlog.address.model.person.GuestNamePredicate;
import wedlog.address.model.person.GuestPhonePredicate;
import wedlog.address.model.person.GuestRsvpPredicate;
import wedlog.address.model.person.GuestTablePredicate;

/**
 * Parses user input for GuestFilter commands.
 */
public class GuestFilterCommandParser implements Parser<GuestFilterCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the GuestFilterCommand
     * and returns an GuestFilterCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public GuestFilterCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_RSVP, PREFIX_DIETARY, PREFIX_TAG);
        Prefix[] prefixes = {PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
            PREFIX_RSVP, PREFIX_TABLE, PREFIX_DIETARY, PREFIX_TAG};

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, GuestFilterCommand.MESSAGE_USAGE));
        }
        argMultimap.verifyNoDuplicatePrefixesFor(prefixes);
        ArrayList<Predicate<Guest>> predicates = new ArrayList<>();

        for (Prefix prefix : prefixes) {
            Optional<String> str = argMultimap.getValue(prefix);
            if (str.isEmpty()) { // skip the ones that the user did not specify
                continue;
            }
            String trimmedKeywords = str.get().trim();
            String[] keywords = trimmedKeywords.split("\\s+");
            if (prefix.equals(PREFIX_NAME)) {
                requireNonEmpty(trimmedKeywords);
                predicates.add(new GuestNamePredicate(Arrays.asList(keywords)));
            } else if (prefix.equals(PREFIX_RSVP)) {
                requireNonEmpty(trimmedKeywords);
                predicates.add(new GuestRsvpPredicate(Arrays.asList(keywords)));
            } else if (prefix.equals(PREFIX_PHONE)) {
                predicates.add(new GuestPhonePredicate(Arrays.asList(keywords)));
            } else if (prefix.equals(PREFIX_EMAIL)) {
                predicates.add(new GuestEmailPredicate(Arrays.asList(keywords)));
            } else if (prefix.equals(PREFIX_ADDRESS)) {
                predicates.add(new GuestAddressPredicate(Arrays.asList(keywords)));
            } else if (prefix.equals(PREFIX_TABLE)) {
                predicates.add(new GuestTablePredicate(Arrays.asList(keywords)));
            }
        }
        if (predicates.size() == 0) {
            throw new ParseException(String.format(MESSAGE_NO_PREFIX_FOUND, GuestFilterCommand.MESSAGE_USAGE));
        }

        Predicate<Guest> chainedPredicates = createChainedPredicates(predicates);
        return new GuestFilterCommand(chainedPredicates);
    }

    /**
     * Truncates a chain of predicates {@code RsvpStatus} into 1 predicate.
     * This is done to find out if chained predicates return an overall true or false.
     *
     * @param predicates ArrayList of predicates.
     * @return Overall predicate.
     */
    private Predicate<Guest> createChainedPredicates(ArrayList<Predicate<Guest>> predicates) {
        return guest -> predicates.stream().allMatch(predicate -> predicate.test(guest));
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
}
