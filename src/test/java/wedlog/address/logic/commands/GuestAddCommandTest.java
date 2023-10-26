package wedlog.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static wedlog.address.testutil.Assert.assertThrows;
import static wedlog.address.testutil.TypicalGuests.GINA;

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
import wedlog.address.testutil.GuestBuilder;

class GuestAddCommandTest {

    @Test
    public void constructor_nullGuest_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new GuestAddCommand(null));
    }

    @Test
    public void execute_guestAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingGuestAdded modelStub = new ModelStubAcceptingGuestAdded();
        Guest validGuest = new GuestBuilder().build();

        CommandResult commandResult = new GuestAddCommand(validGuest).execute(modelStub);

        assertEquals(String.format(GuestAddCommand.MESSAGE_SUCCESS, Messages.format(validGuest)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validGuest), modelStub.guestsAdded);
    }

    @Test
    public void execute_duplicateGuest_throwsCommandException() {
        Guest validGuest = new GuestBuilder().build();
        GuestAddCommand guestAddCommand = new GuestAddCommand(validGuest);
        ModelStub modelStub = new ModelStubWithGuest(validGuest);

        assertThrows(CommandException.class,
                GuestAddCommand.MESSAGE_DUPLICATE_GUEST, () -> guestAddCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Guest alice = new GuestBuilder().withName("Alice").build();
        Guest bob = new GuestBuilder().withName("Bob").build();
        GuestAddCommand addAliceCommand = new GuestAddCommand(alice);
        GuestAddCommand addBobCommand = new GuestAddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        GuestAddCommand addAliceCommandCopy = new GuestAddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different guest -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    @Test
    public void toStringMethod() {
        GuestAddCommand addCommand = new GuestAddCommand(GINA);
        String expected = GuestAddCommand.class.getCanonicalName() + "{toAddGuest=" + GINA + "}";
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
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addGuest(Guest guest) {
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
            // called by GuestAddCommand#execute()
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

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasGuest(Guest guest) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteGuest(Guest target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuest(Guest target, Guest editedGuest) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasVendor(Vendor vendor) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteVendor(Vendor target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addVendor(Vendor vendor) {
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
     * A Model stub that contains a single guest.
     */
    private class ModelStubWithGuest extends ModelStub {
        private final Guest guest;

        ModelStubWithGuest(Guest guest) {
            requireNonNull(guest);
            this.guest = guest;
        }

        @Override
        public boolean hasGuest(Guest guest) {
            requireNonNull(guest);
            return this.guest.equals(guest);
        }
    }

    /**
     * A Model stub that always accept the guest being added.
     */
    private class ModelStubAcceptingGuestAdded extends ModelStub {
        final ArrayList<Guest> guestsAdded = new ArrayList<>();

        @Override
        public boolean hasGuest(Guest guest) {
            requireNonNull(guest);
            return guestsAdded.stream().anyMatch(guest::equals);
        }

        @Override
        public void addGuest(Guest guest) {
            requireNonNull(guest);
            guestsAdded.add(guest);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
