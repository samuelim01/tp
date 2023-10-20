package wedlog.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static wedlog.address.testutil.TypicalGuests.GINA;
import static wedlog.address.testutil.TypicalPersons.AMY;
import static wedlog.address.testutil.TypicalVendors.ANNE;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import wedlog.address.testutil.AddressBookBuilder;

//@@author samuelim01-reused
// Reused from Address Book(Level 4) with minor modifications.
class VersionedAddressBookTest {

    private static final ReadOnlyAddressBook EMPTY_ADDRESS_BOOK = new AddressBookBuilder().build();
    private static final ReadOnlyAddressBook ADDRESS_BOOK_WITH_PERSON_AMY = new AddressBookBuilder()
            .withPerson(AMY).build();
    private static final ReadOnlyAddressBook ADDRESS_BOOK_WITH_GUEST_GINA = new AddressBookBuilder()
            .withGuest(GINA).build();
    private static final ReadOnlyAddressBook ADDRESS_BOOK_WITH_VENDOR_ANNE = new AddressBookBuilder()
            .withVendor(ANNE).build();

    @Test
    public void commit_singleAddressBook_noStatesRemovedCurrentStateSaved() {
        VersionedAddressBook versionedAddressBook = prepareVersionedAddressBook(EMPTY_ADDRESS_BOOK);

        versionedAddressBook.commit();
        assertAddressBookListStatus(versionedAddressBook,
                Collections.singletonList(EMPTY_ADDRESS_BOOK),
                EMPTY_ADDRESS_BOOK,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleAddressBooksCurrentStatePointerAtEnd_noStatesRemovedCurrentStateSaved() {
        VersionedAddressBook versionedAddressBook = prepareVersionedAddressBook(EMPTY_ADDRESS_BOOK,
                ADDRESS_BOOK_WITH_PERSON_AMY, ADDRESS_BOOK_WITH_GUEST_GINA);

        versionedAddressBook.commit();
        assertAddressBookListStatus(versionedAddressBook,
                Arrays.asList(EMPTY_ADDRESS_BOOK, ADDRESS_BOOK_WITH_PERSON_AMY, ADDRESS_BOOK_WITH_GUEST_GINA),
                ADDRESS_BOOK_WITH_GUEST_GINA,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleAddressBooksCurrentStatePointerNotAtEnd_statesRemovedCurrentStateSaved() {
        VersionedAddressBook versionedAddressBook = prepareVersionedAddressBook(EMPTY_ADDRESS_BOOK,
                ADDRESS_BOOK_WITH_PERSON_AMY, ADDRESS_BOOK_WITH_GUEST_GINA);

        shiftCurrentStatePointerLeft(versionedAddressBook, 1);
        versionedAddressBook.commit();
        assertAddressBookListStatus(versionedAddressBook,
                Arrays.asList(EMPTY_ADDRESS_BOOK, ADDRESS_BOOK_WITH_PERSON_AMY),
                ADDRESS_BOOK_WITH_PERSON_AMY,
                Collections.emptyList());
    }

    @Test
    public void undo_multipleAddressBooksCurrentStatePointerAtEnd_success() {
        VersionedAddressBook versionedAddressBook = prepareVersionedAddressBook(EMPTY_ADDRESS_BOOK,
                ADDRESS_BOOK_WITH_PERSON_AMY, ADDRESS_BOOK_WITH_GUEST_GINA);

        versionedAddressBook.undo();
        assertAddressBookListStatus(versionedAddressBook,
                Collections.singletonList(EMPTY_ADDRESS_BOOK),
                ADDRESS_BOOK_WITH_PERSON_AMY,
                Collections.singletonList(ADDRESS_BOOK_WITH_GUEST_GINA));
    }

    @Test
    public void undo_multipleAddressBooksCurrentStatePointerNotAtEnd_success() {
        VersionedAddressBook versionedAddressBook = prepareVersionedAddressBook(EMPTY_ADDRESS_BOOK,
                ADDRESS_BOOK_WITH_PERSON_AMY, ADDRESS_BOOK_WITH_GUEST_GINA);
        shiftCurrentStatePointerLeft(versionedAddressBook, 1);

        versionedAddressBook.undo();
        assertAddressBookListStatus(versionedAddressBook,
                Collections.emptyList(),
                EMPTY_ADDRESS_BOOK,
                Arrays.asList(ADDRESS_BOOK_WITH_PERSON_AMY, ADDRESS_BOOK_WITH_GUEST_GINA));
    }

    @Test
    public void undo_multipleAddressBooksCurrentStatePointerAtStart_throwsNoUndoableStateException() {
        VersionedAddressBook versionedAddressBook = prepareVersionedAddressBook(EMPTY_ADDRESS_BOOK,
                ADDRESS_BOOK_WITH_PERSON_AMY, ADDRESS_BOOK_WITH_GUEST_GINA);
        shiftCurrentStatePointerLeft(versionedAddressBook, 2);

        assertThrows(VersionedAddressBook.NoUndoableStateException.class, () -> versionedAddressBook.undo());
    }

    @Test
    public void undo_singleAddressBook_throwsNoUndoableStateException() {
        VersionedAddressBook versionedAddressBook = prepareVersionedAddressBook(EMPTY_ADDRESS_BOOK);
        assertThrows(VersionedAddressBook.NoUndoableStateException.class, () -> versionedAddressBook.undo());
    }

    @Test
    public void redo_multipleAddressBooksCurrentStatePointerAtStart_success() {
        VersionedAddressBook versionedAddressBook = prepareVersionedAddressBook(EMPTY_ADDRESS_BOOK,
                ADDRESS_BOOK_WITH_PERSON_AMY, ADDRESS_BOOK_WITH_GUEST_GINA);
        shiftCurrentStatePointerLeft(versionedAddressBook, 2);

        versionedAddressBook.redo();
        assertAddressBookListStatus(versionedAddressBook,
                Collections.singletonList(EMPTY_ADDRESS_BOOK),
                ADDRESS_BOOK_WITH_PERSON_AMY,
                Collections.singletonList(ADDRESS_BOOK_WITH_GUEST_GINA));
    }

    @Test
    public void redo_multipleAddressBooksCurrentStatePointerNotAtEnd_success() {
        VersionedAddressBook versionedAddressBook = prepareVersionedAddressBook(EMPTY_ADDRESS_BOOK,
                ADDRESS_BOOK_WITH_PERSON_AMY, ADDRESS_BOOK_WITH_GUEST_GINA);
        shiftCurrentStatePointerLeft(versionedAddressBook, 1);

        versionedAddressBook.redo();
        assertAddressBookListStatus(versionedAddressBook,
                Arrays.asList(EMPTY_ADDRESS_BOOK, ADDRESS_BOOK_WITH_PERSON_AMY),
                ADDRESS_BOOK_WITH_GUEST_GINA,
                Collections.emptyList());
    }

    @Test
    public void redo_multipleAddressBooksCurrentStatePointerAtEnd_throwsNoRedoableException() {
        VersionedAddressBook versionedAddressBook = prepareVersionedAddressBook(EMPTY_ADDRESS_BOOK,
                ADDRESS_BOOK_WITH_PERSON_AMY, ADDRESS_BOOK_WITH_GUEST_GINA);

        assertThrows(VersionedAddressBook.NoRedoableStateException.class, () -> versionedAddressBook.redo());
    }

    @Test
    public void redo_singleAddressBook_throwsNoRedoableException() {
        VersionedAddressBook versionedAddressBook = prepareVersionedAddressBook(EMPTY_ADDRESS_BOOK);

        assertThrows(VersionedAddressBook.NoRedoableStateException.class, () -> versionedAddressBook.redo());
    }

    @Test
    public void canUndo_multipleAddressBooks_returnsTrue() {
        VersionedAddressBook versionedAddressBook = prepareVersionedAddressBook(EMPTY_ADDRESS_BOOK,
                ADDRESS_BOOK_WITH_PERSON_AMY, ADDRESS_BOOK_WITH_GUEST_GINA);
        assertTrue(versionedAddressBook.canUndo());
    }

    @Test
    public void canUndo_singleAddressBook_returnsFalse() {
        VersionedAddressBook versionedAddressBook = prepareVersionedAddressBook(EMPTY_ADDRESS_BOOK);
        assertFalse(versionedAddressBook.canUndo());
    }

    @Test
    public void canUndo_multipleAddressBooksCurrentStatePointerAtStart_returnsFalse() {
        VersionedAddressBook versionedAddressBook = prepareVersionedAddressBook(EMPTY_ADDRESS_BOOK,
                ADDRESS_BOOK_WITH_PERSON_AMY, ADDRESS_BOOK_WITH_GUEST_GINA);
        shiftCurrentStatePointerLeft(versionedAddressBook, 2);
        assertFalse(versionedAddressBook.canUndo());
    }

    @Test
    public void canRedo_singleAddressBook_returnsFalse() {
        VersionedAddressBook versionedAddressBook = prepareVersionedAddressBook(EMPTY_ADDRESS_BOOK);
        assertFalse(versionedAddressBook.canRedo());
    }

    @Test
    public void canRedo_multipleAddressBooksCurrentStatePointerAtStart_returnsTrue() {
        VersionedAddressBook versionedAddressBook = prepareVersionedAddressBook(EMPTY_ADDRESS_BOOK,
                ADDRESS_BOOK_WITH_PERSON_AMY, ADDRESS_BOOK_WITH_GUEST_GINA);
        shiftCurrentStatePointerLeft(versionedAddressBook, 2);
        assertTrue(versionedAddressBook.canRedo());
    }

    @Test
    public void canRedo_multipleAddressBooksCurrentStatePointerAtMiddle_returnsTrue() {
        VersionedAddressBook versionedAddressBook = prepareVersionedAddressBook(EMPTY_ADDRESS_BOOK,
                ADDRESS_BOOK_WITH_PERSON_AMY, ADDRESS_BOOK_WITH_GUEST_GINA);
        shiftCurrentStatePointerLeft(versionedAddressBook, 1);
        assertTrue(versionedAddressBook.canRedo());
    }

    @Test
    public void canRedo_multipleAddressBooksCurrentStatePointerAtEnd_returnsFalse() {
        VersionedAddressBook versionedAddressBook = prepareVersionedAddressBook(EMPTY_ADDRESS_BOOK,
                ADDRESS_BOOK_WITH_PERSON_AMY, ADDRESS_BOOK_WITH_GUEST_GINA);

        assertFalse(versionedAddressBook.canRedo());
    }

    @Test
    public void equals() {
        VersionedAddressBook versionedAddressBook = prepareVersionedAddressBook(EMPTY_ADDRESS_BOOK,
                ADDRESS_BOOK_WITH_PERSON_AMY, ADDRESS_BOOK_WITH_GUEST_GINA);

        // same object -> returns true
        assertTrue(versionedAddressBook.equals(versionedAddressBook));

        // same values -> returns true
        VersionedAddressBook versionedAddressBookCpy = prepareVersionedAddressBook(EMPTY_ADDRESS_BOOK,
                ADDRESS_BOOK_WITH_PERSON_AMY, ADDRESS_BOOK_WITH_GUEST_GINA);
        assertTrue(versionedAddressBook.equals(versionedAddressBookCpy));

        // different types -> returns false
        assertFalse(versionedAddressBook.equals(1));

        // null -> returns false
        assertFalse(versionedAddressBook.equals(null));

        // different state list -> returns false
        VersionedAddressBook differentStateList = prepareVersionedAddressBook(EMPTY_ADDRESS_BOOK,
                ADDRESS_BOOK_WITH_PERSON_AMY, ADDRESS_BOOK_WITH_VENDOR_ANNE);
        assertFalse(versionedAddressBook.equals(differentStateList));

        // different current state pointer -> returns false
        VersionedAddressBook differentStatePointer = prepareVersionedAddressBook(EMPTY_ADDRESS_BOOK,
                ADDRESS_BOOK_WITH_PERSON_AMY, ADDRESS_BOOK_WITH_GUEST_GINA);
        shiftCurrentStatePointerLeft(differentStatePointer, 1);
        assertFalse(versionedAddressBook.equals(differentStatePointer));
    }

    private void assertAddressBookListStatus(VersionedAddressBook versionedAddressBook,
                                             List<ReadOnlyAddressBook> expectedStatesBeforePointer,
                                             ReadOnlyAddressBook expectedCurrentState,
                                             List<ReadOnlyAddressBook> expectedStatesAfterPointer) {

        // check current state
        assertEquals(new AddressBook(versionedAddressBook), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedAddressBook.canUndo()) {
            versionedAddressBook.undo();
        }

        // check states before pointer
        for (ReadOnlyAddressBook expectedState : expectedStatesBeforePointer) {
            assertEquals(expectedState, new AddressBook(versionedAddressBook));
            versionedAddressBook.redo();
        }

        // check states after pointer
        for (ReadOnlyAddressBook expectedAddressBook : expectedStatesAfterPointer) {
            versionedAddressBook.redo();
            assertEquals(expectedAddressBook, new AddressBook(versionedAddressBook));
        }

        // check that there are no more states after pointer
        assertFalse(versionedAddressBook.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedAddressBook.undo());
    }

    private VersionedAddressBook prepareVersionedAddressBook(ReadOnlyAddressBook... addressBookStates) {
        VersionedAddressBook versionedAddressBook = new VersionedAddressBook(addressBookStates[0]);

        for (int i = 1; i < addressBookStates.length; i++) {
            versionedAddressBook.resetData(addressBookStates[i]);
            versionedAddressBook.commit();
        }
        return versionedAddressBook;
    }

    private void shiftCurrentStatePointerLeft(VersionedAddressBook versionedAddressBook, int count) {
        for (int i = 0; i < count; i++) {
            versionedAddressBook.undo();
        }
    }
}
//@@author
