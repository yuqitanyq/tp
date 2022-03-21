package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ISBN_HUNGER_GAMES;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_BOOKS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PATRONS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalBooks.HARRY_POTTER;
import static seedu.address.testutil.TypicalPatrons.ALICE;
import static seedu.address.testutil.TypicalPatrons.BENSON;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.book.Book;
import seedu.address.model.book.BookNameContainsKeywordsPredicate;
import seedu.address.model.patron.NameContainsKeywordsPredicate;
import seedu.address.testutil.BookBuilder;
import seedu.address.testutil.LibTaskBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new LibTask(), new LibTask(modelManager.getLibTask()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setLibTaskFilePath(Paths.get("lib/task/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setLibTaskFilePath(Paths.get("new/lib/task/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setLibTaskFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setLibTaskFilePath(null));
    }

    @Test
    public void setLibTaskFilePath_validPath_setsLibTaskFilePath() {
        Path path = Paths.get("lib/task/file/path");
        modelManager.setLibTaskFilePath(path);
        assertEquals(path, modelManager.getLibTaskFilePath());
    }

    @Test
    public void hasPatron_nullPatron_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasPatron(null));
    }

    @Test
    public void hasBook_nullBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasBook(null));
    }

    @Test
    public void hasPatron_patronNotInLibTask_returnsFalse() {
        assertFalse(modelManager.hasPatron(ALICE));
    }

    @Test
    public void hasBook_bookNotInLibTask_returnsFalse() {
        assertFalse(modelManager.hasBook(HARRY_POTTER));
    }

    @Test
    public void hasPatron_patronInLibTask_returnsTrue() {
        modelManager.addPatron(ALICE);
        assertTrue(modelManager.hasPatron(ALICE));
    }

    @Test
    public void hasBook_bookInLibTask_returnsTrue() {
        modelManager.addBook(HARRY_POTTER);
        assertTrue(modelManager.hasBook(HARRY_POTTER));
    }

    @Test
    public void hasSameIsbnDiffAuthorsOrName_nullBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasSameIsbnDiffAuthorsOrName(null));
    }

    @Test
    public void hasSameIsbnDiffAuthorsOrName_hasInconsistentBook_returnsTrue() {
        modelManager.addBook(HARRY_POTTER);
        Book inconsistentBook = new BookBuilder(HARRY_POTTER).withName("diffname").build();
        assertTrue(modelManager.hasSameIsbnDiffAuthorsOrName(inconsistentBook));
    }

    @Test
    public void hasSameIsbnDiffAuthorsOrName_consistentBook_returnsFalse() {
        modelManager.addBook(HARRY_POTTER);
        // book with same name but different isbn is consistent
        Book consistentBook = new BookBuilder(HARRY_POTTER).withIsbn(VALID_ISBN_HUNGER_GAMES).build();
        assertFalse(modelManager.hasSameIsbnDiffAuthorsOrName(consistentBook));
    }

    @Test
    public void getFilteredPatronList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPatronList().remove(0));
    }

    @Test
    public void getFilteredBookList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredBookList().remove(0));
    }

    @Test
    public void equals() {
        LibTask libTask = new LibTaskBuilder().withPatron(ALICE).withPatron(BENSON).build();
        LibTask differentLibTask = new LibTask();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(libTask, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(libTask, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different libTask -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentLibTask, userPrefs)));

        // different filteredPatronList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPatronList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(libTask, userPrefs)));

        // different filteredBookList -> returns false
        String[] bookKeywords = HARRY_POTTER.getBookName().fullBookName.split("\\s+");
        modelManager.updateFilteredBookList(new BookNameContainsKeywordsPredicate(Arrays.asList(bookKeywords)));
        assertFalse(modelManager.equals(new ModelManager(libTask, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPatronList(PREDICATE_SHOW_ALL_PATRONS);

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredBookList(PREDICATE_SHOW_ALL_BOOKS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setLibTaskFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(libTask, differentUserPrefs)));
    }
}
