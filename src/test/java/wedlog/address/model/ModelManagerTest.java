package wedlog.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static wedlog.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static wedlog.address.testutil.Assert.assertThrows;
import static wedlog.address.testutil.TypicalGuests.GABRIEL;
import static wedlog.address.testutil.TypicalGuests.GEORGE;
import static wedlog.address.testutil.TypicalGuests.GINA;
import static wedlog.address.testutil.TypicalGuests.GREG;
import static wedlog.address.testutil.TypicalVendors.ANNE;
import static wedlog.address.testutil.TypicalVendors.BRYAN;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import wedlog.address.commons.core.GuiSettings;
import wedlog.address.model.person.NamePredicate;
import wedlog.address.model.person.exceptions.DuplicateGuestException;
import wedlog.address.model.person.exceptions.DuplicateVendorException;
import wedlog.address.model.person.exceptions.GuestNotFoundException;
import wedlog.address.model.person.exceptions.VendorNotFoundException;
import wedlog.address.testutil.AddressBookBuilder;

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
    public void hasGuest() {
        // EPs: [null][does not exist][exists]

        // EP1: null -> throws NullPointerException
        assertThrows(NullPointerException.class, () -> modelManager.hasGuest(null));

        // EP2: does not exist -> returns False
        assertFalse(modelManager.hasGuest(GEORGE));

        // EP3: exists -> returns True
        modelManager.addGuest(GEORGE);
        assertTrue(modelManager.hasGuest(GEORGE));
    }

    @Test
    public void deleteGuest() {
        // EPs: [null][does not exist][exists]

        // EP1: null -> throws NullPointerException
        assertThrows(NullPointerException.class, () -> modelManager.deleteGuest(null));

        // EP2: does not exist -> throws GuestNotFoundException
        assertThrows(GuestNotFoundException.class, () -> modelManager.deleteGuest(GEORGE));

        // EP3: exists -> deletes Guest
        modelManager.addGuest(GEORGE);
        modelManager.deleteGuest(GEORGE);
        ModelManager expectedModelManager = new ModelManager();
        assertEquals(modelManager, expectedModelManager);
    }

    @Test
    public void setGuest() {
        // Equivalence Partitions:
        // target: [null][does not exist][exists]
        // editedGuest: [null][non-unique][unique]

        // TC1: null target -> throws NullPointerException
        assertThrows(NullPointerException.class, () -> modelManager.setGuest(null, GEORGE));

        // TC2: target does not exist -> throws GuestNotFoundException
        assertThrows(GuestNotFoundException.class, () -> modelManager.setGuest(GEORGE, GEORGE));

        // TC3: null editedGuest -> throws NullPointerException
        assertThrows(NullPointerException.class, () -> modelManager.setGuest(GEORGE, null));

        // TC4: non-unique editedGuest -> throws DuplicateGuestException
        modelManager.addGuest(GEORGE);
        modelManager.addGuest(GREG);
        assertThrows(DuplicateGuestException.class, () -> modelManager.setGuest(GEORGE, GREG));

        // TC5: target exists and unique editedGuest -> success
        modelManager = new ModelManager();
        modelManager.addGuest(GEORGE);
        modelManager.setGuest(GEORGE, GREG);
        ModelManager expectedModelManager = new ModelManager();
        expectedModelManager.addGuest(GREG);
        assertEquals(modelManager, expectedModelManager);
    }

    @Test
    public void getRsvpStatisticsTest() {
        modelManager.addGuest(GEORGE);
        modelManager.addGuest(GREG);
        assertEquals(new RsvpStatistics(1, 1, 0), modelManager.getRsvpStatistics());
    }

    @Test
    public void getDietaryRequirementStatisticsTest() {
        HashMap<String, Integer> expectedMap = new HashMap<>();
        expectedMap.put("vegan", 1);
        expectedMap.put("no beef", 1);
        DietaryRequirementStatistics expectedDietaryRequirementStatistics =
                new DietaryRequirementStatistics(expectedMap);
        modelManager.addGuest(GINA);
        modelManager.addGuest(GREG);
        modelManager.addGuest(GABRIEL);
        modelManager.addGuest(GEORGE);
        assertEquals(expectedDietaryRequirementStatistics, modelManager.getDietaryRequirementStatistics());
    }

    @Test
    public void hasVendor() {
        // EPs: [null][does not exist][exists]

        // EP1: null -> throws NullPointerException
        assertThrows(NullPointerException.class, () -> modelManager.hasVendor(null));

        // EP2: does not exist -> returns False
        assertFalse(modelManager.hasVendor(ANNE));

        // EP3: exists -> returns True
        modelManager.addVendor(ANNE);
        assertTrue(modelManager.hasVendor(ANNE));
    }

    @Test
    public void deleteVendor() {
        // EPs: [null][does not exist][exists]

        // EP1: null -> throws NullPointerException
        assertThrows(NullPointerException.class, () -> modelManager.deleteVendor(null));

        // EP2: does not exist -> throws VendorNotFoundException
        assertThrows(VendorNotFoundException.class, () -> modelManager.deleteVendor(ANNE));

        // EP3: exists -> deletes Vendor
        modelManager.addVendor(ANNE);
        modelManager.deleteVendor(ANNE);
        ModelManager expectedModelManager = new ModelManager();
        assertEquals(modelManager, expectedModelManager);
    }

    @Test
    public void setVendor() {
        // Equivalence Partitions:
        // target: [null][does not exist][exists]
        // editedVendor: [null][non-unique][unique]

        // TC1: null target -> throws NullPointerException
        assertThrows(NullPointerException.class, () -> modelManager.setVendor(null, ANNE));

        // TC2: target does not exist -> throws VendorNotFoundException
        assertThrows(VendorNotFoundException.class, () -> modelManager.setVendor(ANNE, ANNE));

        // TC3: null editedVendor -> throws NullPointerException
        assertThrows(NullPointerException.class, () -> modelManager.setVendor(ANNE, null));

        // TC4: non-unique editedVendor -> throws DuplicateVendorException
        modelManager.addVendor(ANNE);
        modelManager.addVendor(BRYAN);
        assertThrows(DuplicateVendorException.class, () -> modelManager.setVendor(ANNE, BRYAN));

        // TC5: target exists and unique editedVendor -> success
        modelManager = new ModelManager();
        modelManager.addVendor(ANNE);
        modelManager.setVendor(ANNE, BRYAN);
        ModelManager expectedModelManager = new ModelManager();
        expectedModelManager.addVendor(BRYAN);
        assertEquals(modelManager, expectedModelManager);
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
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder()
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

        // different guest filteredList -> returns false
        String guestFullname = GEORGE.getName().fullName;
        modelManager.updateFilteredGuestList(new NamePredicate(guestFullname));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredGuestList(PREDICATE_SHOW_ALL_PERSONS);

        // different vendor filteredList -> returns false
        String vendorFullname = ANNE.getName().fullName;
        modelManager.updateFilteredVendorList(new NamePredicate(vendorFullname));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredVendorList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));
    }
}
