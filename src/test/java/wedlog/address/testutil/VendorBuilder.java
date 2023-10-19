package wedlog.address.testutil;

import java.util.HashSet;
import java.util.Set;

import wedlog.address.model.person.Address;
import wedlog.address.model.person.Email;
import wedlog.address.model.person.Name;
import wedlog.address.model.person.Phone;
import wedlog.address.model.person.Vendor;
import wedlog.address.model.tag.Tag;
import wedlog.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Vendor objects.
 */
public class VendorBuilder {

    public static final String DEFAULT_NAME = "Annie Chua";
    public static final String DEFAULT_PHONE = "91463224";
    public static final String DEFAULT_EMAIL = "annie@gmail.com";
    public static final String DEFAULT_ADDRESS = "301D, Anchorvale Drive, #05-15";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Tag> tags;

    /**
     * Creates a {@code VendorBuilder} with the default details.
     */
    public VendorBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
    }

    /**
     * Creates a {@code VendorBuilder} with only the given name.
     */
    public VendorBuilder(String name) {
        this.name = new Name(name);
        phone = null;
        email = null;
        address = null;
        tags = new HashSet<>();
    }

    /**
     * Initializes the VendorBuilder with the data of {@code vendorToCopy}.
     */
    public VendorBuilder(Vendor vendorToCopy) {
        name = vendorToCopy.getName();
        phone = vendorToCopy.getPhone().orElse(null);
        email = vendorToCopy.getEmail();
        address = vendorToCopy.getAddress();
        tags = new HashSet<>(vendorToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Vendor} that we are building.
     */
    public VendorBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Vendor} that we are building.
     */
    public VendorBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Vendor} that we are building.
     */
    public VendorBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Vendor} that we are building.
     */
    public VendorBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Vendor} that we are building.
     */
    public VendorBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Vendor} that we are building.
     */
    public VendorBuilder withoutAddress() {
        this.address = null;
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Vendor} that we are building.
     */
    public VendorBuilder withoutPhone() {
        this.phone = null;
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Vendor} that we are building.
     */
    public VendorBuilder withoutEmail() {
        this.email = null;
        return this;
    }

    public Vendor build() {
        return new Vendor(name, phone, email, address, tags);
    }

}
