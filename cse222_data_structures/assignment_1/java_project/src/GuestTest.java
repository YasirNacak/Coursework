import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class GuestTest {

    /**
     * Test for the welcome message.
     */
    @Test
    public void welcomeUser() {
        Guest g = new Guest(0, "guestTestName", "guestTestMail", "guestTestPassword");
        Assert.assertEquals("Welcome to the Hotel Booking System, guestTestName. Your user type is: Guest.", g.welcomeUser());
    }

    /**
     * When a guest tries to cancel a booking that he/she made.
     * Expected result is a successful cancellation.
     */
    @Test
    public void cancelBookSuccess() {
        Guest g = new Guest(0, "guestTestName", "guestTestMail", "guestTestPassword");
        Room r = new Room(10, true, false, g);
        g.cancelBook(r);
        Assert.assertTrue(!r.getBooked() && r.getOwner() == null);
    }

    /**
     * When a guest tries to cancel a booking but the room is not booked.
     * Expected result is a failed cancellation.
     */
    @Test
    public void cancelBookNotBooked(){
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Guest g = new Guest(0, "guestTestName", "guestTestMail", "guestTestPassword");
        Room r = new Room(10, false, false, null);
        g.cancelBook(r);
        Assert.assertEquals("This room is not booked.\n", outContent.toString());
    }

    /**
     * When a guest tries to cancel a booking but the room is
     * booked by somebody else.
     * Expected result is a failed cancellation.
     */
    @Test
    public void cancelBookOthersRoom(){
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Guest g = new Guest(0, "guestTestName", "guestTestMail", "guestTestPassword");
        Guest g2 = new Guest(1, "guest2TestName", "guest2TestMail", "guest2TestPassword");
        Room r = new Room(10, true, false, g2);
        g.cancelBook(r);
        Assert.assertEquals("You haven't booked this room.\n", outContent.toString());
    }

    /**
     * Only case of check in for guests is getting an error/info message
     * the output stream is compared to the expected message.
     */
    @Test
    public void checkIn() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Guest g = new Guest(0, "guestTestName", "guestTestMail", "guestTestPassword");
        Room r = new Room(10, true, false, g);
        g.checkIn(r);
        Assert.assertEquals("Guests are not allowed to check in for rooms.\n", outContent.toString());
    }

    /**
     * Only case of check out for users is getting and error/info message
     * the output stream is compared to the expected message.
     */
    @Test
    public void checkOut() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Guest g = new Guest(0, "guestTestName", "guestTestMail", "guestTestPassword");
        Room r = new Room(10, true, false, g);
        g.checkOut(r);
        Assert.assertEquals("Guests are not allowed to check out of rooms.\n", outContent.toString());
    }
}