import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Course viewer class that is tuned for the custom linked
 * list we created that is capable of holding the next element
 * and next in semester element
 *
 * @author Yasir
 */
public class CourseViewerCustomLinkedList {
    /**
     * CourseList of the elements
     */
    public CourseList listOfCourses;

    /**
     * Simple constructor that creates the linked list
     * and adds the elements in the given file
     * @param courseFileDirectory directory of the courses CSV file
     */
    public CourseViewerCustomLinkedList(String courseFileDirectory){
        listOfCourses = new CourseList();
        readFromCSV(courseFileDirectory);
    }

    /**
     * Opens the CSV file, reads the lines and creates
     * a course based on the line values. Than adds that
     * course to the list
     * @param fileDir directory of the courses CSV file
     */
    private void readFromCSV(String fileDir){
        BufferedReader coursesReader = null;
        String line;
        try{
            coursesReader = new BufferedReader(new FileReader(fileDir));
            String header = coursesReader.readLine();
            while ((line = coursesReader.readLine()) != null && line.length() > 2){
                String[] courseData = line.split(";");
                int semester = Integer.parseInt(courseData[0]);
                String courseCode = courseData[1];
                String courseTitle = courseData[2];
                int ectsCredits = Integer.parseInt(courseData[3]);
                int gtuCredits = Integer.parseInt(courseData[4]);
                String htl = courseData[5];
                Course c = new Course(semester, courseCode, courseTitle, ectsCredits, gtuCredits, htl);
                listOfCourses.add(c);
            }
        } catch (FileNotFoundException fnfx){
            System.out.println("Courses File Has Not Found.");
            fnfx.printStackTrace();
        } catch (IOException ioex){
            System.out.println("IOException");
            ioex.printStackTrace();
        } finally {
            if(coursesReader != null){
                try{
                    coursesReader.close();
                } catch (IOException ioex){
                    ioex.printStackTrace();
                }
            }
        }
    }

    /**
     * Returns a list that contains all the courses with the given course code
     * @param code the code of the courses that is being searched
     * @return the list of courses that have the given code
     */
    public CourseList getByCode(String code){
        CourseList result = new CourseList();
        for (int i=0; i<listOfCourses.size(); i++){
            if(listOfCourses.get(i).getCourseCode().equals(code)){
                result.add(listOfCourses.get(i));
            }
        }
        if(result.size() == 0)
            System.out.println("ERROR: No Course Found With The Given Code.");
        return result;
    }

    /**
     * Returns a list that contains all the courses in the given semester
     * @param semester the semester of the courses that is being searched
     * @return the list of the courses that have the given semester
     */
    public CourseList listSemesterCourses(int semester){
        CourseList result = new CourseList();
        for (int i=0; i<listOfCourses.size(); i++){
            if(listOfCourses.get(i).getSemester() == semester){
                result.add(listOfCourses.get(i));
            }
        }
        if(result.size() == 0)
            System.out.println("ERROR: No Course Found In The Given Semester.");
        return result;
    }

    /**
     * Returns a list that starts from the given index and ends at the
     * other given index
     * @param startIndex first index in the list
     * @param lastIndex last index in the list
     * @return the list of courses that are in the range of the parameters
     */
    public CourseList getByRange(int startIndex, int lastIndex){
        CourseList result = new CourseList();
        if(startIndex >= 0 && lastIndex < listOfCourses.size()) {
            for (int i = startIndex; i < lastIndex; i++) {
                result.add(listOfCourses.get(i));
            }
            if(result.size() == 0)
                System.out.println("ERROR: No Course Found In The Given Range.");
        } else {
            System.out.println("ERROR: Wrong Index Range.");
        }
        return result;
    }

    /**
     * Returns a string that describes the type of the course viewer
     * @return the type of the course viewer
     */
    public String getListType() {
        return "This Course Viewer Uses Fully Custom Linked List Suitable For Courses(NextInSemester)";
    }
}
