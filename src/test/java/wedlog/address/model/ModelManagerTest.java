package wedlog.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static wedlog.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static wedlog.address.testutil.Assert.assertThrows;
import static wedlog.address.testutil.TypicalGuests.GEORGE;
import static wedlog.address.testutil.TypicalGuests.GREG;
import static wedlog.address.testutil.TypicalPersons.ALICE;
import static wedlog.address.testutil.TypicalPersons.BENSON;
import static wedlog.address.testutil.TypicalPersons.BOB;
import static wedlog.address.testutil.TypicalVendors.ANNE;
import static wedlog.address.testutil.TypicalVendors.BRYAN;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import wedlog.address.commons.core.GuiSettings;
import wedlog.address.model.person.Guest;
import wedlog.address.model.person.NameContainsKeywordsPredicate;
import wedlog.address.model.person.Person;
import wedlog.address.model.person.Vendor;
import wedlog.address.model.person.exceptions.DuplicateGuestException;
import wedlog.address.model.person.exceptions.DuplicatePersonException;
import wedlog.address.model.person.exceptions.DuplicateVendorException;
import wedlog.address.model.person.exceptions.GuestNotFoundException;
import wedlog.address.model.person.exceptions.PersonNotFoundException;
import wedlog.address.model.person.exceptions.VendorNotFoundException;
import wedlog.address.testutil.AddressBookBuilder;
import wedlog.address.testutil.GuestBuilder;
import wedlog.address.testutil.PersonBuilder;
import wedlog.address.testutil.VendorBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new AddressBook(), new AddressBook(modelManager.getAddressBook()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setAddressBookFilePath(null));
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setAddressBookFilePath(path);
        assertEquals(path, modelManager.getAddressBookFilePath());
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        modelManager.addPerson(ALICE);
        assertTrue(modelManager.hasPerson(ALICE));
    }

    @Test
    public void deletePerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.deletePerson(null));
    }

    @Test
    public void deletePerson_personDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> modelManager.deletePerson(ALICE));
    }

    @Test
    public void deletePerson_existingPerson_deletesPerson() {
        modelManager.addPerson(ALICE);
        modelManager.deletePerson(ALICE);
        ModelManager expectedModelManager = new ModelManager();
        assertEquals(modelManager, expectedModelManager);
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setPerson(null, ALICE));
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setPerson(ALICE, null));
    }

    @Test
    public void setPerson_targetPersonDoesNotExist_throwsNullPointerException() {
        assertThrows(PersonNotFoundException.class, () -> modelManager.setPerson(ALICE, ALICE));
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        modelManager.addPerson(ALICE);
        modelManager.setPerson(ALICE, ALICE);
        ModelManager expectedModelManager = new ModelManager();
        expectedModelManager.addPerson(ALICE);
        assertEquals(modelManager, expectedModelManager);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        modelManager.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        modelManager.setPerson(ALICE, editedAlice);
        ModelManager expectedModelManager = new ModelManager();
        expectedModelManager.addPerson(editedAlice);
        assertEquals(modelManager, expectedModelManager);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        modelManager.addPerson(ALICE);
        modelManager.setPerson(ALICE, BOB);
        ModelManager expectedModelManager = new ModelManager();
        expectedModelManager.addPerson(BOB);
        assertEquals(modelManager, expectedModelManager);
    }

    @Test
    public void setPerson_editedPersonIsNonUnique_throwsDuplicatePersonException() {
        modelManager.addPerson(ALICE);
        modelManager.addPerson(BOB);
        assertThrows(DuplicatePersonException.class, () -> modelManager.setPerson(ALICE, BOB));
    }

    @Test
    public void hasGuest_nullGuest_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasGuest(null));
    }

    @Test
    public void hasGuest_guestNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasGuest(GEORGE));
    }

    @Test
    public void hasGuest_guestInAddressBook_returnsTrue() {
        modelManager.addGuest(GEORGE);
        assertTrue(modelManager.hasGuest(GEORGE));
    }

    @Test
    public void deleteGuest_nullGuest_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.deleteGuest(null));
    }

    @Test
    public void deleteGuest_vendorDoesNotExist_throwsGuestNotFoundException() {
        assertThrows(GuestNotFoundException.class, () -> modelManager.deleteGuest(GEORGE));
    }

    @Test
    public void deleteGuest_existingGuest_deletesGuest() {
        modelManager.addGuest(GEORGE);
        modelManager.deleteGuest(GEORGE);
        ModelManager expectedModelManager = new ModelManager();
        assertEquals(modelManager, expectedModelManager);
    }

    @Test
    public void setGuest_nullTargetGuest_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuest(null, GEORGE));
    }

    @Test
    public void setGuest_nullEditedGuest_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuest(GEORGE, null));
    }

    @Test
    public void setGuest_targetGuestDoesNotExist_throwsNullPointerException() {
        assertThrows(GuestNotFoundException.class, () -> modelManager.setGuest(GEORGE, GEORGE));
    }

    @Test
    public void setGuest_editedGuestIsSameGuest_success() {
        modelManager.addGuest(GEORGE);
        modelManager.setGuest(GEORGE, GEORGE);
        ModelManager expectedModelManager = new ModelManager();
        expectedModelManager.addGuest(GEORGE);
        assertEquals(modelManager, expectedModelManager);
    }

    @Test
    public void setGuest_editedGuestHasSameIdentity_success() {
        modelManager.addGuest(GEORGE);
        Guest editedGeorge = new GuestBuilder(GEORGE).withAddress(VALID_ADDRESS_BOB).build();
        modelManager.setGuest(GEORGE, editedGeorge);
        ModelManager expectedModelManager = new ModelManager();
        expectedModelManager.addGuest(editedGeorge);
        assertEquals(modelManager, expectedModelManager);
    }

    @Test
    public void setGuest_editedGuestHasDifferentIdentity_success() {
        modelManager.addGuest(GEORGE);
        modelManager.setGuest(GEORGE, GREG);
        ModelManager expectedModelManager = new ModelManager();
        expectedModelManager.addGuest(GREG);
        assertEquals(modelManager, expectedModelManager);
    }

    @Test
    public void setGuest_editedGuestIsNonUnique_throwsDuplicateGuestException() {
        modelManager.addGuest(GEORGE);
        modelManager.addGuest(GREG);
        assertThrows(DuplicateGuestException.class, () -> modelManager.setGuest(GEORGE, GREG));
    }

    @Test
    public void hasVendor_nullVendor_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasVendor(null));
    }

    @Test
    public void hasVendor_vendorNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasVendor(ANNE));
    }

    @Test
    public void hasVendor_vendorInAddressBook_returnsTrue() {
        modelManager.addVendor(ANNE);
        assertTrue(modelManager.hasVendor(ANNE));
    }

    @Test
    public void deleteVendor_nullVendor_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.deleteVendor(null));
    }

    @Test
    public void deleteVendor_vendorDoesNotExist_throwsVendorNotFoundException() {
        assertThrows(VendorNotFoundException.class, () -> modelManager.deleteVendor(ANNE));
    }

    @Test
    public void deleteVendor_existingVendor_deletesVendor() {
        modelManager.addVendor(ANNE);
        modelManager.deleteVendor(ANNE);
        ModelManager expectedModelManager = new ModelManager();
        assertEquals(modelManager, expectedModelManager);
    }

    @Test
    public void setVendor_nullTargetVendor_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setVendor(null, ANNE));
    }

    @Test
    public void setVendor_nullEditedVendor_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setVendor(ANNE, null));
    }

    @Test
    public void setVendor_targetVendorDoesNotExist_throwsNullPointerException() {
        assertThrows(VendorNotFoundException.class, () -> modelManager.setVendor(ANNE, ANNE));
    }

    @Test
    public void setVendor_editedVendorIsSameVendor_success() {
        modelManager.addVendor(ANNE);
        modelManager.setVendor(ANNE, ANNE);
        ModelManager expectedModelManager = new ModelManager();
        expectedModelManager.addVendor(ANNE);
        assertEquals(modelManager, expectedModelManager);
    }

    @Test
    public void setVendor_editedVendorHasSameIdentity_success() {
        modelManager.addVendor(ANNE);
        Vendor editedAnne = new VendorBuilder(ANNE).withAddress(VALID_ADDRESS_BOB).build();
        modelManager.setVendor(ANNE, editedAnne);
        ModelManager expectedModelManager = new ModelManager();
        expectedModelManager.addVendor(editedAnne);
        assertEquals(modelManager, expectedModelManager);
    }

    @Test
    public void setVendor_editedVendorHasDifferentIdentity_success() {
        modelManager.addVendor(ANNE);
        modelManager.setVendor(ANNE, BRYAN);
        ModelManager expectedModelManager = new ModelManager();
        expectedModelManager.addVendor(BRYAN);
        assertEquals(modelManager, expectedModelManager);
    }

    @Test
    public void setVendor_editedVendorIsNonUnique_throwsDuplicateVendorException() {
        modelManager.addVendor(ANNE);
        modelManager.addVendor(BRYAN);
        assertThrows(DuplicateVendorException.class, () -> modelManager.setVendor(ANNE, BRYAN));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
    }

    @Test
    public void getFilteredGuestList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredGuestList().remove(0));
    }

    @Test
    public void getFilteredVendorList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredVendorList().remove(0));
    }

    @Test
    public void getRsvpStatisticsTest() {
        modelManager.addGuest(GEORGE);
        modelManager.addGuest(GREG);
        assertEquals(new RsvpStatistics(1, 1, 0), modelManager.getRsvpStatistics());
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder()
                .withPerson(ALICE).withPerson(BENSON)
                .withGuest(GEORGE).withGuest(GREG)
                .withVendor(ANNE).withVendor(BRYAN)
                .build();
        AddressBook differentAddressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, userPrefs)));

        // different person filteredList -> returns false
        String[] personKeywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(personKeywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different guest filteredList -> returns false
        String[] guestKeywords = GEORGE.getName().fullName.split("\\s+");
        modelManager.updateFilteredGuestList(new NameContainsKeywordsPredicate(Arrays.asList(guestKeywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredGuestList(PREDICATE_SHOW_ALL_PERSONS);

        // different vendor filteredList -> returns false
        String[] vendorKeywords = ANNE.getName().fullName.split("\\s+");
        modelManager.updateFilteredVendorList(new NameContainsKeywordsPredicate(Arrays.asList(vendorKeywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredVendorList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));
    }
}
