package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Patron;

/**
 * Panel containing the list of patrons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<Patron> personListView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public PersonListPanel(ObservableList<Patron> patronList) {
        super(FXML);
        personListView.setItems(patronList);
        personListView.setCellFactory(listView -> new PersonListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Patron} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Patron> {
        @Override
        protected void updateItem(Patron patron, boolean empty) {
            super.updateItem(patron, empty);

            if (empty || patron == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonCard(patron, getIndex() + 1).getRoot());
            }
        }
    }

}
