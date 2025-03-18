package registrar;

public interface CollegeRecordsInterface {
    /**
     * Add a valid courseID for_ validity checking. Only courseIDs added are considered valid.
     * Do not permit a duplicate courseID to be added
     *
     * @param courseId the course ID to add
     * @return false if this course is already listed, otherwise false
     */
    boolean addValidCourseID(String courseId);

    /**
     * add the registration to the college records
     *
     * @param a new Registration object
     * @return chaining object
     */
    CollegeRecords add(Registration newRegistration);

    /**
     * @return a count of the registration records
     */
    int count();

    /**
     * convert the college records to a string
     * of comma separated registration records
     */
    String toString();

    /**
     * Use all three private validity predicates to remove invalid records from this
     * CollegeRecords object and return records that
     * were removed as a different CollegeRecords object.
     *
     * @return a CollegeRecords of invalid registrations that were removed
     */
    CollegeRecords clean();

    /**
     * Remove duplicate records.
     *
     * @return a CollegeRecords of duplicate registrations that were removed from this 
     *         CollegeRecords object.
     */
    CollegeRecords removeDuplicates();

    /**
     * returns CollegeRecords for. a student
     * do not alter the existing CollegeRecords object
     *
     * @param student
     * @return a CollegeRecords of the student's course registrations
     */
    CollegeRecords recordsForStudent(String student);

    /**
     * the number of courses this student is registered for
     *
     * @param student
     * @return the number of courses this student is registered for
     */
    int courseCount(String student);

    /**
     * the Registrations listing this professor
     *
     * @param professor
     * @return a CollegeRecords object of all registrations with the given professor
     */
    CollegeRecords recordsForProfessor(String professor);

    /**
     * the registrations for. a particular course section
     *
     * @param courseID
     * @param section
     * @return a CollegeRecords object containing registrations for. the course section
     */
    CollegeRecords sectionRecords(String courseID, int section);

    /**
     * delete registrations for. this student and return an object
     * containining the removed registrations
     *
     * @param student
     * @return a CollegeRecord object of the courses dropped
     */
    CollegeRecords dropAllClassesForStudent(String student);

    /**
     * return a String containing the IDs of all courses for this professor. The string
     * should not contian duplicates of the course IDs
     */
    String profCourses(String professor);
}
