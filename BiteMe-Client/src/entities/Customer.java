package entities;

import java.io.Serializable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Customer implements Serializable 
{

    private int customerId;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String branch;
    private String accountType;
    private String credit_card;
    private String address;
    private String work_address;
    private int has_discount_code;
    private int userID;

    public Customer() 
    {
    	
    }

    public Customer(int customerId, String username, String password, String email, String phoneNumber, String branch, String accountType, String credit_card, String address, String work_address, int has_discount_code, int userID) {
        this.customerId = customerId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.branch = branch;
        this.accountType = accountType;
        this.credit_card = credit_card;
        this.address = address;
        this.work_address = work_address;
        this.has_discount_code = has_discount_code;
        this.userID = userID;
    }

    // Getters and Setters

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
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

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getCredit_card() { 
        return credit_card;
    }

    public void setCredit_card(String credit_card) { 
        this.credit_card = credit_card;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWork_address() {
        return work_address;
    }

    public void setWork_address(String work_address) {
        this.work_address = work_address;
    }

    public int getHas_discount_code() {
        return has_discount_code;
    }

    public void setHas_discount_code(int has_discount_code) {
        this.has_discount_code = has_discount_code;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", branch='" + branch + '\'' +
                ", accountType='" + accountType + '\'' +
                ", credit_card='" + credit_card + '\'' +
                ", address='" + address + '\'' +
                ", work_address='" + work_address + '\'' +
                ", has_discount_code=" + has_discount_code +
                ", userID=" + userID +
                '}';
    }
    
    
    
    //bbbbbbbbbbbbbbbbbbbbbbbb
    
    public static Customer fromString(String toStringOutput) {
        Pattern pattern = Pattern.compile("Customer\\{customerId=(\\d+), username='(.*?)', password='(.*?)', email='(.*?)', phoneNumber='(.*?)', branch='(.*?)', accountType='(.*?)', credit_card='(.*?)', address='(.*?)', work_address='(.*?)', has_discount_code=(\\d+), userID=(\\d+)\\}");
        Matcher matcher = pattern.matcher(toStringOutput);

        if (matcher.find()) {
            int customerId = Integer.parseInt(matcher.group(1));
            String username = matcher.group(2).equals("null") ? null : matcher.group(2);
            String password = matcher.group(3).equals("null") ? null : matcher.group(3);
            String email = matcher.group(4).equals("null") ? null : matcher.group(4);
            String phoneNumber = matcher.group(5).equals("null") ? null : matcher.group(5);
            String branch = matcher.group(6).equals("null") ? null : matcher.group(6);
            String accountType = matcher.group(7).equals("null") ? null : matcher.group(7);
            String credit_card = matcher.group(8).equals("null") ? null : matcher.group(8);
            String address = matcher.group(9).equals("null") ? null : matcher.group(9);
            String work_address = matcher.group(10).equals("null") ? null : matcher.group(10);
            int has_discount_code = Integer.parseInt(matcher.group(11));
            int userID = Integer.parseInt(matcher.group(12));

            return new Customer(customerId, username, password, email, phoneNumber, branch, accountType, credit_card, address, work_address, has_discount_code, userID);
        } else {
            throw new IllegalArgumentException("Invalid toString output: " + toStringOutput);
        }
    }

    
    //bbbbbbbbbbbbbbbbbbbbbbbb
    
    
    
}
