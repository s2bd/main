package registrar;

/**
 * Write a description of class Enrollment here.
 *
 * @author Dewan Mukto
 * @version 26 Oct 2022
 */
public class Registration {


    // instance variables - replace the example below with your own
    private String courseID;
    private int section;
    private int timeSlot;
    private String student;
    private String professor;

    public Registration(String courseID, int section, int timeSlot, String student, String professor) {
        this.courseID = courseID;
        this.section = section;
        this.timeSlot = timeSlot;
        this.student = student;
        this.professor = professor;
    }


    /**
     * Constructor for objects of class Registration
     */
    public String toString() {
        // all information as a string
        return student + " is registered for " + courseID + "(" + section + ") slot " + timeSlot + " (" + professor + ")";
        // return "student is registered for courseID(section) slot timeslot (professor)";
    }

    // Accessor methods
    public String getCourseID() {
        return courseID;
    }

    public int getTimeSlot() {
        return timeSlot;
    }

    public String getStudent() {
        return student;
    }

    public int getSection() {
        return section;
    }

    public String getProfessor() {
        return professor;
    }
    
    // Mutator methods
    public void setCourseID(String newCourseID) {
        courseID = newCourseID;
    }

    public void setTimeSlot(int newTimeSlot) {
        timeSlot = newTimeSlot;
    }

    public void setStudent(String newStudent) {
        student = newStudent;
    }

    public void setSection(int newSection) {
        section = newSection;
    }

    public void setProfessor(String newProfessor) {
        professor = newProfessor;
    }
    public boolean sameAs(Registration other){
        return courseID.equals(other.getCourseID()) &&
                student.equals(other.getStudent()) &&
                timeSlot == other.getTimeSlot() &&
                section == other.getSection() &&
                professor.equals(other.getProfessor());
    }

    /**
     * Get the last name of the professor based on the professors
     * names separated by blanks as it appears in the professor field.
     * 
     * If there is only one name, return it.
     * 
     * If there are no names, return null.
     */
    public String getProfLast(){
        String lastName;
        String[] profName = professor.strip().split(" ");
        if(professor.strip().contains(" ") && profName.length >= 2) return profName[profName.length - 1]; // if 2 names
        if(!professor.strip().contains(" ") && profName.length >= 1) return professor.strip(); // if 1 name
        if(professor.strip()=="" || professor.strip()==" ") return null; // if no names
        else return null; // if no names
    }

}
