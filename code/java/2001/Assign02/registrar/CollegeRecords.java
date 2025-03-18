package registrar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Write a description of class CollegeRegistrar here.
 *
 * @author Ed Brown, Dewan Mukto
 * @version 2022 Oct 14
 */
public class CollegeRecords implements CollegeRecordsInterface {
    private ArrayList<Registration> records;
    private ArrayList<String> validCourseIds;

    /**
     * Constructor for_ objects of class CollegeRegistrar
     */
    public CollegeRecords()
    {
        records = new ArrayList<>();
        validCourseIds = new ArrayList<>();
    }

    /**
     * Add a valid courseID for_ validity checking. Only courseIDs added are considered valid.
     * Do not permit a duplicate courseID to be added
     * @param courseId the course ID to add
     * @return false if this course is already listed, otherwise true
     */

    public boolean addValidCourseID(String courseId){
        
        //if(records.stream()
        //.filter(reg -> reg.getCourseID().equals(courseId)).size()){};
        //.forEach(reg -> reg.setValidID);
        
        // ArrayList<Registration> matchedrecords = new ArrayList<>();
        // records.stream()
        // .filter(course -> course.getCourseID() == courseId )
        
        // if (records.stream()
        // .filter(reg -> reg.getCourseID().equals(courseId))
        // .count() == 1){
            // records.get
        // }
        // .setValidID();
        
        return true;
    }

    /**
     * add the registration to the college records
     *
     * @param  a new Registration object
     * @return    chaining object
     */
    @Override
    public CollegeRecords add(Registration newRegistration)
    {
        records.add(newRegistration);
        return this;
    }

    /**
     *
     * @return a count of the registration records
     */
    public int count(){
        return records.size();
    }

    public String toString()
    {
        return records.stream()
        .map(reg -> reg.toString())
        .reduce("", String::concat);
    }

    /**
     * Use all three validity predicates to remove invalid records from this
     * CollegeRecords object and return records that
     * were removed as a different CollegeRecords object.
     *
     * @return a CollegeRecords of invalid registrations that were removed
     */
    @Override
    public CollegeRecords clean() {
        CollegeRecords recordsToRemove = new CollegeRecords();
        records.stream()
        .filter(reg -> !reg.isValidID() || !(reg.getSection() >= 0 && reg.getSection() <= 2) || !(reg.getTimeSlot() >= 12 && reg.getTimeSlot() <= 25))
        .forEach(reg -> recordsToRemove.add(reg));
        return recordsToRemove;
        // return this;
    }

    @Override public CollegeRecords removeDuplicates() {
        Set <Registration> recordsWithoutDuplicates = new HashSet<>();
        this.records.stream()
        .forEach(reg -> recordsWithoutDuplicates.add(reg));
        this.records.clear();
        recordsWithoutDuplicates.stream()
        .forEach(reg -> this.records.add(reg));
        // ArrayList<Registration> courses = new ArrayList<>();
        // records.stream()
        // .forEach(reg -> courses.add(reg));
        // Set<Registration> 
        
        // records.stream()
        // .filter(reg -> reg.getProfessor().equals(professor))
        // .map(reg -> reg.getCourseID())
        // .forEach(course -> courses.add(course));
        // Set<String> setOfCourses = new HashSet<>(courses);
        // courses.clear();
        // setOfCourses.stream()
        // .forEach(course -> courses.add(course));
        // return courses.stream()
        // .reduce("",String::concat);
        return this;
    }

    public CollegeRecords recordsForStudent(String student)
    {
        CollegeRecords newRecords = new CollegeRecords();
        this.records.stream().filter(r -> r.getStudent().equals(student))
        .forEach(r->newRecords.add(r));
        return newRecords;
    }

    public int courseCount(String student)
    {
        ArrayList<Registration> courses = new ArrayList<>();
        records.stream()
        .filter(reg -> reg.getStudent().equals(student))
        .forEach(reg -> courses.add(reg));
        return courses.size();
    }

    /**
     * the Registrations listing this professor
     * @param professor
     * @return a CollegeRecords object of all registrations with the given professor
     */
    public CollegeRecords recordsForProfessor(String professor)
    {
        CollegeRecords newRecords = new CollegeRecords();
        this.records.stream().filter(r -> r.getProfessor().equals(professor))
        .forEach(r->newRecords.add(r));
        return newRecords;
    }

    public CollegeRecords sectionRecords(String courseID, int section) {
        CollegeRecords selection = new CollegeRecords();
        this.records.stream()
        .filter(reg -> reg.getCourseID().equals(courseID))
        .filter(reg -> reg.getSection() == section)
        .forEach(reg -> selection.add(reg));
        return selection;
    }

    public CollegeRecords dropAllClassesForStudent(String student)
    {
        ArrayList<Registration> recordsAfterRemoval = new ArrayList<>();
        ArrayList<Registration> recordsToRemove = new ArrayList<>();
        records.stream()
        .filter(reg -> !reg.getStudent().equals(student))
        .forEach(reg -> recordsToRemove.add(reg));
        records.removeAll(recordsToRemove);
        return this;
    }

    public String profCourses(String professor){
        ArrayList<String> courses = new ArrayList<>();
        records.stream()
        .filter(reg -> reg.getProfessor().equals(professor))
        .map(reg -> reg.getCourseID())
        .forEach(course -> courses.add(course));
        Set<String> setOfCourses = new HashSet<>(courses);
        courses.clear();
        setOfCourses.stream()
        .forEach(course -> courses.add(course));
        return courses.stream()
        .reduce("",String::concat);
        //return "THESE ARE NOT THE COURSES";
    }

    /**
     * Method verifyCourseID
     *
     * @param r the registration record to verify
     * @return returns true if and only if the registration object refers to a course previously added as valid
     */
    private boolean verifyCourseID(Registration r)
    {
        return (validCourseIds.stream()
            .filter(id -> id.equals(r.getCourseID()))).count() == 1 ? true : false;
    }

    /**
     * Method verifySection
     *
     * @param  r  the registration record to verify
     * @return    returns true if and only if the registration object's section is between 0 and 2 inclusive
     */
    private boolean verifySection(Registration r)
    {
        return r.getSection() >= 0 && r.getSection() <= 2 ? true : false;
    }

    /**
     * Method verifySlot
     *
     * @param r the registration record to verify
     * @return returns true if and only if the registration object's time slot is between 12 and 25 inclusive
     */
    private boolean verifySlot(Registration r)
    {
        return r.getTimeSlot() >= 12 && r.getTimeSlot() <= 25 ? true : false;
    }
}