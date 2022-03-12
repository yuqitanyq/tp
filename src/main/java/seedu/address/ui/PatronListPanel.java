package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.patron.Patron;

/**
 * Panel containing the list of patrons.
 */
public class PatronListPanel extends UiPart<Region> {
    private static final String FXML = "PatronListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PatronListPanel.class);

    @FXML
    private ListView<Patron> patronListView;

    /**
     * Creates a {@code PatronListPanel} with the given {@code ObservableList}.
     */
    public PatronListPanel(ObservableList<Patron> patronList) {
        super(FXML);
        patronListView.setItems(patronList);
        patronListView.setCellFactory(listView -> new PatronListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Patron} using a {@code PatronCard}.
     */
    class PatronListViewCell extends ListCell<Patron> {
        @Override
        protected void updateItem(Patron patron, boolean empty) {
            super.updateItem(patron, empty);

            if (empty || patron == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PatronCard(patron, getIndex() + 1).getRoot());
            }
        }
    }

}
