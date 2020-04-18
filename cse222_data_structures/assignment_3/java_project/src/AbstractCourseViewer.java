import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Parent class of the course viewers. Implements all the necessary methods.
 *
 * @author Yasir
 */
public abstract class AbstractCourseViewer {
    /**
     * Basic list of the courses. This gets constructed in the child classes.
     */
    protected List<Course> listOfCourses;

    /**
     * Opens the CSV file, reads the lines and creates
     * a course based on the line values. Than adds that
     * course to the list
     * @param courseFileDirectory directory of the courses CSV file
     */
    protected void readFromCSV(String courseFileDirectory){
        BufferedReader coursesReader = null;
        String line;
        try{
            coursesReader = new BufferedReader(new FileReader(courseFileDirectory));
            String header = coursesReader.readLine();
            while ((line = coursesReader.readLine()) != null){
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
    public LinkedList<Course> getByCode(String code){
        LinkedList<Course> result = new LinkedList<Course>();
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
    public LinkedList<Course> listSemesterCourses(int semester){
        LinkedList<Course> result = new LinkedList<Course>();
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
    public LinkedList<Course> getByRange(int startIndex, int lastIndex){
        LinkedList<Course> result = new LinkedList<Course>();
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
     * Polymorphic method that returns a string that describes the type of the course viewer
     * @return the type of the course viewer
     */
    public abstract String getListType();
}
