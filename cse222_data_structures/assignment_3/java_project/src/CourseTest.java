import org.junit.Assert;
import org.junit.jupiter.api.Test;

/**
 * Test Class of the Course class.
 * Does not test getters and setters.
 * Controls toString and 2 outcomes of equals.
 *
 * @author Yasir
 */
public class CourseTest {

    /**
     * toString method test of the Course class.
     * Compares the return value with expected value using assert.
     */
    @Test
    public void toStringTest() {
        Course testCourse = new Course(1, "BIL 101", "Introduction to Computer Engineering", 8, 3, "3+0+0");
        Assert.assertEquals(testCourse.toString(), "Semester: 1. Course Code and Title: BIL 101 - Introduction to Computer Engineering. ECTS and GTU Credits: 8, 3. H+T+L: 3+0+0.");
    }

    /**
     * equals method test of the Course class.
     * Compares two identical courses and compares
     * the result with "true" using assert.
     */
    @Test
    public void equalsTestTrue() {
        Course testCourse = new Course(1, "BIL 101", "Introduction to Computer Engineering", 8, 3, "3+0+0");
        Course testCourse2 = new Course(1, "BIL 101", "Introduction to Computer Engineering", 8, 3, "3+0+0");
        Assert.assertTrue(testCourse.equals(testCourse2));
    }

    /**
     * equals method test of the Course class.
     * Compares two different courses and compares
     * the result with "false" using assert.
     */
    @Test
    public void equalsTestFalse(){
        Course testCourse = new Course(1, "BIL 101", "Introduction to Computer Engineering", 8, 3, "3+0+0");
        Course testCourse2 = new Course(1, "BIL 107", "Introduction to Computer Science Laboratory", 2, 1, "0+0+2");
        Assert.assertFalse(testCourse.equals(testCourse2));

    }
}