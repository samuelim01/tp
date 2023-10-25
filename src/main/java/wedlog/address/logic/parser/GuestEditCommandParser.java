package wedlog.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static wedlog.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_DIETARY;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_NAME;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_RSVP;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_TABLE;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import wedlog.address.commons.core.index.Index;
import wedlog.address.logic.commands.GuestEditCommand;
import wedlog.address.logic.commands.GuestEditCommand.EditGuestDescriptor;
import wedlog.address.logic.parser.exceptions.ParseException;
import wedlog.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new GuestEditCommand object
 */
public class GuestEditCommandParser implements Parser<GuestEditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the GuestEditCommand
     * and returns an GuestEditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public GuestEditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_RSVP, PREFIX_DIETARY, PREFIX_TABLE, PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, GuestEditCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                PREFIX_RSVP, PREFIX_DIETARY, PREFIX_TABLE);

        EditGuestDescriptor editGuestDescriptor = new EditGuestDescriptor();

        // TODO: UPDATE AFTER SAMUEL'S PR IS MERGED
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editGuestDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editGuestDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editGuestDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editGuestDescriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }
        if (argMultimap.getValue(PREFIX_RSVP).isPresent()) {
            editGuestDescriptor.setRsvp(ParserUtil.parseRsvp(argMultimap.getValue(PREFIX_RSVP).get()));
        }
        if (argMultimap.getValue(PREFIX_DIETARY).isPresent()) {
            editGuestDescriptor.setDietary(ParserUtil.parseDietary(argMultimap.getValue(PREFIX_DIETARY).get()));
        }
        if (argMultimap.getValue(PREFIX_TABLE).isPresent()) {
            editGuestDescriptor.setTable(ParserUtil.parseTable(argMultimap.getValue(PREFIX_TABLE).get()));
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editGuestDescriptor::setTags);

        if (!editGuestDescriptor.isAnyFieldEdited()) {
            throw new ParseException(GuestEditCommand.MESSAGE_NOT_EDITED);
        }

        return new GuestEditCommand(index, editGuestDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}
