package entities;

import java.io.Serializable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
    
    public User()
    {
    	
    }


    // Constructor
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
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public boolean isHasDiscountCode() {
        return hasDiscountCode;
    }

    public void setHasDiscountCode(boolean hasDiscountCode) {
        this.hasDiscountCode = hasDiscountCode;
    }

    public int getLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(int loggedIn) {
        this.loggedIn = loggedIn;
    }
    
    public String getaccountStatus() {
        return accountStatus;
    }

    public void setaccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }
    

    // Updated toString method to include accountStatus
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
    
    //bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb
    
    // Static method to create a User from a toString output
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
    
    //bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb
    
    
    
    
}
