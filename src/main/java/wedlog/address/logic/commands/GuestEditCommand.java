package wedlog.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_DIETARY;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_NAME;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_RSVP;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_TABLE;
import static wedlog.address.logic.parser.CliSyntax.PREFIX_TAG;
import static wedlog.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import wedlog.address.commons.core.index.Index;
import wedlog.address.commons.util.CollectionUtil;
import wedlog.address.commons.util.ToStringBuilder;
import wedlog.address.logic.Messages;
import wedlog.address.logic.commands.exceptions.CommandException;
import wedlog.address.model.Model;
import wedlog.address.model.person.Address;
import wedlog.address.model.person.DietaryRequirements;
import wedlog.address.model.person.Email;
import wedlog.address.model.person.Name;
import wedlog.address.model.person.Guest;
import wedlog.address.model.person.Phone;
import wedlog.address.model.person.RsvpStatus;
import wedlog.address.model.person.TableNumber;
import wedlog.address.model.tag.Tag;

/**
 * Edits the details of an existing Guest in the address book.
 */
public class GuestEditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the guest identified "
            + "by the index number used in the displayed guest list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Compulsory Parameter: "
            + "INDEX (must be a positive integer) "
            + "Optional Parameters (fields to edit, min 1): "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_RSVP + "RSVP STATUS "
            + PREFIX_DIETARY + "DIETARY REQUIREMENT(S) "
            + PREFIX_TABLE + "TABLE_NUMBER"
            + PREFIX_TAG + "TAG(S) (tag field can be repeated multiple times)\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_GUEST_SUCCESS = "Edited Guest: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_GUEST = "This guest already exists in the address book.";

    private final Index index;
    private final EditGuestDescriptor editGuestDescriptor;

    /**
     * @param index of the guest in the filtered guest list to edit
     * @param editGuestDescriptor details to edit the guest with
     */
    public GuestEditCommand(Index index, EditGuestDescriptor editGuestDescriptor) {
        requireNonNull(index);
        requireNonNull(editGuestDescriptor);

        this.index = index;
        this.editGuestDescriptor = new EditGuestDescriptor(editGuestDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Guest> lastShownList = model.getFilteredGuestList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_GUEST_DISPLAYED_INDEX);
        }

        Guest guestToEdit = lastShownList.get(index.getZeroBased());
        Guest editedGuest = editGuestDescriptor.createEditedGuest(guestToEdit, editGuestDescriptor);

        if (!guestToEdit.isSamePerson(editedGuest) && model.hasGuest(editedGuest)) {
            throw new CommandException(MESSAGE_DUPLICATE_GUEST);
        }

        model.setGuest(guestToEdit, editedGuest);
        model.updateFilteredGuestList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_EDIT_GUEST_SUCCESS, Messages.format(editedGuest)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GuestEditCommand)) {
            return false;
        }

        GuestEditCommand otherGuestEditCommand = (GuestEditCommand) other;
        return index.equals(otherGuestEditCommand.index)
                && editGuestDescriptor.equals(otherGuestEditCommand.editGuestDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editGuestDescriptor", editGuestDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the guest with. Each non-empty field value will replace the
     * corresponding field value of the guest.
     */
    public static class EditGuestDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private RsvpStatus rsvp;
        private DietaryRequirements dietary;
        private TableNumber table;
        private Set<Tag> tags;
        private boolean isNameEdited = false;
        private boolean isPhoneEdited = false;
        private boolean isEmailEdited = false;
        private boolean isAddressEdited = false;
        private boolean isRsvpEdited = false;
        private boolean isDietaryEdited = false;
        private boolean isTableEdited = false;
        private boolean isTagsEdited = false;

        public EditGuestDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditGuestDescriptor(EditGuestDescriptor toCopy) {
            name = toCopy.name;
            phone = toCopy.phone;
            email = toCopy.email;
            address = toCopy.address;
            rsvp = toCopy.rsvp;
            dietary = toCopy.dietary;
            table = toCopy.table;
            tags = (toCopy.tags != null) ? new HashSet<>(toCopy.tags) : null;

            isNameEdited = toCopy.isNameEdited;
            isPhoneEdited = toCopy.isPhoneEdited;
            isEmailEdited = toCopy.isEmailEdited;
            isAddressEdited = toCopy.isAddressEdited;
            isRsvpEdited = toCopy.isRsvpEdited;
            isDietaryEdited = toCopy.isDietaryEdited;
            isTableEdited = toCopy.isTableEdited;
            isTagsEdited = toCopy.isTagsEdited;
        }

        /**
         * Returns true if at least one field is edited.
         */
        // TODO: Implement after Samuel's PR is merged
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address, tags);
        }
        //  return CollectionUtil.isAnyTrue(isNameEdited, isPhoneEdited, isEmailEdited, isAddressEdited, isRsvpEdited, isDietaryEdited, isTableEdited, isTagsEdited);

        public void setName(Name name) {
            isNameEdited = true;
            this.name = name;
        }

        public void setPhone(Phone phone) {
            isPhoneEdited = true;
            this.phone = phone;
        }

        public void setEmail(Email email) {
            isEmailEdited = true;
            this.email = email;
        }

        public void setAddress(Address address) {
            isAddressEdited = true;
            this.address = address;
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Creates and returns a {@code Guest} with the details of {@code guestToEdit}
         * edited with {@code editGuestDescriptor}.
         */
        private Guest createEditedGuest(Guest guestToEdit, EditGuestDescriptor editGuestDescriptor) {
            assert guestToEdit != null;

            Name updatedName = isNameEdited ? name : guestToEdit.getName();
            Phone updatedPhone = isPhoneEdited ? phone : guestToEdit.getPhone().orElse(null);
            Email updatedEmail = isEmailEdited ? email : guestToEdit.getEmail().orElse(null);
            Address updatedAddress = isAddressEdited ? address : guestToEdit.getAddress().orElse(null);
            RsvpStatus updatedRsvp = isRsvpEdited ? rsvp : guestToEdit.getRsvpStatus();
            DietaryRequirements updatedDietary = isDietaryEdited ? dietary : guestToEdit.getDietaryRequirements();
            TableNumber updatedTable = isTableEdited ? table : guestToEdit.getTableNumber().orElse(null);
            Set<Tag> updatedTags = isTagsEdited ? Collections.unmodifiableSet(tags) : guestToEdit.getTags();

            return new Guest(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedRsvp, updatedDietary,
                    updatedTable, updatedTags);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditGuestDescriptor)) {
                return false;
            }

            EditGuestDescriptor otherEditGuestDescriptor = (EditGuestDescriptor) other;
            return Objects.equals(name, otherEditGuestDescriptor.name)
                    && Objects.equals(phone, otherEditGuestDescriptor.phone)
                    && Objects.equals(email, otherEditGuestDescriptor.email)
                    && Objects.equals(address, otherEditGuestDescriptor.address)
                    && Objects.equals(rsvp, otherEditGuestDescriptor.rsvp)
                    && Objects.equals(dietary, otherEditGuestDescriptor.dietary)
                    && Objects.equals(table, otherEditGuestDescriptor.table)
                    && Objects.equals(tags, otherEditGuestDescriptor.tags);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("email", email)
                    .add("address", address)
                    .add("rsvp status", rsvp)
                    .add("dietary requirement", dietary)
                    .add("table number", table)
                    .add("tags", tags)
                    .toString();
        }
    }
}
