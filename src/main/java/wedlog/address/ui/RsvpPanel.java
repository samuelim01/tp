package wedlog.address.ui;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
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
    private PieChart pieChart;
    private Label sliceLabel;

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
        this.pieChart = generatePiechart();
        this.sliceLabel = new Label();
        rsvpLabel.setText("RSVP Status");
        piechartPlaceholder.getChildren().addAll(pieChart, sliceLabel);
    }

    private PieChart generatePiechart() {
        RsvpStatistics rsvpStatistics = logic.getRsvpStatistics();
        int rsvpNo = rsvpStatistics.getPercentGuestsRsvpNo();
        int rsvpYes = rsvpStatistics.getPercentGuestsRsvpYes();
        int rsvpUnknown = rsvpStatistics.getPercentGuestsRsvpUnknown();

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        if (rsvpNo > 0) {
            pieChartData.add(new PieChart.Data("No: " + rsvpNo + "%", rsvpNo));
        }
        if (rsvpYes > 0) {
            pieChartData.add(new PieChart.Data("Yes: " + rsvpYes + "%", rsvpYes));
        }
        if (rsvpUnknown > 0) {
            pieChartData.add(new PieChart.Data("Unknown: " + rsvpUnknown + "%", rsvpUnknown));
        }

        final PieChart chart = new PieChart(pieChartData);
        chart.setLabelsVisible(true);
        chart.setLegendVisible(false);

        return chart;
    }
}
