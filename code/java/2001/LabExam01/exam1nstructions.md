# COMP2001 F2022 Lab Exam 1

* Rules for the lab exam:

    * 90 minutes duration. Push your solution code often, and your last revision before the time expires.
    * You must attend the lab, remote will not be accepted
    * Attendance will be taken
    * Use the lab computers, not your own laptop
    * Online resources may be used, except communication with other people
        * You may not post new questions or interact with people; only material that already exists on discussion boards, or elsewhere may be accessed.
        * You may use your own material you have loaded on your labnet account.
    * Interaction with other students in the lab is not permitted.
    * Do not look at other students' work.
    * Rules concerning academic misconduct apply to the exam.

# Problem steps
1. Write standard setter/mutator methods for the attributes/fields of the `Registration` class.  The test in ExamSetterTests should compile and run once you have completed this step.
2. Complete the `CollegeRecord.toString()` method as described in its javadoc. To get full marks, do not use explicit loops, use functional style and a reduce() stream method to produce the result.
3. Complete the 'Registration.getProfLast()' method as described in its javadoc. There are three tests for this method.
4. In the CollegeRecords class, create an instance variable String to Integer hashmap called `capacity` which will map each course to an enrollment (maximum registrations) capacity.
5. (requires #4) Complete the setMaxCapacity method with the functionality to set the capacity of a course in the capacity HashMap.
6. (requires #4 and #5) Complete the `safeAdd` method which performs the same function as add, but will not add the registration if the course capacity is set (in the capacity HashMap) and the course is full (registrations are at or above capacity). There are two tests for this method.
7. Change the modifiers of `CollegeRecords.verifyCourseID` so this method can be accessed from outside the class, and it does not require an instance object to use the method. You will also have to alter the `clean()` method to use the new version of `CollegeRecords.verifyCourseID` before it will compile again. (Best to push your working exam code before attempting this change.)
8. Modify the `CollegeRecords.conflicts` method to return the correct value according to its javadoc. (Hint: the nested loop is working correctly)
9. Re-write the `CollegeRecords.conflicts` method in functional style instead of using explicit loops.
10. Complete the `CollegeRecords.teaching` method as described in its javadoc. For full marks, use functional style with no loops.
11. Modify `Responder.responseGenerator()` to implement the following rule A:

        25% of the time (at random), the responder should 
        respond with the phrase "Tell me more about that."

12. Modify `Responder.responseGenerator()` to implement the following rule B:

        Unless rule A is in effect, if a key word from the 
        response map is input, the responder should use the  
        corresponding  response from the responseMap. However, 
        each response from the responseMap can only be used 
        one time.

13. Modify `Responder.responseGenerator()` to implement the following rule C:

        As a modification to rule A, instead of "Tell me more 
        about that." the responder should respond with "Tell me
        more about X", where X is the word that has appeared in 
        the most user inputs, and X is more than 3 letters long. 
        (Hint: use a HashMap to count the occurances of the words.)
        