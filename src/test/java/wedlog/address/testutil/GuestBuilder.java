package wedlog.address.testutil;

import static wedlog.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;

import java.util.HashSet;
import java.util.Set;

import wedlog.address.model.person.Address;
import wedlog.address.model.person.Email;
import wedlog.address.model.person.Guest;
import wedlog.address.model.person.Name;
import wedlog.address.model.person.Phone;
import wedlog.address.model.person.RsvpStatus;
import wedlog.address.model.person.TableNumber;
import wedlog.address.model.tag.DietaryRequirement;
import wedlog.address.model.tag.Tag;
import wedlog.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Guest objects.
 */
public class GuestBuilder {

    public static final String DEFAULT_NAME = "Giselle Gee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "giselle@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_RSVP_STATUS = "yes";
    public static final String DEFAULT_TABLE_NUMBER = "13";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private RsvpStatus rsvpStatus;
    private Set<DietaryRequirement> dietaryRequirements;
    private TableNumber tableNumber;
    private Set<Tag> tags;

    /**
     * Creates a {@code GuestBuilder} with the default details.
     */
    public GuestBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        rsvpStatus = new RsvpStatus(DEFAULT_RSVP_STATUS);
        dietaryRequirements = new HashSet<>();
        tableNumber = new TableNumber(DEFAULT_TABLE_NUMBER);
        tags = new HashSet<>();
        tags.add(new Tag(VALID_TAG_FRIEND));
    }

    /**
     * Creates a {@code GuestBuilder} with only the given name.
     */
    public GuestBuilder(String name) {
        this.name = new Name(name);
        phone = null;
        email = null;
        address = null;
        rsvpStatus = RsvpStatus.unknown();
        dietaryRequirements = new HashSet<>();
        tableNumber = null;
        tags = new HashSet<>();
    }

    /**
     * Initializes the GuestBuilder with the data of {@code personToCopy}.
     */
    public GuestBuilder(Guest guestToCopy) {
        name = guestToCopy.getName();
        phone = guestToCopy.getPhone().orElse(null);
        email = guestToCopy.getEmail().orElse(null);
        address = guestToCopy.getAddress().orElse(null);
        rsvpStatus = guestToCopy.getRsvpStatus();
        dietaryRequirements = new HashSet<>(guestToCopy.getDietaryRequirements());
        tableNumber = guestToCopy.getTableNumber().orElse(null);
        tags = new HashSet<>(guestToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Guest} that we are building.
     */
    public GuestBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Guest} that we are building.
     */
    public GuestBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Guest} that we are building.
     */
    public GuestBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Guest} that we are building.
     */
    public GuestBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code RsvpStatus} of the {@code Guest} that we are building.
     */
    public GuestBuilder withRsvpStatus(String rsvpStatus) {
        this.rsvpStatus = new RsvpStatus(rsvpStatus);
        return this;
    }

    /**
     * Sets the {@code TableNumber} of the {@code Guest} that we are building.
     */
    public GuestBuilder withTableNumber(String tableNumber) {
        this.tableNumber = new TableNumber(tableNumber);
        return this;
    }

    /**
     * Parses the {@code dietaryRequirements} into a {@code Set<DietaryRequirement>}
     * and set it to the {@code Guest} that we are building.
     */
    public GuestBuilder withDietaryRequirements(String ... dietaryRequirements) {
        this.dietaryRequirements = SampleDataUtil.getDietaryRequirementSet(dietaryRequirements);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Guest} that we are building.
     */
    public GuestBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Guest} that we are building.
     */
    public GuestBuilder withoutAddress() {
        this.address = null;
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Guest} that we are building.
     */
    public GuestBuilder withoutPhone() {
        this.phone = null;
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Guest} that we are building.
     */
    public GuestBuilder withoutEmail() {
        this.email = null;
        return this;
    }

    /**
     * Sets the {@code RsvpStatus} of the {@code Guest} that we are building.
     */
    public GuestBuilder withUnknownRsvpStatus() {
        this.rsvpStatus = RsvpStatus.unknown();
        return this;
    }
    /**
     * Sets the {@code TableNumber} of the {@code Guest} that we are building.
     */
    public GuestBuilder withoutTableNumber() {
        this.tableNumber = null;
        return this;
    }

    /**
     * Sets the {@code DietaryRequirements} of the {@code Guest} that we are building to be empty.
     */
    public GuestBuilder withoutDietaryRequirements() {
        this.dietaryRequirements = new HashSet<>();
        return this;
    }

    /**
     * Sets the {@code Tags} of the {@code Guest} that we are building to be empty.
     */
    public GuestBuilder withoutTags() {
        this.tags = new HashSet<>();
        return this;
    }

    public Guest build() {
        return new Guest(name, phone, email, address, rsvpStatus, dietaryRequirements, tableNumber, tags);
    }

}
