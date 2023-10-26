package wedlog.address.ui;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import wedlog.address.commons.core.LogsCenter;
import wedlog.address.logic.Logic;

/**
 * A UI component that displays statistics of WedLog.
 * To be updated in the future.
 */
public class DietaryPanel extends UiPart<Region> {

    private static final String FXML = "DietaryPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(DietaryPanel.class);
    private final Logic logic;

    @FXML
    private Label dietaryLabel;
    @FXML
    private ListView<String> dietaryListView;

    /**
     * Creates a {@code StatisticsPanel}
     * Currently, this is a blank component.
     */
    public DietaryPanel(Logic logic) {
        super(FXML);
        this.logic = logic;
        dietaryLabel.setText("Dietary Requirements");
        dietaryListView.setItems(generateList());
    }

    private ObservableList<String> generateList() {
        ObservableList<String> dietaryList = FXCollections.observableArrayList();
        logic.getDietaryRequirementStatistics().getMap()
                .forEach((k, v) -> dietaryList.add(k + ": " + v));
        return dietaryList;
    }
}
