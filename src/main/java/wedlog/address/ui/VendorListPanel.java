package wedlog.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import wedlog.address.commons.core.LogsCenter;
import wedlog.address.model.person.Vendor;

/**
 * Panel containing the list of guests.
 */
public class VendorListPanel extends UiPart<Region> {
    private static final String FXML = "VendorListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(VendorListPanel.class);

    @FXML
    private ListView<Vendor> vendorListView;

    /**
     * Creates a {@code VendorListPanel} with the given {@code ObservableList}.
     */
    public VendorListPanel(ObservableList<Vendor> vendorList) {
        super(FXML);
        vendorListView.setItems(vendorList);
        vendorListView.setCellFactory(listView -> new VendorListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Vendor} using a {@code VendorCard}.
     */
    class VendorListViewCell extends ListCell<Vendor> {
        @Override
        protected void updateItem(Vendor vendor, boolean isEmpty) {
            super.updateItem(vendor, isEmpty);

            if (isEmpty || vendor == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new VendorCard(vendor, getIndex() + 1).getRoot());
            }
        }
    }
}
