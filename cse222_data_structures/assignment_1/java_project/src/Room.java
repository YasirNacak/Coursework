/**
 * Simple Room class. A hotel can contain an
 * array of this class to have multiple rooms.
 * Has basic fields and methods.
 *
 * @author Yasir
 */
public class Room {
    private int number;
    private boolean isBooked;
    private boolean isChecked;
    private AbstractUser owner;

    Room(int number, boolean isBooked, boolean isChecked, AbstractUser owner){
        this.number = number;
        this.isBooked = isBooked;
        this.isChecked = isChecked;
        this.owner = owner;
    }

    public int getNumber(){
        return number;
    }

    public boolean getBooked(){
        return isBooked;
    }

    public boolean getChecked(){
        return isChecked;
    }

    public AbstractUser getOwner() {
        return owner;
    }

    public void setNumber(int number){
        this.number = number;
    }

    public void setBooked(boolean isBooked){
        this.isBooked = isBooked;
    }

    public void setChecked(boolean isChecked){
        this.isChecked = isChecked;
    }

    public void setOwner(AbstractUser owner){
        this.owner = owner;
    }
}
