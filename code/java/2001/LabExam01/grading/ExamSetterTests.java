package grading;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import registrar.CollegeData;
import registrar.CollegeRecords;
import registrar.Registration;

import java.io.IOException;
import java.lang.reflect.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(GradingTestWatcher.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@GradeValue(35)
class ExamSetterTests {

    ArrayList<Registration> testrecords;

    CollegeRecords cr;



    private void insertTestRecords(String[] adders) {
        Arrays.stream(adders).forEach(
                s -> {
                    Registration r = splitRegistration(s);
                    testrecords.add(r);
                    cr.add(r);
                });
    }
    private Registration splitRegistration(String dat){
        String[] sdata = dat.split("/");
        return new Registration(sdata[0], Integer.parseInt(sdata[1]),
                Integer.parseInt(sdata[2]), sdata[3], sdata[4]);
    }

    public int countSection(){
        return 0;
    }

    @BeforeEach
    void setUp() {
        cr = new CollegeRecords();
        testrecords = CollegeData.load(cr);
    }

    @AfterEach
    void tearDown() {

    }

   

    /*
      add a setter methods/mutator for ()  Registration fields.
     */
    @Test
    @Order(0)
    @GradeValue(3)
    public void setters() {
        testrecords.forEach(r -> {
            for(int i=0;i<3;i++){
                r.setSection(i);
                assertEquals(i,r.getSection(), "setSection does not work");
            }
            for(int i=12;i<25;i++){
                r.setTimeSlot(i);
                assertEquals(i,r.getTimeSlot(), "setTimeslot does not work");
            }
            String[] testStrings = {"one","two two", "three three three"};
            for(String testString: testStrings){
                r.setCourseID(testString);
                r.setProfessor(testString);
                r.setStudent(testString);
                assertEquals(testString,r.getStudent(), "setStudent does not work");
                assertEquals(testString,r.getProfessor(), "setProfessor does not work");
                assertEquals(testString,r.getCourseID(), "setCourseID does not work");
            }
        });
    }


   
}
