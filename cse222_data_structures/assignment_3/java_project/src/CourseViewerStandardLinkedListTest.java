import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * The capabilities of CourseViewer classes are being tested in Main.
 * This just tests the polymorphic method called getListType.
 */
public class CourseViewerStandardLinkedListTest {

    /**
     * Tests the getListType method that returns a string describing the type of the course viewer.
     */
    @Test
    public void getListType() {
        CourseViewerStandardLinkedList cvsll = new CourseViewerStandardLinkedList("res/Courses.csv");
        Assert.assertEquals(cvsll.getListType(), "This Course Viewer Uses Standard Linked List(Collections)");
    }
}