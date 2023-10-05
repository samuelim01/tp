package seedu.address.model.task;

import seedu.address.commons.util.ToStringBuilder;

import java.util.Objects;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a Task in the addressbook.
 */
public class Task {

    private final Description description;

    /**
     * Every field must be present and not null.
     */
    public Task(Description description) {
        requireAllNonNull(description);
        this.description = description;
    }

    public Description getDescription() {
        return description;
    }

    /**
     * Returns true if both tasks have the same description.
     *
     * @param other
     * @return
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Task)) {
            return false;
        }

        Task otherTask = (Task) other;
        return description.equals(otherTask.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("description", description)
                .toString();
    }
}
