package wedlog.address.model.person;

import java.util.Objects;
import java.util.Set;

import wedlog.address.commons.util.ToStringBuilder;
import wedlog.address.model.tag.Tag;

/**
 * Represents a Vendor in the address book.
 * Guarantees: field values are validated, immutable.
 */
public class Vendor extends Person {


    /**
     * Name and tags must be present and not null.
     */
    public Vendor(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        super(name, phone, email, address, tags);
    }

    /**
     * Returns true if both vendors have the same identity and data fields.
     * This defines a stronger notion of equality between two vendors.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Vendor)) {
            return false;
        }

        Vendor otherVendor = (Vendor) other;
        return super.equals(otherVendor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getPhone(), getEmail(), getAddress(), getTags());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", getName())
                .add("phone", getPhone())
                .add("email", getEmail())
                .add("address", getAddress())
                .add("tags", getTags())
                .toString();
    }
}
