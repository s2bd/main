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
class ExamRegistrarTests {

    String[] validCourses = {"COMP 1501", "ENGL 2201", "CHEM 3060", "PHYS 2344", "ENGL 2005", "COMP 2704", "PHYS 2377", "CHEM 4927", "COMP 3444"};
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

    /* use a hashmap to maintain a cap (capacity) limit for the maximum enrollments (number of registrrations)
    in a course.
   All sections count in the enrollment for a course.
 */
    @Order(4)
    @GradeValue(1)
    @Test
    public void testHasCapacityField() throws NoSuchFieldException {
        Field capField = CollegeRecords.class.getDeclaredField("capacity");
        Type t = capField.getType();
        assertEquals("java.util.HashMap", t.getTypeName(), "capacity is wrong type");
        ParameterizedType genericType = (ParameterizedType) capField.getGenericType();
        assertEquals("class java.lang.String", genericType.getActualTypeArguments()[0].toString(), "capacity is wrong type");
        assertEquals("class java.lang.Integer", genericType.getActualTypeArguments()[1].toString(), "capacity is wrong type");
        assertFalse(capField.canAccess(cr), "capacity has wrong modifier");
    }



    @Test
    @Order(5)
    @GradeValue(1)
    public void setMaxCapacity() throws NoSuchFieldException, IllegalAccessException {

        Field capField = CollegeRecords.class.getDeclaredField("capacity");
        capField.setAccessible(true);
        HashMap<String,Integer> x = (HashMap<String, Integer>) capField.get(cr);
        for( int v = 10; v < 100; v *= 10 ) {
            for (String c : validCourses) {
                cr.setMaxCapacity(c, v);
            }
            for (String c : validCourses) {
                assertEquals(v, x.get(c), "value not put in capacity correctly");
            }
        }
    }
/*
add the registration only id the course has not exceeded capacity, if capacioty has been set
 */
    @Test
    @Order(6)
    @GradeValue(3)
    public void safeAdd(){
        String[] additionalrecords = {
                "COMP 2704/2/21/Jim Smith/Hind",
                "COMP 1501/2/21/Bob Jones/Hind",
                "ENGL 2005/2/21/Long Britches/Fusive",
                "COMP 2704/2/21/While E Cotote/Hind",
                "COMP 2704/2/21/Al Dante/X Y Z Seeyah",
                "COMP 2704/2/21/Justin Case/Talker",
                "COMP 2704/2/21/Sally Forth/Borne" };
        for (String c : validCourses) {
            cr.setMaxCapacity(c, 10);
        }
        for (String s : additionalrecords) {
            Registration r = splitRegistration(s);
            assertFalse(cr.safeAdd(r), "capacity exceeded");
        }
        for (String c : validCourses) {
            cr.setMaxCapacity(c, 100);
        }
        for (String s : additionalrecords) {
            Registration r = splitRegistration(s);
            assertTrue(cr.safeAdd(r), "capacity not exceeded");
        }
        assertEquals(164, cr.count(), "registration not added");
    }

    @Test
    @Order(6)
    @GradeValue(2)
    public void safeAddEmpty(){
        String[] additionalrecords = {
                "COMP 2704/2/21/Jim Smith/Hind",
                "COMP 1501/2/21/Bob Jones/Hind",
                "ENGL 2005/2/21/Long Britches/Fusive",
                "COMP 2704/2/21/While E Cotote/Hind",
                "COMP 2704/2/21/Al Dante/X Y Z Seeyah",
                "COMP 2704/2/21/Justin Case/Talker",
                "COMP 2704/2/21/Sally Forth/Borne" };

        for (String s : additionalrecords) {
            Registration r = splitRegistration(s);
            assertTrue(cr.safeAdd(r), "capacity not exceeded");
        }
        assertEquals(164, cr.count(), "registration not added");
    }


    /* CollegeRecords toString should list each records' strings surrounded by brackets [] and
       separated by semi-colon ";". The trailing (last) string should not have a semicolon.
       Four example,
    */
    @Test
    @Order(1)
    @GradeValue(1)
    public void testToString() {
        String bigString = cr.toString();
        String[] allStrings = bigString.split(";");
        assertEquals(testrecords.size(), allStrings.length, "missing semi-colons");
        for(Registration r : testrecords){
            assertTrue(bigString.contains(r.toString()), "missing records");
        }
        for(String s: allStrings){
            String t = s.trim();
            assertEquals('[', t.charAt(0), "records should start with bracket [");
            assertEquals('[', t.charAt(0), "records should end with bracket ]");
        }
    }



    @Test
    @Order(2)
    @GradeValue(1)
    public void testToStringReduce() throws java.io.IOException {
        assertTrue(codeMethHas("registrar/CollegeRecords.java", "toString", "reduce("), "must use reduce method");
        assertNoLoops("registrar/CollegeRecords.java", "toString");
    }

    @Test
    @Order(2)
    @GradeValue(1)
    /*
      add a getProfLast() method to Registration that returns the last name of the prof according to the blank separators.
      This is the same as the last word in the professor name field.
      If the professor has one name, return it.
      If there is no professor name, it should return null.
     */
    public void profLast(){
        String[] lastNames = {"Pressing", "Talker", "Seeyah", "Fusive", "Hind", "Borne"};
        testrecords.forEach(r -> {
            String lname = r.getProfLast();
            assertTrue(Arrays.asList(lastNames).contains(lname), lname + " is not a valid professor last name");
        });
    }

    @Test
    @Order(3)
    @GradeValue(1)
    /*
      add a getProfLast() method to Registration that returns the last name of the prof according to the blank separators.
      This is the same as the last word in the professor name field.
      If the professor has one name, return it.
      If there is no professor name, it should return null.
     */
    public void profLast_OneNames(){
        String[] additionalrecords = {
                "COMP 2704/2/21/Jim Smith/  Hind",
                "COMP 2704/2/21/Bob Jones/Hind",
                "COMP 2704/2/21/Long Britches/ Fusive",
                "COMP 2704/2/21/While E Cotote/Hind",
                "COMP 2704/2/21/Al Dante/X Y Z Seeyah",
                "COMP 2704/2/21/Justin Case/  Talker",
                "COMP 2704/2/21/Sally Forth/Borne" };
        String[] lastNames = {"Pressing", "Talker", "Seeyah", "Fusive", "Hind", "Borne"};
        insertTestRecords(additionalrecords);
        testrecords.forEach(r -> {
            String lname = r.getProfLast();
            assertTrue(Arrays.asList(lastNames).contains(lname), lname + " is not a valid professor last name");
        });
    }

    @Test
    @Order(3)
    @GradeValue(1)
    public void profLast_NoNames(){
        String[] additionalrecords = {
                "COMP 2704/2/21/Jim Smith/  ",
                "COMP 2704/2/21/Bob Jones/  ",
                "COMP 2704/2/21/Long Britches/ ",
                "COMP 2704/2/21/While E Cotote/  " };
        String[] lastNames = {"Pressing", "Talker", "Seeyah", "Fusive", "Hind", "Borne"};
        insertTestRecords(additionalrecords);
        testrecords.forEach(r -> {
            String lname = r.getProfLast();
            if( lname != null)
                assertTrue(Arrays.asList(lastNames).contains(lname), lname + " is not a valid professor last name");
        });
    }

    public void assertNoLoops(String sourceFile, String methName) throws java.io.IOException {

        String fContent = Files.readString(Path.of(sourceFile));
        String[] srcLines = fContent.lines().toArray(String[]::new);

        int[] methLines = findMethod(srcLines, methName);
        assertNotNull(methLines, methName + " method not found");

        removeComments(srcLines, methLines[0], methLines[1]+1);

        for(int i = methLines[0]; i <= methLines[1]; i++) {
            String looptype = lineXLoops(srcLines[i]);
            assertNull(looptype, methName + "(): remove " + looptype + " loop on line " + Integer.toString(i+1));
        }
    }
    public boolean codeMethHas(String sourceFile, String methName, String contentString) throws java.io.IOException {

        String fContent = Files.readString(Path.of(sourceFile));
        String[] srcLines = fContent.lines().toArray(String[]::new);

        int[] methLines = findMethod(srcLines, methName);
        assertNotNull(methLines, sourceFile + "." + methName + " method not found");

        removeComments(srcLines, methLines[0], methLines[1]+1);

        for(int i = methLines[0]; i <= methLines[1]; i++) {
            if ( srcLines[i].contains(contentString)) return true;
        }
        return false;
    }
    String lineXLoops(String codeLine){
        if( codeLine.matches("(.*)\\sfor[\\s(](.*)" ) ) return "for";
        if( codeLine.matches("(.*)\\swhile[\\s(](.*)" ) ) return "while";
        return null;
    }

    void removeComments(String[] src, int start, int end){
        for(int i=start; i<end; i++)
            src[i] =  src[i].replaceAll("(?:/\\*(?:[^*]|(?:\\*+[^*/]))*\\*+/)|(?://.*)","");

    }

    int[] findMethod(String[] srcLines, String methodName){
        int bounds[] = new int[] { -1, -1 };
        int bracecount = 0;
        int index = 0;
        while( bounds[0] < 0 ){
            if( srcLines[index].matches("[^()]*" + methodName + "[^()]*\\([^}]*\\).*") ) bounds[0] = index;
            else index++;
            if( index >= srcLines.length ) return null;
        }
        while( bounds[1] < 0 ){
            // find the method body
            for( char c : srcLines[index].toCharArray()){
                if( c == '{' ) bracecount++;
                if( c == '}' ){
                    bracecount--;
                    if( bracecount == 0 ){
                        bounds[1] = index;
                        return bounds;
                    }
                    if( bracecount < 0 )
                        throw new IndexOutOfBoundsException(methodName + " method syntax problem ");
                }
            }
            index++;
            if( index >= srcLines.length )
                throw new IndexOutOfBoundsException(methodName + " method braces {} don't match ");
        }
        return null;
    }

    /**
     * modify the conflicts method so it returns the correct values
     */
    @Test
    @Order(10)
    @GradeValue(2)
    public void conflicts() {
        /* check additions from setup */
        CollegeRecords c = cr.conflicts();
        // Field pField  = CollegeRecords.class.getDeclaredField("records");
        // pField.setAccessible(true);
        // ArrayList<Registration> theRecords = (ArrayList<Registration>) pField.get(c);
        assertEquals(118, c.count(), "wrong number of records in conflicts");
    }

    /**
     * modify the conflicts method to use functional style without any explicit loops.
     * @throws IOException
     */
    @Test
    @Order(11)
    @GradeValue(3)
    public void conflicts_NoLoops() throws IOException {
        /* check additions from setup */
        assertNoLoops("registrar/CollegeRecords.java", "conflicts");
    }

    /**
     * wrtie a teaching method for a specific prof will return a hash map where the key strings are courseIDs that prof is teaching  and the count in the number of regoistrations for the course.  You can ignore section numbers ofr this method.
     */

    @Test
    @Order(12)
    @GradeValue(3)
    public void teaching() throws IOException {
        /* check additions from setup */
        HashMap<String,Integer> hm = cr.teaching("D. Pressing");
        assertEquals(4,hm.size(),"Wrong number of courses");
        assertEquals(5, hm.get("ENGL 2201"), "Wrong number of student registrations");
        assertEquals(8, hm.get("COMP 3444"), "Wrong number of student registrations");
        assertEquals(7, hm.get("ENGL 2005"), "Wrong number of student registrations");
        assertEquals(8, hm.get("COMP 2704"), "Wrong number of student registrations");
    }

    @Test
    @Order(13)
    @GradeValue(2)
    public void teaching_NoLoops() throws IOException {
        teaching();
        assertNoLoops("registrar/CollegeRecords.java", "teaching");
    }

    /**
     * Change verifyCourseID modifiers so it can be called from outside the class.
     * and so that it can be called without using an instance of CollegeRecords.
     *
     * Change the clean method so it call the new version of verifyCourseId correctly
     */
    @Test
    @Order(7)
    @GradeValue(1)
    public void verifyCourseID() throws NoSuchMethodException {
        Class[] parms = { Registration.class };
        Method m  = CollegeRecords.class.getDeclaredMethod("verifyCourseID", parms);
        assertTrue(Modifier.isPublic(m.getModifiers()), "method can't be accessed from outside");
        assertTrue(Modifier.isStatic(m.getModifiers()), "method can't be used without instance");
    }

    @Test
    @GradeValue(1)
    @Order(8)
    public void verifyCourseID_clean() throws NoSuchMethodException {
        verifyCourseID();
        String[] baddata =
                {
                        "COMP 800/0/15/Bob Jones/B. Hind",
                        "COMP 101/17/15/Fatima Estossa/B. Hind",
                        "COP  2501/0/29/Long Britches/B. Hind"
                };
        int size = cr.count();
        insertTestRecords(baddata);
        assertEquals(size,cr.count() -3, "invalid courses were not added.");
        CollegeRecords cleaned = cr.clean();
        assertEquals(size,cr.count(), "invalid courses were not removed.");
    }

}
