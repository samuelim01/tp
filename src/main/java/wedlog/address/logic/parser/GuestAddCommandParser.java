package wedlog.address.logic.parser;

import static wedlog.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_DIETARY;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_NAME;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_RSVP;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import wedlog.address.logic.commands.GuestAddCommand;
import wedlog.address.logic.parser.exceptions.ParseException;
import wedlog.address.model.person.Address;
import wedlog.address.model.person.DietaryRequirements;
import wedlog.address.model.person.Email;
import wedlog.address.model.person.Guest;
import wedlog.address.model.person.Name;
import wedlog.address.model.person.Phone;
import wedlog.address.model.person.RsvpStatus;
import wedlog.address.model.tag.Tag;

/**
 * Parses user input for GuestAdd commands.
 */
public class GuestAddCommandParser implements Parser<GuestAddCommand> {

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

        // throws parse exception if name is inputted twice
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME);

        // marks the optional fields null if they are empty
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = argMultimap.getValue(PREFIX_PHONE).isEmpty()
                ? null
                : ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = argMultimap.getValue(PREFIX_EMAIL).isEmpty()
                ? null
                : ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Address address = argMultimap.getValue(PREFIX_ADDRESS).isEmpty()
                ? null
                : ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        RsvpStatus rsvpStatus = argMultimap.getValue(PREFIX_RSVP).isEmpty()
                ? RsvpStatus.unknown() // no input defaults to Status stored as unknown
                : ParserUtil.parseRsvp(argMultimap.getValue(PREFIX_RSVP).get());
        DietaryRequirements dietaryRequirements = argMultimap.getValue(PREFIX_DIETARY).isEmpty()
                ? new DietaryRequirements(null)
                : ParserUtil.parseDietary(argMultimap.getValue(PREFIX_DIETARY).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Guest guest = new Guest(name, phone, email, address, rsvpStatus, dietaryRequirements, tagList);
        return new GuestAddCommand(guest);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
