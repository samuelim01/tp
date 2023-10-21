package wedlog.address.logic.parser;


import wedlog.address.logic.commands.EditCommand;
import wedlog.address.logic.commands.FindCommand;
import wedlog.address.logic.commands.GuestAddCommand;
import wedlog.address.logic.parser.exceptions.ParseException;
import wedlog.address.model.person.*;
import wedlog.address.model.tag.Tag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;


import static wedlog.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static wedlog.address.logic.parser.CliSyntax.*;

/**
 * Parses user input for GuestFilter commands.
 */
public class GuestFilterCommandParser implements Parser<GuestFilterCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the GuestAddCommand
     * and returns an GuestAddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public GuestFilterCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_RSVP, PREFIX_DIETARY, PREFIX_TAG);

        ArrayList<? extends Predicate<? extends Person>> predicates = new ArrayList<>();
        Prefix[] prefixes = {PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                PREFIX_RSVP, PREFIX_TABLE, PREFIX_DIETARY, PREFIX_TAG};
        for (Prefix prefix : prefixes) {
            Optional<String> str = argMultimap.getValue(prefix);
            if (str.isEmpty()) { // skip the ones that the user did not specify
                continue;
            }

            String trimmedKeywords = str.get().trim();
            if (prefix.equals(PREFIX_NAME)) {
                String[] nameKeywords = trimmedKeywords.split("\\s+");
                predicates.add(new NamePredicate(Arrays.asList(nameKeywords)));
            } else if (prefix.equals(PREFIX_PHONE)) {
                predicates.add(new PhonePredicate(trimmedKeywords));
            } else if (prefix.equals(PREFIX_EMAIL)) {
                predicates.add(new EmailPredicate(trimmedKeywords));
            } else if (prefix.equals(PREFIX_ADDRESS)) {
                String[] addressKeywords = trimmedKeywords.split("\\s+");
                predicates.add(new AddressPredicate(trimmedKeywords));
            } else if (prefix.equals(PREFIX_RSVP)) {
                predicates.add(new RsvpPredicate(trimmedKeywords));
            } else if (prefix.equals(PREFIX_DIETARY)) {
                predicates.add(new DietaryPredicate(trimmedKeywords));
            } else if (prefix.equals(PREFIX_TABLE)) {
                predicates.add(new TablePredicate(trimmedKeywords));
            }
            // not sure if can filter tag yet
//            else if (prefix.equals(PREFIX_TAG)) {
//                predicates.add(new TagPredicate(trimmedKeywords));
//            }
        }
        Predicate<Guest> chainedPredicates = createChainedPredicates(predicates);
        return new GuestFilterCommand(chainedPredicates);
    }

    private Predicate<Person> createChainedPredicates(ArrayList<Predicate<Person>> predicates) {
        return guest -> {
            for (Predicate<Person> predicate : predicates) {
                if (!predicate.test(guest)) {
                    return false;
                }
            }
            return true;
        };
    }
}
