import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test Class of the CourseNode class.
 * This class is the main structure behind
 * the custom course list.
 * Tests getters and setters, usually creating
 * a 2-3 element lists out of CourseNodes.
 *
 * @author Yasir
 */
public class CourseNodeTest {

    /**
     * Testing if the getter is working correctly.
     * Compares a CourseNode that holds a course with
     * an instance of Course class. Since the courses
     * are the same, expected value is true.
     */
    @Test
    public void getDataTrue() {
        Course testCourse = new Course(1, "BIL 101", "Introduction to Computer Engineering", 8, 3, "3+0+0");
        Course testCourse2 = new Course(1, "BIL 101", "Introduction to Computer Engineering", 8, 3, "3+0+0");
        CourseNode nodeTest = new CourseNode(testCourse);
        Assert.assertTrue(nodeTest.getData().equals(testCourse2));
    }

    /**
     * Testing if the getter is working correctly.
     * Compares a CourseNode that holds a course with
     * an instance of Course class. Since the courses
     * are different, expected value is false.
     */
    @Test
    public void getDataFalse(){
        Course testCourse = new Course(1, "BIL 101", "Introduction to Computer Engineering", 8, 3, "3+0+0");
        Course testCourse2 = new Course(1, "BIL 107", "Introduction to Computer Science Laboratory", 2, 1, "0+0+2");
        CourseNode nodeTest = new CourseNode(testCourse);
        Assert.assertFalse(nodeTest.getData().equals(testCourse2));
    }

    /**
     * Test method of the setData method.
     * Creates 3 nodes and sets the first one X
     * and the other two Y. After that, sets
     * the data of the second to X and compares 3
     * nodes. Expecting first and second to be the same
     * and second and third to be different.
     */
    @Test
    public void setData() {
        Course testCourse = new Course(1, "BIL 101", "Introduction to Computer Engineering", 8, 3, "3+0+0");
        Course testCourse2 = new Course(1, "BIL 107", "Introduction to Computer Science Laboratory", 2, 1, "0+0+2");
        CourseNode testNode = new CourseNode(testCourse);
        CourseNode testNode2 = new CourseNode(testCourse2);
        CourseNode testNode3 = new CourseNode(testCourse2);

        testNode2.setData(testCourse);

        Assert.assertTrue(testNode.getData().equals(testNode2.getData()) && !(testNode.getData().equals(testNode3.getData())));
    }

    /**
     * Creates two nodes with different Courses in them.
     * Sets ones nextInSemester to be the other and
     * controls it using the getNextInSemester method.
     */
    @Test
    public void getNextInSemester() {
        Course testCourse = new Course(1, "BIL 101", "Introduction to Computer Engineering", 8, 3, "3+0+0");
        Course testCourse2 = new Course(1, "BIL 107", "Introduction to Computer Science Laboratory", 2, 1, "0+0+2");
        CourseNode testNode = new CourseNode(testCourse);
        CourseNode testNode2 = new CourseNode(testCourse2);
        testNode.setNextInSemester(testNode2);
        Assert.assertTrue(testNode.getNextInSemester().getData().equals(testNode2.getData()));
    }

    /**
     * Creates two nodes with different Courses in them.
     * Sets ones nextInSemester to be the other using and
     * setNextInSemester method controls it using the getter.
     */
    @Test
    public void setNextInSemester() {
        Course testCourse = new Course(1, "BIL 101", "Introduction to Computer Engineering", 8, 3, "3+0+0");
        Course testCourse2 = new Course(1, "BIL 107", "Introduction to Computer Science Laboratory", 2, 1, "0+0+2");
        CourseNode testNode = new CourseNode(testCourse);
        CourseNode testNode2 = new CourseNode(testCourse2);
        testNode.setNextInSemester(testNode2);
        Assert.assertTrue(testNode.getNextInSemester().getData().equals(testNode2.getData()));
    }

    /**
     * Creates two nodes and sets ones next to be
     * the other and controls it using getNext method.
     */
    @Test
    public void getNext() {
        Course testCourse = new Course(1, "BIL 101", "Introduction to Computer Engineering", 8, 3, "3+0+0");
        Course testCourse2 = new Course(1, "BIL 107", "Introduction to Computer Science Laboratory", 2, 1, "0+0+2");
        CourseNode testNode = new CourseNode(testCourse);
        CourseNode testNode2 = new CourseNode(testCourse2);
        testNode.setNext(testNode2);
        Assert.assertTrue(testNode.getNext().getData().equals(testNode2.getData()));
    }

    /**
     * Creates two nodes and sets ones next to be
     * the other using setNext and controls it using the getter.
     */
    @Test
    public void setNext() {
        Course testCourse = new Course(1, "BIL 101", "Introduction to Computer Engineering", 8, 3, "3+0+0");
        Course testCourse2 = new Course(1, "BIL 107", "Introduction to Computer Science Laboratory", 2, 1, "0+0+2");
        CourseNode testNode = new CourseNode(testCourse);
        CourseNode testNode2 = new CourseNode(testCourse2);
        testNode.setNext(testNode2);
        Assert.assertTrue(testNode.getNext().getData().equals(testNode2.getData()));
    }
}