package wedlog.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static wedlog.address.testutil.Assert.assertThrows;
import static wedlog.address.testutil.TypicalVendors.VICTOR;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import wedlog.address.commons.core.GuiSettings;
import wedlog.address.logic.Messages;
import wedlog.address.logic.commands.exceptions.CommandException;
import wedlog.address.model.AddressBook;
import wedlog.address.model.DietaryRequirementStatistics;
import wedlog.address.model.Model;
import wedlog.address.model.ReadOnlyAddressBook;
import wedlog.address.model.ReadOnlyUserPrefs;
import wedlog.address.model.RsvpStatistics;
import wedlog.address.model.person.Guest;
import wedlog.address.model.person.Person;
import wedlog.address.model.person.Vendor;
import wedlog.address.testutil.VendorBuilder;

class VendorAddCommandTest {

    @Test
    public void constructor_nullVendor_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new VendorAddCommand(null));
    }

    @Test
    public void execute_vendorAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingVendorAdded modelStub = new ModelStubAcceptingVendorAdded();
        Vendor validVendor = new VendorBuilder().build();

        CommandResult commandResult = new VendorAddCommand(validVendor).execute(modelStub);

        assertEquals(String.format(VendorAddCommand.MESSAGE_SUCCESS, Messages.format(validVendor)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validVendor), modelStub.vendorsAdded);
    }

    @Test
    public void execute_duplicateVendor_throwsCommandException() {
        Vendor validVendor = new VendorBuilder().build();
        VendorAddCommand vendorAddCommand = new VendorAddCommand(validVendor);
        ModelStub modelStub = new ModelStubWithVendor(validVendor);

        assertThrows(CommandException.class,
                VendorAddCommand.MESSAGE_DUPLICATE_VENDOR, () -> vendorAddCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Vendor alice = new VendorBuilder().withName("Alice").build();
        Vendor bob = new VendorBuilder().withName("Bob").build();
        VendorAddCommand addAliceCommand = new VendorAddCommand(alice);
        VendorAddCommand addBobCommand = new VendorAddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        VendorAddCommand addAliceCommandCopy = new VendorAddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different vendor -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    @Test
    public void toStringMethod() {
        VendorAddCommand addCommand = new VendorAddCommand(VICTOR);
        String expected = VendorAddCommand.class.getCanonicalName() + "{toAddVendor=" + VICTOR + "}";
        assertEquals(expected, addCommand.toString());
    }

    /**
     * A default model stub that have all the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitAddressBook() {
            // called by VendorAddCommand#execute()
        }

        @Override
        public void undoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        // add
        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addGuest(Guest guest) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addVendor(Vendor vendor) {
            throw new AssertionError("This method should not be called.");
        }


        // has

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasGuest(Guest guest) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasVendor(Vendor vendor) {
            throw new AssertionError("This method should not be called.");
        }


        // delete

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteGuest(Guest guest) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteVendor(Vendor target) {
            throw new AssertionError("This method should not be called.");
        }

        // set
        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuest(Guest target, Guest editedGuest) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setVendor(Vendor target, Vendor editedVendor) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Guest> getFilteredGuestList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredGuestList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Vendor> getFilteredVendorList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredVendorList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        // statistics

        @Override
        public RsvpStatistics getRsvpStatistics() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public DietaryRequirementStatistics getDietaryRequirementStatistics() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single vendor.
     */
    private class ModelStubWithVendor extends ModelStub {
        private final Vendor vendor;

        ModelStubWithVendor(Vendor vendor) {
            requireNonNull(vendor);
            this.vendor = vendor;
        }

        @Override
        public boolean hasVendor(Vendor vendor) {
            requireNonNull(vendor);
            return this.vendor.equals(vendor);
        }
    }

    /**
     * A Model stub that always accept the vendor being added.
     */
    private class ModelStubAcceptingVendorAdded extends ModelStub {
        final ArrayList<Vendor> vendorsAdded = new ArrayList<>();

        @Override
        public boolean hasVendor(Vendor vendor) {
            requireNonNull(vendor);
            return vendorsAdded.stream().anyMatch(vendor::equals);
        }

        @Override
        public void addVendor(Vendor vendor) {
            requireNonNull(vendor);
            vendorsAdded.add(vendor);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
