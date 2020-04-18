import java.util.Objects;

/**
 * The class that holds a Course
 * Each course have its necessary values, getters
 * and setters. This class also overrides toString,
 * equals and hashCode because of the comparison necessities.
 *
 * @author Yasir
 */
public class Course {
    /**
     * Which semester is the course in
     */
    private int semester;
    /**
     * Code of the course
     */
    private String courseCode;
    /**
     * Name of the course
     */
    private String courseTitle;
    /**
     * Credits of the course based on ECTS standards.
     */
    private int ectsCredits;
    /**
     * Credits of the course based on GTU standards.
     */
    private int gtuCredits;
    /**
     * Hours distribution of the course (Theory, Application and Lab.)
     */
    private String htl;

    /**
     * Simple constructor that takes all the fields of the course
     * @param semester which semester is the course in
     * @param courseCode code of the course
     * @param courseTitle name of the course
     * @param ectsCredits ECTS credits of the course
     * @param gtuCredits GTU credits of the course
     * @param htl Hours distribution of the course
     */
    public Course(int semester, String courseCode, String courseTitle, int ectsCredits, int gtuCredits, String htl) {
        this.semester = semester;
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.ectsCredits = ectsCredits;
        this.gtuCredits = gtuCredits;
        this.htl = htl;
    }

    /**
     * Getter for semester field
     * @return semester of the course
     */
    public int getSemester() {
        return semester;
    }

    /**
     * Setter for the semester field
     * @param semester semester to be set
     */
    public void setSemester(int semester) {
        this.semester = semester;
    }

    /**
     * Getter for the course code field
     * @return code of the course
     */
    public String getCourseCode() {
        return courseCode;
    }

    /**
     * Setter for the course code field
     * @param courseCode course code to be set
     */
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    /**
     * Getter for the course title field
     * @return name of the course
     */
    public String getCourseTitle() {
        return courseTitle;
    }

    /**
     * Setter for the course title field
     * @param courseTitle course name to be set
     */
    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    /**
     * Getter for the ECTS credits of the course
     * @return ECTS credits of the course
     */
    public int getEctsCredits() {
        return ectsCredits;
    }

    /**
     * Setter for the ECTS credits of the course
     * @param ectsCredits ECTS credits to be set
     */
    public void setEctsCredits(int ectsCredits) {
        this.ectsCredits = ectsCredits;
    }

    /**
     * Getter for the GTU credits of the course
     * @return GTU credits of the course
     */
    public int getGtuCredits() {
        return gtuCredits;
    }

    /**
     * Setter for the GTU credits of the course
     * @param gtuCredits GTU credits to be set
     */
    public void setGtuCredits(int gtuCredits) {
        this.gtuCredits = gtuCredits;
    }

    /**
     * Getter for the hours distribution of the course
     * @return hours distribution in a string
     */
    public String getHtl() {
        return htl;
    }

    /**
     * Setter for the hours distribution of the course
     * @param htl hours distribution to be set
     */
    public void setHtl(String htl) {
        this.htl = htl;
    }

    /**
     * toString override, describes the basic values of the course
     * @return description of the class in a string
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Semester: ");
        sb.append(Integer.toString(semester));
        sb.append(". Course Code and Title: ");
        sb.append(courseCode);
        sb.append(" - ");
        sb.append(courseTitle);
        sb.append(". ECTS and GTU Credits: ");
        sb.append(Integer.toString(ectsCredits));
        sb.append(", ");
        sb.append(Integer.toString(gtuCredits));
        sb.append(". H+T+L: ");
        sb.append(htl);
        sb.append(".");
        return sb.toString();
    }

    /**
     * equals override of the course. Compares all fields with the
     * paramtere and returns true or false based on those comparisons.
     * @param o object to be compared
     * @return true if the courses are the same, false if not.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return getSemester() == course.getSemester() &&
                getEctsCredits() == course.getEctsCredits() &&
                getGtuCredits() == course.getGtuCredits() &&
                Objects.equals(getCourseCode(), course.getCourseCode()) &&
                Objects.equals(getCourseTitle(), course.getCourseTitle()) &&
                Objects.equals(getHtl(), course.getHtl());
    }

    /**
     * Hash code calculation override.
     * @return an integer value that is being calculated using hash method of the Objects class.
     */
    @Override
    public int hashCode() {
        return Objects.hash(getSemester(), getCourseCode(), getCourseTitle(), getEctsCredits(), getGtuCredits(), getHtl());
    }
}
