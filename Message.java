import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
 
/**
 * Represents a single QuickChat message and tracks the messages
 * sent/stored while the program is running.
 */
public class Message {
 
    // ----- shared state (lives for as long as the program runs) -----
    private static final List<Message> sentMessages = new ArrayList<>();
    private static final List<Message> storedMessages = new ArrayList<>();
    private static int totalSent = 0;
 
    // ----- per-message data -----
    private final String messageID;
    private final int messageNumber;   // number of messages sent when this one was created (starts at 0)
    private final String recipient;
    private final String messageText;
    private final String messageHash;
 
    /** Normal constructor: the message ID is generated randomly. */
    public Message(String recipient, String messageText) {
        this(generateMessageID(), totalSent, recipient, messageText);
    }
 
    /** Constructor that accepts an ID and number, so the hash can be unit tested. */
    public Message(String messageID, int messageNumber, String recipient, String messageText) {
        this.messageID = messageID;
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageHash = createMessageHash();
    }
 
    // ------------------------------------------------------------------
    // Required methods
    // ------------------------------------------------------------------
 
    /** Randomly generated ten-digit number (leading zeros allowed). */
    public static String generateMessageID() {
        long id = ThreadLocalRandom.current().nextLong(0L, 10_000_000_000L);
        return String.format("%010d", id);
    }
 
    /** Ensures the message ID is not more than ten characters. */
    public boolean checkMessageID() {
        return messageID != null && messageID.length() <= 10;
    }
 
    /** Checks the message is not more than 250 characters and returns the matching message. */
    public static String checkMessageLength(String text) {
        if (text.length() <= 250) {
            return "Message ready to send.";
        }
        int over = text.length() - 250;
        return "Message exceeds 250 characters by " + over + "; please reduce the size.";
    }
 
    /**
     * Ensures the recipient number starts with an international code (+ and 1-3 digit
     * country code) followed by no more than ten digits.
     * Regex pattern written with reference to the Java Pattern documentation:
     * Oracle (n.d.) java.util.regex.Pattern. (Replace with your own Harvard/APA reference.)
     */
    public String checkRecipientCell() {
        return checkRecipientCell(recipient);
    }
 
    public static String checkRecipientCell(String number) {
        if (number != null && number.matches("^\\+\\d{1,3}\\d{1,10}$")) {
            return "Cell phone number successfully captured.";
        }
        return "Cell phone number is incorrectly formatted or does not contain an international code. "
                + "Please correct the number and try again.";
    }
 
    /** Format: first two digits of ID : message number : FIRSTWORD + LASTWORD (all caps). */
    public String createMessageHash() {
        String[] words = messageText.trim().split("\\s+");
        String first = words[0].replaceAll("[^A-Za-z0-9]", "");
        String last = words[words.length - 1].replaceAll("[^A-Za-z0-9]", "");
        String prefix = messageID.length() >= 2 ? messageID.substring(0, 2) : messageID;
        return (prefix + ":" + messageNumber + ":" + first + last).toUpperCase();
    }
 
    /**
     * Lets the user send, disregard or store the message.
     * 1 = send, 2 = disregard, 3 = store.
     */
    public String sentMessage(int choice) {
        switch (choice) {
            case 1:
                sentMessages.add(this);
                totalSent++;
                return "Message successfully sent.";
            case 2:
                return "Press 0 to delete the message.";
            case 3:
                storeMessage();
                return "Message successfully stored.";
            default:
                return "Invalid option.";
        }
    }
 
    /** Returns all messages sent while the program is running. */
    public static String printMessages() {
        if (sentMessages.isEmpty()) {
            return "No messages have been sent yet.";
        }
        StringBuilder sb = new StringBuilder();
        for (Message m : sentMessages) {
            sb.append(m.getDetails()).append("\n");
        }
        return sb.toString();
    }
 
    /** Returns the total number of messages sent. */
    public static int returnTotalMessages() {
        return totalSent;
    }
 
    /**
     * Stores the message in a JSON file (messages.json). All stored messages are
     * written as a JSON array. JSON syntax follows json.org (n.d.) "Introducing JSON".
     * (Replace with your own Harvard/APA reference.)
     */
    public void storeMessage() {
        storedMessages.add(this);
        StringBuilder json = new StringBuilder("[\n");
        for (int i = 0; i < storedMessages.size(); i++) {
            Message m = storedMessages.get(i);
            json.append("  {\n")
                .append("    \"messageID\": \"").append(escape(m.messageID)).append("\",\n")
                .append("    \"messageHash\": \"").append(escape(m.messageHash)).append("\",\n")
                .append("    \"recipient\": \"").append(escape(m.recipient)).append("\",\n")
                .append("    \"message\": \"").append(escape(m.messageText)).append("\"\n")
                .append("  }").append(i < storedMessages.size() - 1 ? "," : "").append("\n");
        }
        json.append("]\n");
        try (FileWriter writer = new FileWriter("messages.json")) {
            writer.write(json.toString());
        } catch (IOException e) {
            System.out.println("Could not write to messages.json: " + e.getMessage());
        }
    }
 
    // ------------------------------------------------------------------
    // Helpers
    // ------------------------------------------------------------------
 
    private static String escape(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"")
                .replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t");
    }
 
    /** Message ID, Message Hash, Recipient, Message - in the order the brief requires. */
    public String getDetails() {
        return "Message ID: " + messageID
                + "\nMessage Hash: " + messageHash
                + "\nRecipient: " + recipient
                + "\nMessage: " + messageText + "\n";
    }
 
    public String getMessageID() { return messageID; }
    public String getMessageHash() { return messageHash; }
    public String getRecipient() { return recipient; }
    public String getMessageText() { return messageText; }
 
    /** Clears shared state - used by the unit tests so they do not affect each other. */
    public static void resetState() {
        sentMessages.clear();
        storedMessages.clear();
        totalSent = 0;
    }
}
 
