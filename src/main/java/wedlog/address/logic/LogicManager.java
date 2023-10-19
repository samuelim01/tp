package wedlog.address.logic;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import wedlog.address.commons.core.GuiSettings;
import wedlog.address.commons.core.LogsCenter;
import wedlog.address.logic.commands.Command;
import wedlog.address.logic.commands.CommandResult;
import wedlog.address.logic.commands.exceptions.CommandException;
import wedlog.address.logic.parser.AddressBookParser;
import wedlog.address.logic.parser.exceptions.ParseException;
import wedlog.address.model.Model;
import wedlog.address.model.ReadOnlyAddressBook;
import wedlog.address.model.person.Guest;
import wedlog.address.model.person.Person;
import wedlog.address.model.person.Vendor;
import wedlog.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_FORMAT = "Could not save data due to the following error: %s";

    public static final String FILE_OPS_PERMISSION_ERROR_FORMAT =
            "Could not save data to file %s due to insufficient permissions to write to the file or the folder.";

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final AddressBookParser addressBookParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        addressBookParser = new AddressBookParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = addressBookParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveAddressBook(model.getAddressBook());
        } catch (AccessDeniedException e) {
            throw new CommandException(String.format(FILE_OPS_PERMISSION_ERROR_FORMAT, e.getMessage()), e);
        } catch (IOException ioe) {
            throw new CommandException(String.format(FILE_OPS_ERROR_FORMAT, ioe.getMessage()), ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return model.getAddressBook();
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public ObservableList<Guest> getFilteredGuestList() {
        return model.getFilteredGuestList();
    }

    @Override
    public ObservableList<Vendor> getFilteredVendorList() {
        return model.getFilteredVendorList();
    }

    @Override
    public Path getAddressBookFilePath() {
        return model.getAddressBookFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    @Override
    public int[] getRsvpProportion() {
        int numGuests = model.getFilteredGuestList().size();
        int[] rsvpCounts = model.getRsvpStatusCounts();
        int[] rsvpProportion = new int[3];
        for (int i = 0; i < 3; i++) {
            rsvpProportion[i] = (int) Math.round((double) rsvpCounts[i] / numGuests * 100);
        }
        return rsvpProportion;
    }

}
