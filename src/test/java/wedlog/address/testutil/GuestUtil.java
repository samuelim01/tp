package wedlog.address.testutil;

import static wedlog.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_DIETARY;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_NAME;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_RSVP;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_TABLE;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import wedlog.address.logic.commands.GuestEditCommand.EditGuestDescriptor;
import wedlog.address.model.person.Guest;
import wedlog.address.model.tag.DietaryRequirement;
import wedlog.address.model.tag.Tag;

/**
 * A utility class for Person.
 */
public class GuestUtil {

    /**
     * Returns the part of command string for the given {@code guest}'s details.
     */
    public static String getGuestDetails(Guest guest) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + guest.getName().fullName + " ");
        sb.append(PREFIX_PHONE + guest.getPhone().map(p -> p.value).orElse("") + " ");
        sb.append(PREFIX_EMAIL + guest.getEmail().map(e -> e.value).orElse("") + " ");
        sb.append(PREFIX_ADDRESS + guest.getAddress().map(a -> a.value).orElse("") + " ");
        sb.append(PREFIX_RSVP + guest.getRsvpStatus().toString() + " ");
        guest.getDietaryRequirements().stream().forEach(
                d -> sb.append(PREFIX_DIETARY + d.value + " ")
        );
        sb.append(PREFIX_TABLE + guest.getTableNumber().map(tn -> tn.value).orElse("") + " ");
        guest.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditGuestDescriptor}'s details.
     */
    public static String getEditGuestDescriptorDetails(EditGuestDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        if (descriptor.getIsNameEdited()) {
            sb.append(PREFIX_NAME).append(descriptor.getName().fullName).append(" ");
        }
        if (descriptor.getIsPhoneEdited()) {
            sb.append(PREFIX_PHONE).append(descriptor.getPhone().value).append(" ");
        }
        if (descriptor.getIsEmailEdited()) {
            sb.append(PREFIX_EMAIL).append(descriptor.getEmail().value).append(" ");
        }
        if (descriptor.getIsAddressEdited()) {
            sb.append(PREFIX_ADDRESS).append(descriptor.getAddress().value).append(" ");
        }
        if (descriptor.getIsRsvpEdited()) {
            sb.append(PREFIX_RSVP).append(descriptor.getRsvp().value).append(" ");
        }
        if (descriptor.getIsTableEdited()) {
            sb.append(PREFIX_TABLE).append(descriptor.getTable().value).append(" ");
        }
        if (descriptor.getIsDietaryEdited()) {
            Set<DietaryRequirement> dietaryRequirements = descriptor.getDietary();
            if (dietaryRequirements.isEmpty()) {
                sb.append(PREFIX_DIETARY);
            } else {
                dietaryRequirements.forEach(s -> sb.append(PREFIX_DIETARY).append(s.value).append(" "));
            }
        }
        if (descriptor.getIsTagsEdited()) {
            Set<Tag> tags = descriptor.getTags();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
