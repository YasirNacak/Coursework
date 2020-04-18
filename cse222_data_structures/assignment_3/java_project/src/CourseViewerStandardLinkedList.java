import java.util.LinkedList;

/**
 * Course viewer class that uses standard linked list of the java collections
 * to hold courses. Extends the Abstract Course viewer because all course
 * viewing mechanisms share the same principles.
 */
public class CourseViewerStandardLinkedList extends AbstractCourseViewer{

    /**
     * Simple constructor. Creates the list using LinkedList and
     * calls the read from csv method to fill the list.
     * @param courseFileDirectory directory of the courses CSV file
     */
    public CourseViewerStandardLinkedList(String courseFileDirectory){
        listOfCourses = new LinkedList<Course>();
        this.readFromCSV(courseFileDirectory);
    }

    /**
     * Override of the polymorphic method. Describes the type of the course viewer
     * @return description of the course viewer
     */
    @Override
    public String getListType() {
        return "This Course Viewer Uses Standard Linked List(Collections)";
    }
}
