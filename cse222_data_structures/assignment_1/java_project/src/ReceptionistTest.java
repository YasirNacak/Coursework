import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ReceptionistTest {

    /**
     * Test for the welcome message.
     */
    @Test
    public void welcomeUser() {
        Receptionist r = new Receptionist(0, "receptionistTestName", "receptionistTestMail", "receptionistTestPassword");
        Assert.assertEquals("Welcome to the Hotel Booking System, receptionistTestName. Your user type is: Receptionist.", r.welcomeUser());
    }

    /**
     * When a receptionist tries to cancel a booked room.
     * Expected result is a successful cancellation.
     */
    @Test
    public void cancelBookSuccess() {
        Receptionist r = new Receptionist(0, "receptionistTestName", "receptionistTestMail", "receptionistTestPassword");
        Guest g = new Guest(0, "guestTestName", "guestTestMail", "guestTestPassword");
        Room rm = new Room(10, true, false, g);
        r.cancelBook(rm);
        Assert.assertTrue(rm.getOwner() == null && !rm.getBooked());
    }

    /**
     * When a receptionist tries to cancel a non-booked room.
     * Expected result is a failed cancellation.
     */
    @Test
    public void cancelBookNotBooked(){
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Receptionist r = new Receptionist(0, "receptionistTestName", "receptionistTestMail", "receptionistTestPassword");
        Room rm = new Room(10, false, false, null);
        r.cancelBook(rm);
        Assert.assertEquals("This room is not booked.\n", outContent.toString());
    }

    /**
     * When a receptionist tries to check in for a booked room.
     * Expected result is a successful check in.
     */
    @Test
    public void checkInSuccess() {
        Receptionist r = new Receptionist(0, "receptionistTestName", "receptionistTestMail", "receptionistTestPassword");
        Guest g = new Guest(0, "guestTestName", "guestTestMail", "guestTestPassword");
        Room rm = new Room(10, true, false, g);
        r.checkIn(rm);
        Assert.assertTrue(rm.getChecked() && rm.getOwner() == g);
    }

    /**
     * When a receptionist tries to check in for a room that is not booked.
     * Expected result is a failed check in.
     */
    @Test
    public void checkInNotBooked(){
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Receptionist r = new Receptionist(0, "receptionistTestName", "receptionistTestMail", "receptionistTestPassword");
        Room rm = new Room(10, false, false, null);
        r.checkIn(rm);
        Assert.assertEquals("You can not check in for a room that is not booked.\n", outContent.toString());
    }

    /**
     * When a receptionist tries to check in for a room that is already checked.
     * Expected result is a failed check in.
     */
    @Test
    public void checkInAlreadyChecked(){
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Receptionist r = new Receptionist(0, "receptionistTestName", "receptionistTestMail", "receptionistTestPassword");
        Guest g = new Guest(0, "guestTestName", "guestTestMail", "guestTestPassword");
        Room rm = new Room(10, false, true, g);
        r.checkIn(rm);
        Assert.assertEquals("This room is already checked in.\n", outContent.toString());
    }

    /**
     * When a receptionist tries to check out for a checked in room.
     * Expected result is successful check out.
     */
    @Test
    public void checkOutSuccess() {
        Receptionist r = new Receptionist(0, "receptionistTestName", "receptionistTestMail", "receptionistTestPassword");
        Guest g = new Guest(0, "guestTestName", "guestTestMail", "guestTestPassword");
        Room rm = new Room(10, false, true, g);
        r.checkOut(rm);
        Assert.assertTrue(rm.getOwner() == null && !rm.getChecked());
    }

    /**
     * When a receptionist tries to check out for a not checked in room.
     * Expected result is a failed check out.
     */
    @Test
    public void checkOutNotCheckedIn(){
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Receptionist r = new Receptionist(0, "receptionistTestName", "receptionistTestMail", "receptionistTestPassword");
        Guest g = new Guest(0, "guestTestName", "guestTestMail", "guestTestPassword");
        Room rm = new Room(10, true, false, g);
        r.checkOut(rm);
        Assert.assertEquals("You can not check out a room that is not checked in.\n", outContent.toString());
    }
}