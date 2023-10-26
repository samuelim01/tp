package wedlog.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static wedlog.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static wedlog.address.logic.commands.CommandTestUtil.ADDRESS_DESC_VAL;
import static wedlog.address.logic.commands.CommandTestUtil.EMAIL_DESC_VAL;
import static wedlog.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static wedlog.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static wedlog.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static wedlog.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static wedlog.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static wedlog.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static wedlog.address.logic.commands.CommandTestUtil.NAME_DESC_VAL;
import static wedlog.address.logic.commands.CommandTestUtil.PHONE_DESC_VAL;
import static wedlog.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static wedlog.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static wedlog.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static wedlog.address.logic.commands.CommandTestUtil.TAG_DESC_PHOTOGRAPHER;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_NAME_VAL;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static wedlog.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static wedlog.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static wedlog.address.testutil.TypicalVendors.VAL;

import org.junit.jupiter.api.Test;

import wedlog.address.logic.commands.VendorAddCommand;
import wedlog.address.logic.parser.exceptions.ParseException;
import wedlog.address.model.person.Address;
import wedlog.address.model.person.Email;
import wedlog.address.model.person.Name;
import wedlog.address.model.person.Phone;
import wedlog.address.model.person.Vendor;
import wedlog.address.model.tag.Tag;
import wedlog.address.testutil.TypicalVendors;
import wedlog.address.testutil.VendorBuilder;

public class VendorAddCommandParserTest {
    private VendorAddCommandParser parser = new VendorAddCommandParser();
    @Test
    public void parse_allFieldsPresent_success() throws ParseException {
        VendorAddCommand vendorAddCommand = parser.parse(NAME_DESC_VAL + PHONE_DESC_VAL + EMAIL_DESC_VAL
                + ADDRESS_DESC_VAL + TAG_DESC_PHOTOGRAPHER);
        assertEquals(vendorAddCommand, new VendorAddCommand(VAL));
    }

    @Test
    public void parse_onlyNamePresent_success() throws ParseException {
        Vendor expectedVendor = new VendorBuilder(VALID_NAME_VAL).build();
        VendorAddCommand vendorAddCommand = parser.parse(NAME_DESC_VAL);
        assertEquals(new VendorAddCommand(expectedVendor), vendorAddCommand);
    }

    @Test
    public void parse_repeatedNameValue_failure() {
        String validExpectedPersonString = NAME_DESC_VAL + PHONE_DESC_VAL + EMAIL_DESC_VAL
                + ADDRESS_DESC_VAL + TAG_DESC_FRIEND;

        assertThrows(ParseException.class, () -> parser.parse(NAME_DESC_BOB + validExpectedPersonString));
    }

    /**
     * Tests instance where a field has an invalid value (with no valid value present).
     */
    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_VAL + EMAIL_DESC_VAL + ADDRESS_DESC_VAL
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_VAL + INVALID_PHONE_DESC + EMAIL_DESC_VAL + ADDRESS_DESC_VAL
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_VAL + PHONE_DESC_VAL + INVALID_EMAIL_DESC + ADDRESS_DESC_VAL
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Email.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_VAL + PHONE_DESC_VAL + EMAIL_DESC_VAL + INVALID_ADDRESS_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Address.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_VAL + PHONE_DESC_VAL + EMAIL_DESC_VAL + ADDRESS_DESC_VAL
                + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_VAL + EMAIL_DESC_VAL + INVALID_ADDRESS_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_VAL + PHONE_DESC_VAL + EMAIL_DESC_VAL
                        + ADDRESS_DESC_VAL + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, VendorAddCommand.MESSAGE_USAGE));
    }

    /**
     * Tests instance where a field has an invalid value (with a valid value before / after).
     */
    @Test
    public void parse_invalidValuesWithValidValues_failure() {
        String validExpectedPersonString = NAME_DESC_VAL + PHONE_DESC_VAL
                + ADDRESS_DESC_VAL + TAG_DESC_FRIEND;

        // invalid value followed by valid value

        // invalid name
        assertThrows(ParseException.class, () -> parser.parse(INVALID_NAME_DESC + validExpectedPersonString));

        // invalid phone
        assertThrows(ParseException.class, () -> parser.parse(INVALID_PHONE_DESC + validExpectedPersonString));

        // invalid email
        assertThrows(ParseException.class, () -> parser.parse(INVALID_EMAIL_DESC + validExpectedPersonString));

        // invalid address
        assertThrows(ParseException.class, () -> parser.parse(INVALID_ADDRESS_DESC + validExpectedPersonString));


        // valid value followed by invalid value

        // invalid name
        assertThrows(ParseException.class, () -> parser.parse(validExpectedPersonString + INVALID_NAME_DESC));

        // invalid phone
        assertThrows(ParseException.class, () -> parser.parse(validExpectedPersonString + INVALID_PHONE_DESC));

        // invalid email
        assertThrows(ParseException.class, () -> parser.parse(validExpectedPersonString + INVALID_EMAIL_DESC));

        // invalid address
        assertThrows(ParseException.class, () -> parser.parse(validExpectedPersonString + INVALID_ADDRESS_DESC));

    }

    @Test
    public void parse_compulsoryNameFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, VendorAddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_VAL + PHONE_DESC_VAL + EMAIL_DESC_VAL + ADDRESS_DESC_VAL,
                expectedMessage);
    }

    @Test
    public void parse_missingPhone_success() {
        Vendor expectedVendor = new VendorBuilder(VAL).withoutPhone().build();
        assertParseSuccess(parser, NAME_DESC_VAL + EMAIL_DESC_VAL + ADDRESS_DESC_VAL
                + TAG_DESC_PHOTOGRAPHER, new VendorAddCommand(expectedVendor));
    }

    @Test
    public void parse_missingEmail_success() {
        Vendor expectedVendor = new VendorBuilder(VAL).withoutEmail().build();
        assertParseSuccess(parser, NAME_DESC_VAL + PHONE_DESC_VAL + ADDRESS_DESC_VAL
                + TAG_DESC_PHOTOGRAPHER, new VendorAddCommand(expectedVendor));
    }

    @Test
    public void parse_missingAddress_success() {
        Vendor expectedVendor = new VendorBuilder(VAL).withoutAddress().build();
        assertParseSuccess(parser, NAME_DESC_VAL + PHONE_DESC_VAL + EMAIL_DESC_VAL
                + TAG_DESC_PHOTOGRAPHER, new VendorAddCommand(expectedVendor));
    }

    @Test
    public void parse_missingTags_success() {
        // zero tags
        Vendor expectedVendor = new VendorBuilder(TypicalVendors.VAL).withTags().build();
        assertParseSuccess(parser, NAME_DESC_VAL + PHONE_DESC_VAL + EMAIL_DESC_VAL + ADDRESS_DESC_VAL,
                new VendorAddCommand(expectedVendor));
    }
}
