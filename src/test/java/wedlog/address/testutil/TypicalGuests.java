package wedlog.address.testutil;

import static wedlog.address.logic.commands.CommandTestUtil.VALID_ADDRESS_GABE;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_ADDRESS_GIA;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_DIETARY_REQUIREMENTS_GABE;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_DIETARY_REQUIREMENTS_GIA;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_EMAIL_GABE;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_EMAIL_GIA;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_NAME_GABE;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_NAME_GIA;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_PHONE_GABE;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_PHONE_GIA;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_RSVP_STATUS_GABE;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_RSVP_STATUS_GIA;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_TABLE_NUMBER_GABE;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_TABLE_NUMBER_GIA;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import wedlog.address.model.AddressBook;
import wedlog.address.model.person.Guest;

/**
 * A utility class containing a list of {@code Guest} objects to be used in tests.
 */
public class TypicalGuests {

    public static final Guest GINA = new GuestBuilder().withName("Gina Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("gina@example.com")
            .withPhone("94351253").withRsvpStatus("yes").withDietaryRequirements("vegan")
            .withTableNumber("1").withTags("friends").build();
    public static final Guest GREG = new GuestBuilder().withName("Greg Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("greg@example.com").withPhone("98765432")
            .withRsvpStatus("no").withDietaryRequirements("none")
            .withTableNumber("1").withTags("owesMoney", "friends").build();
    public static final Guest GABRIEL = new GuestBuilder().withName("Gabriel Kurz").withPhone("95352563")
            .withEmail("gabkurz@example.com").withAddress("wall street")
            .withRsvpStatus("unknown").withTableNumber("2")
            .build();
    public static final Guest GEORGE = new GuestBuilder().withName("George Tan").withPhone("87652533")
            .withEmail("georgemeier@example.com").withAddress("10th street")
            .withRsvpStatus("yes").withDietaryRequirements("no beef").withTableNumber("2")
            .withTags("friends").build();

    // Manually added
    public static final Guest GIDEON = new GuestBuilder().withName("Gideon Lim").withPhone("8482424")
            .withEmail("gideon@example.com").withAddress("little india")
            .withRsvpStatus("unknown").withDietaryRequirements("none").withTableNumber("3")
            .build();
    public static final Guest GRACE = new GuestBuilder().withName("Grace Mueller").withPhone("8482131")
            .withEmail("grace@example.com").withAddress("chicago ave")
            .withRsvpStatus("yes").withDietaryRequirements("no shellfish", "no pork").withTableNumber("3")
            .build();

    // Manually added - Guest's details found in {@code CommandTestUtil}
    public static final Guest GIA = new GuestBuilder().withName(VALID_NAME_GIA).withPhone(VALID_PHONE_GIA)
            .withEmail(VALID_EMAIL_GIA).withAddress(VALID_ADDRESS_GIA).withRsvpStatus(VALID_RSVP_STATUS_GIA)
            .withDietaryRequirements(VALID_DIETARY_REQUIREMENTS_GIA).withTableNumber(VALID_TABLE_NUMBER_GIA)
            .withTags(VALID_TAG_FRIEND).build();
    public static final Guest GABE = new GuestBuilder().withName(VALID_NAME_GABE).withPhone(VALID_PHONE_GABE)
            .withEmail(VALID_EMAIL_GABE).withAddress(VALID_ADDRESS_GABE).withRsvpStatus(VALID_RSVP_STATUS_GABE)
            .withDietaryRequirements(VALID_DIETARY_REQUIREMENTS_GABE).withTableNumber(VALID_TABLE_NUMBER_GABE)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
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
        return new ArrayList<>(Arrays.asList(GIA, GINA, GREG, GABRIEL, GEORGE));
    }
}
