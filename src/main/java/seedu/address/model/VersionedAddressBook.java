package seedu.address.model;

import java.util.LinkedList;

public class VersionedAddressBook extends AddressBook {
    LinkedList<AddressBook> addressBookList = new LinkedList<>();
    int curAddressBookIndex = -1;

    public VersionedAddressBook(ReadOnlyAddressBook toBeCopied) {
        super(toBeCopied);
        commit();
    }

    public void commit() {
        curAddressBookIndex++;
        while (curAddressBookIndex < addressBookList.size()) {
            addressBookList.removeLast();
        }
        addressBookList.add(new AddressBook(this));
    }

    public void undo() {
        // assume that it can undo
        curAddressBookIndex--;
        resetData(addressBookList.get(curAddressBookIndex));
    }

    public void redo() {
        // assume that it can redo
        curAddressBookIndex++;
        resetData(addressBookList.get(curAddressBookIndex));
    }

    public boolean canUndo() {
        return curAddressBookIndex > 0;
    }

    public boolean canRedo() {
        return curAddressBookIndex < addressBookList.size() - 1;
    }
}
