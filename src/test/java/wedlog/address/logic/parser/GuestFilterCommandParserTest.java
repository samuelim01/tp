package wedlog.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static wedlog.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static wedlog.address.logic.Messages.MESSAGE_NO_PREFIX_FOUND;
import static wedlog.address.logic.commands.CommandTestUtil.ADDRESS_DESC_GIA;
import static wedlog.address.logic.commands.CommandTestUtil.DIETARY_DESC_GABE;
import static wedlog.address.logic.commands.CommandTestUtil.DIETARY_DESC_GIA;
import static wedlog.address.logic.commands.CommandTestUtil.EMAIL_DESC_GIA;
import static wedlog.address.logic.commands.CommandTestUtil.NAME_DESC_GIA;
import static wedlog.address.logic.commands.CommandTestUtil.PHONE_DESC_GIA;
import static wedlog.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static wedlog.address.logic.commands.CommandTestUtil.RSVP_DESC_GIA;
import static wedlog.address.logic.commands.CommandTestUtil.TABLE_DESC_GIA;
import static wedlog.address.logic.commands.CommandTestUtil.TAG_DESC_FLORIST;
import static wedlog.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_ADDRESS_GIA;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_DIETARY_REQUIREMENTS_GABE;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_DIETARY_REQUIREMENTS_GIA;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_EMAIL_GIA;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_NAME_GIA;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_PHONE_GIA;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_RSVP_STATUS_GIA;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_TABLE_NUMBER_GIA;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_TAG_FLORIST;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static wedlog.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import wedlog.address.logic.commands.GuestFilterCommand;
import wedlog.address.logic.parser.exceptions.ParseException;
import wedlog.address.model.person.AddressPredicate;
import wedlog.address.model.person.EmailPredicate;
import wedlog.address.model.person.Guest;
import wedlog.address.model.person.GuestRsvpPredicate;
import wedlog.address.model.person.GuestTablePredicate;
import wedlog.address.model.person.NamePredicate;
import wedlog.address.model.person.PhonePredicate;
import wedlog.address.model.tag.GuestDietaryPredicate;
import wedlog.address.model.tag.TagPredicate;

class GuestFilterCommandParserTest {
    private GuestFilterCommandParser parser = new GuestFilterCommandParser();

    @Test
    public void parse_invalidValue_failure() {
        // no predicates
        assertParseFailure(parser, " ", String.format(MESSAGE_NO_PREFIX_FOUND,
                GuestFilterCommand.MESSAGE_USAGE));

        // non empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, GuestFilterCommand.MESSAGE_USAGE));

        // repeated name
        assertThrows(ParseException.class, () -> parser.parse(" n/gina n/greg"));

        // repeated phone
        assertThrows(ParseException.class, () -> parser.parse(PHONE_DESC_GIA + PHONE_DESC_GIA));

        // repeated email
        assertThrows(ParseException.class, () -> parser.parse(EMAIL_DESC_GIA + EMAIL_DESC_GIA));

        // repeated address
        assertThrows(ParseException.class, () -> parser.parse(" a/Jurong a/West"));

        // repeated rsvp
        assertThrows(ParseException.class, () -> parser.parse(RSVP_DESC_GIA + RSVP_DESC_GIA));

        // repeated table number
        assertThrows(ParseException.class, () -> parser.parse(TABLE_DESC_GIA + TABLE_DESC_GIA));
    }

    @Test
    public void parse_repeatedDietary_success() throws ParseException {
        GuestFilterCommand guestFilterCommand = parser.parse(DIETARY_DESC_GIA + DIETARY_DESC_GABE);
        List<Predicate<? super Guest>> predicates = Arrays.asList(
                new GuestDietaryPredicate(Arrays.asList(VALID_DIETARY_REQUIREMENTS_GIA,
                        VALID_DIETARY_REQUIREMENTS_GABE)));
        assertEquals(new GuestFilterCommand(predicates), guestFilterCommand);
    }

    @Test
    public void parse_repeatedTags_success() throws ParseException {
        GuestFilterCommand guestFilterCommand = parser.parse(TAG_DESC_FRIEND + TAG_DESC_FLORIST);
        List<Predicate<? super Guest>> predicates = Arrays.asList(
                new TagPredicate(Arrays.asList(VALID_TAG_FRIEND,
                        VALID_TAG_FLORIST)));
        assertEquals(new GuestFilterCommand(predicates), guestFilterCommand);
    }

    @Test
    public void parse_onlyOneFieldPresent_success() throws ParseException {
        // name present
        GuestFilterCommand guestFilterCommand = parser.parse(NAME_DESC_GIA);
        List<Predicate<? super Guest>> predicateList = Collections.singletonList(
                new NamePredicate(VALID_NAME_GIA));
        assertEquals(guestFilterCommand, new GuestFilterCommand(predicateList));

        // phone present
        guestFilterCommand = parser.parse(PHONE_DESC_GIA);
        predicateList = Collections.singletonList(new PhonePredicate(VALID_PHONE_GIA));
        assertEquals(guestFilterCommand, new GuestFilterCommand(predicateList));

        // email present
        guestFilterCommand = parser.parse(EMAIL_DESC_GIA);
        predicateList = Collections.singletonList(new EmailPredicate(VALID_EMAIL_GIA));
        assertEquals(guestFilterCommand, new GuestFilterCommand(predicateList));

        // address present
        guestFilterCommand = parser.parse(" a/jurong");
        predicateList = Collections.singletonList(new AddressPredicate("jurong"));
        assertEquals(guestFilterCommand, new GuestFilterCommand(predicateList));

        // rsvp present
        guestFilterCommand = parser.parse(RSVP_DESC_GIA);
        predicateList = Collections.singletonList(new GuestRsvpPredicate(VALID_RSVP_STATUS_GIA));
        assertEquals(guestFilterCommand, new GuestFilterCommand(predicateList));

        // table number present
        guestFilterCommand = parser.parse(TABLE_DESC_GIA);
        predicateList = Collections.singletonList(new GuestTablePredicate(VALID_TABLE_NUMBER_GIA));
        assertEquals(guestFilterCommand, new GuestFilterCommand(predicateList));

        // dietary requirement present
        guestFilterCommand = parser.parse(DIETARY_DESC_GIA);
        predicateList = Collections.singletonList(new GuestDietaryPredicate(Collections.singletonList(
                VALID_DIETARY_REQUIREMENTS_GIA)));
        assertEquals(guestFilterCommand, new GuestFilterCommand(predicateList));

        // tag present
        guestFilterCommand = parser.parse(TAG_DESC_FRIEND);
        predicateList = Collections.singletonList(new TagPredicate(Collections.singletonList(
                VALID_TAG_FRIEND)));
        assertEquals(guestFilterCommand, new GuestFilterCommand(predicateList));
    }

    @Test
    public void parse_allFieldsPresent_success() throws ParseException {
        GuestFilterCommand guestFilterCommand = parser.parse(NAME_DESC_GIA + PHONE_DESC_GIA + EMAIL_DESC_GIA
                + ADDRESS_DESC_GIA + RSVP_DESC_GIA + TABLE_DESC_GIA + DIETARY_DESC_GIA + TAG_DESC_FRIEND);

        List<Predicate<? super Guest>> predicates = Arrays.asList(
                new NamePredicate(VALID_NAME_GIA),
                new PhonePredicate(VALID_PHONE_GIA),
                new EmailPredicate(VALID_EMAIL_GIA),
                new AddressPredicate(VALID_ADDRESS_GIA),
                new GuestRsvpPredicate(VALID_RSVP_STATUS_GIA),
                new GuestTablePredicate(VALID_TABLE_NUMBER_GIA),
                new GuestDietaryPredicate(Arrays.asList(VALID_DIETARY_REQUIREMENTS_GIA)),
                new TagPredicate(Arrays.asList(VALID_TAG_FRIEND))
        );
        assertEquals(guestFilterCommand, new GuestFilterCommand(predicates));
    }
}
