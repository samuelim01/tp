package wedlog.address.logic.parser;

import wedlog.address.logic.commands.GuestFilterCommand;
import wedlog.address.logic.parser.exceptions.ParseException;
import wedlog.address.model.person.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;


import static wedlog.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static wedlog.address.logic.parser.CliSyntax.*;

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

        // check if preamble is valid
        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, GuestFilterCommand.MESSAGE_USAGE));
        }
        Prefix[] prefixes = {PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                PREFIX_RSVP, PREFIX_TABLE, PREFIX_DIETARY, PREFIX_TAG};
        // throws parse exception if prefixes are inputted twice
        argMultimap.verifyNoDuplicatePrefixesFor(prefixes);
        ArrayList<Predicate<Guest>> predicates = new ArrayList<>();

        for (Prefix prefix : prefixes) {
            Optional<String> str = argMultimap.getValue(prefix);
            if (str.isEmpty()) { // skip the ones that the user did not specify
                continue;
            }

            String trimmedKeywords = str.get().trim();
            if (prefix.equals(PREFIX_NAME)) {
                String[] nameKeywords = trimmedKeywords.split("\\s+");
                predicates.add(new GuestNamePredicate(Arrays.asList(nameKeywords)));
            } else if (prefix.equals(PREFIX_PHONE)) {
                predicates.add(new GuestPhonePredicate(trimmedKeywords));
            } else if (prefix.equals(PREFIX_EMAIL)) {
                predicates.add(new GuestEmailPredicate(trimmedKeywords));
            } else if (prefix.equals(PREFIX_ADDRESS)) {
                String[] addressKeywords = trimmedKeywords.split("\\s+");
                predicates.add(new GuestAddressPredicate(Arrays.asList(addressKeywords)));
            } else if (prefix.equals(PREFIX_RSVP)) {
                predicates.add(new GuestRsvpPredicate(trimmedKeywords));
            } else if (prefix.equals(PREFIX_DIETARY)) {
                String[] dietaryKeywords = trimmedKeywords.split("\\s+");
                predicates.add(new GuestDietaryPredicate(Arrays.asList(dietaryKeywords)));
            } else if (prefix.equals(PREFIX_TABLE)) {
                predicates.add(new GuestTablePredicate(trimmedKeywords));
            }
        }

        // reject no filter fields
        if (predicates.size() == 0) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, GuestFilterCommand.MESSAGE_USAGE));
        }
        Predicate<Guest> chainedPredicates = createChainedPredicates(predicates);
        return new GuestFilterCommand(chainedPredicates);
    }

    private Predicate<Guest> createChainedPredicates(ArrayList<Predicate<Guest>> predicates) {
        return guest -> {
            for (Predicate<Guest> predicate : predicates) {
                if (!predicate.test(guest)) {
                    return false;
                }
            }
            return true;
        };
    }

//    public static void main (String[] args) {
//        try {
//            System.out.println("test");
//            GuestFilterCommandParser g = new GuestFilterCommandParser();
//            ModelManager m = new ModelManager();
//            g.parse("n/joe d/ ").execute(m);
//            System.out.println("successfully executed");
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//    }
}
