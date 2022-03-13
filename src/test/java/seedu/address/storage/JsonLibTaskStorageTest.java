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
    public void readAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readAddressBook(null));
    }

    private java.util.Optional<ReadOnlyLibTask> readAddressBook(String filePath) throws Exception {
        return new JsonLibTaskStorage(Paths.get(filePath)).readAddressBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readAddressBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readAddressBook("notJsonFormatLibTask.json"));
    }

    @Test
    public void readAddressBook_invalidPatronAddressBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readAddressBook("invalidPatronLibTask.json"));
    }

    @Test
    public void readAddressBook_invalidAndValidPatronAddressBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readAddressBook("invalidAndValidPatronLibTask.json"));
    }

    @Test
    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempAddressBook.json");
        LibTask original = TypicalLibTask.getTypicalAddressBook();
        JsonLibTaskStorage jsonLibTaskStorage = new JsonLibTaskStorage(filePath);

        // Save in new file and read back
        jsonLibTaskStorage.saveAddressBook(original, filePath);
        ReadOnlyLibTask readBack = jsonLibTaskStorage.readAddressBook(filePath).get();
        assertEquals(original, new LibTask(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPatron(HOON);
        original.removePatron(ALICE);
        jsonLibTaskStorage.saveAddressBook(original, filePath);
        readBack = jsonLibTaskStorage.readAddressBook(filePath).get();
        assertEquals(original, new LibTask(readBack));

        // Save and read without specifying file path
        original.addPatron(IDA);
        jsonLibTaskStorage.saveAddressBook(original); // file path not specified
        readBack = jsonLibTaskStorage.readAddressBook().get(); // file path not specified
        assertEquals(original, new LibTask(readBack));

    }

    @Test
    public void saveAddressBook_nullAddressBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAddressBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveAddressBook(ReadOnlyLibTask addressBook, String filePath) {
        try {
            new JsonLibTaskStorage(Paths.get(filePath))
                    .saveAddressBook(addressBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAddressBook(new LibTask(), null));
    }
}
