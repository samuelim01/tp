package wedlog.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static wedlog.address.testutil.Assert.assertThrows;
import static wedlog.address.testutil.TypicalGuests.GABRIEL;
import static wedlog.address.testutil.TypicalGuests.GIDEON;
import static wedlog.address.testutil.TypicalGuests.GINA;
import static wedlog.address.testutil.TypicalGuests.GREG;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import wedlog.address.model.person.exceptions.DuplicateGuestException;
import wedlog.address.model.person.exceptions.GuestNotFoundException;
import wedlog.address.testutil.GuestBuilder;

public class UniqueGuestListTest {

    private final UniqueGuestList uniqueGuestList = new UniqueGuestList();

    @Test
    public void contains_nullGuest_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGuestList.contains(null));
    }

    @Test
    public void contains_guestNotInList_returnsFalse() {
        assertFalse(uniqueGuestList.contains(GINA));
    }

    @Test
    public void contains_guestInList_returnsTrue() {
        uniqueGuestList.add(GINA);
        assertTrue(uniqueGuestList.contains(GINA));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniqueGuestList.add(GINA);
        Guest editedGina = new GuestBuilder(GINA).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueGuestList.contains(editedGina));
    }

    @Test
    public void add_nullGuest_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGuestList.add(null));
    }

    @Test
    public void add_duplicateGuest_throwsDuplicateGuestException() {
        uniqueGuestList.add(GINA);
        assertThrows(DuplicateGuestException.class, () -> uniqueGuestList.add(GINA));
    }

    @Test
    public void setGuest_nullTargetGuest_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGuestList.setGuest(null, GINA));
    }

    @Test
    public void setGuest_nullEditedGuest_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGuestList.setGuest(GINA, null));
    }

    @Test
    public void setGuest_targetGuestNotInList_throwsGuestNotFoundException() {
        assertThrows(GuestNotFoundException.class, () -> uniqueGuestList.setGuest(GINA, GINA));
    }

    @Test
    public void setGuest_editedGuestIsSameGuest_success() {
        uniqueGuestList.add(GINA);
        uniqueGuestList.setGuest(GINA, GINA);
        UniqueGuestList expectedUniqueGuestList = new UniqueGuestList();
        expectedUniqueGuestList.add(GINA);
        assertEquals(expectedUniqueGuestList, uniqueGuestList);
    }

    @Test
    public void setGuest_editedGuestHasSameIdentity_success() {
        uniqueGuestList.add(GINA);
        Guest editedAlice = new GuestBuilder(GINA).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueGuestList.setGuest(GINA, editedAlice);
        UniqueGuestList expectedUniqueGuestList = new UniqueGuestList();
        expectedUniqueGuestList.add(editedAlice);
        assertEquals(expectedUniqueGuestList, uniqueGuestList);
    }

    @Test
    public void setGuest_editedGuestHasDifferentIdentity_success() {
        uniqueGuestList.add(GINA);
        uniqueGuestList.setGuest(GINA, GREG);
        UniqueGuestList expectedUniqueGuestList = new UniqueGuestList();
        expectedUniqueGuestList.add(GREG);
        assertEquals(expectedUniqueGuestList, uniqueGuestList);
    }

    @Test
    public void setGuest_editedGuestHasNonUniqueIdentity_throwsDuplicateGuestException() {
        uniqueGuestList.add(GINA);
        uniqueGuestList.add(GREG);
        assertThrows(DuplicateGuestException.class, () -> uniqueGuestList.setGuest(GINA, GREG));
    }

    @Test
    public void remove_nullGuest_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGuestList.remove(null));
    }

    @Test
    public void remove_guestDoesNotExist_throwsGuestNotFoundException() {
        assertThrows(GuestNotFoundException.class, () -> uniqueGuestList.remove(GINA));
    }

    @Test
    public void remove_existingGuest_removesGuest() {
        uniqueGuestList.add(GINA);
        uniqueGuestList.remove(GINA);
        UniqueGuestList expectedUniqueGuestList = new UniqueGuestList();
        assertEquals(expectedUniqueGuestList, uniqueGuestList);
    }

    @Test
    public void setGuests_nullUniqueGuestList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGuestList.setGuests((UniqueGuestList) null));
    }

    @Test
    public void setGuests_uniqueGuestList_replacesOwnListWithProvidedUniqueGuestList() {
        uniqueGuestList.add(GINA);
        UniqueGuestList expectedUniqueGuestList = new UniqueGuestList();
        expectedUniqueGuestList.add(GREG);
        uniqueGuestList.setGuests(expectedUniqueGuestList);
        assertEquals(expectedUniqueGuestList, uniqueGuestList);
    }

    @Test
    public void setGuests_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGuestList.setGuests((List<Guest>) null));
    }

    @Test
    public void setGuests_list_replacesOwnListWithProvidedList() {
        uniqueGuestList.add(GINA);
        List<Guest> personList = Collections.singletonList(GREG);
        uniqueGuestList.setGuests(personList);
        UniqueGuestList expectedUniqueGuestList = new UniqueGuestList();
        expectedUniqueGuestList.add(GREG);
        assertEquals(expectedUniqueGuestList, uniqueGuestList);
    }

    @Test
    public void setGuests_listWithDuplicateGuests_throwsDuplicateGuestException() {
        List<Guest> listWithDuplicateGuests = Arrays.asList(GINA, GINA);
        assertThrows(DuplicateGuestException.class, () -> uniqueGuestList.setGuests(listWithDuplicateGuests));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
                -> uniqueGuestList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void getNumGuestsRsvpYes() {
        uniqueGuestList.add(GABRIEL);
        uniqueGuestList.add(GIDEON);
        uniqueGuestList.add(GINA);
        uniqueGuestList.add(GREG);
        assertEquals(1, uniqueGuestList.getNumGuestsRsvpYes());
    }

    @Test
    public void getNumGuestsRsvpNo() {
        uniqueGuestList.add(GABRIEL);
        uniqueGuestList.add(GIDEON);
        uniqueGuestList.add(GINA);
        uniqueGuestList.add(GREG);
        assertEquals(1, uniqueGuestList.getNumGuestsRsvpNo());
    }

    @Test
    public void getNumGuestsRsvpUnknown() {
        uniqueGuestList.add(GABRIEL);
        uniqueGuestList.add(GIDEON);
        uniqueGuestList.add(GINA);
        uniqueGuestList.add(GREG);
        assertEquals(2, uniqueGuestList.getNumGuestsRsvpUnknown());
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueGuestList.asUnmodifiableObservableList().toString(), uniqueGuestList.toString());
    }
}
