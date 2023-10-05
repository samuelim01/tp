package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.task.Description;
import seedu.address.model.task.Task;

/**
 * A utility class containing a list of {@code Task} objects to be used in tests.
 */
public class TypicalTasks {

    public static final Task ARRANGE = new Task(new Description("Arrange meeting with friends"));
    public static final Task BUY = new Task(new Description("Buy groceries"));
    public static final Task CALL = new Task(new Description("Call to check in on Ben"));
    public static final Task DRAFT = new Task(new Description("Draft an internship email"));
    public static final Task EXERCISE = new Task(new Description("Go for a long run"));
    public static final Task FILE = new Task(new Description("Tidy up notes"));
    public static final Task GRADE = new Task(new Description("Grade student assignments"));


    private TypicalTasks() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Task task : getTypicalTasks()) {
            ab.addTask(task);
        }
        return ab;
    }

    public static List<Task> getTypicalTasks() {
        return new ArrayList<>(Arrays.asList(ARRANGE, BUY, CALL, DRAFT, EXERCISE));
    }
}
