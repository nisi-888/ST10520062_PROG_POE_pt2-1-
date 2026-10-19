import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
 
public class MessageTest {
 
    private static final String MSG1 = "Hi Mike, can you join us for dinner tonight?";
    private static final String NUM1 = "+27718693002";
    private static final String MSG2 = "Hi Keegan, did you receive the payment?";
    private static final String NUM2 = "08575975889";
 
    @Before
    public void reset() {
        Message.resetState();
    }
 
    // Message length 
    @Test
    public void testMessageLength_Success() {
        assertEquals("Message ready to send.", Message.checkMessageLength(MSG1));
    }
 
    @Test
    public void testMessageLength_Failure() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 260; i++) sb.append('a');
        assertEquals("Message exceeds 250 characters by 10; please reduce the size.",
                Message.checkMessageLength(sb.toString()));
    }
 
    // Recipient number 
    @Test
    public void testRecipient_Success() {
        assertEquals("Cell phone number successfully captured.", Message.checkRecipientCell(NUM1));
    }
 
    @Test
    public void testRecipient_Failure() {
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. "
                + "Please correct the number and try again.", Message.checkRecipientCell(NUM2));
    }
 
    // Message hash 
    @Test
    public void testMessageHash_TestCase1() {
        Message m = new Message("0012345678", 0, NUM1, MSG1);
        assertEquals("00:0:HITONIGHT", m.createMessageHash());
    }
 
    @Test
    public void testMessageHash_Loop() {
        String[] ids = {"0012345678", "1234567890"};
        String[] texts = {MSG1, MSG2};
        String[] expected = {"00:0:HITONIGHT", "12:1:HIPAYMENT"};
        for (int i = 0; i < texts.length; i++) {
            Message m = new Message(ids[i], i, NUM1, texts[i]);
            assertEquals(expected[i], m.createMessageHash());
        }
    }
 
    //  Message ID 
    @Test
    public void testMessageID_Created() {
        Message m = new Message(NUM1, MSG1);
        assertEquals(10, m.getMessageID().length());
        assertTrue(m.checkMessageID());
        assertEquals("Message ID generated: " + m.getMessageID(),
                "Message ID generated: " + m.getMessageID());
    }
 
    // Send / Disregard / Store 
    @Test
    public void testMessageSent_Send() {
        assertEquals("Message successfully sent.", new Message(NUM1, MSG1).sentMessage(1));
    }
 
    @Test
    public void testMessageSent_Disregard() {
        assertEquals("Press 0 to delete the message.", new Message(NUM2, MSG2).sentMessage(2));
    }
 
    @Test
    public void testMessageSent_Store() {
        assertEquals("Message successfully stored.", new Message(NUM1, MSG1).sentMessage(3));
    }
 
    // Totals (test data: 2 messages, one sent, one discarded) 
    @Test
    public void testTotalMessagesSent() {
        new Message(NUM1, MSG1).sentMessage(1);
        new Message(NUM2, MSG2).sentMessage(2);
        assertEquals(1, Message.returnTotalMessages());
    }
 
    @Test
    public void testPrintMessages() {
        new Message(NUM1, MSG1).sentMessage(1);
        assertTrue(Message.printMessages().contains(MSG1));
    }
}
 
