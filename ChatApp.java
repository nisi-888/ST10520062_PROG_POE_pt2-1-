import java.util.Scanner;

public class ChatApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Login userLogin = new Login();

        System.out.println("------ USER REGISTRATION ------");
        System.out.print("Enter First Name: ");
        userLogin.setFirstName(scanner.nextLine());

        System.out.print("Enter Last Name: ");
        userLogin.setLastName(scanner.nextLine());

        //  loop for username validation
        while (true) {
            System.out.print("Enter Username: ");
            userLogin.setUsername(scanner.nextLine());

            if (userLogin.checkUserName()) {
                System.out.println("Username successfully captured.\n");
                break;
            } else {
                System.out.println("\nUsername is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.\n");
            }
        }

        // password validation loop
        while (true) {
            System.out.print("Enter Password: ");
            userLogin.setPassword(scanner.nextLine());

            if (userLogin.checkPasswordComplexity()) {
                System.out.println("Password successfully captured.\n");
                break;
            } else {
                System.out.println("\nPassword is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.\n");
            }
        }

        // cell phone number validation loop
        while (true) {
            System.out.print("Enter Cell Phone Number (e.g., +27838968976): ");
            userLogin.setCellPhoneNumber(scanner.nextLine());

            if (userLogin.checkCellPhoneNumber()) {
                System.out.println("Cell number successfully captured.\n");
                break;
            } else {
                System.out.println("\nCell number is incorrectly formatted or does not contain an international code; please correct the number and try again.\n");
            }
        }

        System.out.println("--- REGISTRATION COMPLETE ---\n");

        // user login interface
        System.out.println("------ USER LOGIN ------");
        while (true) {
            System.out.print("Enter Username: ");
            String loginUser = scanner.nextLine();

            System.out.print("Enter Password: ");
            String loginPass = scanner.nextLine();

            boolean isAuthenticated = userLogin.loginUser(loginUser, loginPass);
            String loginStatusMessage = userLogin.returnLoginStatus(isAuthenticated);
            
            System.out.println("\n" + loginStatusMessage + "\n");

            if (isAuthenticated) {
                break; 
            }
        }
        scanner.close();
    }
}
