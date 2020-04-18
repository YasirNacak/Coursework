/**
 * The guest class. Overrides methods
 * from AbstractUser class. Does not
 * allow a guest to do check ins and check outs.
 * Only allows bookings and cancelling the books
 * that this user has made.
 * @author Yasir
 */
public class Guest extends AbstractUser {
    /**
     * Simple constructor. Takes all fields as parameters and
     * calls the constructor of the parent class.
     * @param id id of the user
     * @param name name and surname of the user
     * @param mail email address of the user
     * @param password password of the user
     */
    public Guest(int id, String name, String mail, String password) {
        super(UserType.GUEST, id, name, mail, password);
    }

    /**
     * Utility method. Created to show the polymorphism aspect.
     * @return a string that welcomes the user.
     */
    @Override
    public String welcomeUser() {
        return "Welcome to the Hotel Booking System, " + this.getName() + ". Your user type is: Guest.";
    }

    /**
     * Polymorphic method that allows a user to cancel a booking.
     * @param room room that is needed to be cancelled from being booked.
     */
    @Override
    public void cancelBook(Room room) {
        if(room.getBooked() == true && room.getOwner().getID() == this.getID()){
            room.setBooked(false);
            room.setOwner(null);
            System.out.printf("Action successful.\n");
        } else if(room.getBooked() == false){
            System.out.printf("This room is not booked.\n");
        } else if(room.getOwner().getID() != this.getID()){
            System.out.printf("You haven't booked this room.\n");
        }
    }

    /**
     * Polymorphic method that allows a user to check in to a room
     * Gives an information message for this class because guests are
     * not allowed to do check ins.
     * @param room room that is needed to be checked in.
     */
    @Override
    public void checkIn(Room room) {
        System.out.printf("Guests are not allowed to check in for rooms.\n");
    }

    /**
     * Polymorphic method that allows a user to check out of a room
     * Gives an information message for this class because guests are
     * not allowed to do check outs.
     * @param room room that is needed to be checked out.
     */
    @Override
    public void checkOut(Room room) {
        System.out.printf("Guests are not allowed to check out of rooms.\n");
    }
}
