package wedlog.address.testutil;

import static wedlog.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_DIETARY;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_NAME;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_RSVP;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import wedlog.address.logic.commands.AddCommand;
import wedlog.address.logic.commands.EditCommand.EditPersonDescriptor;
import wedlog.address.model.person.Guest;
import wedlog.address.model.tag.Tag;

/**
 * A utility class for Person.
 */
public class GuestUtil {

    /**
     * Returns an add command string for adding the {@code guest}.
     */
    public static String getAddCommand(Guest guest) {
        return AddCommand.COMMAND_WORD + " " + getGuestDetails(guest);
    }

    /**
     * Returns the part of command string for the given {@code guest}'s details.
     */
    public static String getGuestDetails(Guest guest) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + guest.getName().fullName + " ");
        sb.append(PREFIX_PHONE + guest.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + guest.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + guest.getAddress().value + " ");
        sb.append(PREFIX_RSVP + guest.getRsvpStatus().toString() + " ");
        sb.append(PREFIX_DIETARY + guest.getDietaryRequirements().value + " ");
        guest.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
        // descriptor.getRsvpStatus().ifPresent(rsvpStatus -> sb.append(PREFIX_RSVP).append(rsvpStatus.toString())
        // .append(" "));
        // descriptor.getDietaryRequirements().ifPresent(dietaryRequirements -> sb.append(PREFIX_DIETARY).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
