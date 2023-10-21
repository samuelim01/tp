package wedlog.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static wedlog.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static wedlog.address.logic.commands.VendorEditCommand.EditVendorDescriptor;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_NAME;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import wedlog.address.commons.core.index.Index;
import wedlog.address.logic.commands.VendorEditCommand;
import wedlog.address.logic.parser.exceptions.ParseException;
import wedlog.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new VendorEditCommand object
 */
public class VendorEditCommandParser implements Parser<VendorEditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the VendorEditCommand
     * and returns an VendorEditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public VendorEditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    VendorEditCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS);

        EditVendorDescriptor editVendorDescriptor = new EditVendorDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editVendorDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editVendorDescriptor.setPhone(ParserUtil.parseIfNotBlank(argMultimap.getValue(PREFIX_PHONE).get(),
                    ParserUtil::parsePhone));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editVendorDescriptor.setEmail(ParserUtil.parseIfNotBlank(argMultimap.getValue(PREFIX_EMAIL).get(),
                    ParserUtil::parseEmail));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editVendorDescriptor.setAddress(ParserUtil.parseIfNotBlank(argMultimap.getValue(PREFIX_ADDRESS).get(),
                    ParserUtil::parseAddress));
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editVendorDescriptor::setTags);

        if (!editVendorDescriptor.isAnyFieldEdited()) {
            throw new ParseException(VendorEditCommand.MESSAGE_NOT_EDITED);
        }

        return new VendorEditCommand(index, editVendorDescriptor);
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
