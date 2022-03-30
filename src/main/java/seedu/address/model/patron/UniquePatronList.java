package seedu.address.model.patron;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.patron.exceptions.DuplicatePatronException;
import seedu.address.model.patron.exceptions.PatronNotFoundException;

/**
 * A list of patrons that enforces uniqueness between its elements and does not allow nulls.
 * A patron is considered unique by comparing using {@code Patron#isSamePatron(Patron)}. As such, adding and updating of
 * patrons uses Patron#isSamePatron(Patron) for equality so as to ensure that the patron being added or updated is
 * unique in terms of identity in the UniquePatronList. However, the removal of a patron uses Patron#equals(Object) so
 * as to ensure that the patron with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Patron#isSamePatron(Patron)
 */
public class UniquePatronList implements Iterable<Patron> {

    private final ObservableList<Patron> internalList = FXCollections.observableArrayList();
    private final ObservableList<Patron> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent patron as the given argument.
     */
    public boolean contains(Patron toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSamePatron);
    }

    /**
     * Adds a patron to the list.
     * The patron must not already exist in the list.
     */
    public void add(Patron toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePatronException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the patron {@code target} in the list with {@code editedPatron}.
     * {@code target} must exist in the list.
     * The patron identity of {@code editedPatron} must not be the same as another existing patron in the list.
     */
    public void setPatron(Patron target, Patron editedPatron) {
        requireAllNonNull(target, editedPatron);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PatronNotFoundException();
        }

        if (!target.isSamePatron(editedPatron) && contains(editedPatron)) {
            throw new DuplicatePatronException();
        }

        internalList.set(index, editedPatron);
    }

    /**
     * Removes the equivalent patron from the list.
     * The patron must exist in the list.
     */
    public void remove(Patron toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PatronNotFoundException();
        }
    }

    public void setPatrons(UniquePatronList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code patrons}.
     * {@code patrons} must not contain duplicate patrons.
     */
    public void setPatrons(List<Patron> patrons) {
        requireAllNonNull(patrons);
        if (!patronsAreUnique(patrons)) {
            throw new DuplicatePatronException();
        }

        internalList.setAll(patrons);
    }

    /**
     * Returns true if this patron list contains a patron that is not the same as {@code oldPatron} based on
     * {@link Patron#equals(Object)}, but same as {@code editedPatron} based on {@link Patron#isSamePatron(Patron)}.
     */
    public boolean hasEditedPatron(Patron oldPatron, Patron editedPatron) {
        requireAllNonNull(oldPatron, editedPatron);
        return internalList.stream().anyMatch(patron -> !patron.equals(oldPatron) && patron.isSamePatron(editedPatron));
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Patron> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Patron> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniquePatronList // instanceof handles nulls
                        && internalList.equals(((UniquePatronList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code patrons} contains only unique patrons.
     */
    private boolean patronsAreUnique(List<Patron> patrons) {
        for (int i = 0; i < patrons.size() - 1; i++) {
            for (int j = i + 1; j < patrons.size(); j++) {
                if (patrons.get(i).isSamePatron(patrons.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
