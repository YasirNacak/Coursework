import java.util.LinkedList;

/**
 * Main test class. Uses all the 3 ways of viewing
 * courses and the data structures behind them
 * in random scenarios. Trying to utilize all the
 * methods and proves that the data structures work
 * correctly.
 *
 * @author Yasir
 */
public class Main {
    public static void main(String[] args){
        System.out.println("\n-----------------------------------------------------------------------------------------------------------------------------");
        System.out.println("NOW TESTING PART I");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------");

        CourseViewerStandardLinkedList courseViewLinkedList = new CourseViewerStandardLinkedList("res/Courses.csv");
        int semesterControl = 1;
        int startIndexControl = 13, endIndexControl = 23;
        String codeControl = "XXX XXX";

        System.out.printf("\nPrinting courses in the semester %d:\n", semesterControl);
        LinkedList<Course> testSemesterCourses = courseViewLinkedList.listSemesterCourses(semesterControl);
        for (Course testSemesterCourse : testSemesterCourses) {
            System.out.println(testSemesterCourse);
        }

        System.out.printf("\nPrinting courses with %s code:\n", codeControl);
        LinkedList<Course> testCoursesWithCode = courseViewLinkedList.getByCode(codeControl);
        for (Course aTestCoursesWithCode : testCoursesWithCode) {
            System.out.println(aTestCoursesWithCode);
        }

        System.out.printf("\nPrinting courses in range of %d-%d:\n", startIndexControl, endIndexControl);
        LinkedList<Course> testCoursesInRange = courseViewLinkedList.getByRange(startIndexControl, endIndexControl);
        for (Course aTestCoursesInRange : testCoursesInRange) {
            System.out.println(aTestCoursesInRange);
        }

        System.out.println("\n-----------------------------------------------------------------------------------------------------------------------------");
        System.out.println("NOW TESTING PART II");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------\n");
        CourseViewerSwitchableLinkedList courseViewEnableDisable = new CourseViewerSwitchableLinkedList("res/Courses.csv");

        courseViewEnableDisable.disable(0);
        courseViewEnableDisable.disable(3);
        courseViewEnableDisable.listDisabled();

        System.out.printf("\nPrinting courses in the semester %d:\n", semesterControl);
        testSemesterCourses = courseViewEnableDisable.listSemesterCourses(semesterControl);
        for (Course testSemesterCourse : testSemesterCourses) {
            System.out.println(testSemesterCourse);
        }
        System.out.println("\nRe-enabling physics I.\n");

        courseViewEnableDisable.enable(new Course(1,"PHYS 121", "Physics I", 6, 4, "3+0+0"));
        courseViewEnableDisable.listDisabled();
        System.out.printf("\nPrinting courses in the semester %d:\n", semesterControl);
        testSemesterCourses = courseViewEnableDisable.listSemesterCourses(semesterControl);
        for (Course testSemesterCourse : testSemesterCourses) {
            System.out.println(testSemesterCourse);
        }

        System.out.println("\n-----------------------------------------------------------------------------------------------------------------------------");
        System.out.println("NOW TESTING PART III");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------\n");

        CourseViewerCustomLinkedList courseViewNextInSemester = new CourseViewerCustomLinkedList("res/Courses.csv");
        System.out.printf("Printing courses that are in semester %d using NextSemester() method:\n", semesterControl);
        courseViewNextInSemester.listOfCourses.printListUsingNextSemester(semesterControl);
        courseViewNextInSemester.listOfCourses.remove(2);
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------");
        System.out.println("Removing an element and printing the courses that are in same semester as before using NextSemester() method:");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------");
        courseViewNextInSemester.listOfCourses.printListUsingNextSemester(semesterControl);
    }
}
