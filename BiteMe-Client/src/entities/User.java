package entities;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a User entity with various attributes and functionalities.
 * This class implements Serializable to allow object serialization.
 * 
 * @author Kfir Amoyal
 * @author Israel Ohayon
 * @author Yaniv Shatil
 * @author Noam Furmann
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
     * Default constructor for the User class.
     */
    public User() {
    	
    }

    /**
     * Constructor to initialize a User with specified attributes.
     *
     * @param userId the user ID
     * @param username the username
     * @param password the password
     * @param email the email address
     * @param phoneNumber the phone number
     * @param permission the permission level
     * @param branch the branch
     * @param hasDiscountCode whether the user has a discount code
     * @param loggedIn the logged in status
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
     * @param userId the user ID
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
     * @param username the username
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
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the email address.
     *
     * @return the email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address.
     *
     * @param email the email address
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
     * @param phoneNumber the phone number
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
     * @param permission the permission level
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
     * @param branch the branch
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
     * @param hasDiscountCode the discount code status
     */
    public void setHasDiscountCode(boolean hasDiscountCode) {
        this.hasDiscountCode = hasDiscountCode;
    }

    /**
     * Gets the logged in status.
     *
     * @return the logged in status
     */
    public int getLoggedIn() {
        return loggedIn;
    }

    /**
     * Sets the logged in status.
     *
     * @param loggedIn the logged in status
     */
    public void setLoggedIn(int loggedIn) {
        this.loggedIn = loggedIn;
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
                '}';
    }
    
    /**
     * Creates a User object from a string representation.
     *
     * @param toStringOutput the string representation of the User object
     * @return a User object
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