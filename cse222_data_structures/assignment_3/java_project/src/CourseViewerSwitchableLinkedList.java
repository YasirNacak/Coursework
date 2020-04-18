/**
 * Course viewer class that uses standard switchable linked list class
 * that extends the standard linked list class
 * to hold courses. Extends the Abstract Course viewer because all course
 * viewing mechanisms share the same principles.
 */
public class CourseViewerSwitchableLinkedList extends AbstractCourseViewer{
    /**
     * reference copy of the main linked list in parent class
     * all methods in this class uses this field because
     * it constructs as a switchable linked list.
     */
    private SwitchableLinkedList<Course> sllCopy;

    /**
     * Simple constructor.
     * @param courseFileDirectory directory of the courses CSV file
     */
    public CourseViewerSwitchableLinkedList(String courseFileDirectory){
        this.listOfCourses = new SwitchableLinkedList<Course>();
        this.readFromCSV(courseFileDirectory);
        sllCopy = (SwitchableLinkedList<Course>) this.listOfCourses;
    }

    /**
     * Redirects the disable method to switchable linked list field
     * @param index index of the item to be disabled
     */
    public void disable(int index){
        sllCopy.disable(index);
    }

    /**
     * Redirects the disable method to switchable linked list field
     * @param c item that needs to be disabled
     */
    public void disable(Course c){
        sllCopy.disable(c);
    }

    /**
     * Redirects the enable method to switchable linked list field
     * @param c item that needs to be enabled
     */
    public void enable(Course c){
        sllCopy.enable(c);
    }

    /**
     * Redirects the list disabled method to switchable linked list field
     */
    public void listDisabled(){
        sllCopy.listDisabled();
    }

    /**
     * Override of the polymorphic method. Describes the type of the course viewer
     * @return description of the course viewer
     */
    @Override
    public String getListType() {
        return "This Course Viewer Uses Switchable Linked List(Extension of Standard Linked List)";
    }
}
