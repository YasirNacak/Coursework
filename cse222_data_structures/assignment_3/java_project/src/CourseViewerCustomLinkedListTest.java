import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * The capabilities of CourseViewer classes are being tested in Main.
 * This just tests the method called getListType.
 */
public class CourseViewerCustomLinkedListTest {

    /**
     * Tests the getListType method that returns a string describing the type of the course viewer.
     */
    @Test
    public void getListType() {
        CourseViewerCustomLinkedList cvcll= new CourseViewerCustomLinkedList("res/Courses.csv");
        Assert.assertEquals(cvcll.getListType(), "This Course Viewer Uses Fully Custom Linked List Suitable For Courses(NextInSemester)");
    }
}