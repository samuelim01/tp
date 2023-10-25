package wedlog.address.logic.parser;

import static wedlog.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static wedlog.address.logic.commands.CommandTestUtil.ADDRESS_DESC_GABE;
import static wedlog.address.logic.commands.CommandTestUtil.ADDRESS_DESC_GIA;
import static wedlog.address.logic.commands.CommandTestUtil.DIETARY_DESC_GIA;
import static wedlog.address.logic.commands.CommandTestUtil.EMAIL_DESC_GABE;
import static wedlog.address.logic.commands.CommandTestUtil.EMAIL_DESC_GIA;
import static wedlog.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static wedlog.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static wedlog.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static wedlog.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static wedlog.address.logic.commands.CommandTestUtil.INVALID_RSVP_DESC;
import static wedlog.address.logic.commands.CommandTestUtil.INVALID_TABLE_DESC;
import static wedlog.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static wedlog.address.logic.commands.CommandTestUtil.NAME_DESC_GIA;
import static wedlog.address.logic.commands.CommandTestUtil.PHONE_DESC_GABE;
import static wedlog.address.logic.commands.CommandTestUtil.PHONE_DESC_GIA;
import static wedlog.address.logic.commands.CommandTestUtil.RSVP_DESC_GIA;
import static wedlog.address.logic.commands.CommandTestUtil.TABLE_DESC_GIA;
import static wedlog.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static wedlog.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_ADDRESS_GIA;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_DIETARY_REQUIREMENTS_GIA;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_EMAIL_GIA;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_NAME_GIA;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_PHONE_GABE;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_PHONE_GIA;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_RSVP_STATUS_GIA;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_TABLE_NUMBER_GIA;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_TAG;
import static wedlog.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static wedlog.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static wedlog.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static wedlog.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static wedlog.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.jupiter.api.Test;

import wedlog.address.commons.core.index.Index;
import wedlog.address.logic.Messages;
import wedlog.address.logic.commands.GuestEditCommand;
import wedlog.address.logic.commands.GuestEditCommand.EditGuestDescriptor;
import wedlog.address.model.person.Address;
import wedlog.address.model.person.Email;
import wedlog.address.model.person.Name;
import wedlog.address.model.person.Phone;
import wedlog.address.model.person.RsvpStatus;
import wedlog.address.model.person.TableNumber;
import wedlog.address.model.tag.Tag;
import wedlog.address.testutil.EditGuestDescriptorBuilder;

public class GuestEditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, GuestEditCommand.MESSAGE_USAGE);

    private GuestEditCommandParser parser = new GuestEditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_GIA, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", GuestEditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_GIA, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_GIA, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, "1" + INVALID_EMAIL_DESC, Email.MESSAGE_CONSTRAINTS); // invalid email
        assertParseFailure(parser, "1" + INVALID_ADDRESS_DESC, Address.MESSAGE_CONSTRAINTS); // invalid address
        assertParseFailure(parser, "1" + INVALID_RSVP_DESC, RsvpStatus.MESSAGE_CONSTRAINTS); // invalid rsvp
        // invalid dietary requirement does not exist
        assertParseFailure(parser, "1" + INVALID_TABLE_DESC, TableNumber.MESSAGE_CONSTRAINTS); // invalid rsvp
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // invalid phone followed by valid email
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC + EMAIL_DESC_GIA, Phone.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Guest} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_DESC_HUSBAND + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_EMPTY + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);

        // TODO: REPLACE GIA WITH GIA
        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_EMAIL_DESC + VALID_ADDRESS_GIA + VALID_PHONE_GIA,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_GABE + TAG_DESC_HUSBAND
                + EMAIL_DESC_GIA + ADDRESS_DESC_GIA + NAME_DESC_GIA + TAG_DESC_FRIEND;

        EditGuestDescriptor descriptor = new EditGuestDescriptorBuilder().withName(VALID_NAME_GIA)
                .withPhone(VALID_PHONE_GABE).withEmail(VALID_EMAIL_GIA).withAddress(VALID_ADDRESS_GIA)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        GuestEditCommand expectedCommand = new GuestEditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_GABE + EMAIL_DESC_GIA;

        EditGuestDescriptor descriptor = new EditGuestDescriptorBuilder().withPhone(VALID_PHONE_GABE)
                .withEmail(VALID_EMAIL_GIA).build();
        GuestEditCommand expectedCommand = new GuestEditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        Index targetIndex = INDEX_THIRD_PERSON;

        // name
        String userInput = targetIndex.getOneBased() + NAME_DESC_GIA;
        EditGuestDescriptor descriptor = new EditGuestDescriptorBuilder().withName(VALID_NAME_GIA).build();
        GuestEditCommand expectedCommand = new GuestEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetIndex.getOneBased() + PHONE_DESC_GIA;
        descriptor = new EditGuestDescriptorBuilder().withPhone(VALID_PHONE_GIA).build();
        expectedCommand = new GuestEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetIndex.getOneBased() + EMAIL_DESC_GIA;
        descriptor = new EditGuestDescriptorBuilder().withEmail(VALID_EMAIL_GIA).build();
        expectedCommand = new GuestEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = targetIndex.getOneBased() + ADDRESS_DESC_GIA;
        descriptor = new EditGuestDescriptorBuilder().withAddress(VALID_ADDRESS_GIA).build();
        expectedCommand = new GuestEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // RSVP
        userInput = targetIndex.getOneBased() + RSVP_DESC_GIA;
        descriptor = new EditGuestDescriptorBuilder().withRsvp(VALID_RSVP_STATUS_GIA).build();
        expectedCommand = new GuestEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // dietary
        userInput = targetIndex.getOneBased() + DIETARY_DESC_GIA;
        descriptor = new EditGuestDescriptorBuilder().withDietary(VALID_DIETARY_REQUIREMENTS_GIA).build();
        expectedCommand = new GuestEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // table
        userInput = targetIndex.getOneBased() + TABLE_DESC_GIA;
        descriptor = new EditGuestDescriptorBuilder().withTable(VALID_TABLE_NUMBER_GIA).build();
        expectedCommand = new GuestEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_FRIEND;
        descriptor = new EditGuestDescriptorBuilder().withTags(VALID_TAG_FRIEND).build();
        expectedCommand = new GuestEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // More extensive testing of duplicate parameter detections is done in
        // AddCommandParserTest#parse_repeatedNonTagValue_failure()

        // valid followed by invalid
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + PHONE_DESC_GABE;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid followed by valid
        userInput = targetIndex.getOneBased() + PHONE_DESC_GABE + INVALID_PHONE_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // mulltiple valid fields repeated
        userInput = targetIndex.getOneBased() + PHONE_DESC_GIA + ADDRESS_DESC_GIA + EMAIL_DESC_GIA
                + TAG_DESC_FRIEND + PHONE_DESC_GIA + ADDRESS_DESC_GIA + EMAIL_DESC_GIA + TAG_DESC_FRIEND
                + PHONE_DESC_GABE + ADDRESS_DESC_GABE + EMAIL_DESC_GABE + TAG_DESC_HUSBAND;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS));

        // multiple invalid values
        userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + INVALID_ADDRESS_DESC + INVALID_EMAIL_DESC
                + INVALID_PHONE_DESC + INVALID_ADDRESS_DESC + INVALID_EMAIL_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS));
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditGuestDescriptor descriptor = new EditGuestDescriptorBuilder().withTags().build();
        GuestEditCommand expectedCommand = new GuestEditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
