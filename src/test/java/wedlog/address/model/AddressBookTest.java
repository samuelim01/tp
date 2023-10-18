package wedlog.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static wedlog.address.testutil.Assert.assertThrows;
import static wedlog.address.testutil.TypicalGuests.GINA;
import static wedlog.address.testutil.TypicalGuests.GREG;
import static wedlog.address.testutil.TypicalPersons.ALICE;
import static wedlog.address.testutil.TypicalPersons.getTypicalAddressBook;
import static wedlog.address.testutil.TypicalVendors.ANNE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import wedlog.address.model.person.Guest;
import wedlog.address.model.person.Person;
import wedlog.address.model.person.Vendor;
import wedlog.address.model.person.exceptions.DuplicateGuestException;
import wedlog.address.model.person.exceptions.DuplicatePersonException;
import wedlog.address.model.person.exceptions.DuplicateVendorException;
import wedlog.address.model.person.exceptions.GuestNotFoundException;
import wedlog.address.testutil.GuestBuilder;
import wedlog.address.testutil.PersonBuilder;
import wedlog.address.testutil.VendorBuilder;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    // person-tests

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newPersons, new ArrayList<>(), new ArrayList<>());

        assertThrows(DuplicatePersonException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        assertTrue(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getPersonList().remove(0));
    }

    // guest-tests

    @Test
    public void resetData_withDuplicateGuests_throwsDuplicateGuestException() {
        // Two guests with the same identity fields
        Guest editedGina = new GuestBuilder(GINA).withAddress(VALID_ADDRESS_BOB)
                .build();
        List<Guest> newGuests = Arrays.asList(GINA, editedGina);
        AddressBookStub newData = new AddressBookStub(new ArrayList<>(), newGuests, new ArrayList<>());

        assertThrows(DuplicateGuestException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasGuest_nullGuest_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasGuest(null));
    }

    @Test
    public void hasGuest_guestNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasGuest(GINA));
    }

    @Test
    public void hasGuest_guestInAddressBook_returnsTrue() {
        addressBook.addGuest(GINA);
        assertTrue(addressBook.hasGuest(GINA));
    }

    @Test
    public void hasGuest_guestWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addGuest(GINA);
        Guest editedGina = new GuestBuilder(GINA).withAddress(VALID_ADDRESS_BOB)
                .build();
        assertTrue(addressBook.hasGuest(editedGina));
    }

    @Test
    public void setGuest_nullTargetGuest_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.setGuest(null, GINA));
    }

    @Test
    public void setGuest_nullEditedGuest_throwsNullPointerException() {
        addressBook.addGuest(GINA);
        assertThrows(NullPointerException.class, () -> addressBook.setGuest(GINA, null));
    }

    @Test
    public void setGuest_targetGuestNotInList_throwsGuestNotFoundException() {
        assertThrows(GuestNotFoundException.class, () -> addressBook.setGuest(GINA, GINA));
    }

    @Test
    public void setGuest_editedGuestHasSameIdentity_success() {
        addressBook.addGuest(GINA);
        Guest editedGina = new GuestBuilder(GINA).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        addressBook.setGuest(GINA, editedGina);
        AddressBook expectedAddressBook = new AddressBook();
        expectedAddressBook.addGuest(editedGina);
        assertEquals(expectedAddressBook, addressBook);
    }

    @Test
    public void removeGuest_nullGuest_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.removeGuest(null));
    }

    @Test
    public void removeGuest_guestExists_success() {
        addressBook.addGuest(GINA);
        addressBook.removeGuest(GINA);
        AddressBook expectedAddressBook = new AddressBook();
        assertEquals(expectedAddressBook, addressBook);
    }

    @Test
    public void getGuestList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getGuestList().remove(0));
    }

    // vendor-tests

    @Test
    public void resetData_withDuplicateVendors_throwsDuplicateVendorException() {
        // Two vendors with the same identity fields
        Vendor editedAnne = new VendorBuilder(ANNE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Vendor> newVendors = Arrays.asList(ANNE, editedAnne);
        AddressBookStub newData = new AddressBookStub(new ArrayList<>(), new ArrayList<>(), newVendors);

        assertThrows(DuplicateVendorException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasVendor_nullVendor_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasVendor(null));
    }

    @Test
    public void hasVendor_vendorNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasVendor(ANNE));
    }

    @Test
    public void hasVendor_vendorInAddressBook_returnsTrue() {
        addressBook.addVendor(ANNE);
        assertTrue(addressBook.hasVendor(ANNE));
    }

    @Test
    public void hasVendor_vendorWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addVendor(ANNE);
        Vendor editedAlice = new VendorBuilder(ANNE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasVendor(editedAlice));
    }

    @Test
    public void getVendorList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getVendorList().remove(0));
    }

    @Test
    public void getPercentRsvp_success() {
        addressBook.addGuest(GINA);
        addressBook.addGuest(GREG);
        assertEquals(addressBook.getPercentRsvp(), 50);
    }

    @Test
    public void toStringMethod() {
        String expected = AddressBook.class.getCanonicalName()
                + "{persons=" + addressBook.getPersonList()
                + ", guests=" + addressBook.getGuestList()
                + ", vendors=" + addressBook.getVendorList() + "}";
        assertEquals(expected, addressBook.toString());
    }

    /**
     * A stub ReadOnlyAddressBook whose persons list, guests list and vendors list can violate interface constraints.
     * violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Guest> guests = FXCollections.observableArrayList();
        private final ObservableList<Vendor> vendors = FXCollections.observableArrayList();

        AddressBookStub(Collection<Person> persons, Collection<Guest> guests, Collection<Vendor> vendors) {
            this.persons.setAll(persons);
            this.guests.setAll(guests);
            this.vendors.setAll(vendors);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        @Override
        public ObservableList<Guest> getGuestList() {
            return guests;
        }

        @Override
        public ObservableList<Vendor> getVendorList() {
            return vendors;
        }
    }

}
