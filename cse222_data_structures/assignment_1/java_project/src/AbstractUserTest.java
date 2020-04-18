import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class AbstractUserTest {

    public class AbstractUserTestExtend extends AbstractUser{
        public AbstractUserTestExtend(UserType type, int id, String name, String mail, String password) {
            super(type, id, name, mail, password);
        }

        @Override
        public void cancelBook(Room room) {
            // this overrides are not necessary since the guest and receptionists
            // classes already override these and they have their unit tests.
        }

        @Override
        public void checkIn(Room room) {
            // this overrides are not necessary since the guest and receptionists
            // classes already override these and they have their unit tests.
        }

        @Override
        public void checkOut(Room room) {
            // this overrides are not necessary since the guest and receptionists
            // classes already override these and they have their unit tests.
        }

        @Override
        public String welcomeUser() {
            // this overrides are not necessary since the guest and receptionists
            // classes already override these and they have their unit tests.
            return null;
        }
    }

    /**
     * When an AbstractUser (Guest or Receptionist) tries to book an unbooked room.
     * Expected result is a successful booking.
     */
    @Test
    public void bookRoomSuccess() {
        AbstractUserTestExtend auRec = new AbstractUserTestExtend(UserType.RECEPTIONIST, 0, "aUserTestName",
                "aUserTestMail", "aUserTestPassword");
        AbstractUserTestExtend auGue = new AbstractUserTestExtend(UserType.GUEST, 1, "aUserTestName2",
                "aUserTestMail2", "aUserTestPassword2");
        Room r = new Room(100, false, false, null);
        auRec.bookRoom(r, auGue);
        Assert.assertTrue(r.getBooked() && r.getOwner() == auGue);
    }

    /**
     * When an AbstractUser (Guest or Receptionist) tries to book an already booked room.
     * Expected result is a failed booking.
     */
    @Test
    public void bookRoomAlreadyBooked(){
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        AbstractUserTestExtend auRec = new AbstractUserTestExtend(UserType.RECEPTIONIST, 0, "aUserTestName",
                "aUserTestMail", "aUserTestPassword");
        AbstractUserTestExtend auGue = new AbstractUserTestExtend(UserType.GUEST, 1, "aUserTestName2",
                "aUserTestMail2", "aUserTestPassword2");
        AbstractUserTestExtend auGue2 = new AbstractUserTestExtend(UserType.GUEST, 2, "aUserTestName3",
                "aUserTestMail3", "aUserTestPassword3");
        Room r = new Room(100, true, false, auGue2);
        auRec.bookRoom(r, auGue);
        Assert.assertEquals("This room is already booked.\n", outContent.toString());
    }

    /**
     * When an AbstractUser (Guest or Receptionist) tries to book a room that is checked in for somebody else.
     * Expected result is a failed booking.
     */
    @Test
    public void bookRoomSomebodyElseRoom(){
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        AbstractUserTestExtend auRec = new AbstractUserTestExtend(UserType.RECEPTIONIST, 0, "aUserTestName",
                "aUserTestMail", "aUserTestPassword");
        AbstractUserTestExtend auGue = new AbstractUserTestExtend(UserType.GUEST, 1, "aUserTestName2",
                "aUserTestMail2", "aUserTestPassword2");
        AbstractUserTestExtend auGue2 = new AbstractUserTestExtend(UserType.GUEST, 2, "aUserTestName3",
                "aUserTestMail3", "aUserTestPassword3");
        Room r = new Room(100, false, true, auGue2);
        auRec.bookRoom(r, auGue);
        Assert.assertEquals("Somebody else already stays in this room.\n", outContent.toString());
    }

    /**
     * Creates an abstract user, tests its toString method.
     */
    @Test
    public void toStringTest() {
        AbstractUserTestExtend auRec = new AbstractUserTestExtend(UserType.RECEPTIONIST, 0, "aUserTestName", "aUserTestMail", "aUserTestPassword");
        Assert.assertEquals("Name: aUserTestName User Type: Receptionist Mail Address: aUserTestMail", auRec.toString());
    }
}