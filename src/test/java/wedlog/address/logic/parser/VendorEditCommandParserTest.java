package wedlog.address.logic.parser;

import static wedlog.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static wedlog.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static wedlog.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static wedlog.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static wedlog.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static wedlog.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static wedlog.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static wedlog.address.logic.commands.CommandTestUtil.INVALID_EMPTY_NAME_DESC;
import static wedlog.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static wedlog.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static wedlog.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static wedlog.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static wedlog.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static wedlog.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static wedlog.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static wedlog.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_NAME;
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
import wedlog.address.logic.commands.EditCommand;
import wedlog.address.logic.commands.VendorEditCommand;
import wedlog.address.logic.commands.VendorEditCommand.EditVendorDescriptor;
import wedlog.address.model.person.Email;
import wedlog.address.model.person.Name;
import wedlog.address.model.person.Phone;
import wedlog.address.model.tag.Tag;
import wedlog.address.testutil.EditVendorDescriptorBuilder;

public class VendorEditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, VendorEditCommand.MESSAGE_USAGE);

    private VendorEditCommandParser parser = new VendorEditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_EMPTY_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, "1" + INVALID_EMAIL_DESC, Email.MESSAGE_CONSTRAINTS); // invalid email
        // no invalid values for address as empty string signifies deletion
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // invalid phone followed by valid email
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC + EMAIL_DESC_AMY, Phone.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Vendor} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_DESC_HUSBAND + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_EMPTY + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_EMAIL_DESC + VALID_ADDRESS_AMY + VALID_PHONE_AMY,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_blankOptionalValue_success() {
        // phone
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_PHONE;
        EditVendorDescriptor descriptor = new EditVendorDescriptorBuilder().withoutPhone().build();
        VendorEditCommand expectedCommand = new VendorEditCommand(INDEX_FIRST_PERSON, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetIndex.getOneBased() + " " + PREFIX_EMAIL + " ";
        descriptor = new EditVendorDescriptorBuilder().withoutEmail().build();
        expectedCommand = new VendorEditCommand(INDEX_FIRST_PERSON, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = targetIndex.getOneBased() + " " + PREFIX_ADDRESS;
        descriptor = new EditVendorDescriptorBuilder().withoutAddress().build();
        expectedCommand = new VendorEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + " " + PREFIX_TAG;
        descriptor = new EditVendorDescriptorBuilder().withTags().build();
        expectedCommand = new VendorEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_blankNameValue_failure() {
        String userInput = "1 " + PREFIX_NAME;
        assertParseFailure(parser, userInput, Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + TAG_DESC_HUSBAND
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + NAME_DESC_AMY + TAG_DESC_FRIEND;

        EditVendorDescriptor descriptor = new EditVendorDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        VendorEditCommand expectedCommand = new VendorEditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + EMAIL_DESC_AMY;

        EditVendorDescriptor descriptor = new EditVendorDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_AMY).build();
        VendorEditCommand expectedCommand = new VendorEditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;
        EditVendorDescriptor descriptor = new EditVendorDescriptorBuilder().withName(VALID_NAME_AMY).build();
        VendorEditCommand expectedCommand = new VendorEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetIndex.getOneBased() + PHONE_DESC_AMY;
        descriptor = new EditVendorDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new VendorEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetIndex.getOneBased() + EMAIL_DESC_AMY;
        descriptor = new EditVendorDescriptorBuilder().withEmail(VALID_EMAIL_AMY).build();
        expectedCommand = new VendorEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = targetIndex.getOneBased() + ADDRESS_DESC_AMY;
        descriptor = new EditVendorDescriptorBuilder().withAddress(VALID_ADDRESS_AMY).build();
        expectedCommand = new VendorEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_FRIEND;
        descriptor = new EditVendorDescriptorBuilder().withTags(VALID_TAG_FRIEND).build();
        expectedCommand = new VendorEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // More extensive testing of duplicate parameter detections is done in
        // AddCommandParserTest#parse_repeatedNonTagValue_failure()

        // valid followed by invalid
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + PHONE_DESC_BOB;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid followed by valid
        userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + INVALID_PHONE_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // mulltiple valid fields repeated
        userInput = targetIndex.getOneBased() + PHONE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY
                + TAG_DESC_FRIEND + PHONE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY + TAG_DESC_FRIEND
                + PHONE_DESC_BOB + ADDRESS_DESC_BOB + EMAIL_DESC_BOB + TAG_DESC_HUSBAND;

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

        EditVendorDescriptor descriptor = new EditVendorDescriptorBuilder().withTags().build();
        VendorEditCommand expectedCommand = new VendorEditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
