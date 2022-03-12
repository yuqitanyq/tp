package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.AddressBook;
import seedu.address.testutil.LengthyBooks;
import seedu.address.testutil.TypicalAddressBook;
import seedu.address.testutil.TypicalBooks;

public class JsonSerializableAddressBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_PATRONS_FILE = TEST_DATA_FOLDER.resolve("typicalPatronsAddressBook.json");
    private static final Path INVALID_PATRON_FILE = TEST_DATA_FOLDER.resolve("invalidPatronAddressBook.json");
    private static final Path DUPLICATE_PATRON_FILE = TEST_DATA_FOLDER.resolve("duplicatePatronAddressBook.json");
    private static final Path TYPICAL_BOOKS_FILE = TEST_DATA_FOLDER.resolve("typicalBooksAddressBook.json");
    private static final Path INVALID_BOOK_FILE = TEST_DATA_FOLDER.resolve("invalidBookAddressBook.json");
    private static final Path LENGTHY_BOOK_FILE = TEST_DATA_FOLDER.resolve("lengthyBooksAddressBook.json");

    @Test
    public void toModelType_typicalPatronsFile_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_PATRONS_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalPatronsAddressBook = TypicalAddressBook.getTypicalAddressBook();
        assertEquals(addressBookFromFile, typicalPatronsAddressBook);
    }

    @Test
    public void toModelType_invalidPatronFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(INVALID_PATRON_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePatrons_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PATRON_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableAddressBook.MESSAGE_DUPLICATE_PATRON,
                dataFromFile::toModelType);
    }

    @Test
    public void toModelType_typicalBooksFile_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_BOOKS_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalBooksAddressBook = TypicalBooks.getTypicalAddressBook();
        assertEquals(addressBookFromFile, typicalBooksAddressBook);
    }

    @Test
    public void toModelType_invalidBookFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(INVALID_BOOK_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_lengthyBooksFile_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(LENGTHY_BOOK_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook lengthyBooksAddressBook = LengthyBooks.getLengthyAddressBook();
        assertEquals(addressBookFromFile, lengthyBooksAddressBook);
    }

}
