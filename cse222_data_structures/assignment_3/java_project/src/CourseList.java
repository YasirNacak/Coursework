/**
 * Custom linked list that is capable
 * of holding a course, next in the list of
 * that course and the next course that is
 * in the same semester as that course.
 *
 * @author Yasir
 */
public class CourseList {
    /**
     * Starting element of the list
     */
    private CourseNode head;

    /**
     * Current element when iterating using
     * next and nextInSemester methods
     */
    private CourseNode currentNode;

    /**
     * Number of elements in the list
     */
    private int size;

    /**
     * Describes if the first call of iteration is being made
     */
    private boolean firstCall = true;

    /**
     * Simple constructor, sets the start as null, current
     * node as the starting node and the size as 0
     */
    public CourseList(){
        head = null;
        currentNode = head;
        this.size = 0;
    }

    /**
     * Getter for the size field
     * @return number of elements in the list
     */
    public int size(){
        return this.size;
    }

    /**
     * Adds a course at the end of the list. Finds
     * its nextInSemester value and a value that is before this
     * element that has the same semester as this, constructs the links
     * between those.
     * @param crs course to be added
     */
    public void add(Course crs){
        if(head == null){
            head = new CourseNode(crs);
            size++;
            return;
        }
        CourseNode tmp = new CourseNode(crs);
        CourseNode current = head;
        while (current.getNext() != null)
            current = current.getNext();
        current.setNext(tmp);
        this.size++;

        current = head;
        int lastSameSemesterPosition = 0;
        int iterations = 0;
        while(current.getNext() != null){
            if(current.getData().getSemester() == tmp.getData().getSemester()){
                lastSameSemesterPosition = iterations;
            }
            current = current.getNext();
            iterations++;
        }
        current = head;
        iterations = lastSameSemesterPosition;
        while(iterations > 0){
            current = current.getNext();
            iterations--;
        }
        current.setNextInSemester(tmp);

        if(size == 1)
            tmp.setNextInSemester(tmp);
        if(head.getNext().getData().getSemester() == head.getData().getSemester())
            head.setNextInSemester(head.getNext());

        current = head;
        boolean hasFirstInSameSemesterNotFound = true;
        while(hasFirstInSameSemesterNotFound){
            if(current.getData().getSemester() == tmp.getData().getSemester()){
                hasFirstInSameSemesterNotFound = false;
            } else {
                current = current.getNext();
            }
        }
        tmp.setNextInSemester(current);
    }

    /**
     * getter for an element of the list based on index values
     * @param index which index in the list is trying to be get
     * @return the element that is at the given list
     */
    public Course get(int index){
        if (index < 0 || index >= size)
            return null;
        CourseNode current = head;
        for (int i=0; i<index; i++)
            current = current.getNext();
        return current.getData();
    }

    /**
     * pushes the currentNode by one index and and returns
     * the value that is at the currentNode
     * @return the value of the node after the currentNode
     */
    public Course next(){
        // small bugfix with nullptr references
        if(firstCall){
            this.currentNode = this.head;
            firstCall = false;
        }
        currentNode = currentNode.getNext();
        return currentNode.getData();
    }

    /**
     * pushes the currentNode to the element that is in the same
     * semester as the currentNode and first after it.
     * @return the value of the node after the currentNode that is in the same semester.
     */
    public Course nextInSemester(){
        // small bugfix with nullptr references
        if(firstCall){
            this.currentNode = this.head;
            firstCall = false;
        }
        currentNode = currentNode.getNextInSemester();
        return currentNode.getData();
    }

    /**
     * Removes and element in the list based on the index value.
     * Fixes the links before and after this element so the flow of
     * the list stays the same.
     * @param index which index needs to be removed
     */
    public void remove(int index){
        if (index < 0 || index >= this.size)
            return;
        CourseNode current = head;
        if (head != null){
            for(int i=0; i<index; i++)
                current = current.getNext();
            CourseNode nodeToBeDeleted = current;
            current = head;
            CourseNode prevInSemesterOfToBeDeleted = null;
            for(int i=0; i<size; i++){
                if(current.getNextInSemester().getData().equals(nodeToBeDeleted.getData())){
                    prevInSemesterOfToBeDeleted = current;
                    break;
                }
                else {
                    current = current.getNext();
                }
            }
            if(prevInSemesterOfToBeDeleted.getNextInSemester() != null && nodeToBeDeleted.getNextInSemester() != null)
                prevInSemesterOfToBeDeleted.setNextInSemester(nodeToBeDeleted.getNextInSemester());
            current = head;
            if(index != 0) {
                if(index != size-1) {
                    for (int i = 0; i < index-1; i++) {
                        if (current.getNext() == null)
                            return;
                        current = current.getNext();
                    }
                    current.setNext(current.getNext().getNext());
                } else {
                    while (current.getNext().getNext() != null){
                        current = current.getNext();
                    }
                    current.setNext(null);
                }
            } else {
                head = head.getNext();
            }
            size--;
        }
    }

    /**
     * Prints all elements in the linked list starting
     * from the first and ending at size-1
     */
    public void printListInOrder(){
        System.out.println("Printing List:");
        CourseNode current = head;
        for(int i=0; i<size; i++){
            System.out.println(current.getData());
            current = current.getNext();
        }
        System.out.println();
    }

    /**
     * Prints all elements that are in the given semester using
     * the nextSemester method
     * @param semester the semester of the courses that needs to be printed
     */
    public void printListUsingNextSemester(int semester){
        CourseNode current = head;
        while(current.getData().getSemester() != semester){
            current = current.getNext();
        }
        CourseNode startingNode = current;
        current = startingNode.getNextInSemester();
        System.out.println(startingNode.getData());
        while(current.getData() != startingNode.getData()){
            System.out.println(current.getData());
            current = current.getNextInSemester();
        }
    }
}