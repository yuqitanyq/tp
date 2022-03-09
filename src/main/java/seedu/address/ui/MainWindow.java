package seedu.address.ui;

import static seedu.address.model.util.SampleDataUtil.getTagSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.bookstub.Author;
import seedu.address.model.bookstub.BookStub;
import seedu.address.model.bookstub.Name;
import seedu.address.model.tag.Tag;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private PersonListPanel personListPanel;
    private BookListPanel bookListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane personListPanelPlaceholder;

    @FXML
    private StackPane bookListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    /**
     * Creates a {@code MainWindow} with the given {@code Stage} and {@code Logic}.
     */
    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        personListPanel = new PersonListPanel(logic.getFilteredPersonList());
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());

        // Remove the stub deployment books for testing during integration -- NOT SAFE for deployment
        ObservableList<BookStub> observableBookList = FXCollections.observableArrayList();

        // Normal book
        Name bookName0 = new Name("Deception Point");
        Author bookZeroAuthorOne = new Author("Dan Brown");
        String isbnBook0 = "0-545-12345-9";
        Set<Tag> categoryTagsBook0 = getTagSet("Novel", "Suspense", "Thriller", "TechnoThriller");
        List<Author> authorListBook0 = new ArrayList<Author>();
        Collections.addAll(authorListBook0, bookZeroAuthorOne);
        BookStub book0 = new BookStub(bookName0, authorListBook0, isbnBook0, categoryTagsBook0, true,
                "9/3/2022");


        // Intensive book
        Name bookName1 = new Name("Lorem Ipsum comes from a latin text written in 45BC by Roman statesman Lorem Ipsum"
                + " comes from a latin text written in 45BC by Roman statesman Lorem Ipsum comes from a latin text"
                + " written in 45BC by Roman statesman Lorem Ipsum comes from a latin text written in 45BC by Roman"
                + " statesman Lorem Ipsum comes from a latin text written in 45BC by Roman statesman Lorem Ipsum comes"
                + " from a latin text written in 45BC by Roman statesman");

        Author bookOneAuthorOne = new Author("accusamus acutum aegritudine alios aliquip allevatio antiqua"
                + " arbitramur arbitrium arcu augeri captet censes cetero cogitavisse comparare conscientiam"
                + " contentus cupiditatum deditum democritus depravata deserunt desiderant desistunt detracta"
                + " didicerimus dirigentur displicet dissensio disseretur distinguique eget elit eo epicurei equos"
                + " exercitus exorsus fastidium faucibus felis foedus fuissent generis geometrica gravida illam impetus"
                + " incursione indignae inhaererent initia inquam inter ipsas ipsi iriure lacinia licet mel mi minim"
                + " mors muniti netus noster nostro occultarum odioque omnino oratoribus oriantur ornateque percipit"
                + " plura pluribus possint praetulerit privamur probamus procedat propemodum pulchraeque quaerendi"
                + " quanti quisque rudem sensum sole stultorum successerit successionem suspicor tranquilli unde varias"
                + " videre voce vulgo ");

        Author bookOneAuthorTwo = new Author("ad admodum aeterno affert amori animo animum antiquitate aperiri"
                + " arbitrer ars augeri beate class coniunctione conscientiam consentaneum cu cumanum declinationem"
                + " didicisse dissensio dissentientium distinctio doleamus efficiantur eloquentiam epicuri euripidis"
                + " fames filio finibus firmitatem foedus grata gubernatoris his homero homines hymenaeos impetu"
                + " indocti inimicus intellegebat interesset iudicant iudicatum legendis legimus leniter levitatibus"
                + " loquerer materia memoriter minimum mirari miseram modice monet mortem motum munere naturales nostra"
                + " nulla nullam nulli omnes optimi parta peccant persequeris plus politus postea praeteritas praetor"
                + " prima primis pronuntiaret proposita pulvinar quaedam reiciendis scriptorem situm solitudo suavitate"
                + " summa tempor tenebo terentianus triari tritani uberiora ultima urbane utraque vacuitate velim ");

        Author bookOneAuthorThree = new Author("ab afficit albucius aliquo amici angere angoribus aperiam bonas"
                + " brevi caret contentam continent convenire cupiditates curae defendere detraxit detrimenti directam"
                + " disciplinis dissentiunt distinctio dolere effecerit effectrices effluere enim epicureum evolvendis"
                + " expetendis fore fuerit generibusque generis graeca harum hominibus illo indoctis inliberali"
                + " intellegamus invidus ipsi iucundius laetitia legimus liber magnis maiora malorum mandaremus"
                + " maximam medeam mediocris meminit mi momenti nullus occulta offendit omnis operosam oporteat"
                + " optinere parum pauca pellat percussit permagna posset posuit praesidium pugnantibus putanda putas"
                + " quamvis quisque reformidans retinent robustus salutatus sapienti scriptum sensu stabilitas"
                + " studuisse suspicor tantis temeritate traditur utrumque varias velint veniam veri vias videamus"
                + " videmus voluptaria ");

        String isbnBook1 = "0-545-01022-5";

        Set<Tag> categoryTagsBook1 = getTagSet("Detective", "Mystery", "Fantasy", "Classics", "Historical",
                "Horror", "Literary", "Romance", "Crime", "SciFi", "Thrillers", "Drama", "Poetry", "Media",
                "Nonfiction", "Horror1", "Literary1", "Romance1", "Crime1", "SciFi1", "Thrillers1", "Drama1", "Poetry1",
                "Media1" , "Media2", "Media3", "Media4", "Media5", "Media6", "Media7", "Media8", "Media9", "Media10");

        List<Author> authorListBook1 = new ArrayList<Author>();
        Collections.addAll(authorListBook1, bookOneAuthorOne, bookOneAuthorTwo, bookOneAuthorThree);
        BookStub book1 = new BookStub(bookName1, authorListBook1, isbnBook1, categoryTagsBook1, true,
                "14/3/2022");

        // book 2 with empty tags and empty author list

        Name bookName2 = new Name("Emptiness");
        List<Author> emptyAuthorListBook2 = new ArrayList<Author>();
        Set<Tag> emptyCategoryTagsBook2 = getTagSet();
        String isbnBook2 = "0-0000-00000-0";

        BookStub book2 = new BookStub(bookName2, emptyAuthorListBook2, isbnBook2, emptyCategoryTagsBook2,
                false, "14/3/2022");

        // Algorithm book
        Name bookName3 = new Name("CLRS Introduction to Algorithms");
        Author bookThreeAuthorOne = new Author("Thomas H Cormen");
        Author bookThreeAuthorTwo = new Author("Charles Eric Leiserson");
        Author bookThreeAuthorThree = new Author("Ron Rivest");
        Author bookThreeAuthorFour = new Author("Clifford Stein");
        List<Author> authorListBook3 = new ArrayList<Author>();

        Collections.addAll(authorListBook3, bookThreeAuthorOne, bookThreeAuthorTwo, bookThreeAuthorThree,
                bookThreeAuthorFour);

        Set<Tag> categoryTagsBook3 = getTagSet("Algorithm", "Mathematics");
        String isbnBook3 = "978-0-262-03384-8";
        BookStub book3 = new BookStub(bookName3, authorListBook3, isbnBook3, categoryTagsBook3, false,
                "14/3/2022");

        observableBookList.addAll(book0, book2, book3, book1);

        ObservableList<BookStub> internalUnmodifiableList =
                FXCollections.unmodifiableObservableList(observableBookList);

        FilteredList<BookStub> bookStubObservableList = new FilteredList<BookStub>(internalUnmodifiableList);

        // Remember to replace bookStubObservableList to logic.getFilteredBookList() as it is not avail currently
        bookListPanel = new BookListPanel(bookStubObservableList);
        bookListPanelPlaceholder.getChildren().add(bookListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }

    public PersonListPanel getPersonListPanel() {
        return personListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
