package wedlog.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static wedlog.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static wedlog.address.logic.Messages.MESSAGE_NO_PREFIX_FOUND;
import static wedlog.address.logic.commands.CommandTestUtil.EMAIL_DESC_VAL;
import static wedlog.address.logic.commands.CommandTestUtil.PHONE_DESC_VAL;
import static wedlog.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static wedlog.address.logic.commands.CommandTestUtil.RSVP_DESC_GIA;
import static wedlog.address.logic.commands.CommandTestUtil.TABLE_DESC_GIA;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_EMAIL_VAL;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_PHONE_VAL;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_NAME;
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

        // empty name
        assertParseFailure(parser, " " + PREFIX_NAME, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        "Cannot filter for empty compulsory field."));

        // rsvp present
        assertParseFailure(parser, RSVP_DESC_GIA,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, VendorFilterCommand.MESSAGE_USAGE));

        // table number present
        assertParseFailure(parser, TABLE_DESC_GIA,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, VendorFilterCommand.MESSAGE_USAGE));

        // repeated name
        assertThrows(ParseException.class, () -> parser.parse(" n/val n/val"));

        // repeated phone
        assertThrows(ParseException.class, () -> parser.parse(PHONE_DESC_VAL + PHONE_DESC_VAL));

        // repeated email
        assertThrows(ParseException.class, () -> parser.parse(EMAIL_DESC_VAL + EMAIL_DESC_VAL));

        // repeated address
        assertThrows(ParseException.class, () -> parser.parse(" a/Jurong a/West"));
    }

    @Test
    public void parse_onlyOneFieldPresent_success() throws ParseException {
        // name present
        VendorFilterCommand vendorFilterCommand = parser.parse(" n/val");
        List<Predicate<? super Vendor>> predicateList = Collections.singletonList(
                new NamePredicate(Collections.singletonList("val")));
        assertEquals(vendorFilterCommand, new VendorFilterCommand(predicateList));

        // phone present
        vendorFilterCommand = parser.parse(PHONE_DESC_VAL);
        predicateList = Collections.singletonList(new PhonePredicate(Collections.singletonList(VALID_PHONE_VAL)));
        assertEquals(vendorFilterCommand, new VendorFilterCommand(predicateList));

        // email present
        vendorFilterCommand = parser.parse(EMAIL_DESC_VAL);
        predicateList = Collections.singletonList(new EmailPredicate(Collections.singletonList(VALID_EMAIL_VAL)));
        assertEquals(vendorFilterCommand, new VendorFilterCommand(predicateList));

        // address present
        vendorFilterCommand = parser.parse(" a/jurong");
        predicateList = Collections.singletonList(new AddressPredicate(Collections.singletonList("jurong")));
        assertEquals(vendorFilterCommand, new VendorFilterCommand(predicateList));
    }

    @Test
    public void parse_allFieldsPresent_success() throws ParseException {
        VendorFilterCommand vendorFilterCommand = parser.parse(" n/val" + PHONE_DESC_VAL + EMAIL_DESC_VAL
                + " a/jurong");

        List<Predicate<? super Vendor>> predicates = Arrays.asList(
                new NamePredicate(Arrays.asList("val")),
                new PhonePredicate(Arrays.asList(VALID_PHONE_VAL)),
                new EmailPredicate(Arrays.asList(VALID_EMAIL_VAL)),
                new AddressPredicate(Arrays.asList("jurong"))
        );
        assertEquals(vendorFilterCommand, new VendorFilterCommand(predicates));
    }
}
