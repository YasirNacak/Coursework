/**
 * The receptionist class. Overrides methods
 * from AbstractUser class. Allows the receptionist
 * to do all the activities that are doable in this
 * hotel management system.
 * @author Yasir
 */
public class Receptionist extends AbstractUser {
    /**
     * Simple constructor. Takes all fields as parameters and
     * calls the constructor of the parent class.
     * @param id id of the user
     * @param name name and surname of the user
     * @param mail email address of the user
     * @param password password of the user
     */
    public Receptionist(int id, String name, String mail, String password) {
        super(UserType.RECEPTIONIST, id, name, mail, password);
    }

    /**
     * Utility method. Created to show the polymorphism aspect.
     * @return a string that welcomes the user.
     */
    @Override
    public String welcomeUser() {
        return "Welcome to the Hotel Booking System, " + this.getName() + ". Your user type is: Receptionist.";
    }

    /**
     * Polymorphic method that allows a user to cancel a booking.
     * @param room room that is needed to be cancelled from being booked.
     */
    @Override
    public void cancelBook(Room room) {
        if(room.getBooked() == true){
            room.setBooked(false);
            room.setOwner(null);
            System.out.printf("Action successful.\n");
        } else if(room.getBooked() == false){
            System.out.printf("This room is not booked.\n");
        }
    }

    /**
     * Polymorphic method that allows a user to check in to a room.
     * @param room room that is needed to be checked in.
     */
    @Override
    public void checkIn(Room room) {
        if(room.getBooked() == true && room.getChecked() == false){
            room.setChecked(true);
            room.setBooked(false);
            System.out.printf("Action successful.\n");
        } else if(room.getBooked() == false && room.getChecked() == false){
            System.out.printf("You can not check in for a room that is not booked.\n");
        } else if(room.getChecked() == true){
            System.out.printf("This room is already checked in.\n");
        }
    }

    /**
     * Polymorphic method that allows a user to check out of a room.
     * @param room room that is needed to be checked out.
     */
    @Override
    public void checkOut(Room room) {
        if(room.getBooked() == false && room.getChecked() == true){
            room.setBooked(false);
            room.setChecked(false);
            room.setOwner(null);
            System.out.printf("Action successful.\n");
        } else if(room.getChecked() == false){
            System.out.printf("You can not check out a room that is not checked in.\n");
        }
    }
}
