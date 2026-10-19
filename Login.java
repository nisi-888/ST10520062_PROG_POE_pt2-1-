public class Login {
    // User fields
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String cellPhoneNumber;

    // Default Constructor
    public Login() {
    }

    // Overloaded Constructor
    public Login(String firstName, String lastName, String username, String password, String cellPhoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.cellPhoneNumber = cellPhoneNumber;
    }

    // Getters & Setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCellPhoneNumber() {
        return cellPhoneNumber;
    }

    public void setCellPhoneNumber(String cellPhoneNumber) {
        this.cellPhoneNumber = cellPhoneNumber;
    }

    
     //Ensures username contains an underscore (_) and is no more than 5 characters 
     
    public boolean checkUserName() {
        if (this.username == null) {
            return false;
        }
        return this.username.contains("_") && this.username.length() <= 5;
    }

  
     //Checks if the password meets complexity rules
    public boolean checkPasswordComplexity() {
        if (this.password == null || this.password.length() < 8) {
            return false;
        }

        boolean hasCapital = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        for (char c : this.password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasCapital = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (!Character.isLetterOrDigit(c)) {
                hasSpecial = true;
            }
        }

        return hasCapital && hasDigit && hasSpecial;
    }

    //Validates South African cell phone number with international country code
    
    public boolean checkCellPhoneNumber() {
        if (this.cellPhoneNumber == null) {
            return false;
        }
        String regex = "^\\+\\d{11,12}$";
        return this.cellPhoneNumber.matches(regex);
    }

    // Evaluates validation checks and returns specific success or error messages
    public String registerUser() {
        if (!checkUserName()) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }
        if (!checkPasswordComplexity()) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        if (!checkCellPhoneNumber()) {
            return "Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.";
        }
        return "Username successfully captured.\nPassword successfully captured.\nCell number successfully captured.";
    }

    //Verifies entered login credentials against registered user credentials
    public boolean loginUser(String enteredUsername, String enteredPassword) {
        if (this.username == null || this.password == null) {
            return false;
        }
        return this.username.equals(enteredUsername) && this.password.equals(enteredPassword);
    }

     //Returns login status message based on authentication outcome 
    public String returnLoginStatus(boolean isLoggedIn) {
        if (isLoggedIn) {
            return "Welcome " + this.firstName + ", " + this.lastName + " it is great to see you again.";
        } else {
            return "incorrect username or password , please try again.";
        }
    }
}

