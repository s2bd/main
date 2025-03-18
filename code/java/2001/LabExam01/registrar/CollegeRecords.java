package registrar;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Write a description of class CollegeRegistrar here.
 *
 * @author Dewan Mukto
 * @version 2022 Oct 26
 */
public class CollegeRecords {
    private ArrayList<Registration> records;
    private HashMap<String, Integer> capacity;
    
    /**
     * Constructor for_ objects of class CollegeRegistrar
     */
    public CollegeRecords()
    {
        records = new ArrayList<>();
        capacity = new HashMap<String, Integer>();
    }

    /**
     * Check that the CourseID is one of the valid courses formatted
     * with 4 letters and 4 numerals.
     * @param r a Registration object
     * @return true/false depending if the course is formatted correctly
     */
    public boolean verifyCourseID(Registration r) {
        return r.getCourseID().matches("[A-Z]{4} \\d{4}");
    }
    
    /**
     * Check that the CourseID is one of the valid courses formatted
     * with 4 letters and 4 numerals.
     * @param c a CourseID
     * @return true/false depending if the course is formatted correctly
     */
    public boolean verifyCourseID(String c) {
        return c.matches("[A-Z]{4} \\d{4}");
    }

    /**
     * Check that the registration timeslot is valid
     * @param r a Registration object
     * @return true/false depending if the section is valid
     */
    private boolean verifySection(Registration r) {
        return r.getSection() <= 2 && r.getSection() >= 0;
    }

    /**
     * Check that the registration section is valid
     * @param a Registration object
     * @return true/false depending if the timeslot is valid
     */
    private boolean verifySlot(Registration r) {
        return r.getTimeSlot() <= 25 && r.getTimeSlot() >= 12;
    }

    /**
     * add the registration to the college records
     *
     * @param  a new Registration object
     * @return chaining object
     */

    public CollegeRecords add(Registration newRegistration)
    {
        records.add(newRegistration);
        return this;
        // put your code here
        // return null;
    }

    /**
     *
     * @return a count of the registration records
     */

    public int count(){ return records.size(); }

    /**
     * convert the college records to a string
     * of semi-colon separated registration records
     * Each record is surrounded by brackets [] such as:
     *
     * [record here];[record here];[record here]
     * 
     * There should be no trailing semi-colon in the string.
     *
     * @return    CollegeRecords as a string representation
     */

    public String toString()
    {
        String regString = records.stream()
        .map(reg -> "["+reg.toString()+"];")
        .reduce("",String::concat);
        regString.substring(0,regString.length()-1);
        return regString;
    }

    /**
     * Use all three validity predicates to remove invalid records from this
     * CollegeRecords object and return records that
     * were removed as a different CollegeRecords object.
     *
     * @return a CollegeRecords of invalid registrations that were removed
     */

    public CollegeRecords clean() {
        // this is some comment
        ArrayList<Registration> valid = new ArrayList<Registration>();
        this.records.stream().filter(this::verifySlot)  // and so is this
                             .filter(reg -> verifyCourseID(reg.getCourseID()))
                             .filter(this::verifySection)
                             .forEach(valid::add);

        this.records.removeAll(valid);
        int i = 0;
        ArrayList<Registration> invalid = this.records;
        this.records = valid;
        CollegeRecords c = new CollegeRecords();
        invalid.forEach(c::add);
        return c;
    }
    

    /**
     * Set the maximum capacity for a course. Do not distinguish
     * course sections, the capacity is for all course sections.
     *
     * @param student
     * @return a CollegeRecords of the student's course registrations
     */

    // exam
    public void setMaxCapacity(String c, int cap){
        if(capacity.get(c) != null) capacity.remove(c);
        capacity.put(c, cap);
    }

    /* add the registration to the reocrords unless
     * the capacity of the course has
     * been set and the course is full acording to the records.
     * 
     * Return true if the course is added, false if it is not.
     */
    public boolean safeAdd(Registration r){
        ArrayList<String> regs = new ArrayList<>();
        records.stream()
        .filter(reg -> reg.getCourseID().equals(r))
        .map(reg -> reg.getCourseID())
        .forEach(reg -> regs.add(reg));
        if(capacity.containsKey(r.getCourseID()) && capacity.get(r.getCourseID())>=regs.size()){
            return false;
        }
        records.add(r);
        return true;
    }

    /**
     * Find all the records where there is a student scheduling conflict, in other words,
     * a student is registered for more than one class in the same time slot.
     *
     * Return a CollegeRecords object containing all records that have a conflict.
     */
    public CollegeRecords conflicts(){
        records.stream();
        // CollegeRecords conflictRecords = new CollegeRecords();
        // ArrayList<Registration> conflictList = conflictRecords.records;
        // for(Registration r : records){
            // for(Registration r2: records){
                // if(r2.getTimeSlot()==r.getTimeSlot() && r2.getStudent().equals(r.getStudent()) && !r2.sameAs(r)){
                    // if(conflictList.isEmpty() || !r.sameAs(conflictList.get(conflictList.size()-1)))
                        // conflictList.add(r);
                // }
            // }
        // }
        return this;
    }

    /**
     * Use a filter-map-forEach chain to determine how many students a prof has registered in each class.
     * No loops are allowed.
     * @param prof the professor whose courses to consider
     * @return a HashMap of courseID-enrollment mappings.
     */
    public HashMap<String,Integer> teaching(String prof){
        HashMap<String,Integer> taught = new HashMap<String, Integer>();
        records.stream()
        .filter(reg -> reg.getProfessor().equals(prof))
        .map(reg -> reg.getCourseID())
        .forEach(course -> { if(taught.get(course)!=null){
            taught.replace(course, taught.get(course)+1);
        } else {
            taught.put(course,1);
        }
        });
        return taught;
    }

}