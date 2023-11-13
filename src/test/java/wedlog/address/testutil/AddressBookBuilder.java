package wedlog.address.testutil;

import wedlog.address.model.AddressBook;
import wedlog.address.model.person.Guest;
import wedlog.address.model.person.Vendor;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private AddressBook addressBook;

    public AddressBookBuilder() {
        addressBook = new AddressBook();
    }

    public AddressBookBuilder(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Guest} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withGuest(Guest guest) {
        addressBook.addGuest(guest);
        return this;
    }

    /**
     * Adds a new {@code Vendor} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withVendor(Vendor vendor) {
        addressBook.addVendor(vendor);
        return this;
    }

    public AddressBook build() {
        return addressBook;
    }
}
