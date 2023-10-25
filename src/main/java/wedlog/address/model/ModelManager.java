package wedlog.address.model;

import static java.util.Objects.requireNonNull;
import static wedlog.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import wedlog.address.commons.core.GuiSettings;
import wedlog.address.commons.core.LogsCenter;
import wedlog.address.model.person.Guest;
import wedlog.address.model.person.Person;
import wedlog.address.model.person.Vendor;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedAddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Guest> filteredGuests;
    private final FilteredList<Vendor> filteredVendors;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new VersionedAddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredGuests = new FilteredList<>(this.addressBook.getGuestList());
        filteredVendors = new FilteredList<>(this.addressBook.getVendorList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public void commitAddressBook() {
        addressBook.commit();
    }

    @Override
    public void undoAddressBook() {
        addressBook.undo();
    }

    @Override
    public void redoAddressBook() {
        addressBook.redo();
    }

    @Override
    public boolean canUndoAddressBook() {
        return addressBook.canUndo();
    }

    @Override
    public boolean canRedoAddressBook() {
        return addressBook.canRedo();
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
    }

    @Override
    public boolean hasGuest(Guest guest) {
        requireNonNull(guest);
        return addressBook.hasGuest(guest);
    }

    @Override
    public void deleteGuest(Guest target) {
        addressBook.removeGuest(target);
    }

    @Override
    public void addGuest(Guest guest) {
        addressBook.addGuest(guest);
        updateFilteredGuestList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setGuest(Guest target, Guest editedGuest) {
        requireAllNonNull(target, editedGuest);

        addressBook.setGuest(target, editedGuest);
    }

    @Override
    public RsvpStatistics getRsvpStatistics() {
        return addressBook.getRsvpStatistics();
    }

    @Override
    public DietaryRequirementStatistics getDietaryRequirementStatistics() {
        return addressBook.getDietaryRequirementStatistics();
    }

    @Override
    public boolean hasVendor(Vendor vendor) {
        requireNonNull(vendor);
        return addressBook.hasVendor(vendor);
    }

    @Override
    public void deleteVendor(Vendor target) {
        addressBook.removeVendor(target);
    }

    @Override
    public void addVendor(Vendor vendor) {
        addressBook.addVendor(vendor);
        updateFilteredVendorList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setVendor(Vendor target, Vendor editedVendor) {
        requireAllNonNull(target, editedVendor);

        addressBook.setVendor(target, editedVendor);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    /**
     * Returns an unmodifiable view of the list of {@code Guest} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Guest> getFilteredGuestList() {
        return filteredGuests;
    }

    @Override
    public void updateFilteredGuestList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredGuests.setPredicate(predicate);
    }

    /**
     * Returns an unmodifiable view of the list of {@code Vendor} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Vendor> getFilteredVendorList() {
        return filteredVendors;
    }

    @Override
    public void updateFilteredVendorList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredVendors.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return addressBook.equals(otherModelManager.addressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons)
                && filteredGuests.equals(otherModelManager.filteredGuests)
                && filteredVendors.equals(otherModelManager.filteredVendors);
    }

}
