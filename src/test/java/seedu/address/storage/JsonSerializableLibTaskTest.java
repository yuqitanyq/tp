package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.LibTask;
import seedu.address.testutil.LengthyBooks;
import seedu.address.testutil.ManyPatrons;
import seedu.address.testutil.TypicalLibTask;

public class JsonSerializableLibTaskTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableLibTaskTest");
    private static final Path TYPICAL_PATRONS_FILE = TEST_DATA_FOLDER.resolve("typicalPatronsLibTask.json");
    private static final Path INVALID_PATRON_FILE = TEST_DATA_FOLDER.resolve("invalidPatronLibTask.json");
    private static final Path DUPLICATE_PATRON_FILE = TEST_DATA_FOLDER.resolve("duplicatePatronLibTask.json");
    private static final Path TYPICAL_BOOKS_FILE = TEST_DATA_FOLDER.resolve("typicalBooksLibTask.json");
    private static final Path INVALID_BOOK_FILE = TEST_DATA_FOLDER.resolve("invalidBookLibTask.json");
    private static final Path LENGTHY_BOOK_FILE = TEST_DATA_FOLDER.resolve("lengthyBooksLibTask.json");
    private static final Path MANY_PATRONS_FILE = TEST_DATA_FOLDER.resolve("ManyPatronsLibTask.json");

    @Test
    public void toModelType_typicalPatronsFile_success() throws Exception {
        JsonSerializableLibTask dataFromFile = JsonUtil.readJsonFile(TYPICAL_PATRONS_FILE,
                JsonSerializableLibTask.class).get();
        LibTask libTaskFromFile = dataFromFile.toModelType();
        LibTask typicalPatronsLibTask = TypicalLibTask.getTypicalLibTask();
        assertEquals(libTaskFromFile, typicalPatronsLibTask);
    }

    @Test
    public void toModelType_invalidPatronFile_throwsIllegalValueException() throws Exception {
        JsonSerializableLibTask dataFromFile = JsonUtil.readJsonFile(INVALID_PATRON_FILE,
                JsonSerializableLibTask.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePatrons_throwsIllegalValueException() throws Exception {
        JsonSerializableLibTask dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PATRON_FILE,
                JsonSerializableLibTask.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableLibTask.MESSAGE_DUPLICATE_PATRON,
                dataFromFile::toModelType);
    }

    @Test
    public void toModelType_typicalBooksFile_success() throws Exception {
        JsonSerializableLibTask dataFromFile = JsonUtil.readJsonFile(TYPICAL_BOOKS_FILE,
                JsonSerializableLibTask.class).get();
        LibTask libTaskFromFile = dataFromFile.toModelType();
        LibTask typicalBooksLibTask = TypicalLibTask.getTypicalLibTask();
        assertEquals(libTaskFromFile, typicalBooksLibTask);
    }

    @Test
    public void toModelType_invalidBookFile_throwsIllegalValueException() throws Exception {
        JsonSerializableLibTask dataFromFile = JsonUtil.readJsonFile(INVALID_BOOK_FILE,
                JsonSerializableLibTask.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_lengthyBooksFile_success() throws Exception {
        JsonSerializableLibTask dataFromFile = JsonUtil.readJsonFile(LENGTHY_BOOK_FILE,
                JsonSerializableLibTask.class).get();
        LibTask libTaskFromFile = dataFromFile.toModelType();
        LibTask lengthyBooksLibTask = LengthyBooks.getLengthyLibTask();
        assertEquals(libTaskFromFile, lengthyBooksLibTask);
    }

    @Test
    public void toModelType_manyPatronsFile_success() throws Exception {
        JsonSerializableLibTask dataFromFile = JsonUtil.readJsonFile(MANY_PATRONS_FILE,
                JsonSerializableLibTask.class).get();

        LibTask libTaskFromFile = dataFromFile.toModelType();
        LibTask manyPatronsLibTask = ManyPatrons.getManyPatronsLibTask();
        assertEquals(libTaskFromFile, manyPatronsLibTask);
    }

}
