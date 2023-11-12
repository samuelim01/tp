package wedlog.address.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import wedlog.address.commons.core.GuiSettings;
import wedlog.address.logic.commands.CommandResult;
import wedlog.address.logic.commands.exceptions.CommandException;
import wedlog.address.logic.parser.exceptions.ParseException;
import wedlog.address.model.DietaryRequirementStatistics;
import wedlog.address.model.ReadOnlyAddressBook;
import wedlog.address.model.RsvpStatistics;
import wedlog.address.model.person.Guest;
import wedlog.address.model.person.Vendor;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the AddressBook.
     *
     * @see wedlog.address.model.Model#getAddressBook()
     */
    ReadOnlyAddressBook getAddressBook();

    /** Returns an unmodifiable view of the filtered list of guests */
    ObservableList<Guest> getFilteredGuestList();

    /** Returns an unmodifiable view of the filtered list of vendors */
    ObservableList<Vendor> getFilteredVendorList();

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns a {@code RsvpStatistics} with information about the RSVP statuses of guests.
     * @return {@code RsvpStatistics}
     */
    RsvpStatistics getRsvpStatistics();

    /**
     * Returns a {@code DietaryRequirementStatistics} with information about the dietary requirements of guests.
     * @return {@code DietaryRequirementStatistics}
     */
    DietaryRequirementStatistics getDietaryRequirementStatistics();
}
