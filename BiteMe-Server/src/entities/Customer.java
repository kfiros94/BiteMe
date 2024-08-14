package entities;

import java.io.Serializable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* Represents a Customer entity with various attributes.
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
    /**
     * Default constructor for Customer.
     */
    public Customer() 
    {
    	
    }
    /**
     * Constructs a Customer object with specified values.
     *
     * @param customerId       the customer ID
     * @param username         the username
     * @param password         the password
     * @param email            the email address
     * @param phoneNumber      the phone number
     * @param branch           the branch associated with the customer
     * @param accountType      the type of account
     * @param credit_card      the credit card information
     * @param address          the residential address
     * @param work_address     the work address
     * @param has_discount_code indicates if the customer has a discount code (1 for yes, 0 for no)
     * @param userID           the user ID associated with the customer
     */
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
    
    /**
     * Returns the customer ID.
     *
     * @return the customer ID
     */
    public int getCustomerId() {
        return customerId;
    }
    /**
     * Sets the customer ID.
     *
     * @param customerId the customer ID to set
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    /**
     * Returns the username.
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
     * Returns the password.
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
     * Returns the email address.
     *
     * @return the email address
     */
    public String getEmail() {
        return email;
    }
    /**
     * Sets the email address.
     *
     * @param email the email address to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * Returns the phone number.
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
     * Returns the branch associated with the customer.
     *
     * @return the branch
     */
    public String getBranch() {
        return branch;
    }
    /**
     * Sets the branch associated with the customer.
     *
     * @param branch the branch to set
     */
    public void setBranch(String branch) {
        this.branch = branch;
    }
    /**
     * Returns the account type.
     *
     * @return the account type
     */
    public String getAccountType() {
        return accountType;
    }
    /**
     * Sets the account type.
     *
     * @param accountType the account type to set
     */
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
    /**
     * Returns the credit card information.
     *
     * @return the credit card information
     */
    public String getCredit_card() { 
        return credit_card;
    }
    /**
     * Sets the credit card information.
     *
     * @param credit_card the credit card information to set
     */
    public void setCredit_card(String credit_card) { 
        this.credit_card = credit_card;
    }
    /**
     * Returns the residential address.
     *
     * @return the residential address
     */
    public String getAddress() {
        return address;
    }
    /**
     * Sets the residential address.
     *
     * @param address the residential address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }
    /**
     * Returns the work address.
     *
     * @return the work address
     */
    public String getWork_address() {
        return work_address;
    }
    /**
     * Sets the work address.
     *
     * @param work_address the work address to set
     */
    public void setWork_address(String work_address) {
        this.work_address = work_address;
    }
    /**
     * Returns whether the customer has a discount code.
     *
     * @return 1 if the customer has a discount code, 0 otherwise
     */
    public int getHas_discount_code() {
        return has_discount_code;
    }
    /**
     * Sets whether the customer has a discount code.
     *
     * @param has_discount_code 1 if the customer has a discount code, 0 otherwise
     */
    public void setHas_discount_code(int has_discount_code) {
        this.has_discount_code = has_discount_code;
    }
    /**
     * Returns the user ID associated with the customer.
     *
     * @return the user ID
     */
    public int getUserID() {
        return userID;
    }
    /**
     * Sets the user ID associated with the customer.
     *
     * @param userID the user ID to set
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }
    
    /**
     * Returns a string representation of the Customer object.
     *
     * @return a string representation of the Customer object
     */
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
    
    
    /**
     * Creates a Customer object from a string representation.
     *
     * @param toStringOutput the string representation of the Customer object
     * @return the Customer object created from the string representation
     * @throws IllegalArgumentException if the string representation is invalid
     */
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

}
