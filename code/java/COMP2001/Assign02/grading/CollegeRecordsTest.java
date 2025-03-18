package grading;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import registrar.CollegeRecords;
import registrar.Registration;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

@ExtendWith(GradingTestWatcher.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@GradeValue(60)
class CollegeRecordsTest {
    String[] validCourses = {"COMP 1501", "ENGL 2201", "CHEM 3060", "PHYS 2344", "ENGL 2005", "COMP 2704", "PHYS 2377", "CHEM 4927", "COMP 3444"};
    String[] testdata =
            {
                    "COMP 1501/0/15/Bob Jones/B. Hind",
                    "COMP 1501/0/15/Fatima Estossa/B. Hind",
                    "COMP 1501/0/15/Long Britches/B. Hind",
                    "COMP 1501/0/15/While E Cotote/B. Hind",
                    "COMP 1501/0/15/Al Dante/B. Hind",
                    "COMP 1501/0/15/Sally Forth/B. Hind",
                    "COMP 1501/0/15/Juan Castro/B. Hind",
                    "COMP 1501/1/17/Bob Jones/C. Borne",
                    "COMP 1501/1/17/Fatima Estossa/C. Borne",
                    "COMP 1501/1/17/Long Britches/C. Borne",
                    "COMP 1501/1/17/While E Cotote/C. Borne",
                    "COMP 1501/1/17/Al Dante/C. Borne",
                    "COMP 1501/1/17/Sally Forth/C. Borne",
                    "COMP 1501/2/12/Jim Smith/F. Fusive",
                    "COMP 1501/2/12/Fatima Estossa/F. Fusive",
                    "COMP 1501/2/12/Long Britches/F. Fusive",
                    "COMP 1501/2/12/While E Cotote/F. Fusive",
                    "COMP 1501/2/12/Al Dante/F. Fusive",
                    "COMP 1501/2/12/Justin Case/F. Fusive",
                    "COMP 1501/2/12/Juan Castro/F. Fusive",
                    "ENGL 2201/1/24/Bob Jones/D. Pressing",
                    "ENGL 2201/1/24/Long Britches/D. Pressing",
                    "ENGL 2201/1/24/While E Cotote/D. Pressing",
                    "ENGL 2201/1/24/Justin Case/D. Pressing",
                    "ENGL 2201/1/24/Sally Forth/D. Pressing",
                    "CHEM 3060/0/22/Jim Smith/C. Borne",
                    "CHEM 3060/0/22/Fatima Estossa/C. Borne",
                    "CHEM 3060/0/22/Long Britches/C. Borne",
                    "CHEM 3060/0/22/While E Cotote/C. Borne",
                    "CHEM 3060/0/22/Al Dante/C. Borne",
                    "CHEM 3060/0/22/Juan Castro/C. Borne",
                    "CHEM 3060/1/19/Jim Smith/A. Talker",
                    "CHEM 3060/1/19/Bob Jones/A. Talker",
                    "CHEM 3060/1/19/Fatima Estossa/A. Talker",
                    "CHEM 3060/1/19/While E Cotote/A. Talker",
                    "CHEM 3060/1/19/Al Dante/A. Talker",
                    "CHEM 3060/1/19/Justin Case/A. Talker",
                    "CHEM 3060/1/19/Sally Forth/A. Talker",
                    "CHEM 3060/1/19/Juan Castro/A. Talker",
                    "CHEM 3060/2/14/Jim Smith/A. Talker",
                    "CHEM 3060/2/14/Bob Jones/A. Talker",
                    "CHEM 3060/2/14/Fatima Estossa/A. Talker",
                    "CHEM 3060/2/14/Long Britches/A. Talker",
                    "CHEM 3060/2/14/While E Cotote/A. Talker",
                    "CHEM 3060/2/14/Al Dante/A. Talker",
                    "CHEM 3060/2/14/Justin Case/A. Talker",
                    "CHEM 3060/2/14/Sally Forth/A. Talker",
                    "CHEM 3060/2/14/Juan Castro/A. Talker",
                    "PHYS 2344/0/14/Jim Smith/A. Talker",
                    "PHYS 2344/0/14/Fatima Estossa/A. Talker",
                    "PHYS 2344/0/14/Long Britches/A. Talker",
                    "PHYS 2344/0/14/While E Cotote/A. Talker",
                    "PHYS 2344/0/14/Al Dante/A. Talker",
                    "PHYS 2344/0/14/Sally Forth/A. Talker",
                    "PHYS 2344/0/14/Juan Castro/A. Talker",
                    "PHYS 2344/1/17/Jim Smith/F. Fusive",
                    "PHYS 2344/1/17/Bob Jones/F. Fusive",
                    "PHYS 2344/1/17/Fatima Estossa/F. Fusive",
                    "PHYS 2344/1/17/Long Britches/F. Fusive",
                    "PHYS 2344/1/17/While E Cotote/F. Fusive",
                    "PHYS 2344/1/17/Justin Case/F. Fusive",
                    "PHYS 2344/1/17/Sally Forth/F. Fusive",
                    "PHYS 2344/1/17/Juan Castro/F. Fusive",
                    "ENGL 2005/0/12/Jim Smith/F. Fusive",
                    "ENGL 2005/0/12/Bob Jones/F. Fusive",
                    "ENGL 2005/0/12/Fatima Estossa/F. Fusive",
                    "ENGL 2005/0/12/Long Britches/F. Fusive",
                    "ENGL 2005/0/12/While E Cotote/F. Fusive",
                    "ENGL 2005/0/12/Al Dante/F. Fusive",
                    "ENGL 2005/0/12/Justin Case/F. Fusive",
                    "ENGL 2005/0/12/Sally Forth/F. Fusive",
                    "ENGL 2005/1/24/Jim Smith/D. Pressing",
                    "ENGL 2005/1/24/Bob Jones/D. Pressing",
                    "ENGL 2005/1/24/Long Britches/D. Pressing",
                    "ENGL 2005/1/24/While E Cotote/D. Pressing",
                    "ENGL 2005/1/24/Justin Case/D. Pressing",
                    "ENGL 2005/1/24/Sally Forth/D. Pressing",
                    "ENGL 2005/1/24/Juan Castro/D. Pressing",
                    "COMP 2704/0/20/Jim Smith/E. Seeyah",
                    "COMP 2704/0/20/Bob Jones/E. Seeyah",
                    "COMP 2704/0/20/Fatima Estossa/E. Seeyah",
                    "COMP 2704/0/20/Al Dante/E. Seeyah",
                    "COMP 2704/0/20/Justin Case/E. Seeyah",
                    "COMP 2704/0/20/Sally Forth/E. Seeyah",
                    "COMP 2704/0/20/Juan Castro/E. Seeyah",
                    "COMP 2704/1/22/Jim Smith/D. Pressing",
                    "COMP 2704/1/22/Bob Jones/D. Pressing",
                    "COMP 2704/1/22/Fatima Estossa/D. Pressing",
                    "COMP 2704/1/22/Long Britches/D. Pressing",
                    "COMP 2704/1/22/While E Cotote/D. Pressing",
                    "COMP 2704/1/22/Justin Case/D. Pressing",
                    "COMP 2704/1/22/Sally Forth/D. Pressing",
                    "COMP 2704/1/22/Juan Castro/D. Pressing",
                    "COMP 2704/2/21/Jim Smith/B. Hind",
                    "COMP 2704/2/21/Bob Jones/B. Hind",
                    "COMP 2704/2/21/Long Britches/B. Hind",
                    "COMP 2704/2/21/While E Cotote/B. Hind",
                    "COMP 2704/2/21/Al Dante/B. Hind",
                    "COMP 2704/2/21/Justin Case/B. Hind",
                    "COMP 2704/2/21/Sally Forth/B. Hind",
                    "PHYS 2377/0/17/Jim Smith/F. Fusive",
                    "PHYS 2377/0/17/Bob Jones/F. Fusive",
                    "PHYS 2377/0/17/Fatima Estossa/F. Fusive",
                    "PHYS 2377/0/17/Long Britches/F. Fusive",
                    "PHYS 2377/0/17/While E Cotote/F. Fusive",
                    "PHYS 2377/0/17/Al Dante/F. Fusive",
                    "PHYS 2377/0/17/Justin Case/F. Fusive",
                    "PHYS 2377/0/17/Sally Forth/F. Fusive",
                    "PHYS 2377/1/18/Jim Smith/B. Hind",
                    "PHYS 2377/1/18/Bob Jones/B. Hind",
                    "PHYS 2377/1/18/While E Cotote/B. Hind",
                    "PHYS 2377/1/18/Sally Forth/B. Hind",
                    "PHYS 2377/1/18/Juan Castro/B. Hind",
                    "PHYS 2377/2/17/Jim Smith/F. Fusive",
                    "PHYS 2377/2/17/Bob Jones/F. Fusive",
                    "PHYS 2377/2/17/Fatima Estossa/F. Fusive",
                    "PHYS 2377/2/17/Long Britches/F. Fusive",
                    "PHYS 2377/2/17/While E Cotote/F. Fusive",
                    "PHYS 2377/2/17/Al Dante/F. Fusive",
                    "PHYS 2377/2/17/Justin Case/F. Fusive",
                    "PHYS 2377/2/17/Sally Forth/F. Fusive",
                    "CHEM 4927/0/13/Jim Smith/E. Seeyah",
                    "CHEM 4927/0/13/Bob Jones/E. Seeyah",
                    "CHEM 4927/0/13/Fatima Estossa/E. Seeyah",
                    "CHEM 4927/0/13/Long Britches/E. Seeyah",
                    "CHEM 4927/0/13/While E Cotote/E. Seeyah",
                    "CHEM 4927/0/13/Al Dante/E. Seeyah",
                    "CHEM 4927/0/13/Justin Case/E. Seeyah",
                    "CHEM 4927/0/13/Sally Forth/E. Seeyah",
                    "CHEM 4927/2/13/Jim Smith/A. Talker",
                    "CHEM 4927/2/13/Bob Jones/A. Talker",
                    "CHEM 4927/2/13/Fatima Estossa/A. Talker",
                    "CHEM 4927/2/13/Long Britches/A. Talker",
                    "CHEM 4927/2/13/Al Dante/A. Talker",
                    "CHEM 4927/2/13/Justin Case/A. Talker",
                    "CHEM 4927/2/13/Sally Forth/A. Talker",
                    "COMP 3444/0/22/Jim Smith/A. Talker",
                    "COMP 3444/0/22/Bob Jones/A. Talker",
                    "COMP 3444/0/22/Fatima Estossa/A. Talker",
                    "COMP 3444/0/22/Long Britches/A. Talker",
                    "COMP 3444/0/22/Justin Case/A. Talker",
                    "COMP 3444/0/22/Sally Forth/A. Talker",
                    "COMP 3444/0/22/Juan Castro/A. Talker",
                    "COMP 3444/1/22/Jim Smith/C. Borne",
                    "COMP 3444/1/22/While E Cotote/C. Borne",
                    "COMP 3444/1/22/Al Dante/C. Borne",
                    "COMP 3444/1/22/Justin Case/C. Borne",
                    "COMP 3444/1/22/Sally Forth/C. Borne",
                    "COMP 3444/1/22/Juan Castro/C. Borne",
                    "COMP 3444/2/15/Jim Smith/D. Pressing",
                    "COMP 3444/2/15/Bob Jones/D. Pressing",
                    "COMP 3444/2/15/Fatima Estossa/D. Pressing",
                    "COMP 3444/2/15/Long Britches/D. Pressing",
                    "COMP 3444/2/15/Al Dante/D. Pressing",
                    "COMP 3444/2/15/Justin Case/D. Pressing",
                    "COMP 3444/2/15/Sally Forth/D. Pressing",
                    "COMP 3444/2/15/Juan Castro/D. Pressing"
            };
    ArrayList<Registration> testrecords;

    CollegeRecords cr;

    @BeforeEach
    void setUp() {
        testrecords = new ArrayList<>();
        cr = new CollegeRecords();
        Arrays.stream(testdata).forEach(s ->{
            String[] sdata = s.split("/");
            Registration r = new Registration(sdata[0], Integer.parseInt(sdata[1]),
                                Integer.parseInt(sdata[2]), sdata[3], sdata[4]);
            testrecords.add(r);
            // System.out.println(r);
            cr.add(r);
        });
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    @Order(0)
    @GradeValue(10)
    public void codeNoLoops() throws java.io.IOException {

        String sourceFile = "registrar/CollegeRecords.java";
        String content = Files.readString(Path.of(sourceFile));

        final AtomicInteger lineNo = new AtomicInteger();

        assertFalse(content.contains("while"), "'while' detected in source. Loop is not allowed. Use streams and lambdas in this problem");
        assertFalse(content.contains("for("), "For loop is not allowed. Use streams and lambdas in this problem");

        Files.lines(Path.of(sourceFile)).forEach(line ->
        {
            assertFalse(line.matches("(.*)\\sfor[\\s(](.*)"), "<for> not allowed:" + " line " + lineNo.incrementAndGet()
                    + " " + sourceFile);
        });

    }

    @Test
    @Order(1)
    @GradeValue(1)
    public void add() throws NoSuchFieldException, IllegalAccessException {
        /* check additions from setup */
        Field pField  = CollegeRecords.class.getDeclaredField("records");
        pField.setAccessible(true);
        ArrayList<Registration> theRecords = (ArrayList<Registration>) pField.get(cr);
        assertEquals(testdata.length, theRecords.size(), "Wrong number of record in CollegeRecords");
        assertTrue(theRecords.containsAll(testrecords), "Some records are missing");
    }

    @Test
    @GradeValue(1)
    @Order(2)
    public void getCourseIDMethodExists() throws NoSuchMethodException {
        /* check additions from setup */
        Method m  = Registration.class.getDeclaredMethod("getCourseID");
        testrecords.forEach(r-> {
            try {
                String cID = (String) m.invoke(r);
            } catch (Exception e) {
                fail("Couldn't execute getCourseID ");
            }
        });
    }

    @Test
    @GradeValue(1)
    @Order(2)
    public void getTimeSlotExists() throws NoSuchMethodException {
        /* check additions from setup */
        Method m  = Registration.class.getDeclaredMethod("getTimeSlot");
        testrecords.forEach(r-> {
            try {
                int slot = (int) m.invoke(r);
            } catch (Exception e) {
                fail("Couldn't execute getTimeSLot ");
            }
        });
    }

    @Test
    @GradeValue(1)
    @Order(2)
    public void getSectionMethodExists() throws NoSuchMethodException {
        /* check additions from setup */
        Method m  = Registration.class.getDeclaredMethod("getSection");
        testrecords.forEach(r-> {
            try {
                int slot = (int) m.invoke(r);
            } catch (Exception e) {
                fail("Couldn't execute getSection ");
            }
        });
    }

    @Test
    @GradeValue(1)
    @Order(2)
    public void getStudentMethodExists() throws NoSuchMethodException {
        /* check additions from setup */
        Method m  = Registration.class.getDeclaredMethod("getStudent");
        testrecords.forEach(r-> {
            try {
                String stu = (String) m.invoke(r);
            } catch (Exception e) {
                fail("Couldn't execute getStudent ");
            }
        });
    }

    @Test
    @GradeValue(1)
    @Order(2)
    public void getProfessorMethodExists() throws NoSuchMethodException {
        /* check additions from setup */
        Method m  = Registration.class.getDeclaredMethod("getProfessor");
        testrecords.forEach(r-> {
            try {
                String prof = (String) m.invoke(r);
            } catch (Exception e) {
                fail("Couldn't execute getProfessor");
            }
        });
    }

    @Test
    @GradeValue(1)
    @Order(3)
    public void verifyCourseMethodExists() throws NoSuchMethodException {
        /* check method */
        Class[] parms = { Registration.class };
        Method m  = CollegeRecords.class.getDeclaredMethod("verifyCourseID", parms);
        assertFalse(m.canAccess(cr), "method should be private");
        m.setAccessible(true);
        assertTrue(m.canAccess(cr), "method should be private");
        testrecords.forEach(r-> {
            try {
                boolean prof = (boolean) m.invoke(cr, r);
            } catch (Exception e) {
                fail("Couldn't execute verifyCourseID");
            }
        });
    }

    @Test
    @GradeValue(1)
    @Order(3)
    public void verifySectionExists() throws NoSuchMethodException {
        /* check method */
        Class[] parms = { Registration.class };
        Method m  = CollegeRecords.class.getDeclaredMethod("verifySection", parms);
        assertFalse(m.canAccess(cr), "method should be private");
        m.setAccessible(true);
        assertTrue(m.canAccess(cr), "method should be private");
        testrecords.forEach(r-> {
            try {
                boolean prof = (boolean) m.invoke(cr, r);
            } catch (Exception e) {
                fail("Couldn't execute verifySection");
            }
        });
    }

    @Test
    @GradeValue(1)
    @Order(3)
    public void verifySlotExists() throws NoSuchMethodException {
        /* check method */
        Class[] parms = { Registration.class };
        Method m  = CollegeRecords.class.getDeclaredMethod("verifySlot", parms);
        assertFalse(m.canAccess(cr), "method should be private");
        m.setAccessible(true);
        assertTrue(m.canAccess(cr), "method should be private");
        testrecords.forEach(r-> {
            try {
                boolean prof = (boolean) m.invoke(cr, r);
            } catch (Exception e) {
                fail("Couldn't execute verifySlot");
            }
        });
    }

    @Test
    @Order(4)
    @GradeValue(1)
    public void registrationToString(){
        Arrays.stream(testdata).forEach(s ->{
            String[] sdata = s.split("/");
            Registration r = new Registration(sdata[0], Integer.parseInt(sdata[1]),
                    Integer.parseInt(sdata[2]), sdata[3], sdata[4]);
            Arrays.stream(sdata).forEach(ditem->{
                assertTrue(r.toString().contains(ditem), "include " + s + "in toString()");
            });
        });
    }

    @Test
    @Order(4)
    @GradeValue(1)
    public void collegeRecordsToString(){
        String bigString = cr.toString();
        Arrays.stream(testdata).forEach(s ->{
            String[] sdata = s.split("/");
            Arrays.stream(sdata).forEach(ditem->{
                assertTrue(bigString.contains(ditem), "include " + s + "in toString()");
            });
        });
    }

    @Test
    @GradeValue(1)
    @Order(5)
    public void addValidCourses(){
        Arrays.stream(validCourses).forEach(
                s -> {
                    assertTrue(cr.addValidCourseID(s));
                    assertFalse(cr.addValidCourseID(s), "do not allow courseID to be added twice");
                }
        );

    }

    @Test
    @GradeValue(3)
    @Order(5)
    public void clean(){
        addValidCourses();
        String[] baddata =
                {
                        "COMP 8000/0/15/Bob Jones/B. Hind",
                        "COMP 1501/17/15/Fatima Estossa/B. Hind",
                        "COMP 1501/0/29/Long Britches/B. Hind"
                };
        ArrayList<Registration> badRegistrations = new ArrayList<>();
        int size = cr.count();

        Arrays.stream(baddata).forEach(
                s -> {
                    String[] sdata = s.split("/");
                    Registration r = new Registration(sdata[0], Integer.parseInt(sdata[1]),
                            Integer.parseInt(sdata[2]), sdata[3], sdata[4]);
                    badRegistrations.add(r);
                    cr.add(r);
                });
        assertEquals(size,cr.count() -3, "invalid courses were not added.");
        CollegeRecords cleaned = cr.clean();
        assertEquals(size,cr.count(), "invalid courses were not removed.");
        assertEquals(3,cleaned.count(), "invalid courses should be returned as CollegeRecords.");
    }

    @Test
    @Order(6)
    @GradeValue(2)
    public void testRecordsForStudent() {
        // toString and getStudent must be working for this to work
        CollegeRecords result = cr.recordsForStudent("Long Britches");
        String out = result.toString();
        testrecords.stream().filter(r->r.toString().contains("Long Britches"))
                .forEach(r-> assertTrue(out.contains(r.toString()), "not the correct records"));
        assertEquals(18,result.count(), "Wrong number of records in result");
    }

    @Test
    @Order(6)
    @GradeValue(2)
    public void courseCount() {
        // toString and getStudent must be working for this to work
        assertEquals(18, cr.courseCount("Long Britches"), "course count is not correct");
    }

    @Test
    @Order(6)
    @GradeValue(2)
    public void recordsForProfessor() {
        // toString and getStudent must be working for this to work
        registrationToString();
        CollegeRecords result = cr.recordsForProfessor("D. Pressing");
        String out = result.toString();
        testrecords.stream().filter(r->r.toString().contains("D. Pressing"))
                .forEach(r-> assertTrue(out.contains(r.toString()), "not the correct records"));
        assertEquals(28,result.count(), "Wrong number of records in result");
    }

    @Test
    @Order(6)
    @GradeValue(2)
    public void sectionRecords() {
        CollegeRecords result = cr.sectionRecords("COMP 2704",2); // toString must be working for this to work
        ArrayList<Registration> expected = new ArrayList<>();
        String[] expectstrings = {
                "COMP 2704/2/21/Jim Smith/B. Hind",
                "COMP 2704/2/21/Bob Jones/B. Hind",
                "COMP 2704/2/21/Long Britches/B. Hind",
                "COMP 2704/2/21/While E Cotote/B. Hind",
                "COMP 2704/2/21/Al Dante/B. Hind",
                "COMP 2704/2/21/Justin Case/B. Hind",
                "COMP 2704/2/21/Sally Forth/B. Hind" };
        Arrays.stream(expectstrings).forEach(
            s -> {
                String[] sdata = s.split("/");
                Registration r = new Registration(sdata[0], Integer.parseInt(sdata[1]),
                        Integer.parseInt(sdata[2]), sdata[3], sdata[4]);
                expected.add(r);
            });
        String out = result.toString();
        assertEquals(7,result.count(), "Wrong number of records in result");
        expected.forEach(r-> assertTrue(out.contains(r.toString()), "not the correct records"));
    }

    @Test
    @Order(6)
    @GradeValue(2)
    public void dropAllClassesForStudent() {
            // toString  must be working for this to work
            CollegeRecords result = cr.dropAllClassesForStudent("Long Britches");
            String out = result.toString();
            testrecords.stream().filter(r->r.toString().contains("Long Britches"))
                    .forEach(r-> assertTrue(out.contains(r.toString()), "not the correct records"));
            assertEquals(18,result.count(), "Wrong number of records in result");
            assertFalse(cr.toString().contains("Long Britches"), "not all student records removed");
    }

    @Test
    @Order(6)
    @GradeValue(2)
    public void profCourses() {
        String[] expected = "COMP 1501/CHEM 3060/COMP 3444".split("/");
        String[] notExpected = {"ENGL 2201", "PHYS 2344", "ENGL 2005", "COMP 2704", "PHYS 2377", "CHEM 4927"};
        String result = cr.profCourses("C. Borne");
        Arrays.stream(expected).forEach(s -> {
                assertTrue(result.contains(s), "Missing course " + s);
                assertTrue( result.split(s).length < 3 , s + " occurs multiple times in result");
        });
        Arrays.stream(notExpected).forEach(s ->
                assertFalse(result.contains(s), "Extra course " + s));
    }

    @Test
    @Order(6)
    @GradeValue(2)
    public void removeDuplicates() {

        String[] dupStrings = {
                "COMP 2704/2/21/Jim Smith/B. Hind",
                "COMP 2704/2/21/Bob Jones/B. Hind",
                "COMP 2704/2/21/Long Britches/B. Hind",
                "COMP 2704/2/21/While E Cotote/B. Hind",
                "COMP 2704/2/21/Al Dante/B. Hind",
                "COMP 2704/2/21/Justin Case/B. Hind",
                "COMP 2704/2/21/Sally Forth/B. Hind" };
        ArrayList<Registration> expected = new ArrayList<>();
        Arrays.stream(dupStrings).forEach(
                s -> {
                    String[] sdata = s.split("/");
                    Registration r = new Registration(sdata[0], Integer.parseInt(sdata[1]),
                            Integer.parseInt(sdata[2]), sdata[3], sdata[4]);
                    cr.add(r);
                    cr.add(r);
                    expected.add(r);
                });
        assertEquals(171, cr.count(), "test duplicates not added correctly");
        CollegeRecords result = cr.removeDuplicates();
        assertEquals(157, cr.count(), "test duplicates not removed correctly");
        assertEquals(14, result.count(), "test duplicates not returned correctly");
        CollegeRecords resultresult = result.removeDuplicates();
        assertEquals(7, result.count(), "test duplicates should also have duplicates");
        assertEquals(7, resultresult.count(), "test duplicates should also have test results");
        String out1 = cr.toString();
        String out2 = resultresult.toString();
        expected.stream().forEach(r -> {
            String s = r.toString();
            assertTrue(out1.contains(s), "missing course " + s);
            assertTrue(out2.contains(s), "results are missing course " + s);
        });
    }

    @Test
    @Order(7)
    @GradeValue(4)
    public void chain1() {
        int c =  cr.recordsForStudent("Justin Case").recordsForProfessor("F. Fusive").count();
        assertEquals(5, c, "recordsForStudent/recordsForProfessor/count chain wrong");
    }

    @Test
    @Order(7)
    @GradeValue(4)
    public void chain2() {
        String[] validCourses = {"COMP 1501", "ENGL 2201", "CHEM 3060", "PHYS 2344", "ENGL 2005", "COMP 2704", "PHYS 2377", "CHEM 4927", "COMP 3444"};

        cr.addValidCourseID("COMP 1501");
        cr.addValidCourseID("COMP 2704");
        cr.addValidCourseID("COMP 4927");
        cr.addValidCourseID("COMP 3444");
        CollegeRecords dr =  cr.clean().dropAllClassesForStudent("Justin Case");
        assertEquals(10, dr.count(), "clean/dropAllClassesForStudent/count chain wrong");
        assertEquals(63, cr.count(),"clean/dropAllClassesForStudent chain registrations dropped from wrong object");
    }
}
