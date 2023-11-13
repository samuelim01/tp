package wedlog.address.commons.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Point;

import org.junit.jupiter.api.Test;

public class GuiSettingsTest {

    private GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);

    @Test
    public void getWindowCoordinates() {
        // non-null -> returns coordinates
        Point expectedCoordinates = new Point(3, 4);
        assertEquals(expectedCoordinates, guiSettings.getWindowCoordinates());

        // null -> returns null
        guiSettings = new GuiSettings();
        assertEquals(null, guiSettings.getWindowCoordinates());
    }

    @Test
    public void equals() {
        // same object -> returns true
        assertTrue(guiSettings.equals(guiSettings));

        // same values -> returns true
        GuiSettings guiSettingsCpy = new GuiSettings(1, 2, 3, 4);
        assertTrue(guiSettings.equals(guiSettingsCpy));

        // null -> returns false
        assertFalse(guiSettings.equals(null));

        // different type -> returns false
        assertFalse(guiSettings.equals(5.0f));

        // different windowWidth -> returns false
        GuiSettings diffValues = new GuiSettings(2, 2, 3, 4);
        assertFalse(guiSettings.equals(diffValues));

        // different windowHeight -> returns false
        diffValues = new GuiSettings(1, 3, 3, 4);
        assertFalse(guiSettings.equals(diffValues));

        // different xPosition -> returns false
        diffValues = new GuiSettings(1, 2, 4, 4);
        assertFalse(guiSettings.equals(diffValues));

        // different yPosition -> returns false
        diffValues = new GuiSettings(1, 2, 3, 5);
        assertFalse(guiSettings.equals(diffValues));
    }

    @Test
    public void toStringMethod() {
        GuiSettings guiSettings = new GuiSettings();
        String expected = GuiSettings.class.getCanonicalName() + "{windowWidth=" + guiSettings.getWindowWidth()
                + ", windowHeight=" + guiSettings.getWindowHeight() + ", windowCoordinates="
                + guiSettings.getWindowCoordinates() + "}";
        assertEquals(expected, guiSettings.toString());
    }
}
