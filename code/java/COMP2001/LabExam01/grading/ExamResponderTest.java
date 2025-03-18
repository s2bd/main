package grading;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import techsupport4.InputReader;
import techsupport4.Responder;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(GradingTestWatcher.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@GradeValue(35)
class ExamResponderTest {

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
    @GradeValue(3)
    public void ruleA() {

        String result;
        int tellMeCount=0;
        for(int i=0; i<400; i++) {
            result = tryResponder("My system crashes all the time!");
            if(result.contains("Tell me about")) tellMeCount++;
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      if (result.contains("Tell me more about")) tellMeCount++;
        }
        assertTrue(tellMeCount > 70, "ruleA not applied often enough");
        assertTrue(tellMeCount < 130, "ruleA applied too often");

    }

    @Test
    @Order(2)
    @GradeValue(2)
    public void ruleB() {
        String result;
        String[] words = {"slow", "installation", "buggy"};
        for (String word : words) {
            int repcount = 0;
            for (int i = 0; i < 10; i++) {
                result = tryResponder("My machine is " + word);
                if (matchResponse(word, result)) repcount++;
            }
            assertEquals(1, repcount, "a mapresponse entry can be used only once");
        }
    }


    @Test
    @Order(3)
    @GradeValue(3)
    public void ruleC() {
        String result;

        tryResponder("I love animals !");
        tryResponder("and I love cookies !");
        tryResponder("But I don't love crashes");
        for(int i=0; i<20; i++) {
            result = tryResponder("I am as I am");
            if(result.contains("Tell me more about")){
                assertTrue(result.contains("love"), "Most frequent word was not chosen");
                return;
            }
        }
        fail("Frequent word not chosen");

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
