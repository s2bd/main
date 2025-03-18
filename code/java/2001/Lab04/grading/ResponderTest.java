package grading;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import techsupport4.InputReader;
import techsupport4.Responder;
import techsupport4.SupportSystem;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(GradingTestWatcher.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@GradeValue(8)
class ResponderTest {

    private InputReader reader;
    private Responder responder;
    private ResponderData data;

    private String tryResponder(String tryInput) {
        return responder.generateResponse(reader.wordSet(tryInput));
    }

    private boolean matchResponse(String keyword, String response) {
        String[] expected = data.responseMap.get(keyword).split("\n");
        for (String exString : expected)
            if (!response.contains(exString)) return false;
        return true;
    }

    private void assertResponse(String keyword, String response) {
        assertTrue(matchResponse(keyword, response), "Did not get response for " + keyword);
    }

    private void assertNotResponse(String keyword, String response) {
        assertFalse(matchResponse(keyword, response), "Should not get response for " + keyword);
    }

    @BeforeEach
    void setUp() {
        /* similar to system support loop */
        reader = new InputReader();
        responder = new Responder();
        data = new ResponderData();

    }

    @Test
    @Order(1)
    @GradeValue(1)
    public void rule1() {

        String result;

        result = tryResponder("My system crashes all the time!");
        assertResponse("crashes", result);

        result = tryResponder("I still have crashes but also its slow");
        assertResponse("slow", result);

        result = tryResponder("Why do I have have crashes ?");
        assertNotResponse("crashes", result);

        result = tryResponder("And why is it still slow ?");
        assertNotResponse("slow", result);

        result = tryResponder("Why don't you help me !");
        assertNotResponse("slow", result);
        assertNotResponse("crashes", result);

        result = tryResponder("I said its slow and it crashes !");
        assertNotResponse("slow", result);
        assertNotResponse("crashes", result);

        result = tryResponder("What bout the crashes and slow stuff?");
        assertNotResponse("slow", result);
        assertResponse("crashes", result);

        result = tryResponder("Its still slow and it still crashes !");
        assertResponse("slow", result);
        assertNotResponse("crashes", result);
    }

    @Test
    @Order(2)
    @GradeValue(1)
    public void rule2() {
        String result;

        result = tryResponder("My machine is slow !");
        assertResponse("slow", result);

        result = tryResponder("My machine is slow and I think it's buggy !");
        assertResponse("buggy", result);

        result = tryResponder("Slow buggy crappy performance and more besides !");
        assertResponse("performance", result);

        tryResponder("Can you help me?");

        result = tryResponder("Maybe it's a slow memory problem?");
        assertResponse("memory", result);

        result = tryResponder("I've never had a memory problem before.");
        assertNotResponse("memory", result);

        result = tryResponder("Could memory or bad installation be making it slow ?");
        assertResponse("slow", result);
    }

    @Test
    @Order(3)
    @GradeValue(1)
    public void rule3() {
        String result;

        result = tryResponder("My machine is slow !");
        assertResponse("slow", result);

        result = tryResponder("My machine is slow and I think it's buggy !");
        assertResponse("buggy", result);

        result = tryResponder("Maybe it's a memory problem?");
        assertResponse("memory", result);

        result = tryResponder("Slow buggy crappy performance and more besides !");
        assertResponse("performance", result);

        tryResponder("Can you help me?");

        result = tryResponder("I've never had a memory problem before");
        assertNotResponse("memory", result);

        tryResponder("I am waiting for a good answer");

        tryResponder("But you don't seem to have one - a good memory I mean - haha.");

        // tie breaker
        result = tryResponder("Could memory or bad installation be making it slow ?");
        boolean slowResponse = matchResponse("slow", result);
        boolean memoryResponse = matchResponse("memory", result);

        result = tryResponder("Do you think it's memory making it slow ?");
        boolean slowResponse2 = matchResponse("slow", result);
        boolean memoryResponse2 = matchResponse("memory", result);

        assertTrue(slowResponse == memoryResponse2, "Tie breaker not observing rule 1");
        assertTrue(slowResponse2 == memoryResponse, "Tie breaker not observing rule 1");

    }


    @Test
    @Order(4)
    @GradeValue(1)
    public void rule4() {

        String result;

        result = tryResponder("My system crashes all the time!");
        assertResponse("crashes", result);
        assertEquals(data.responseMap.get("crashes"), result, "First tine, do not randomize sentences.");

        result = tryResponder("I still have crashes but also its slow");
        assertResponse("slow", result);
        assertEquals(data.responseMap.get("slow"), result, "First tine, do not randomize sentences.");

        result = tryResponder("Why do I have have crashes ?");
        assertNotResponse("crashes", result);

        result = tryResponder("And why is it still slow ?");
        assertNotResponse("slow", result);

        result = tryResponder("Why don't you help me !");
        assertNotResponse("slow", result);
        assertNotResponse("crashes", result);

        result = tryResponder("I said its slow and it crashes !");
        assertNotResponse("slow", result);
        assertNotResponse("crashes", result);

        result = tryResponder("What bout the crashes and slow stuff?");
        assertNotResponse("slow", result);
        assertResponse("crashes", result);
        assertNotEquals(data.responseMap.get("crashes"), result, "Sentences are not randomized");

        result = tryResponder("Its still slow and it still crashes !");
        assertResponse("slow", result);
        assertNotResponse("crashes", result);
        assertNotEquals(data.responseMap.get("slow"), result, "Sentences are not randomized");
    }
}

/**
 * Mock responder is a copy of the responder class so the strings are available to the unit tests.
 * A shared data file would be a better solution. Repeating strings that might change is bad coding.
 */
class ResponderData
{
    // Used to map key words to responses.

    public HashMap<String, String> responseMap;

    public ResponderData()
    {   responseMap = new HashMap<>();
        fillResponseMap();
    }

    /**
     * Enter all the known keywords and their associated responses
     * into our response map.
     */
    private void fillResponseMap()
    {
        responseMap.put("crash",
                "Well, it never crashes on our system.\n" +
                        "It must have something to do with your system.\n" +
                        "Tell me more about your configuration.");
        responseMap.put("crashes",
                "That is really unfortunate.\n" +
                        "I recommend that you check for a virus.\n" +
                        "Is there software running that you do not recognize?");
        responseMap.put("slow",
                "I think this has to do with your hardware.\n" +
                        "Upgrading your processor should solve all performance problems.\n" +
                        "Have you got a problem with our software?");
        responseMap.put("performance",
                "Performance was quite adequate in all our tests.\n" +
                        "Are you running any other processes in the background?");
        responseMap.put("bug",
                "Well, you know, all software has some bugs.\n" +
                        "Our software engineers are working very hard to fix them.\n" +
                        "Can you describe the problem a bit further?");
        responseMap.put("buggy",
                "It is possible there are some bugs.\n" +
                        "We can only work on problems that are brought to our attention.\n" +
                        "It might help if you described the problem a bit more.");
        responseMap.put("windows",
                "This is a known bug to do with the Windows operating system.\n" +
                        "Please report it to Microsoft.\n" +
                        "There is nothing we can do about this.");
        responseMap.put("mac",
                "This is a known bug to do with the Mac operating system.\n" +
                        "Please report it to Apple.\n" +
                        "rThere is nothing we can do about this.");
        responseMap.put("expensive",
                "The cost of our product is quite competitive.\n" +
                        "Have you looked around and really compared our features?");
        responseMap.put("installation",
                "The installation is really quite straight forward.\n" +
                        "We have tons of wizards that do all the work for you.\n" +
                        "Have you read the installation instructions?");
        responseMap.put("memory",
                "If you read the system requirements carefully.\n" +
                        "The specified memory requirements are 1.5 giga byte.\n" +
                        "You really should upgrade your memory.");
        responseMap.put("linux",
                "We take Linux support very seriously.\n" +
                        "There are some problems.\n" +
                        "Most of the problems have to do with incompatible glibc versions.\n" +
                        "Can you be a bit more precise?");
        responseMap.put("bluej",
                "BlueJ, yes.\n" +
                        "We tried to buy out those guys long ago.\n" +
                        "They simply won't sell... Stubborn people they are.\n" +
                        "We don't support it, I'm afraid.");
    }

}
