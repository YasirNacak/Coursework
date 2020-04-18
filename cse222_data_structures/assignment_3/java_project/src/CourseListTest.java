import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test Class of the CourseList class.
 * CourseList is a custom linked list that
 * is tuned for holding Courses in them
 * and one link is for the next element in
 * the list and the other link is for the
 * next element that is in the same semester as the
 * current element.
 *
 * @author Yasir
 */
public class CourseListTest {
    /**
     * These fields will be used to test different instances of the list.
     * To avoid code duplication, these are defined as fields of the test class.
     */
    private Course testCourse = new Course(1, "BIL 101", "Introduction to Computer Engineering", 8, 3, "3+0+0");
    private Course testCourse2 = new Course(1, "BIL 107", "Introduction to Computer Science Laboratory", 2, 1, "0+0+2");
    private Course testCourse3 = new Course(2, "PHYS 152", "Physics Laboratory II", 1, 1, "0+0+2");
    private Course testCourse4 = new Course(2, "CSE 102", "Computer Programming", 8, 4, "4+0+0");
    private Course testCourse5 = new Course(3, "CSE 211", "Discrete Mathematics", 6, 3, "3+0+0");
    private Course testCourse6 = new Course(3, "CSE 231", "Circuits and Electronics", 8, 4, "4+0+0");

    /**
     * Tries to add 3 elements and compares the return value of size method with integer 3.
     */
    @Test
    public void size() {
        CourseList testList = new CourseList();

        testList.add(testCourse);
        testList.add(testCourse2);
        testList.add(testCourse3);

        Assert.assertEquals(testList.size(), 3);
    }

    /**
     * Tries to add an element to the list and controls the size and the first element of the list
     * and makes sure that the size is 1 and the first element is the added one.
     */
    @Test
    public void add() {
        CourseList testList = new CourseList();

        testList.add(testCourse);

        Assert.assertTrue(testList.size() == 1 && testList.get(0).equals(testCourse));
    }

    /**
     * Tries to get an element with its index and compares it with the element added at that index.
     */
    @Test
    public void get() {
        CourseList testList = new CourseList();

        testList.add(testCourse);
        testList.add(testCourse2);
        testList.add(testCourse3);

        Assert.assertTrue(testList.get(1).equals(testCourse2));
    }

    /**
     * Tries to move the current element of the list to its next value and compares the resulting value
     * with the element that is added after first element.
     */
    @Test
    public void next() {
        CourseList testList = new CourseList();

        testList.add(testCourse);
        testList.add(testCourse2);
        testList.add(testCourse3);

        Assert.assertTrue(testList.next().equals(testCourse2));
    }

    /**
     * Tries to move the current element of the list to its nextInSemester value and compares the resulting
     * value with the actual element that is in the same semester and next.
     */
    @Test
    public void nextInSemester() {
        CourseList testList = new CourseList();

        testList.add(testCourse);
        testList.add(testCourse2);
        testList.add(testCourse3);
        testList.add(testCourse4);

        testList.next();
        testList.next();

        Assert.assertTrue(testList.nextInSemester().equals(testCourse4));
    }

    /**
     * Tries to remove 3 elements and compares first index with the expected element.
     */
    @Test
    public void remove() {
        CourseList testList = new CourseList();

        testList.add(testCourse);
        testList.add(testCourse2);
        testList.add(testCourse3);
        testList.add(testCourse4);
        testList.add(testCourse5);
        testList.add(testCourse6);

        testList.remove(0);
        testList.remove(2);
        testList.remove(3);

        Assert.assertTrue(testList.get(0).equals(testCourse2));
    }
}