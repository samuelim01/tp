package wedlog.address.model.person;

import static java.util.Objects.requireNonNull;
import static wedlog.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import wedlog.address.model.person.exceptions.DuplicateGuestException;
import wedlog.address.model.person.exceptions.GuestNotFoundException;

/**
 * A list of guests that enforces uniqueness between its elements and does not allow nulls.
 * A guest is considered unique by comparing using {@code Person#isSamePerson(Person)}. As such, adding and updating of
 * guests uses Person#isSamePerson(Person) for equality to ensure that the guest being added or updated is
 * unique in terms of identity in the UniqueGuestList. However, the removal of a guest uses Guest#equals(Object)
 * to ensure that the guest with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Person#isSamePerson(Person)
 */
public class UniqueGuestList implements Iterable<Guest> {

    private final ObservableList<Guest> internalList = FXCollections.observableArrayList();
    private final ObservableList<Guest> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent guest as the given argument.
     */
    public boolean contains(Guest toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSamePerson);
    }

    /**
     * Adds a guest to the list.
     * The guest must not already exist in the list.
     */
    public void add(Guest toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateGuestException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the guest {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the list.
     * The guest identity of {@code editedPerson} must not be the same as another existing guest in the list.
     */
    public void setGuest(Guest target, Guest editedPerson) {
        requireAllNonNull(target, editedPerson);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new GuestNotFoundException();
        }

        if (!target.isSamePerson(editedPerson) && contains(editedPerson)) {
            throw new DuplicateGuestException();
        }

        internalList.set(index, editedPerson);
    }

    /**
     * Removes the equivalent guest from the list.
     * The guest must exist in the list.
     */
    public void remove(Guest toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new GuestNotFoundException();
        }
    }

    public void setGuests(UniqueGuestList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code guests}.
     * {@code guests} must not contain duplicate guests.
     */
    public void setGuests(List<Guest> guests) {
        requireAllNonNull(guests);
        if (!guestsAreUnique(guests)) {
            throw new DuplicateGuestException();
        }

        internalList.setAll(guests);
    }

    /**
     * Returns the number of guests with each rsvp status.
     * The return value is an array of integers with each entry representing each of the 3 rsvp statuses.
     * @return integer array of rsvp status counts.
     */
    public int[] getRsvpStatusCounts() {
        int[] count = new int[3];
        for (Guest guest : internalList) {
            switch (guest.getRsvpStatus().value) {
            case "yes":
                count[0]++;
                break;
            case "no":
                count[1]++;
                break;
            case "unknown":
                count[2]++;
                break;
            default:
                break;
            }
        }
        return count;
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Guest> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Guest> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniqueGuestList)) {
            return false;
        }

        UniqueGuestList otherUniqueGuestList = (UniqueGuestList) other;
        return internalList.equals(otherUniqueGuestList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    /**
     * Returns true if {@code guests} contains only unique guests.
     */
    private boolean guestsAreUnique(List<Guest> guests) {
        for (int i = 0; i < guests.size() - 1; i++) {
            for (int j = i + 1; j < guests.size(); j++) {
                if (guests.get(i).isSamePerson(guests.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
