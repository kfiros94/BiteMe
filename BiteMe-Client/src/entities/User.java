package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a User entity with various attributes such as userId, username, password, email, etc.
 * Implements Serializable interface for object serialization.
 * 
 * @author Kfir Amoyal
 * @author Israel Ohayon
 * @author Yaniv Shatil
 * @author Noam Furman
 * @author Omri Heit
 * @author Eitan Zerbel
 * 
 * @version August 2024
 */
public class User implements Serializable{
    private int userId;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String permission;
    private String branch;
    private boolean hasDiscountCode;
    private int loggedIn;
    private ClientInfo cl;
    private String accountStatus;
    /**
     * Default constructor.
     */
    public User()
    {
    	
    }


    /**
     * Parameterized constructor to initialize a User object.
     * 
     * @param userId          the ID of the user
     * @param username        the username of the user
     * @param password        the password of the user
     * @param email           the email of the user
     * @param phoneNumber     the phone number of the user
     * @param permission      the permission level of the user
     * @param branch          the branch associated with the user
     * @param hasDiscountCode whether the user has a discount code
     * @param loggedIn        the login status of the user (e.g., 1 for logged in, 0 for not)
     * @param accountStatus   the status of the user account (e.g., active, inactive)
     */
    public User(int userId, String username, String password, String email, String phoneNumber, String permission, String branch, boolean hasDiscountCode, int loggedIn,String accountStatus) 
    {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.permission = permission;
        this.branch = branch;
        this.hasDiscountCode = hasDiscountCode;
        this.loggedIn = loggedIn;
        this.accountStatus = accountStatus;
    }

    // Getters and Setters
    
    /**
     * Gets the user ID.
     * 
     * @return the user ID
     */
    public int getUserId() {
        return userId;
    }
    /**
     * Sets the user ID.
     * 
     * @param userId the user ID to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }
    /**
     * Gets the username.
     * 
     * @return the username
     */
    public String getUsername() {
        return username;
    }
    /**
     * Sets the username.
     * 
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * Gets the password.
     * 
     * @return the password
     */
    public String getPassword() {
        return password;
    }
    /**
     * Sets the password.
     * 
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * Gets the email.
     * 
     * @return the email
     */
    public String getEmail() {
        return email;
    }
    /**
     * Sets the email.
     * 
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * Gets the phone number.
     * 
     * @return the phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }
    /**
     * Sets the phone number.
     * 
     * @param phoneNumber the phone number to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    /**
     * Gets the permission level.
     * 
     * @return the permission level
     */
    public String getPermission() {
        return permission;
    }
    /**
     * Sets the permission level.
     * 
     * @param permission the permission level to set
     */
    public void setPermission(String permission) {
        this.permission = permission;
    }
    /**
     * Gets the branch.
     * 
     * @return the branch
     */
    public String getBranch() {
        return branch;
    }
    /**
     * Sets the branch.
     * 
     * @param branch the branch to set
     */
    public void setBranch(String branch) {
        this.branch = branch;
    }
    /**
     * Checks if the user has a discount code.
     * 
     * @return true if the user has a discount code, false otherwise
     */
    public boolean isHasDiscountCode() {
        return hasDiscountCode;
    }
    /**
     * Sets the discount code status.
     * 
     * @param hasDiscountCode the discount code status to set
     */
    public void setHasDiscountCode(boolean hasDiscountCode) {
        this.hasDiscountCode = hasDiscountCode;
    }
    /**
     * Gets the login status.
     * 
     * @return the login status
     */
    public int getLoggedIn() {
        return loggedIn;
    }
    /**
     * Sets the login status.
     * 
     * @param loggedIn the login status to set
     */
    public void setLoggedIn(int loggedIn) {
        this.loggedIn = loggedIn;
    }
    /**
     * Gets the account status.
     * 
     * @return the account status
     */
    public String getaccountStatus() {
        return accountStatus;
    }
    /**
     * Sets the account status.
     * 
     * @param accountStatus the account status to set
     */
    public void setaccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }
    

    /**
     * Returns a string representation of the User object.
     * 
     * @return a string representation of the User object
     */
    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", permission='" + permission + '\'' +
                ", branch='" + branch + '\'' +
                ", hasDiscountCode=" + hasDiscountCode +
                ", loggedIn=" + loggedIn +
                ", accountStatus='" + accountStatus + '\'' +
                '}';
    }
    
    
    /**
     * Creates a User object from a string representation.
     * 
     * @param toStringOutput the string representation of a User object
     * @return a User object parsed from the input string
     * @throws IllegalArgumentException if the input string is not a valid User object representation
     */
    public static User fromString(String toStringOutput)
    {
        Pattern pattern = Pattern.compile("User\\{userId=(\\d+), username='(.*?)', password='(.*?)', email='(.*?)', phoneNumber='(.*?)', permission='(.*?)', branch='(.*?)', hasDiscountCode=(true|false), loggedIn=(\\d+), accountStatus='(.*?)'\\}");
        Matcher matcher = pattern.matcher(toStringOutput);

        if (matcher.find()) {
            int userId = Integer.parseInt(matcher.group(1));
            String username = matcher.group(2).equals("null") ? null : matcher.group(2);
            String password = matcher.group(3).equals("null") ? null : matcher.group(3);
            String email = matcher.group(4).equals("null") ? null : matcher.group(4);
            String phoneNumber = matcher.group(5).equals("null") ? null : matcher.group(5);
            String permission = matcher.group(6).equals("null") ? null : matcher.group(6);
            String branch = matcher.group(7).equals("null") ? null : matcher.group(7);
            boolean hasDiscountCode = Boolean.parseBoolean(matcher.group(8));
            int loggedIn = Integer.parseInt(matcher.group(9));
            String accountStatus = matcher.group(10).equals("null") ? null : matcher.group(10);

            return new User(userId, username, password, email, phoneNumber, permission, branch, hasDiscountCode, loggedIn, accountStatus);
        } else {
            throw new IllegalArgumentException("Invalid toString output: " + toStringOutput);
        }
    }
    
    /**
     * Creates an ArrayList of User objects from a string representation of an array of Users.
     * 
     * <p>This method parses a string containing multiple User object representations
     * and converts them into an ArrayList of User objects. Each User object in the string
     * should be in the format produced by the User class's toString() method.</p>
     *
     * @param arrayString A string containing one or more User object representations.
     *                    Each User object should be in the format:
     *                    "User{userId=X, username='Y', password='Z', ...}"
     * 
     * @return An ArrayList of User objects parsed from the input string.
     * 
     * @throws IllegalArgumentException implicitly if the input string contains
     *         User representations that do not match the expected format.
     * 
     * @see User#fromString(String)
     */
    public static ArrayList<User> fromStringArray(String arrayString) {
        Pattern pattern = Pattern.compile(
            "User\\{userId=(\\d+), username='(.*?)', password='(.*?)', email='(.*?)', phoneNumber='(.*?)', " +
            "permission='(.*?)', branch='(.*?)', hasDiscountCode=(true|false), loggedIn=(\\d+), accountStatus='(.*?)'\\}"
        );
        Matcher matcher = pattern.matcher(arrayString);

        ArrayList<User> users = new ArrayList<>();
        while (matcher.find()) {
            int userId = Integer.parseInt(matcher.group(1));
            String username = matcher.group(2).equals("null") ? null : matcher.group(2);
            String password = matcher.group(3).equals("null") ? null : matcher.group(3);
            String email = matcher.group(4).equals("null") ? null : matcher.group(4);
            String phoneNumber = matcher.group(5).equals("null") ? null : matcher.group(5);
            String permission = matcher.group(6).equals("null") ? null : matcher.group(6);
            String branch = matcher.group(7).equals("null") ? null : matcher.group(7);
            boolean hasDiscountCode = Boolean.parseBoolean(matcher.group(8));
            int loggedIn = Integer.parseInt(matcher.group(9));
            String accountStatus = matcher.group(10).equals("null") ? null : matcher.group(10);

            users.add(new User(userId, username, password, email, phoneNumber, permission, branch, hasDiscountCode, loggedIn, accountStatus));
        }

        return users;
    }
    

}
