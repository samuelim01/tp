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

        // invalid table numbers

        // EP1: empty string or non-numeric characters (fails VALIDATION_REGEX \\d+)
        assertFalse(TableNumber.isValidTableNumber("")); // empty string
        assertFalse(TableNumber.isValidTableNumber(" ")); // spaces only
        assertFalse(TableNumber.isValidTableNumber("table")); // alphabets
        assertFalse(TableNumber.isValidTableNumber("9011p041")); // alphabets within digits
        assertFalse(TableNumber.isValidTableNumber("9312 1534")); // spaces within digits
        assertFalse(TableNumber.isValidTableNumber("1.1")); //special character
        assertFalse(TableNumber.isValidTableNumber("-1")); // special character

        // EP2: zero (fails isNonZeroUnsignedInteger test)
        assertFalse(TableNumber.isValidTableNumber("0")); //zero
        assertFalse(TableNumber.isValidTableNumber("0000")); //zeros

        // EP3: integers greater than Integer.MAX_VALUE (fails isNonZeroUnsignedInteger test)
        assertFalse(TableNumber.isValidTableNumber("2147483648")); // Integer.MAX_VALUE + 1
        assertFalse(TableNumber.isValidTableNumber("9999999999999999")); // very very big number

        // EP4: null
        assertThrows(NullPointerException.class, () -> TableNumber.isValidTableNumber(null));

        // valid table numbers

        // EP5: integers between 1 and Integer.MAX_VALUE inclusive
        assertTrue(TableNumber.isValidTableNumber("130"));
        assertTrue(TableNumber.isValidTableNumber("0002")); //preceding zeros
        assertTrue(TableNumber.isValidTableNumber("1")); // lower boundary value
        assertTrue(TableNumber.isValidTableNumber("2147483647")); // upper boundary value (Integer.MAX_VALUE)
        assertTrue(TableNumber.isValidTableNumber("000002147483647")); // Integer.MAX_VALUE with preceding zeros
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
