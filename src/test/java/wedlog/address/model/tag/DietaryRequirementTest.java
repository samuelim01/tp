package wedlog.address.model.tag;

import static wedlog.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DietaryRequirementTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DietaryRequirement(null));
    }

}
