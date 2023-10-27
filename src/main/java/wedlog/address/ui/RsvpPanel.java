package wedlog.address.ui;

import static javafx.geometry.Side.LEFT;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import wedlog.address.commons.core.LogsCenter;
import wedlog.address.logic.Logic;
import wedlog.address.model.RsvpStatistics;

/**
 * A UI component that displays rsvp statistics of WedLog.
 */
public class RsvpPanel extends UiPart<Region> {

    private static final String FXML = "RsvpPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(RsvpPanel.class);
    private final Logic logic;

    @FXML
    private Label rsvpLabel;

    @FXML
    private HBox piechartPlaceholder;

    /**
     * Creates a {@code StatisticsPanel}
     * Currently, this is a blank component.
     */
    public RsvpPanel(Logic logic) {
        super(FXML);
        this.logic = logic;
        rsvpLabel.setText("RSVP Status");
        piechartPlaceholder.getChildren().add(generatePiechart());
    }

    private PieChart generatePiechart() {
        RsvpStatistics rsvpStatistics = logic.getRsvpStatistics();
        int rsvpNo = rsvpStatistics.getPercentGuestsRsvpNo();
        int rsvpYes = rsvpStatistics.getPercentGuestsRsvpYes();
        int rsvpUnknown = rsvpStatistics.getPercentGuestsRsvpUnknown();
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        if (rsvpNo > 0) {
            pieChartData.add(new PieChart.Data("No", rsvpNo));
        }
        if (rsvpYes > 0) {
            pieChartData.add(new PieChart.Data("Yes", rsvpYes));
        }
        if (rsvpUnknown > 0) {
            pieChartData.add(new PieChart.Data("Unknown", rsvpUnknown));
        }
//                FXCollections.observableArrayList(
//                        new PieChart.Data("No", rsvpStatistics.getPercentGuestsRsvpNo()),
//                        new PieChart.Data("Unknown", rsvpStatistics.getPercentGuestsRsvpUnknown()),
//                        new PieChart.Data("Yes", rsvpStatistics.getPercentGuestsRsvpYes()));



        final PieChart chart = new PieChart(pieChartData);
        chart.setLabelsVisible(true);
        chart.setLabelLineLength(10);
        chart.setLegendVisible(true);
        chart.setLegendSide(LEFT);
        chart.setPadding(new Insets(0));
        return chart;
    }
}
