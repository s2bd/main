

# COMP2001 F2022 Assignment 2 Instructions

## Objectives

Practice functional style coding.

The problem concerns representing a list of student/course registrations maintained in a CollegeRecords object.

## Activity

* Checkout comp2001-assign2 repository from github classroom for the following credit work
*  Confirm that the **registrar** package and the **grading** package are present in the project
* Open the project `registrar` package in Bluej.
* Modify the **CollegeRecords** class to implement the methods as described in **CollegeRecordsInterface** Note the declaration line of **CollegeRecords** indicates the implementation requirement to the compiler.
    * In your solution, make changes to CollegeRecords code but do not alter the class declaration line

           public class CollegeRecords implements CollegeRecordsInterface
* Open the **grading** package to run the JUNIT tests. You should be able to compile and run the JUNIT tests immediately as many of the required methods are already implemented or stubbed out with incorrect implementations.
* Some tests we use for grading will be held back and not released with the assignment.
* The following methods are provided correct as written:
  * CollegeRecords.count()
  * CollegeRecords.add()
  * CollegeRecords.recordsForProfessor()
* Other methods need to be corrected with code modifications.
* Three standard accessor/getter methods are missing from the Registration class. Implement them to pass three of the tests.
* Three private predicate methods are required in the `CollegeRecords` class. Because they are private, they do not appear in the Interface description, but they are relevant to the tests.
  * `CollegeRecords.verifyCourseID(Registration r)` is true if and only if the registration object refers to a course previously added as valid using the CollegeRecords.addValidCourseID(String courseId) method.
  * `CollegeRecords.verifySection(Registration r)` is true if and only if the registration object's section is between 0 and 2 inclusive.
  * `CollegeRecords.verifySlot(Registration r)` is true if and only if the registration object's time slot is between 12 and 25 inclusive.
* The `clean()` method should use all three verify predicates to determine whether to remove a registration or not. A registration failing any of the three validations should be cleaned out.
* Instead of calling printing methods within the classes, you can use method chaining and terminate the function chain with a call to `System.out::println` to view the objects you construct.  There are no printing calls within the Classes. This is a departure from the code the book has used up to this point; the authors put print calls inside their classes.
* For printing to work satisfactorily, implement the `toString()` method for the classes:
  * `Registration.toString()` should return a string containing values of the attributes in the Registration object.
  * `CollegeRecords.toString()` should return a String combining all of the toString's for each Registration in its records. To construct this, call `Registration.toString()` for each Registration object. 
* Many of the subsequent tests use `toString` to check results, so get `toString` methods working correctly before moving on to the rest of the tests.
* Many of the methods require you to return a different `CollegeRecords` object. `CollegeRecords.recordsForProfessor()` illustrates how you can populate a new object with a forEach call at the end of a function chain. Streams also have collection methods that serve this purpose, but we do not cover them in lecture, so you may explore them on your own if you wish; but they are not necessary for the assignment.
* Use reduce for the `profCourses()` method; it is the only one that makes use of reduce and you will need the practice for the mid-term.

