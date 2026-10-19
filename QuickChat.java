import java.util.Scanner;
 

//(Part 2) QuickChat menu. Call QuickChat.run(scanner) from ChatApp.main only after the user has logged in successfully.

public class QuickChat {
 
    public static void run(Scanner scanner) {
        System.out.println("Welcome to QuickChat.");
 
        int maxMessages = readInt(scanner, "How many messages do you wish to enter? ", 1, Integer.MAX_VALUE);
        int entered = 0;
 
        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();
 
            if (choice.equals("1")) {
                if (entered >= maxMessages) {
                    System.out.println("You have already entered your " + maxMessages + " message(s).");
                    continue;
                }
                entered += handleSend(scanner) ? 1 : 0;
            } else if (choice.equals("2")) {
                System.out.println("Coming Soon.");
            } else if (choice.equals("3") || choice.equalsIgnoreCase("quit")) {
                System.out.println("\nTotal messages sent: " + Message.returnTotalMessages());
                System.out.println("Goodbye!");
                break;
            } else {
                System.out.println("Invalid option, please choose 1, 2 or 3.");
            }
        }
    }
 
    //Returns true if a message slot was used (sent, stored or disregarded)
    private static boolean handleSend(Scanner scanner) {
        // Recipient
        String recipient;
        while (true) {
            System.out.print("Enter recipient cell number (with international code, e.g. +27718693002): ");
            recipient = scanner.nextLine().trim();
            String result = Message.checkRecipientCell(recipient);
            System.out.println(result);
            if (result.equals("Cell phone number successfully captured.")) break;
        }
 
        // Message text
        String text;
        while (true) {
            System.out.print("Enter your message: ");
            text = scanner.nextLine();
            if (text.length() <= 250) {
                System.out.println("Message ready to send.");
                break;
            }
            System.out.println("Please enter a message of less than 250 characters.");
            System.out.println(Message.checkMessageLength(text));
        }
 
        Message message = new Message(recipient, text);
 
        // Send / disregard / store
        int option;
        while (true) {
            System.out.println("\n1) Send Message\n2) Disregard Message\n3) Store Message to send later");
            option = readInt(scanner, "Choose an option: ", 1, 3);
            break;
        }
 
        String result = message.sentMessage(option);
        System.out.println(result);
 
        if (option == 1) {
            System.out.println("\n" + message.getDetails());
        } else if (option == 2) {
            System.out.print("");
            String zero = scanner.nextLine().trim();
            if (zero.equals("0")) {
                System.out.println("Message deleted.");
            }
        }
        return true;
    }
 
    private static int readInt(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = Integer.parseInt(scanner.nextLine().trim());
                if (value >= min && value <= max) return value;
            } catch (NumberFormatException ignored) { }
            System.out.println("Please enter a valid number.");
        }
    }
}

