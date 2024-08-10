package entities;

import java.io.Serializable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a User entity with various attributes and functionalities.
 * This class implements Serializable to allow object serialization.
 * 
 * <p>This class contains attributes for a user's ID, username, password,
 * email, phone number, permission level, branch, discount code status,
 * and login status. It also provides constructors, getters, setters,
 * a method for converting the object to a string, and a static method
 * for creating a User object from a string.</p>
 * 
 * @author Kfir Amoyal
 * @author Israel Ohayon
 * @author Yaniv Shatil
 * @author Noam Furman
 * @author Omri Heit
 * @author Eithan Zerbel
 */

public class User implements Serializable {
    
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

    /**
     * Default constructor.
     * Initializes a new instance of the User class with default values.
     */
    public User() {
    }

    /**
     * Parameterized constructor.
     * Initializes a new instance of the User class with specified values.
     * 
     * @param userId the user's ID
     * @param username the user's username
     * @param password the user's password
     * @param email the user's email address
     * @param phoneNumber the user's phone number
     * @param permission the user's permission level
     * @param branch the user's branch
     * @param hasDiscountCode whether the user has a discount code
     * @param loggedIn the user's login status
     */
    public User(int userId, String username, String password, String email, String phoneNumber, String permission, String branch, boolean hasDiscountCode, int loggedIn) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.permission = permission;
        this.branch = branch;
        this.hasDiscountCode = hasDiscountCode;
        this.loggedIn = loggedIn;
    }

    /**
     * Gets the user's ID.
     * 
     * @return the user's ID
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the user's ID.
     * 
     * @param userId the user's ID
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets the user's username.
     * 
     * @return the user's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the user's username.
     * 
     * @param username the user's username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the user's password.
     * 
     * @return the user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's password.
     * 
     * @param password the user's password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the user's email address.
     * 
     * @return the user's email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's email address.
     * 
     * @param email the user's email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the user's phone number.
     * 
     * @return the user's phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the user's phone number.
     * 
     * @param phoneNumber the user's phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets the user's permission level.
     * 
     * @return the user's permission level
     */
    public String getPermission() {
        return permission;
    }

    /**
     * Sets the user's permission level.
     * 
     * @param permission the user's permission level
     */
    public void setPermission(String permission) {
        this.permission = permission;
    }

    /**
     * Gets the user's branch.
     * 
     * @return the user's branch
     */
    public String getBranch() {
        return branch;
    }

    /**
     * Sets the user's branch.
     * 
     * @param branch the user's branch
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
     * Sets the user's discount code status.
     * 
     * @param hasDiscountCode true if the user has a discount code, false otherwise
     */
    public void setHasDiscountCode(boolean hasDiscountCode) {
        this.hasDiscountCode = hasDiscountCode;
    }

    /**
     * Gets the user's login status.
     * 
     * @return the user's login status
     */
    public int getLoggedIn() {
        return loggedIn;
    }

    /**
     * Sets the user's login status.
     * 
     * @param loggedIn the user's login status
     */
    public void setLoggedIn(int loggedIn) {
        this.loggedIn = loggedIn;
    }

    /**
     * Converts the User object to a string representation.
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
                '}';
    }

    /**
     * Creates a User object from a string representation.
     * 
     * @param toStringOutput the string representation of a User object
     * @return a new User object based on the string representation
     * @throws IllegalArgumentException if the string representation is invalid
     */
    public static User fromString(String toStringOutput) {
        Pattern pattern = Pattern.compile("User\\{userId=(\\d+), username='(.*?)', password='(.*?)', email='(.*?)', phoneNumber='(.*?)', permission='(.*?)', branch='(.*?)', hasDiscountCode=(true|false), loggedIn=(\\d+)\\}");
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

            return new User(userId, username, password, email, phoneNumber, permission, branch, hasDiscountCode, loggedIn);
        } else {
            throw new IllegalArgumentException("Invalid toString output: " + toStringOutput);
        }
    }
}