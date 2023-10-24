package wedlog.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static wedlog.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static wedlog.address.logic.commands.CommandTestUtil.ADDRESS_DESC_GIA;
import static wedlog.address.logic.commands.CommandTestUtil.DIETARY_DESC_GIA;
import static wedlog.address.logic.commands.CommandTestUtil.EMAIL_DESC_GIA;
import static wedlog.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static wedlog.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static wedlog.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static wedlog.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static wedlog.address.logic.commands.CommandTestUtil.INVALID_RSVP_DESC;
import static wedlog.address.logic.commands.CommandTestUtil.INVALID_TABLE_DESC;
import static wedlog.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static wedlog.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static wedlog.address.logic.commands.CommandTestUtil.NAME_DESC_GIA;
import static wedlog.address.logic.commands.CommandTestUtil.PHONE_DESC_GIA;
import static wedlog.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static wedlog.address.logic.commands.CommandTestUtil.RSVP_DESC_GIA;
import static wedlog.address.logic.commands.CommandTestUtil.TABLE_DESC_GIA;
import static wedlog.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static wedlog.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_NAME_GIA;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static wedlog.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static wedlog.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static wedlog.address.testutil.TypicalGuests.GIA;

import org.junit.jupiter.api.Test;

import wedlog.address.logic.commands.GuestAddCommand;
import wedlog.address.logic.parser.exceptions.ParseException;
import wedlog.address.model.person.Address;
import wedlog.address.model.person.Email;
import wedlog.address.model.person.Guest;
import wedlog.address.model.person.Name;
import wedlog.address.model.person.Phone;
import wedlog.address.model.person.RsvpStatus;
import wedlog.address.model.person.TableNumber;
import wedlog.address.model.tag.Tag;
import wedlog.address.testutil.GuestBuilder;

public class GuestAddCommandParserTest {
    private GuestAddCommandParser parser = new GuestAddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws ParseException {
        GuestAddCommand guestAddCommand = parser.parse(NAME_DESC_GIA + PHONE_DESC_GIA + EMAIL_DESC_GIA
                + ADDRESS_DESC_GIA + RSVP_DESC_GIA + DIETARY_DESC_GIA + TABLE_DESC_GIA + TAG_DESC_FRIEND);
        assertEquals(guestAddCommand, new GuestAddCommand(GIA));
    }

    @Test
    public void parse_onlyNamePresent_success() throws ParseException {
        Guest expectedGuest = new GuestBuilder(VALID_NAME_GIA).build();
        GuestAddCommand guestAddCommand = parser.parse(NAME_DESC_GIA);
        assertEquals(new GuestAddCommand(expectedGuest), guestAddCommand);
    }

    @Test
    public void parse_repeatedNameValue_failure() {
        String validExpectedPersonString = NAME_DESC_GIA + PHONE_DESC_GIA + EMAIL_DESC_GIA
                + ADDRESS_DESC_GIA + TAG_DESC_FRIEND;

        assertThrows(ParseException.class, () -> parser.parse(NAME_DESC_AMY + validExpectedPersonString));
    }

    /**
     * Tests instance where a field has an invalid value (with no valid value present).
     */
    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_GIA + EMAIL_DESC_GIA + ADDRESS_DESC_GIA
                + RSVP_DESC_GIA + DIETARY_DESC_GIA + TABLE_DESC_GIA + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_GIA + INVALID_PHONE_DESC + EMAIL_DESC_GIA + ADDRESS_DESC_GIA
                + RSVP_DESC_GIA + DIETARY_DESC_GIA + TABLE_DESC_GIA + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_GIA + PHONE_DESC_GIA + INVALID_EMAIL_DESC + ADDRESS_DESC_GIA
                + RSVP_DESC_GIA + DIETARY_DESC_GIA + TABLE_DESC_GIA + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Email.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_GIA + PHONE_DESC_GIA + EMAIL_DESC_GIA + INVALID_ADDRESS_DESC
                + RSVP_DESC_GIA + DIETARY_DESC_GIA + TABLE_DESC_GIA + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Address.MESSAGE_CONSTRAINTS);

        // invalid rsvp
        assertParseFailure(parser, NAME_DESC_GIA + PHONE_DESC_GIA + EMAIL_DESC_GIA + ADDRESS_DESC_GIA
                + INVALID_RSVP_DESC + DIETARY_DESC_GIA + TABLE_DESC_GIA + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                RsvpStatus.MESSAGE_CONSTRAINTS);

        // invalid dietary requirement does not exist

        // invalid table number
        assertParseFailure(parser, NAME_DESC_GIA + PHONE_DESC_GIA + EMAIL_DESC_GIA + ADDRESS_DESC_GIA
                + RSVP_DESC_GIA + DIETARY_DESC_GIA + INVALID_TABLE_DESC + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                TableNumber.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_GIA + PHONE_DESC_GIA + EMAIL_DESC_GIA + ADDRESS_DESC_GIA
                + RSVP_DESC_GIA + DIETARY_DESC_GIA + TABLE_DESC_GIA + INVALID_TAG_DESC + VALID_TAG_FRIEND,
                Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_GIA + EMAIL_DESC_GIA + INVALID_ADDRESS_DESC
                + RSVP_DESC_GIA + DIETARY_DESC_GIA + TABLE_DESC_GIA,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_GIA + PHONE_DESC_GIA + EMAIL_DESC_GIA
                        + ADDRESS_DESC_GIA + RSVP_DESC_GIA + DIETARY_DESC_GIA + TABLE_DESC_GIA + TAG_DESC_HUSBAND
                        + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, GuestAddCommand.MESSAGE_USAGE));
    }

    /**
     * Tests instance where a field has an invalid value (with a valid value before / after).
     */
    @Test
    public void parse_invalidValuesWithValidValues_failure() throws ParseException {
        String validExpectedPersonString = NAME_DESC_GIA + PHONE_DESC_GIA + EMAIL_DESC_GIA + ADDRESS_DESC_GIA
                + RSVP_DESC_GIA + DIETARY_DESC_GIA + TABLE_DESC_GIA + TAG_DESC_FRIEND;

        // invalid value followed by valid value

        // invalid name
        assertThrows(ParseException.class, () -> parser.parse(INVALID_NAME_DESC + validExpectedPersonString));

        // invalid phone
        assertThrows(ParseException.class, () -> parser.parse(INVALID_PHONE_DESC + validExpectedPersonString));

        // invalid email
        assertThrows(ParseException.class, () -> parser.parse(INVALID_EMAIL_DESC + validExpectedPersonString));

        // invalid address
        assertThrows(ParseException.class, () -> parser.parse(INVALID_ADDRESS_DESC + validExpectedPersonString));

        // invalid rsvp
        assertThrows(ParseException.class, () -> parser.parse(INVALID_RSVP_DESC + validExpectedPersonString));

        // invalid dietary requirement does not exist

        // invalid table number
        assertThrows(ParseException.class, () -> parser.parse(INVALID_TABLE_DESC + validExpectedPersonString));


        // valid value followed by invalid value

        // invalid name
        assertThrows(ParseException.class, () -> parser.parse(validExpectedPersonString + INVALID_NAME_DESC));

        // invalid phone
        assertThrows(ParseException.class, () -> parser.parse(validExpectedPersonString + INVALID_PHONE_DESC));

        // invalid email
        assertThrows(ParseException.class, () -> parser.parse(validExpectedPersonString + INVALID_EMAIL_DESC));

        // invalid address
        assertThrows(ParseException.class, () -> parser.parse(validExpectedPersonString + INVALID_ADDRESS_DESC));

        // invalid rsvp
        assertThrows(ParseException.class, () -> parser.parse(validExpectedPersonString + INVALID_RSVP_DESC));

        // invalid dietary requirement does not exist

        // invalid table number
        assertThrows(ParseException.class, () -> parser.parse(validExpectedPersonString + INVALID_TABLE_DESC));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, GuestAddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_GIA + PHONE_DESC_GIA + EMAIL_DESC_GIA + ADDRESS_DESC_GIA,
                expectedMessage);
    }

    @Test
    public void parse_missingPhone_success() {
        Guest expectedGuest = new GuestBuilder(GIA).withoutPhone().build();
        assertParseSuccess(parser, NAME_DESC_GIA + EMAIL_DESC_GIA + ADDRESS_DESC_GIA
                + RSVP_DESC_GIA + DIETARY_DESC_GIA + TABLE_DESC_GIA + TAG_DESC_FRIEND,
                new GuestAddCommand(expectedGuest));
    }

    @Test
    public void parse_missingEmail_success() {
        Guest expectedGuest = new GuestBuilder(GIA).withoutEmail().build();
        assertParseSuccess(parser, NAME_DESC_GIA + PHONE_DESC_GIA + ADDRESS_DESC_GIA
                + RSVP_DESC_GIA + DIETARY_DESC_GIA + TABLE_DESC_GIA + TAG_DESC_FRIEND,
                new GuestAddCommand(expectedGuest));
    }

    @Test
    public void parse_missingAddress_success() {
        Guest expectedGuest = new GuestBuilder(GIA).withoutAddress().build();
        assertParseSuccess(parser, NAME_DESC_GIA + PHONE_DESC_GIA + EMAIL_DESC_GIA
                + RSVP_DESC_GIA + DIETARY_DESC_GIA + TABLE_DESC_GIA + TAG_DESC_FRIEND,
                new GuestAddCommand(expectedGuest));
    }

    @Test
    public void parse_missingRsvp_success() {
        Guest expectedGuest = new GuestBuilder(GIA).withUnknownRsvpStatus().build();
        assertParseSuccess(parser, NAME_DESC_GIA + PHONE_DESC_GIA + EMAIL_DESC_GIA + ADDRESS_DESC_GIA
                + DIETARY_DESC_GIA + TABLE_DESC_GIA + TAG_DESC_FRIEND,
                new GuestAddCommand(expectedGuest));
    }

    @Test
    public void parse_missingDietaryRequirement_success() {
        Guest expectedGuest = new GuestBuilder(GIA).withDietaryRequirements().build();
        assertParseSuccess(parser, NAME_DESC_GIA + PHONE_DESC_GIA + EMAIL_DESC_GIA + ADDRESS_DESC_GIA
                + RSVP_DESC_GIA + TABLE_DESC_GIA + TAG_DESC_FRIEND,
                new GuestAddCommand(expectedGuest));
    }

    @Test
    public void parse_missingTableNumber_success() {
        Guest expectedGuest = new GuestBuilder(GIA).withoutTableNumber().build();
        assertParseSuccess(parser, NAME_DESC_GIA + PHONE_DESC_GIA + EMAIL_DESC_GIA + ADDRESS_DESC_GIA
                + RSVP_DESC_GIA + DIETARY_DESC_GIA + TAG_DESC_FRIEND,
                new GuestAddCommand(expectedGuest));
    }

    @Test
    public void parse_missingTags_success() {
        // zero tags
        Guest expectedGuest = new GuestBuilder(GIA).withTags().build();
        assertParseSuccess(parser, NAME_DESC_GIA + PHONE_DESC_GIA + EMAIL_DESC_GIA + ADDRESS_DESC_GIA
                        + RSVP_DESC_GIA + DIETARY_DESC_GIA + TABLE_DESC_GIA,
                new GuestAddCommand(expectedGuest));
    }
}
