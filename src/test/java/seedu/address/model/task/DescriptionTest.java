package seedu.address.model.task;

import org.junit.jupiter.api.Test;
import seedu.address.model.person.Name;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.testutil.Assert.assertThrows;

class DescriptionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Description(null));
    }

    @Test
    public void constructor_invalidDescription_throwsIllegalArgumentException() {
        String invalidDescription = "";
        assertThrows(IllegalArgumentException.class, () -> new Description(invalidDescription));
    }

    @Test
    public void isValidDescription() {
        // null description
        assertThrows(NullPointerException.class, () -> Description.isValidDescription(null));

        // invalid description
        assertFalse(Description.isValidDescription("")); // empty string
        assertFalse(Description.isValidDescription(" ")); // spaces only
        assertFalse(Description.isValidDescription("^")); // only non-alphanumeric characters

        // valid name
        assertTrue(Name.isValidName("peter jack")); // alphabets only
        assertTrue(Name.isValidName("12345")); // numbers only
        assertTrue(Name.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(Name.isValidName("CS2100: Assignment 1")); // non-alphanumeric characters
        assertTrue(Name.isValidName("Homework")); // with capital letters
        assertTrue(Name.isValidName("get the three different CS2103T assignments done")); // long description
        assertTrue(Name.isValidName(" do homework")); // starts with whitespace
    }

    @Test
    public void equals() {
        Description description = new Description("Valid Description");

        // same values -> return true
        assertTrue(description.equals(new Description("Valid Description")));

        // same object -> return true
        assertTrue(description.equals(description));

        // null -> returns false
        assertFalse(description.equals(null));

        // different types -> returns false
        assertFalse(description.equals(5.0f));

        // different values -> returns false
        assertFalse(description.equals(new Description("Other Valid Name")));
    }
}