import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * The capabilities of CourseViewer classes are being tested in Main.
 * This just tests the polymorphic method called getListType.
 */
public class CourseViewerSwitchableLinkedListTest {

    /**
     * Tests the getListType method that returns a string describing the type of the course viewer.
     */
    @Test
    public void getListType() {
        CourseViewerSwitchableLinkedList cvsll = new CourseViewerSwitchableLinkedList("res/Courses.csv");
        Assert.assertEquals(cvsll.getListType(), "This Course Viewer Uses Switchable Linked List(Extension of Standard Linked List)");
    }
}