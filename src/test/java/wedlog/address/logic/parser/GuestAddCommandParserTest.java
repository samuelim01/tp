package wedlog.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static wedlog.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static wedlog.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static wedlog.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static wedlog.address.logic.commands.CommandTestUtil.DIETARY_DESC_AMY;
import static wedlog.address.logic.commands.CommandTestUtil.DIETARY_DESC_BOB;
import static wedlog.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static wedlog.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static wedlog.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static wedlog.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static wedlog.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static wedlog.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static wedlog.address.logic.commands.CommandTestUtil.INVALID_RSVP_DESC;
import static wedlog.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static wedlog.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static wedlog.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static wedlog.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static wedlog.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static wedlog.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static wedlog.address.logic.commands.CommandTestUtil.RSVP_DESC_AMY;
import static wedlog.address.logic.commands.CommandTestUtil.RSVP_DESC_BOB;
import static wedlog.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static wedlog.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static wedlog.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static wedlog.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static wedlog.address.testutil.TypicalGuests.AMY;
import static wedlog.address.testutil.TypicalGuests.BOB;

import org.junit.jupiter.api.Test;

import wedlog.address.logic.commands.GuestAddCommand;
import wedlog.address.logic.parser.exceptions.ParseException;
import wedlog.address.model.person.Address;
import wedlog.address.model.person.Email;
import wedlog.address.model.person.Guest;
import wedlog.address.model.person.Name;
import wedlog.address.model.person.Phone;
import wedlog.address.model.person.RsvpStatus;
import wedlog.address.model.tag.Tag;
import wedlog.address.testutil.GuestBuilder;

public class GuestAddCommandParserTest {
    private GuestAddCommandParser parser = new GuestAddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws ParseException {
        GuestAddCommand guestAddCommand = parser.parse(NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + RSVP_DESC_AMY + DIETARY_DESC_AMY + TAG_DESC_FRIEND);
        assertEquals(guestAddCommand, new GuestAddCommand(AMY));
    }

    @Test
    public void parse_onlyNamePresent_success() throws ParseException {
        Guest expectedGuest = new GuestBuilder(VALID_NAME_AMY).build();
        GuestAddCommand guestAddCommand = parser.parse(NAME_DESC_AMY);
        assertEquals(new GuestAddCommand(expectedGuest), guestAddCommand);
    }

    @Test
    public void parse_repeatedNameValue_failure() throws ParseException {
        String validExpectedPersonString = NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND;

        assertThrows(ParseException.class, () -> parser.parse(NAME_DESC_AMY + validExpectedPersonString));
    }

    @Test
    public void parse_invalidValues_failure() throws ParseException {
        String validExpectedPersonString = NAME_DESC_BOB + PHONE_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND;

        // invalid value followed by valid value

        // invalid name
        assertThrows(ParseException.class, () -> parser.parse(INVALID_NAME_DESC + validExpectedPersonString));

        // invalid email
        assertThrows(ParseException.class, () -> parser.parse(INVALID_EMAIL_DESC + validExpectedPersonString));


        // valid value followed by invalid value

        // invalid name
        assertThrows(ParseException.class, () -> parser.parse(validExpectedPersonString + INVALID_NAME_DESC));

        // invalid email
        assertThrows(ParseException.class, () -> parser.parse(validExpectedPersonString + INVALID_EMAIL_DESC));

        // invalid phone
        assertThrows(ParseException.class, () -> parser.parse(validExpectedPersonString + INVALID_PHONE_DESC));

        // invalid address
        assertThrows(ParseException.class, () -> parser.parse(validExpectedPersonString + INVALID_ADDRESS_DESC));

        // invalid rsvp
        assertThrows(ParseException.class, () -> parser.parse(validExpectedPersonString + INVALID_RSVP_DESC));

    }

    @Test
    public void parse_missingTags_success() {
        // zero tags
        Guest expectedGuest = new GuestBuilder(AMY).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                        + RSVP_DESC_AMY + DIETARY_DESC_AMY,
                new GuestAddCommand(expectedGuest));
    }

    @Test
    public void parse_missingPhone_success() {
        Guest expectedGuest = new GuestBuilder(BOB).withoutPhone().build();
        assertParseSuccess(parser, NAME_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + RSVP_DESC_BOB + DIETARY_DESC_BOB + TAG_DESC_FRIEND
                + TAG_DESC_HUSBAND, new GuestAddCommand(expectedGuest));
    }

    @Test
    public void parse_missingEmail_success() {

        Guest expectedGuest = new GuestBuilder(BOB).withoutEmail().build();
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + ADDRESS_DESC_BOB
                + RSVP_DESC_BOB + DIETARY_DESC_BOB + TAG_DESC_FRIEND
                + TAG_DESC_HUSBAND, new GuestAddCommand(expectedGuest));
    }

    @Test
    public void parse_missingAddress_success() {
        Guest expectedGuest = new GuestBuilder(BOB).withoutAddress().build();
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + RSVP_DESC_BOB + DIETARY_DESC_BOB + TAG_DESC_FRIEND
                + TAG_DESC_HUSBAND, new GuestAddCommand(expectedGuest));

    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, GuestAddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Email.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Address.MESSAGE_CONSTRAINTS);

        // invalid rsvp
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INVALID_RSVP_DESC + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, RsvpStatus.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, GuestAddCommand.MESSAGE_USAGE));
    }
}
