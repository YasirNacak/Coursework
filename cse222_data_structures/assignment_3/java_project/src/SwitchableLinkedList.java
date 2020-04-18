import java.util.LinkedList;

/**
 * Custom linked list that is capable of having items
 * disabled or enabled. Extends the linked list of
 * java collections.
 * @param <E> data type that the list holds
 *
 * @author Yasir
 */
public class SwitchableLinkedList<E> extends LinkedList<E>{
    /**
     * List of the disabled elements
     */
    private LinkedList<E> disabledList;

    /**
     * Indices of the elements before they were disabled
     */
    private LinkedList<Integer> disabledIndices;

    /**
     * Simple constructor that calls the default constructors of its fields.
     */
    public SwitchableLinkedList() {
        this.disabledList = new LinkedList<E>();
        this.disabledIndices = new LinkedList<Integer>();
    }

    /**
     * Disables an item. Does it by taking the element, adding it
     * to the disabled items array and removing it from the current linked list.
     * Also stores the previous index of the disabled element.
     * @param index index of the item that needs to be disabled
     */
    public void disable(int index){
        if(index >= 0 && index < this.size()) {
            disabledList.add(super.get(index));
            disabledIndices.add(index);
            this.remove(index);
        } else {
            System.out.printf("ERROR: Invalid Index For Removal.\n");
        }
    }

    /**
     * Disables an item. Does it by taking the element, adding it
     * to the disabled items array and removing it from the current linked list.
     * Also stores the previous index of the disabled element.
     * @param obj instance of the item that needs to be disabled
     */
    public void disable(E obj){
        boolean isObjInList = this.contains(obj);
        if(isObjInList) {
            for (int i = 0; i <this.size(); i++){
                if(this.get(i).equals(obj)){
                    disabledList.add(this.get(i));
                    disabledIndices.add(i);
                    this.remove(i);
                    break;
                }
            }
        } else {
            System.out.printf("ERROR: Item Not Found.\n");
        }
    }

    /**
     * Takes the given object, looks for it in the list of disabled items
     * and if the item can be found, adds it back to the list
     * @param obj the object that needs to enabled
     */
    public void enable(E obj){
        for(int i=0; i<disabledList.size(); i++){
            if(obj.equals(disabledList.get(i))){
                this.add(disabledIndices.get(i), disabledList.get(i));
                disabledIndices.remove(i);
                disabledList.remove(i);
            }
        }
    }

    /**
     * Prints out the disabled items.
     */
    public void listDisabled(){
        if(disabledList.size() > 0) {
            System.out.printf("Disabled Item(s):\n");
            for(int i=0; i<disabledList.size(); i++){
                System.out.printf("%s\n", disabledList.get(i).toString());
            }
        } else {
            System.out.printf("ERROR: No Items Are Disabled.\n");
        }
    }

    /**
     * Prints out all the items.
     */
    public void printElements(){
        System.out.printf("Printing Elements In Order:\n");
        for(int i=0; i<this.size(); i++) {
            System.out.printf("%s ", this.get(i).toString());
        }
        System.out.printf("\n");
    }
}
