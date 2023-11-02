package wedlog.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static wedlog.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static wedlog.address.logic.Messages.MESSAGE_NO_PREFIX_FOUND;
import static wedlog.address.logic.commands.CommandTestUtil.ADDRESS_DESC_VAL;
import static wedlog.address.logic.commands.CommandTestUtil.EMAIL_DESC_VAL;
import static wedlog.address.logic.commands.CommandTestUtil.NAME_DESC_VAL;
import static wedlog.address.logic.commands.CommandTestUtil.PHONE_DESC_VAL;
import static wedlog.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static wedlog.address.logic.commands.CommandTestUtil.RSVP_DESC_GIA;
import static wedlog.address.logic.commands.CommandTestUtil.TABLE_DESC_GIA;
import static wedlog.address.logic.commands.CommandTestUtil.TAG_DESC_FLORIST;
import static wedlog.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static wedlog.address.logic.commands.CommandTestUtil.TAG_DESC_PHOTOGRAPHER;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_ADDRESS_VAL;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_EMAIL_VAL;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_NAME_VAL;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_PHONE_VAL;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_TAG_FLORIST;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_TAG_PHOTOGRAPHER;
import static wedlog.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import wedlog.address.logic.commands.VendorFilterCommand;
import wedlog.address.logic.parser.exceptions.ParseException;
import wedlog.address.model.person.AddressPredicate;
import wedlog.address.model.person.EmailPredicate;
import wedlog.address.model.person.NamePredicate;
import wedlog.address.model.person.PhonePredicate;
import wedlog.address.model.person.Vendor;
import wedlog.address.model.tag.TagPredicate;

class VendorFilterCommandParserTest {
    private VendorFilterCommandParser parser = new VendorFilterCommandParser();

    @Test
    public void parse_invalidValue_failure() {
        // no predicates
        assertParseFailure(parser, " ", String.format(MESSAGE_NO_PREFIX_FOUND,
                VendorFilterCommand.MESSAGE_USAGE));

        // non empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, VendorFilterCommand.MESSAGE_USAGE));

        // rsvp present
        assertParseFailure(parser, RSVP_DESC_GIA,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, VendorFilterCommand.MESSAGE_USAGE));

        // table number present
        assertParseFailure(parser, TABLE_DESC_GIA,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, VendorFilterCommand.MESSAGE_USAGE));

        // repeated name
        assertThrows(ParseException.class, () -> parser.parse(NAME_DESC_VAL + NAME_DESC_VAL));

        // repeated phone
        assertThrows(ParseException.class, () -> parser.parse(PHONE_DESC_VAL + PHONE_DESC_VAL));

        // repeated email
        assertThrows(ParseException.class, () -> parser.parse(EMAIL_DESC_VAL + EMAIL_DESC_VAL));

        // repeated address
        assertThrows(ParseException.class, () -> parser.parse(ADDRESS_DESC_VAL + ADDRESS_DESC_VAL));
    }

    @Test
    public void parse_onlyOneFieldPresent_success() throws ParseException {
        // name present
        VendorFilterCommand vendorFilterCommand = parser.parse(NAME_DESC_VAL);
        List<Predicate<? super Vendor>> predicateList = Collections.singletonList(
                new NamePredicate(VALID_NAME_VAL));
        assertEquals(vendorFilterCommand, new VendorFilterCommand(predicateList));

        // phone present
        vendorFilterCommand = parser.parse(PHONE_DESC_VAL);
        predicateList = Collections.singletonList(new PhonePredicate(VALID_PHONE_VAL));
        assertEquals(vendorFilterCommand, new VendorFilterCommand(predicateList));

        // email present
        vendorFilterCommand = parser.parse(EMAIL_DESC_VAL);
        predicateList = Collections.singletonList(new EmailPredicate(VALID_EMAIL_VAL));
        assertEquals(vendorFilterCommand, new VendorFilterCommand(predicateList));

        // address present
        vendorFilterCommand = parser.parse(ADDRESS_DESC_VAL);
        predicateList = Collections.singletonList(new AddressPredicate(VALID_ADDRESS_VAL));
        assertEquals(vendorFilterCommand, new VendorFilterCommand(predicateList));
    }

    @Test
    public void parse_repeatedTags_success() throws ParseException {
        VendorFilterCommand guestFilterCommand = parser.parse(TAG_DESC_FRIEND + TAG_DESC_FLORIST);
        List<Predicate<? super Vendor>> predicates = Arrays.asList(
                new TagPredicate(Arrays.asList(VALID_TAG_FRIEND,
                        VALID_TAG_FLORIST)));
        assertEquals(new VendorFilterCommand(predicates), guestFilterCommand);
    }


    @Test
    public void parse_allFieldsPresent_success() throws ParseException {
        VendorFilterCommand vendorFilterCommand = parser.parse(NAME_DESC_VAL + PHONE_DESC_VAL + EMAIL_DESC_VAL
                + ADDRESS_DESC_VAL + TAG_DESC_PHOTOGRAPHER);

        List<Predicate<? super Vendor>> predicates = Arrays.asList(
                new NamePredicate(VALID_NAME_VAL),
                new PhonePredicate(VALID_PHONE_VAL),
                new EmailPredicate(VALID_EMAIL_VAL),
                new AddressPredicate(VALID_ADDRESS_VAL),
                new TagPredicate(Collections.singletonList(VALID_TAG_PHOTOGRAPHER))
        );
        assertEquals(vendorFilterCommand, new VendorFilterCommand(predicates));
    }
}
