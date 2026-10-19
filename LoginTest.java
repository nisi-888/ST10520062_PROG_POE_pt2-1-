import org.junit.Test;
import static org.junit.Assert.*;

public class LoginTest {

    // Logic Verification Tests  

    @Test
    public void testCheckUserName_Valid() {
        Login login = new Login();
        login.setUsername("kyl_1");
        assertTrue(login.checkUserName());
    }

    @Test
    public void testCheckUserName_Invalid() {
        Login login = new Login();
        login.setUsername("kyle!!!!!!");
        assertFalse(login.checkUserName());
    }

    @Test
    public void testCheckPasswordComplexity_Valid() {
        Login login = new Login();
        login.setPassword("Ch&&&sec@ke99!");
        assertTrue(login.checkPasswordComplexity());
    }

    @Test
    public void testCheckPasswordComplexity_Invalid() {
        Login login = new Login();
        login.setPassword("password");
        assertFalse(login.checkPasswordComplexity());
    }

    @Test
    public void testCheckCellPhoneNumber_Valid() {
        Login login = new Login();
        login.setCellPhoneNumber("+27838968976");
        assertTrue(login.checkCellPhoneNumber());
    }

    @Test
    public void testCheckCellPhoneNumber_Invalid() {
        Login login = new Login();
        login.setCellPhoneNumber("08966553");
        assertFalse(login.checkCellPhoneNumber());
    }

    @Test
    public void testLoginUser_Success() {
        Login login = new Login("Kyle", "Smith", "kyl_1", "Ch&&&sec@ke99!", "+27838968976");
        assertTrue(login.loginUser("kyl_1", "Ch&&&sec@ke99!"));
    }

    @Test
    public void testLoginUser_Failed() {
        Login login = new Login("Kyle", "Smith", "kyl_1", "Ch&&&sec@ke99!", "+27838968976");
        assertFalse(login.loginUser("kyl_1", "wrongpassword"));
    }

    // --- Message Exact String Verification Tests (assertEquals) ---

    @Test
    public void testRegisterUser_UsernameInvalidMessage() {
        Login login = new Login("Kyle", "Smith", "kyle!!!!!!", "Ch&&&sec@ke99!", "+27838968976");
        String expected = "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        assertEquals(expected, login.registerUser());
    }

    @Test
    public void testRegisterUser_PasswordInvalidMessage() {
        Login login = new Login("Kyle", "Smith", "kyl_1", "password", "+27838968976");
        String expected = "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        assertEquals(expected, login.registerUser());
    }

    @Test
    public void testRegisterUser_CellPhoneInvalidMessage() {
        Login login = new Login("Kyle", "Smith", "kyl_1", "Ch&&&sec@ke99!", "08966553");
        String expected = "Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.";
        assertEquals(expected, login.registerUser());
    }
}

