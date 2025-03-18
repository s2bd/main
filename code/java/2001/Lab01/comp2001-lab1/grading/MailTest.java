package grading;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import mailtoo.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(GradingTestWatcher.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MailTest {

    MailClient allie, bob, charlie;
    MailServer mailServer;


    @BeforeEach
    void setUp() {
        mailServer = new MailServer();
        allie = new MailClient(mailServer, "Alice");
        bob = new MailClient(mailServer, "Robert");
        charlie = new MailClient(mailServer, "Charles");
    }

    @Test
    @GradeValue(1)
    @Order(1)
    public void sendMail() {
        MailItem mItem = allie.sendMailItem("Robert", "I am bored");
        assertNotNull(mItem, "returning null instead of MailItem");
        assertEquals("I am bored", mItem.getMessage(), "returns incorrect mail item");
    }

    @Test
    @GradeValue(1)
    @Order(3)
    public void sendMailBadTo() {
        MailItem mItem = allie.sendMailItem("", "I am bored");
        assertNull(mItem, "sendMail() should return null for bad To");
    }

    @Test
    @GradeValue(1)
    @Order(4)
    public void sendMailBadMessage() {
        MailItem mItem = allie.sendMailItem("Robert", "");
        assertNull(mItem, "sendMail() should be null for bad message");
        mItem = allie.sendMailItem("Robert", null);
        assertNull(mItem, "sendMail() should be null for bad message");
        assertNull(bob.getNextMailItem(),"sendMail() should not send empty message");
    }

    @Test
    @GradeValue(1)
    @Order(2)
    public void getNextMailItem() {
        MailItem mItem = allie.sendMailItem("Robert", "I am bored");
        assertNotNull(mItem, "sendMailItem() returned null");
        assertEquals(mItem, bob.getNextMailItem(), "does not return correct item.");
    }

    @Test
    @GradeValue(1)
    @Order(5)
    public void reSend() {
        MailItem mItem = allie.sendMailItem("Robert", "I am bored");
        MailItem mItem2 = allie.reSend(mItem, "Charles");
        assertEquals("Charles", mItem2.getTo(), "return item does not have new recipient");
    }

    @Test
    @GradeValue(1)
    @Order(15)
    public  void reSendDoesNotMutate() {
        MailItem mItem = allie.sendMailItem("Robert", "I am bored");
        MailItem mItem2 = allie.reSend(mItem, "Charles");
        assertEquals("Robert", mItem.getTo(),
                "resend() should not modify the original message");
    }

    @Test
    @GradeValue(1)
    @Order(10)
    public void reSendReturnValue() {
        MailItem mItem = allie.sendMailItem("Robert", "I am bored");
        MailItem mItem2 = allie.reSend(mItem, "Charles");

        MailItem receiveA = bob.getNextMailItem();
        mailtoo.MailItem receiveC = charlie.getNextMailItem();

        assertEquals("I am bored", receiveC.getMessage(),
                "reSend() message not found with getNextMailItem()");
    }

    @Test
    @GradeValue(1)
    @Order(11)
    public void reSendDoesNotCorrupt() {
        MailItem mItem = allie.sendMailItem("Robert", "I am bored");
        MailItem mItem2 = allie.reSend(mItem, "Charles");

        mailtoo.MailItem receiveB = bob.getNextMailItem();
        MailItem receiveC = charlie.getNextMailItem();

        assertEquals("Robert", receiveB.getTo(),
                "reSend() corrupted the original message receiver (to)");
    }

    @Test
    @GradeValue(1)
    @Order(9)
    public void reSendMailBadTo() {

        MailItem mItem = allie.sendMailItem("Robert", "I am bored");
        MailItem mItem2 = allie.reSend(mItem, "");

        MailItem receiveB = bob.getNextMailItem();
        MailItem receiveC = charlie.getNextMailItem();

        assertNull(mItem2, "reSendMail() should be null for bad To");
    }

    @Test
    @GradeValue(1)
    @Order(10)
    public void reSendMailBadMessage() {
        MailItem mItem = allie.sendMailItem("Robert", "This is OK");
        MailItem m = new MailItem("Alice", "Bob", "");
        mItem = allie.reSend(m, "Charles");

        assertNull(mItem, "sendMail() should be null for bad message");
        assertNull(charlie.getNextMailItem(),"sendMail() should not send empty message");
    }


}
 