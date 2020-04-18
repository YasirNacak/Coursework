/**
 * Main test class. Uses the 3 parameter version of the Hotel constructor
 * that allows user input to be simulated from a file. Tests the full flow
 * and the usage of the hotel management system. Books rooms, cancels bookings,
 * logs in and out, checks in as a receptionist, tries to checkout as a guest and fails etc.
 */
public class MainTest {
    public static void main(String[] args) {

        //THE LINES BELOW ARE FOR FILE SIMULATED USER INPUT
        Hotel myHotel = new Hotel("res/roominfo.csv", "res/userinfo.csv", "res/testInputStream.txt");
        myHotel.start();

        //THE LINES BELOW ARE FOR REAL USER INPUT

        /*
        Hotel myOtherHotel = new Hotel("res/roominfo.csv", "res/userinfo.csv");
        myOtherHotel.start();
         */
    }
}
