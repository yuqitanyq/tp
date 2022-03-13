package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyLibTask;

/**
 * A class to access LibTask data stored as a json file on the hard disk.
 */
public class JsonLibTaskStorage implements LibTaskStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonLibTaskStorage.class);

    private Path filePath;

    public JsonLibTaskStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getLibTaskFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyLibTask> readLibTask() throws DataConversionException {
        return readLibTask(filePath);
    }

    /**
     * Similar to {@link #readLibTask()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyLibTask> readLibTask(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableLibTask> jsonLibTask = JsonUtil.readJsonFile(
                filePath, JsonSerializableLibTask.class);
        if (!jsonLibTask.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonLibTask.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveLibTask(ReadOnlyLibTask libTask) throws IOException {
        saveLibTask(libTask, filePath);
    }

    /**
     * Similar to {@link #saveLibTask(ReadOnlyLibTask)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveLibTask(ReadOnlyLibTask libTask, Path filePath) throws IOException {
        requireNonNull(libTask);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableLibTask(libTask), filePath);
    }

}
