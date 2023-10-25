package wedlog.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import wedlog.address.logic.commands.GuestEditCommand.EditGuestDescriptor;
import wedlog.address.model.person.Address;
import wedlog.address.model.person.DietaryRequirements;
import wedlog.address.model.person.Email;
import wedlog.address.model.person.Guest;
import wedlog.address.model.person.Name;
import wedlog.address.model.person.Phone;
import wedlog.address.model.person.RsvpStatus;
import wedlog.address.model.person.TableNumber;
import wedlog.address.model.tag.Tag;

/**
 * A utility class to help with building EditGuestDescriptor objects.
 */
public class EditGuestDescriptorBuilder {

    private EditGuestDescriptor descriptor;

    public EditGuestDescriptorBuilder() {
        descriptor = new EditGuestDescriptor();
    }

    public EditGuestDescriptorBuilder(EditGuestDescriptor descriptor) {
        this.descriptor = new EditGuestDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditGuestDescriptor} with fields containing {@code guest}'s details
     */
    public EditGuestDescriptorBuilder(Guest guest) {
        descriptor = new EditGuestDescriptor();
        descriptor.setName(guest.getName());
        descriptor.setPhone(guest.getPhone().orElse(null));
        descriptor.setEmail(guest.getEmail().orElse(null));
        descriptor.setAddress(guest.getAddress().orElse(null));
        descriptor.setTable(guest.getTableNumber().orElse(null));

        // orElse not needed as these fields are guaranteed to be non-null
        descriptor.setRsvp(guest.getRsvpStatus());
        descriptor.setDietary(guest.getDietaryRequirements());
        descriptor.setTags(guest.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditGuestDescriptor} that we are building.
     */
    public EditGuestDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditGuestDescriptor} that we are building.
     */
    public EditGuestDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditGuestDescriptor} that we are building.
     */
    public EditGuestDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditGuestDescriptor} that we are building.
     */
    public EditGuestDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Sets the {@code Rsvp Status} of the {@code EditGuestDescriptor} that we are building.
     */
    public EditGuestDescriptorBuilder withRsvp(String rsvp) {
        descriptor.setRsvp(new RsvpStatus(rsvp));
        return this;
    }

    /**
     * Sets the {@code Dietary Requirements} of the {@code EditGuestDescriptor} that we are building.
     */
    public EditGuestDescriptorBuilder withDietary(String dietary) {
        descriptor.setDietary(new DietaryRequirements(dietary));
        return this;
    }

    /**
     * Sets the {@code Table Number} of the {@code EditGuestDescriptor} that we are building.
     */
    public EditGuestDescriptorBuilder withTable(String table) {
        descriptor.setTable(new TableNumber(table));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditGuestDescriptor}
     * that we are building.
     */
    public EditGuestDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditGuestDescriptor} that we are building to null.
     */
    public EditGuestDescriptorBuilder withoutPhone() {
        descriptor.setPhone(null);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditGuestDescriptor} that we are building to null.
     */
    public EditGuestDescriptorBuilder withoutEmail() {
        descriptor.setEmail(null);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditGuestDescriptor} that we are building to null.
     */
    public EditGuestDescriptorBuilder withoutAddress() {
        descriptor.setAddress(null);
        return this;
    }

    /**
     * Sets the {@code RSVP status} of the {@code EditGuestDescriptor} that we are building to Unknown.
     */
    public EditGuestDescriptorBuilder withUnknownRsvp() {
        descriptor.setRsvp(RsvpStatus.unknown());
        return this;
    }

    /**
     * Sets the {@code Dietary Requirement} of the {@code EditGuestDescriptor} that we are building to NONE.
     */
    public EditGuestDescriptorBuilder withNoneDietaryRequirement() {
        descriptor.setDietary(new DietaryRequirements(""));
        return this;
    }

    /**
     * Sets the {@code Table Number} of the {@code EditGuestDescriptor} that we are building to null.
     */
    public EditGuestDescriptorBuilder withoutTable() {
        descriptor.setTable(null);
        return this;
    }

    public EditGuestDescriptor build() {
        return descriptor;
    }
}
