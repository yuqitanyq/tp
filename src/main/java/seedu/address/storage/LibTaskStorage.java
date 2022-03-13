package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.LibTask;
import seedu.address.model.ReadOnlyLibTask;

/**
 * Represents a storage for {@link LibTask}.
 */
public interface LibTaskStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getLibTaskFilePath();

    /**
     * Returns LibTask data as a {@link ReadOnlyLibTask}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyLibTask> readLibTask() throws DataConversionException, IOException;

    /**
     * @see #getLibTaskFilePath()
     */
    Optional<ReadOnlyLibTask> readLibTask(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyLibTask} to the storage.
     * @param libTask cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveLibTask(ReadOnlyLibTask libTask) throws IOException;

    /**
     * @see #saveLibTask(ReadOnlyLibTask)
     */
    void saveLibTask(ReadOnlyLibTask libTask, Path filePath) throws IOException;

}
