package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.person.Guest;
import seedu.address.model.person.Person;
import seedu.address.model.person.Vendor;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

    /**
     * Returns an unmodifiable view of the guest list.
     * This list will not contain any duplicate guests.
     */
    ObservableList<Guest> getGuestList();

    /**
     * Returns an unmodifiable view of the vendors list.
     * This list will not contain any duplicate vendors.
     */
    ObservableList<Vendor> getVendorList();

}
