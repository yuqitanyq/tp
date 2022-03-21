package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.LibTask;
import seedu.address.model.patron.Patron;

public class ManyPatrons {

    // Thirty Patrons are manually added to test if JsonSerializableLibTask will be able to load huge number of Patrons
    public static final Patron ALICE_PAULINE = new PatronBuilder().withName("Alice Pauline").withPhone("94351253")
            .withEmail("alice@example.com").withId("A0123422H").withTags("Student").build();

    public static final Patron BENSON_MEIER = new PatronBuilder().withName("Benson Meier").withPhone("98765432")
            .withEmail("johnd@example.com").withId("A0123412H").withTags("Student").build();

    public static final Patron CARL_KURZ = new PatronBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withId("A0123450H").build();

    public static final Patron DANIEL_MEIER = new PatronBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withId("A0123451H").withTags("Student").build();

    public static final Patron ELLE_MEYER = new PatronBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withId("A0123452H").build();

    public static final Patron FIONA_KUNZ = new PatronBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withId("A0123454H").build();

    public static final Patron GEORGE_BEST = new PatronBuilder().withName("George Best").withPhone("94824421")
            .withEmail("george@example.com").withId("A0123455H").build();

    public static final Patron AMY = new PatronBuilder().withName("Amy").withPhone("94824452")
            .withEmail("amy@example.com").withId("A0123457H").build();

    public static final Patron ROBERT = new PatronBuilder().withName("Robert").withPhone("91234412")
            .withEmail("robert99@example.com").withId("A1231231X").withTags("Student").build();

    public static final Patron JEAN = new PatronBuilder().withName("Jean").withPhone("91234416")
            .withEmail("jean199@example.com").withId("A0012125Y").withTags("Student").build();

    public static final Patron THOMAS = new PatronBuilder().withName("Thomas").withPhone("91234013")
            .withEmail("thomas1212@example.com").withId("A0002105Y").withTags("Student").build();

    public static final Patron TOMMY_GOH = new PatronBuilder().withName("Tommy Goh").withPhone("91234013")
            .withEmail("tommyGoh1919@example.com").withId("A0001234G").withTags("Student").build();

    public static final Patron TOMMY_LIM = new PatronBuilder().withName("Tommy Lim").withPhone("91234543")
            .withEmail("tomlim121@example.com").withId("A0001111G").withTags("Student").build();

    public static final Patron ALICE_MAKER = new PatronBuilder().withName("Alice Maker").withPhone("94354443")
            .withEmail("aliceMaker1221@example.com").withId("A0129090H").withTags("Student").build();

    public static final Patron BENSON_TOH = new PatronBuilder().withName("Benson Toh").withPhone("90120991")
            .withEmail("bensontoh@example.com").withId("A0123400F").withTags("Student").build();

    public static final Patron CARL_KARL = new PatronBuilder().withName("Carl Karl").withPhone("81118222")
            .withEmail("KarlKarl11@example.com").withId("A1234111Y").build();

    public static final Patron DANIEL_NG = new PatronBuilder().withName("Daniel Ng").withPhone("87652533")
            .withEmail("DanielNgWarrior11@example.com").withId("A4343123H").withTags("Student").build();

    public static final Patron MASHMALLOW = new PatronBuilder().withName("Mashmallow").withPhone("80001234")
            .withEmail("mashmallowFire@example.com").withId("A8889991H").build();

    public static final Patron FIONA_RAIN = new PatronBuilder().withName("Fiona Rain").withPhone("99911122")
            .withEmail("FionaRain1143@example.com").withId("A0012345E").build();

    public static final Patron GEORGER_WONG = new PatronBuilder().withName("Georger Wong").withPhone("99911123")
            .withEmail("GeorgerWong99@example.com").withId("A1212126T").build();

    public static final Patron AMY_LOW = new PatronBuilder().withName("Amy Low").withPhone("97876565")
            .withEmail("amylow66@example.com").withId("A1234432R").build();

    public static final Patron ROBBERT_HOOK = new PatronBuilder().withName("Robert Hook").withPhone("98001234")
            .withEmail("robertHook11@example.com").withId("A5454121U").withTags("Student").build();

    public static final Patron JEAN_CHUA = new PatronBuilder().withName("Jean Chua").withPhone("98019022")
            .withEmail("jeanChua11@example.com").withId("A1122334C").withTags("Student").build();

    public static final Patron THOMAS_TAN = new PatronBuilder().withName("Thomas Tan").withPhone("97616467")
            .withEmail("thomastan222@example.com").withId("A9898123Y").withTags("Student").build();

    private ManyPatrons() {} // prevents instantiation

    /**
     * Returns an {@code LibTask} with many Patrons.
     */
    public static LibTask getManyPatronsLibTask() {
        LibTask ab = new LibTask();
        for (Patron patron : getManyPatrons()) {
            ab.addPatron(patron);
        }
        return ab;
    }

    public static List<Patron> getManyPatrons() {
        return new ArrayList<>(Arrays.asList(ALICE_PAULINE, BENSON_MEIER, CARL_KURZ, DANIEL_MEIER, ELLE_MEYER,
                FIONA_KUNZ, GEORGE_BEST, AMY, ROBERT, JEAN, THOMAS, TOMMY_GOH, TOMMY_LIM, ALICE_MAKER, BENSON_TOH,
                CARL_KARL, DANIEL_NG, MASHMALLOW, FIONA_RAIN, GEORGER_WONG, AMY_LOW, ROBBERT_HOOK, JEAN_CHUA,
                THOMAS_TAN));
    }
}
