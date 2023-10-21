package wedlog.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import wedlog.address.logic.Logic;

/**
 * A UI component that displays statistics of WedLog.
 * To be updated in the future.
 */
public class StatisticsPanel extends UiPart<Region> {

    private static final String FXML = "StatisticsPanel.fxml";

    private final Logic logic;

    @FXML
    private Label guestsTrackedLabel;

    @FXML
    private Label vendorsTrackedLabel;

    /**
     * Creates a {@code StatisticsPanel}
     * Currently, this is a blank component.
     */
    public StatisticsPanel(Logic logic) {
        super(FXML);
        this.logic = logic;
        guestsTrackedLabel.setText("Guests: " + logic.getFilteredGuestList().size());
        vendorsTrackedLabel.setText("Vendors: " + logic.getFilteredVendorList().size());
    }


}
