package wedlog.address.model.person;

import static java.lang.Integer.parseInt;
import static java.util.Objects.requireNonNull;
import static wedlog.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's table number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTableNumber(String)}
 */
public class TableNumber {


    public static final String MESSAGE_CONSTRAINTS =
            "Table numbers should only contain numbers";
    public static final String VALIDATION_REGEX = "\\d+";
    public final String value;

    /**
     * Constructs a {@code TableNumber}.
     *
     * @param tableNumber A valid table number.
     */
    public TableNumber(String tableNumber) {
        requireNonNull(tableNumber);
        checkArgument(isValidTableNumber(tableNumber), MESSAGE_CONSTRAINTS);
        value = Integer.toString(parseInt(tableNumber)); //Integer.parseInt removes preceding zeros from tableNumber
    }

    /**
     * Returns true if a given string is a valid table number.
     */
    public static boolean isValidTableNumber(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TableNumber)) {
            return false;
        }

        TableNumber otherTableNumber = (TableNumber) other;
        return value.equals(otherTableNumber.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
