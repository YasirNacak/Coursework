/**
 * Base class to construct a linked list of courses
 * that can hold the next element and next in semester element.
 *
 * @author Yasir
 */
public class CourseNode {
    /**
     * Course information being hold in the node
     */
    private Course data;

    /**
     * The node right after this node
     */
    private CourseNode next;

    /**
     * The node that is after this node and is first to have
     * the semester as this nodes data.
     */
    private CourseNode nextInSemester;

    /**
     * Simple constructor that takes a course and makes the
     * nodes data, that course
     * @param data data of the course that needs to be put in the node
     */
    public CourseNode(Course data){
        this.next = null;
        this.nextInSemester = null;
        this.data = data;
    }

    /**
     * Getter for the data field of the node
     * @return data field of the node
     */
    public Course getData(){
        return this.data;
    }

    /**
     * Setter for the data field of the node
     * @param c data field that needs to set
     */
    public void setData(Course c){
        this.data = c;
    }

    /**
     * Getter for the node that is next and in the same semester
     * @return the node that is next and in the same semester
     */
    public CourseNode getNextInSemester() {
        return nextInSemester;
    }

    /**
     * Setter for the node that is next and in the same semester
     * @param nextInSemesterNode node that needs to be set as the next in semester node
     */
    public void setNextInSemester(CourseNode nextInSemesterNode){
        this.nextInSemester = nextInSemesterNode;
    }

    /**
     * Getter for the next node
     * @return the node right after this one
     */
    public CourseNode getNext() {
        return next;
    }

    /**
     * Setter for the next node
     * @param nextNode the node that needs to be set after this one
     */
    public void setNext(CourseNode nextNode){
        this.next = nextNode;
    }
}
