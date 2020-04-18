/**
 * Abstract User class. Both Receptionists
 * and Guests will inherit this class.
 * Has all the necessary methods for both of these
 * classes.
 *
 * @author Yasir
 */
public abstract class AbstractUser implements InterfaceUser {
    /**
     * The type of the user (Receptionist or Guest)
     */
    UserType type;

    /**
     * The id of the user, unique for each user.
     */
    int id;

    /**
     * Name of the user, contains name and surname together.
     */
    String name;

    /**
     * E-Mail address of the user.
     */
    String mail;

    /**
     * Log-In password of the user.
     */
    String password;

    /**
     * Getter for userType field.
     * @return type of the user as UserType
     */
    @Override
    public UserType getType() {
        return type;
    }

    /**
     * Getter for id field.
     * @return id of the user as an int
     */
    @Override
    public int getID() {
        return id;
    }

    /**
     * Getter for name field.
     * @return name of the user as a String
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Getter for mail field.
     * @return name of the user as a String
     */
    @Override
    public String getMail() {
        return mail;
    }

    /**
     * Getter for password field.
     * @return password of the user a String
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Setter for userType field.
     * @param type the type that is going to be set
     */
    @Override
    public void setType(UserType type) {
        this.type = type;
    }

    /**
     * Setter for id field.
     * @param id the id as an int that is going to be set
     */
    @Override
    public void setID(int id) {
        this.id = id;
    }

    /**
     * Setter for name field.
     * @param name the name as a String that is going to be set
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Setter for mail field.
     * @param mail the mail as a String that is going to be set
     */
    @Override
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * Setter for password field.
     * @param password the password as a String that is going to be set
     */
    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Utility method. Created to show the polymorphism aspect.
     * @return a string that welcomes the user.
     */
    public abstract String welcomeUser();

    /**
     * Constructor. Takes all fields as parameters.
     * @param type type of the user
     * @param id id of the user
     * @param name name and surname of the user
     * @param mail email address of the user
     * @param password password of the user
     */
    public AbstractUser(UserType type, int id, String name, String mail, String password){
        this.setType(type);
        this.setID(id);
        this.setName(name);
        this.setMail(mail);
        this.setPassword(password);
    }

    /**
     * Method that lets a user too book a room.
     * @param room room to be booked
     * @param booker the user that wanted to room a book
     */
    public void bookRoom(Room room, AbstractUser booker){
        if(room.getBooked() == false && room.getChecked() == false){
            room.setBooked(true);
            room.setOwner(booker);
            System.out.printf("Action successful.\n");
        } else if(room.getBooked() == true){
            System.out.printf("This room is already booked.\n");
        } else if(room.getChecked() == true) {
            System.out.printf("Somebody else already stays in this room.\n");
        }
    }

    /**
     * Polymorphic method that allows a user to cancel a booking.
     * @param room room that is needed to be cancelled from being booked.
     */
    public abstract void cancelBook(Room room);

    /**
     * Polymorphic method that allows a user to check in to a room.
     * @param room room that is needed to be checked in.
     */
    public abstract void checkIn(Room room);

    /**
     * Polymorphic method that allows a user to check out of a room.
     * @param room room that is needed to be checked out.
     */
    public abstract void checkOut(Room room);

    /**
     * Override of the toString method that comes from the
     * Object class. Gives the information about the user.
     * @return a string that contains name, user type and email address of the user.
     */
    @Override
    public String toString() {
        String userTypeString = "";
        if(this.type == UserType.RECEPTIONIST)
            userTypeString = "Receptionist";
        else if(this.type == UserType.GUEST)
            userTypeString = "Guest";
        return "Name: " + name + " User Type: " + userTypeString + " Mail Address: " + mail;
    }
}
