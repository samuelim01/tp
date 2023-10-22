package wedlog.address.logic.parser;

import static wedlog.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_NAME;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import wedlog.address.logic.commands.VendorAddCommand;
import wedlog.address.logic.parser.exceptions.ParseException;
import wedlog.address.model.person.Address;
import wedlog.address.model.person.Email;
import wedlog.address.model.person.Name;
import wedlog.address.model.person.Phone;
import wedlog.address.model.person.Vendor;
import wedlog.address.model.tag.Tag;

/**
 * Parses user input specifically for VendorAdd commands.
 */
public class VendorAddCommandParser implements Parser<VendorAddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the VendorAddCommand
     * and returns an VendorAddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public VendorAddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

        // check compulsory fields; only name is compulsory
        if (!arePrefixesPresent(argMultimap, PREFIX_NAME) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, VendorAddCommand.MESSAGE_USAGE));
        }

        // throws parse exception if any field (except tags) is inputted twice
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS);

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parseOptionally(argMultimap.getValue(PREFIX_PHONE), ParserUtil::parsePhone);
        Email email = ParserUtil.parseOptionally(argMultimap.getValue(PREFIX_EMAIL), ParserUtil::parseEmail);
        Address address = ParserUtil.parseOptionally(argMultimap.getValue(PREFIX_ADDRESS), ParserUtil::parseAddress);
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));


        Vendor vendor = new Vendor(name, phone, email, address, tagList);
        return new VendorAddCommand(vendor);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
