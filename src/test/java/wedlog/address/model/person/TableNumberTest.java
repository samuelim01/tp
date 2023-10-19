package wedlog.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static wedlog.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TableNumberTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TableNumber(null));
    }

    @Test
    public void constructor_invalidTableNumber_throwsIllegalArgumentException() {
        String invalidTableNumber = "";
        assertThrows(IllegalArgumentException.class, () -> new TableNumber(invalidTableNumber));
    }

    @Test
    public void isValidTableNumber() {
        // null table number
        assertThrows(NullPointerException.class, () -> TableNumber.isValidTableNumber(null));

        // invalid table numbers
        assertFalse(TableNumber.isValidTableNumber("")); // empty string
        assertFalse(TableNumber.isValidTableNumber(" ")); // spaces only
        assertFalse(TableNumber.isValidTableNumber("phone")); // non-numeric
        assertFalse(TableNumber.isValidTableNumber("9011p041")); // alphabets within digits
        assertFalse(TableNumber.isValidTableNumber("9312 1534")); // spaces within digits
        assertFalse(TableNumber.isValidTableNumber("-1")); // special character
        assertFalse(TableNumber.isValidTableNumber("1.1")); //special character

        // valid table numbers
        assertTrue(TableNumber.isValidTableNumber("02"));
        assertTrue(TableNumber.isValidTableNumber("130"));
        assertTrue(TableNumber.isValidTableNumber("124293842033123")); // long table numbers
    }

    @Test
    public void equals() {
        TableNumber tableNumber = new TableNumber("13");

        // same values -> returns true
        assertTrue(tableNumber.equals(new TableNumber("13")));

        // same object -> returns true
        assertTrue(tableNumber.equals(tableNumber));

        // null -> returns false
        assertFalse(tableNumber.equals(null));

        // different types -> returns false
        assertFalse(tableNumber.equals(5.0f));

        // different values -> returns false
        assertFalse(tableNumber.equals(new TableNumber("31")));
    }
}
