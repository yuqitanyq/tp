package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyLibTask;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of LibTask data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private LibTaskStorage libTaskStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code LibTaskStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(LibTaskStorage libTaskStorage, UserPrefsStorage userPrefsStorage) {
        this.libTaskStorage = libTaskStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ LibTask methods ==============================

    @Override
    public Path getLibTaskFilePath() {
        return libTaskStorage.getLibTaskFilePath();
    }

    @Override
    public Optional<ReadOnlyLibTask> readLibTask() throws DataConversionException, IOException {
        return readLibTask(libTaskStorage.getLibTaskFilePath());
    }

    @Override
    public Optional<ReadOnlyLibTask> readLibTask(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return libTaskStorage.readLibTask(filePath);
    }

    @Override
    public void saveLibTask(ReadOnlyLibTask libTask) throws IOException {
        saveLibTask(libTask, libTaskStorage.getLibTaskFilePath());
    }

    @Override
    public void saveLibTask(ReadOnlyLibTask libTask, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        libTaskStorage.saveLibTask(libTask, filePath);
    }

}
