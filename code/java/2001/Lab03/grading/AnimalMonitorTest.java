package grading;

import animal.AnimalMonitor;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(GradingTestWatcher.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@GradeValue(30)
public class AnimalMonitorTest {

    private AnimalMonitor am;

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        am = new AnimalMonitor();
        am.addSightings("animal/sightings.csv");
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    @GradeValue(1)
    public void printEndangered() {
        String[] initializers = {"Elephant", "Mountain Gorilla", "Topi", "Horse"};
        ArrayList<String> animals = new ArrayList(java.util.Arrays.asList(initializers));
        assertSame(am, am.printEndangered(animals, 50), "wrong return value");
        String x = outputStreamCaptor.toString();
        assertTrue(outputStreamCaptor.toString().contains("Horse"), "Wrong result printed");
        assertTrue(outputStreamCaptor.toString().contains("Mountain Gorilla"), "Wrong result printed");
        assertFalse(outputStreamCaptor.toString().contains("Topi"), "Wrong result printed");
        assertTrue(outputStreamCaptor.toString().contains("Elephant"), "Wrong result printed");

    }

    @Test
    @GradeValue(10)
    public void codeValidity() throws java.io.IOException {
        String content = Files.readString(Path.of("animal/AnimalMonitor.java"));

        assertFalse(content.contains("while"), "'while' detected in source. Loop is not allowed. Use streams and lambdas in this problem");
        assertFalse(content.contains("for("), "For loop is not allowed. Use streams and lambdas in this problem");
        assertFalse(content.matches("\\sfor[\\s(]"), "For loop is not allowed. Use streams and lambdas in this problem");

    }

    @Test
    @GradeValue(3)
    public void chain1() {
        String[] expected = {
                "Mountain Gorilla, count = 0, area = 2, spotter = 3, period = 0",
                "Buffalo, count = 2, area = 1, spotter = 3, period = 0",
                "Topi, count = 25, area = 1, spotter = 3, period = 0",
                "Buffalo, count = 0, area = 2, spotter = 3, period = 1",
                "Topi, count = 30, area = 2, spotter = 3, period = 1",
                "Topi, count = 30, area = 2, spotter = 3, period = 2",
                "Elephant, count = 24, area = 2, spotter = 3, period = 2"
        };
        assertEquals(28, am.printSightingsBy(3).removeZeroCounts().getCount("Buffalo"), "chain of printSightingsBy/removeZeroCounts/getCount failed");
        String out = outputStreamCaptor.toString();
        for( String e: expected ) assertTrue(out.contains(e), "printSightingsBy did not chain correctly");

    }

    @Test
    @GradeValue(2)
    public void chain2() {
        String[] expected = {
                "Mountain Gorilla, count = 3, area = 1, spotter = 0, period = 0",
                "Buffalo, count = 10, area = 1, spotter = 0, period = 0",
                "Mountain Gorilla, count = 1, area = 2, spotter = 1, period = 0",
                "Mountain Gorilla, count = 3, area = 3, spotter = 2, period = 0",
                "Buffalo, count = 2, area = 1, spotter = 3, period = 0",
                "Topi, count = 25, area = 1, spotter = 3, period = 0",
                "Mountain Gorilla, count = 4, area = 1, spotter = 0, period = 1",
                "Buffalo, count = 16, area = 1, spotter = 0, period = 1",
                "Topi, count = 20, area = 1, spotter = 1, period = 1",
                "Topi, count = 30, area = 2, spotter = 3, period = 1",
                "Mountain Gorilla, count = 1, area = 1, spotter = 0, period = 2",
                "Mountain Gorilla, count = 2, area = 2, spotter = 1, period = 2",
                "Topi, count = 30, area = 2, spotter = 3, period = 2",
                "Elephant, count = 24, area = 2, spotter = 3, period = 2",
        };
        assertEquals(57, am.removeZeroCounts().printList().getCount(2), "chain of removeZeroCounts/printList/getCount failed");
        String out = outputStreamCaptor.toString();
        for( String e: expected ) assertTrue(out.contains(e), "printList did not produce correct output when chained");

    }

    @Test
    @GradeValue(3)
    public void sightingsBy() {
        String animalList = am.sightingsBy(3, 1);
        assertTrue(animalList.contains("Topi"), "problem with sightingsBy");
        assertFalse(animalList.contains("Buffalo"), "problem with sightingsBy");
    }
}