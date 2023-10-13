package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIETARY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RSVP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.GuestAddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.DietaryRequirements;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.RsvpStatus;
import seedu.address.model.tag.Tag;

/**
 * Parses user input for GuestAdd commands.
 */
public class GuestAddCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the GuestAddCommand
     * and returns an GuestAddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public GuestAddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_RSVP, PREFIX_DIETARY, PREFIX_TAG);

        // check compulsory fields; only name is compulsory
        if (!arePrefixesPresent(argMultimap, PREFIX_NAME) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, GuestAddCommand.MESSAGE_USAGE));
            // message usage is a generic message about the add command for guests
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME); // throws parse exception if have duplicates
        // make the empty optional fields null if they are empty
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = argMultimap.getValue(PREFIX_PHONE).isEmpty()
                ? null
                : ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = argMultimap.getValue(PREFIX_EMAIL).isEmpty()
                ? null
                : ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Address address = argMultimap.getValue(PREFIX_ADDRESS).isEmpty() // this is whether Optional is empty
                ? null
                : ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        RsvpStatus rsvpStatus = argMultimap.getValue(PREFIX_RSVP).isEmpty() // this is whether Optional is empty
                ? null
                : ParserUtil.parseRsvp(argMultimap.getValue(PREFIX_RSVP).get());
        DietaryRequirements dietaryRequirements = argMultimap.getValue(PREFIX_DIETARY).isEmpty()
                ? null
                : ParserUtil.parseDietary(argMultimap.getValue(PREFIX_DIETARY).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        // throw a ParseException as edits need to be made to Person/Guest/Vendor class first before this is valid
        throw new ParseException("Guest not created in GuestAddCommand due to un-evolved classes");

        // once Person/Guest/Vendor classes are evolved, can edit & add this back in
        // Person person = new Guest(name, phone, email, address, rsvpStatus, dietaryRequirements, tagList);
        // return new GuestAddCommand(person);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
