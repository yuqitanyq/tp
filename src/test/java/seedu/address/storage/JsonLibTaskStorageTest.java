package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPatrons.ALICE;
import static seedu.address.testutil.TypicalPatrons.HOON;
import static seedu.address.testutil.TypicalPatrons.IDA;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.LibTask;
import seedu.address.model.ReadOnlyLibTask;
import seedu.address.testutil.TypicalLibTask;

public class JsonLibTaskStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonLibTaskStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readLibTask_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readLibTask(null));
    }

    private java.util.Optional<ReadOnlyLibTask> readLibTask(String filePath) throws Exception {
        return new JsonLibTaskStorage(Paths.get(filePath)).readLibTask(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readLibTask("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readLibTask("notJsonFormatLibTask.json"));
    }

    @Test
    public void readLibTask_invalidPatronLibTask_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readLibTask("invalidPatronLibTask.json"));
    }

    @Test
    public void readLibTask_invalidAndValidPatronLibTask_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readLibTask("invalidAndValidPatronLibTask.json"));
    }

    @Test
    public void readAndSaveLibTask_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempLibTask.json");
        LibTask original = TypicalLibTask.getTypicalLibTask();
        JsonLibTaskStorage jsonLibTaskStorage = new JsonLibTaskStorage(filePath);

        // Save in new file and read back
        jsonLibTaskStorage.saveLibTask(original, filePath);
        ReadOnlyLibTask readBack = jsonLibTaskStorage.readLibTask(filePath).get();
        assertEquals(original, new LibTask(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPatron(HOON);
        original.removePatron(ALICE);
        jsonLibTaskStorage.saveLibTask(original, filePath);
        readBack = jsonLibTaskStorage.readLibTask(filePath).get();
        assertEquals(original, new LibTask(readBack));

        // Save and read without specifying file path
        original.addPatron(IDA);
        jsonLibTaskStorage.saveLibTask(original); // file path not specified
        readBack = jsonLibTaskStorage.readLibTask().get(); // file path not specified
        assertEquals(original, new LibTask(readBack));

    }

    @Test
    public void saveLibTask_nullLibTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveLibTask(null, "SomeFile.json"));
    }

    /**
     * Saves {@code libTask} at the specified {@code filePath}.
     */
    private void saveLibTask(ReadOnlyLibTask libTask, String filePath) {
        try {
            new JsonLibTaskStorage(Paths.get(filePath))
                    .saveLibTask(libTask, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveLibTask_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveLibTask(new LibTask(), null));
    }
}
