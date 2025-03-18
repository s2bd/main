package registrar;

/**
 * Write a description of class Enrollment here.
 *
 * @author Ed Brown, Dewan Mukto
 * @version 2022 Oct 16
 */
public class Registration {
    // instance variables - replace the example below with your own
    private String courseID;
    private int section;
    private int timeSlot;
    private String student;
    private String professor;
    public boolean validatedID;

    public Registration(String courseID, int section, int timeSlot, String student, String professor) {
        this.courseID = courseID;
        this.section = section ;
        this.timeSlot = timeSlot;
        this.student = student;
        this.professor = professor;
        this.validatedID = false;
    }

    /**
     * Constructor for objects of class Registration
     */


    /**
     * toString method should include all the attributes of the object
     */
    public String toString() {
        return student + " is registered for " + courseID + " ("+ section +")" + " slot " + timeSlot + " (" + professor + ")";
        //return "studentX is registered for courseID(section) slot timeslot (professor)";
    }


    public String getProfessor() {
        return professor;
    }
    
    public String getCourseID() {
        return courseID;
    }
    
    public int getSection() {
        return section;
    }
    
    public int getTimeSlot() {
        return timeSlot;
    }
    
    public String getStudent() {
        return student;
    }
    
    public boolean isValidID() {
        return validatedID;
    }
    
    public void setValidID(Registration r) {
        r.validatedID = true;
    }

}