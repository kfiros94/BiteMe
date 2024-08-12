package entities;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Restaurant implements Serializable{
	private int restaurantId;
    private String name;
    private String address;
    private String phoneNumber;
    private String branch;
	private int supplierID;


    // Constructor
    public Restaurant(int restaurantId, String name, String address, String phoneNumber, String branch, int supplierID) {
        this.restaurantId = restaurantId;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.branch = branch;
        this.supplierID=supplierID;
    }

    // Default Constructor
    public Restaurant() {}

    // Getters
    public int getRestaurantId() {
        return restaurantId;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getbranch() {
        return branch;
    }
    
    public int getsupplierID() {
        return supplierID;
    }

    // Setters
    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public void setRestaurantName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setbranch(String branch) {
        this.branch = branch;
    }

	public void setSupplierID(int supplierID) {
		this.supplierID = supplierID;
	}

    @Override
    public String toString() {
        return "Restaurant{" +
                "restaurantId=" + restaurantId +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", branch='" + branch + '\'' +
                ", SupplierID='"+supplierID+'\'' +
                '}';
    }
    
    public static Restaurant fromString(String toStringOutput) {
        Pattern pattern = Pattern.compile("Restaurant\\{restaurantId=(\\d+), name='(.*?)', address='(.*?)', phoneNumber='(.*?)', branch='(.*?)', SupplierID='(\\d+)'\\}");
        Matcher matcher = pattern.matcher(toStringOutput);

        if (matcher.find()) {
            int restaurantId = Integer.parseInt(matcher.group(1));
            String name = matcher.group(2).equals("null") ? null : matcher.group(2);
            String address = matcher.group(3).equals("null") ? null : matcher.group(3);
            String phoneNumber = matcher.group(4).equals("null") ? null : matcher.group(4);
            String branch = matcher.group(5).equals("null") ? null : matcher.group(5);
            int supplierID = Integer.parseInt(matcher.group(6));

            return new Restaurant(restaurantId, name, address, phoneNumber, branch, supplierID);
        } else {
            throw new IllegalArgumentException("Invalid toString output: " + toStringOutput);
        }
    }


	



}
