/**
 * Simple interface for all types of users.
 * Includes necessary methods.
 *
 * @author Yasir
 */
public interface InterfaceUser {
    /**
     * Getter for userType field.
     * @return type of the user as UserType
     */
    UserType getType();

    /**
     * Getter for id field.
     * @return id of the user as an int
     */
    int getID();

    /**
     * Getter for name field.
     * @return name of the user as a String
     */
    String getName();

    /**
     * Getter for mail field.
     * @return name of the user as a String
     */
    String getMail();

    /**
     * Getter for password field.
     * @return password of the user a String
     */
    String getPassword();

    /**
     * Setter for userType field.
     * @param type the type that is going to be set
     */
    void setType(UserType type);

    /**
     * Setter for id field.
     * @param id the id as an int that is going to be set
     */
    void setID(int id);

    /**
     * Setter for name field.
     * @param name the name as a String that is going to be set
     */
    void setName(String name);

    /**
     * Setter for mail field.
     * @param mail the mail as a String that is going to be set
     */
    void setMail(String mail);

    /**
     * Setter for password field.
     * @param password the password as a String that is going to be set
     */
    void setPassword(String password);
}
