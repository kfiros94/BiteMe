package logic;

import java.io.Serializable;

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
    
    public User() {
    }


    // Constructor
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

    // toString method
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
}
