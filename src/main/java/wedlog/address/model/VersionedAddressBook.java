package wedlog.address.model;

import java.util.ArrayList;
import java.util.List;

//@@author samuelim01-reused
// Reused from Address Book(Level 4) with minor modifications.

/**
 * {@code AddressBook} that keeps track of its own history
 */
public class VersionedAddressBook extends AddressBook {

    private final List<ReadOnlyAddressBook> addressBookStateList;
    private int currentStatePointer;


    /**
     * Creates an AddressBook using the persons in {@code initialState}
     * @param initialState
     */
    public VersionedAddressBook(ReadOnlyAddressBook initialState) {
        super(initialState);

        addressBookStateList = new ArrayList<>();
        addressBookStateList.add(new AddressBook(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code AddressBook} state.
     */
    public void commit() {
        removeStatesAfterCurrentStatePointer();
        addressBookStateList.add(new AddressBook(this));
        currentStatePointer++;
    }

    private void removeStatesAfterCurrentStatePointer() {
        addressBookStateList.subList(currentStatePointer + 1, addressBookStateList.size()).clear();
    }

    /**
     * Restores the address book to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(addressBookStateList.get(currentStatePointer));
    }

    /**
     * Restores the address book to its previous state before undo.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(addressBookStateList.get(currentStatePointer));
    }

    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    public boolean canRedo() {
        return currentStatePointer < addressBookStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedAddressBook)) {
            return false;
        }

        VersionedAddressBook otherAddressBook = (VersionedAddressBook) other;
        return super.equals(otherAddressBook)
                && addressBookStateList.equals(otherAddressBook.addressBookStateList)
                && currentStatePointer == otherAddressBook.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo} with no undoable state.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo} with no redoable state.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of list, unable to redo.");
        }
    }
}
//@@author
