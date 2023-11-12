package wedlog.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import wedlog.address.model.AddressBook;
import wedlog.address.model.ReadOnlyAddressBook;
import wedlog.address.model.person.Address;
import wedlog.address.model.person.Email;
import wedlog.address.model.person.Guest;
import wedlog.address.model.person.Name;
import wedlog.address.model.person.Phone;
import wedlog.address.model.person.RsvpStatus;
import wedlog.address.model.person.TableNumber;
import wedlog.address.model.person.Vendor;
import wedlog.address.model.tag.DietaryRequirement;
import wedlog.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    public static Guest[] getSampleGuests() {
        return new Guest[] {
            new Guest(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"), new RsvpStatus("yes"),
                getDietaryRequirementSet("vegan"), new TableNumber("1"), getTagSet("friends")),
            new Guest(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new RsvpStatus("yes"),
                getDietaryRequirementSet(), new TableNumber("1"), getTagSet("colleagues", "friends")),
            new Guest(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new RsvpStatus("no"),
                getDietaryRequirementSet(), null, getTagSet("neighbours")),
            new Guest(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new RsvpStatus("unknown"),
                getDietaryRequirementSet("vegetarian"), new TableNumber("2"), getTagSet("family")),
            new Guest(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"), new RsvpStatus("no"),
                getDietaryRequirementSet(), null, getTagSet("classmates")),
            new Guest(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"), new RsvpStatus("yes"),
                getDietaryRequirementSet("pescatarian", "peanut allergy"), new TableNumber("3"),
                getTagSet("colleagues"))
        };
    }

    public static Vendor[] getSampleVendors() {
        return new Vendor[] {
            new Vendor(new Name("ABC Catering"), new Phone("67143607"), new Email("abccatering@example.com"),
                new Address("Blk 105 Towner Road, #01-400"), getTagSet("catering")),
            new Vendor(new Name("Bridal Boutique"), new Phone("66242358"), new Email("bridalboutique@example.com"),
                new Address("Blk 42 Ang Mo Kio, #03-04"), getTagSet("boutique")),
            new Vendor(new Name("Creative Catering"), new Phone("62404261"), new Email("creativecatering@example.com"),
                new Address("Blk 167 Tampines Street 74, #01-042"), getTagSet("catering")),
            new Vendor(new Name("Dave Catering"), new Phone("61781282"), new Email("davecatering@example.com"),
                new Address("Blk 260 Choa Chu Kang Drive, #01-47"), getTagSet("catering")),
            new Vendor(new Name("Evergreen Hotel"), new Phone("62413366"), new Email("evergreenhotel@example.com"),
                new Address("162 Orchard Boulevard"), getTagSet("venue", "catering")),
            new Vendor(new Name("Flora Concepts"), new Phone("61423611"), new Email("floraconcepts@example.com"),
                new Address("Blk 2 Jalan Bukit Merah, #14-21"), getTagSet("florist"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Guest sampleGuest : getSampleGuests()) {
            sampleAb.addGuest(sampleGuest);
        }
        for (Vendor sampleVendor : getSampleVendors()) {
            sampleAb.addVendor(sampleVendor);
        }
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
