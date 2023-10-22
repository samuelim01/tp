package wedlog.address.logic.parser;

import wedlog.address.logic.commands.VendorFilterCommand;
import wedlog.address.logic.parser.exceptions.ParseException;
import wedlog.address.model.ModelManager;
import wedlog.address.model.person.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;


import static wedlog.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static wedlog.address.logic.Messages.MESSAGE_NO_PREFIX_FOUND;
import static wedlog.address.logic.parser.CliSyntax.*;

/**
 * Parses user input for VendorFilter commands.
 */
public class VendorFilterCommandParser implements Parser<VendorFilterCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the VendorFilterCommand
     * and returns an VendorFilterCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public VendorFilterCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, VendorFilterCommand.MESSAGE_USAGE));
        }
        Prefix[] prefixes = {PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS};
        // throws parse exception if prefixes are inputted twice
        argMultimap.verifyNoDuplicatePrefixesFor(prefixes);
        ArrayList<Predicate<Vendor>> predicates = new ArrayList<>();
        for (Prefix prefix : prefixes) {
            Optional<String> str = argMultimap.getValue(prefix);
            if (str.isEmpty()) { // skip the ones that the user did not specify
                continue;
            }

            String trimmedKeywords = str.get().trim();
            if (prefix.equals(PREFIX_NAME)) {
                String[] nameKeywords = trimmedKeywords.split("\\s+");
                predicates.add(new VendorNamePredicate(Arrays.asList(nameKeywords)));
            } else if (prefix.equals(PREFIX_PHONE)) {
                predicates.add(new VendorPhonePredicate(trimmedKeywords));
            } else if (prefix.equals(PREFIX_EMAIL)) {
                predicates.add(new VendorEmailPredicate(trimmedKeywords));
            } else if (prefix.equals(PREFIX_ADDRESS)) {
                System.out.println("test 4");
                String[] addressKeywords = trimmedKeywords.split("\\s+");
                predicates.add(new VendorAddressPredicate(Arrays.asList(addressKeywords)));
                System.out.println("test 5");
            }
        }

        // reject no filter fields
        if (predicates.size() == 0) {
            throw new ParseException(String.format(MESSAGE_NO_PREFIX_FOUND, VendorFilterCommand.MESSAGE_USAGE));
        }
        Predicate<Vendor> chainedPredicates = createChainedPredicates(predicates);
        return new VendorFilterCommand(chainedPredicates);
    }

    private Predicate<Vendor> createChainedPredicates(ArrayList<Predicate<Vendor>> predicates) {
        return vendor -> {
            for (Predicate<Vendor> predicate : predicates) {
                if (!predicate.test(vendor)) {
                    return false;
                }
            }
            return true;
        };
    }

    public static void main (String[] args) {
        try {
            System.out.println("test");
            VendorFilterCommandParser g = new VendorFilterCommandParser();
            ModelManager m = new ModelManager();
            g.parse(" p/97834 e/asoidhaoi@gmail.com a/asd").execute(m);
            System.out.println("successfully executed");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
