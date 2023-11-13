package wedlog.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import wedlog.address.model.AddressBook;
import wedlog.address.model.ReadOnlyAddressBook;
import wedlog.address.model.tag.DietaryRequirement;
import wedlog.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a set of dietary requirements containing the list of strings given.
     */
    public static Set<DietaryRequirement> getDietaryRequirementSet(String... strings) {
        return Arrays.stream(strings)
                .map(DietaryRequirement::new)
                .collect(Collectors.toSet());
    }

}
