package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.patron.Patron;

/**
 * A utility class containing a list of {@code Patron} objects to be used in tests.
 */
public class TypicalPatrons {

    public static final Patron ALICE = new PatronBuilder().withName("Alice Pauline")
            .withId("A0123456H").withEmail("alice@example.com")
            .withPhone("94351253")
            .withTags("friends").build();
    public static final Patron BENSON = new PatronBuilder().withName("Benson Meier")
            .withId("A0123456H")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withTags("owesMoney", "friends").build();
    public static final Patron CARL = new PatronBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withId("A0123456H").build();
    public static final Patron DANIEL = new PatronBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withId("A0123456H").withTags("friends").build();
    public static final Patron ELLE = new PatronBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withId("A0123456H").build();
    public static final Patron FIONA = new PatronBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withId("A0123456H").build();
    public static final Patron GEORGE = new PatronBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withId("A0123456H").build();

    // Manually added
    public static final Patron HOON = new PatronBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withId("A0123456H").build();
    public static final Patron IDA = new PatronBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withId("A0123456H").build();

    // Manually added - Patron's details found in {@code CommandTestUtil}
    public static final Patron AMY = new PatronBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withId(VALID_ID_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Patron BOB = new PatronBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withId(VALID_ID_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPatrons() {} // prevents instantiation

    public static List<Patron> getTypicalPatrons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
