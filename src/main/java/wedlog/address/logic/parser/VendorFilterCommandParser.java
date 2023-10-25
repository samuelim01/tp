package wedlog.address.logic.parser;

import static wedlog.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static wedlog.address.logic.Messages.MESSAGE_NO_PREFIX_FOUND;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_NAME;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

import wedlog.address.logic.commands.VendorFilterCommand;
import wedlog.address.logic.parser.exceptions.ParseException;
import wedlog.address.model.person.AddressPredicate;
import wedlog.address.model.person.EmailPredicate;
import wedlog.address.model.person.NamePredicate;
import wedlog.address.model.person.PhonePredicate;
import wedlog.address.model.person.Vendor;

/**
 * Parses user input for VendorFilter commands.
 */
public class VendorFilterCommandParser implements Parser<VendorFilterCommand> {
    private static Prefix[] prefixes = {PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG};

    /**
     * Parses the given {@code String} of arguments in the context of the VendorFilterCommand
     * and returns an VendorFilterCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public VendorFilterCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, VendorFilterCommand.MESSAGE_USAGE));
        }
        argMultimap.verifyNoDuplicatePrefixesFor(prefixes);
        ArrayList<Predicate<? super Vendor>> predicates = new ArrayList<>();

        for (Prefix prefix : prefixes) {
            Optional<String> str = argMultimap.getValue(prefix);
            if (str.isEmpty()) { // skip the ones that the user did not specify
                continue;
            }
            String trimmedKeywords = str.get().trim();
            String[] keywords = trimmedKeywords.split("\\s+");
            if (prefix.equals(PREFIX_NAME)) {
                requireNonEmpty(trimmedKeywords);
                predicates.add(new NamePredicate(Arrays.asList(keywords)));
            } else if (prefix.equals(PREFIX_PHONE)) {
                predicates.add(new PhonePredicate(Arrays.asList(keywords)));
            } else if (prefix.equals(PREFIX_EMAIL)) {
                predicates.add(new EmailPredicate(Arrays.asList(keywords)));
            } else if (prefix.equals(PREFIX_ADDRESS)) {
                predicates.add(new AddressPredicate(Arrays.asList(keywords)));
            }
        }
        if (predicates.size() == 0) {
            throw new ParseException(String.format(MESSAGE_NO_PREFIX_FOUND, VendorFilterCommand.MESSAGE_USAGE));
        }

        Predicate<Vendor> chainedPredicates = createChainedPredicates(predicates);
        return new VendorFilterCommand(chainedPredicates);
    }

    /**
     * Truncates a chain of predicates {@code RsvpStatus} into 1 predicate.
     * This is done to find out if chained predicates return an overall true or false.
     *
     * @param predicates ArrayList of predicates.
     * @return Overall predicate.
     */
    private Predicate<Vendor> createChainedPredicates(ArrayList<Predicate<? super Vendor>> predicates) {
        return vendor -> predicates.stream().allMatch(predicate -> predicate.test(vendor));
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
