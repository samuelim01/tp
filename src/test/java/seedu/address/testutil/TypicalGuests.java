package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DIETARY_REQUIREMENTS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DIETARY_REQUIREMENTS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RSVP_STATUS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RSVP_STATUS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Guest;

/**
 * A utility class containing a list of {@code Guest} objects to be used in tests.
 */
public class TypicalGuests {

    public static final Guest GINA = new GuestBuilder().withName("Gina Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("gina@example.com")
            .withPhone("94351253").withRsvpStatus("yes").withDietaryRequirements("vegan")
            .withTags("friends").build();
    public static final Guest GREG = new GuestBuilder().withName("Greg Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("greg@example.com").withPhone("98765432")
            .withRsvpStatus("no").withDietaryRequirements("none")
            .withTags("owesMoney", "friends").build();
    public static final Guest GABRIEL = new GuestBuilder().withName("Gabriel Kurz").withPhone("95352563")
            .withEmail("gabkurz@example.com").withAddress("wall street")
            .withRsvpStatus("unknown").withDietaryRequirements("vegetarian")
            .build();
    public static final Guest GEORGE = new GuestBuilder().withName("George Meier").withPhone("87652533")
            .withEmail("georgemeier@example.com").withAddress("10th street")
            .withRsvpStatus("yes").withDietaryRequirements("no beef")
            .withTags("friends").build();

    // Manually added
    public static final Guest GIDEON = new GuestBuilder().withName("Gideon Meier").withPhone("8482424")
            .withEmail("gideon@example.com").withAddress("little india")
            .withRsvpStatus("unknown").withDietaryRequirements("none")
            .build();
    public static final Guest GRACE = new GuestBuilder().withName("Grace Mueller").withPhone("8482131")
            .withEmail("grace@example.com").withAddress("chicago ave")
            .withRsvpStatus("unknown").withDietaryRequirements("none")
            .build();

    // Manually added - Guest's details found in {@code CommandTestUtil}
    public static final Guest AMY = new GuestBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withRsvpStatus(VALID_RSVP_STATUS_AMY)
            .withDietaryRequirements(VALID_DIETARY_REQUIREMENTS_AMY)
            .withTags(VALID_TAG_FRIEND).build();
    public static final Guest BOB = new GuestBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withRsvpStatus(VALID_RSVP_STATUS_BOB)
            .withDietaryRequirements(VALID_DIETARY_REQUIREMENTS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalGuests() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical guests.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Guest guest : getTypicalGuests()) {
            ab.addGuest(guest);
        }
        return ab;
    }

    public static List<Guest> getTypicalGuests() {
        return new ArrayList<>(Arrays.asList(GINA, GREG, GABRIEL, GEORGE));
    }
}
