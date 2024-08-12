package entities;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Restaurant implements Serializable
{

    private int restaurantID;
    private String name;
    private String address;
    private String phoneNumber;
    private String branch;
    private int supplierID;

    // Default constructor
    public Restaurant() 
    {
    	
    }

    // Parameterized constructor
    public Restaurant(int restaurantID, String name, String address, String phoneNumber, String branch, int supplierID)
    {
        this.restaurantID = restaurantID;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.branch = branch;
        this.supplierID = supplierID;
    }

    // Getters and Setters
    public int getRestaurantID() 
    {
        return restaurantID;
    }

    public void setRestaurantID(int restaurantID) 
    {
        this.restaurantID = restaurantID;
    }

    public String getName() 
    {
        return name;
    }

    public void setName(String name) 
    {
        this.name = name;
    }

    public String getAddress() 
    {
        return address;
    }

    public void setAddress(String address) 
    {
        this.address = address;
    }

    public String getPhoneNumber() 
    {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) 
    {
        this.phoneNumber = phoneNumber;
    }

    public String getBranch() 
    {
        return branch;
    }

    public void setBranch(String branch) 
    {
        this.branch = branch;
    }

    public int getSupplierID() 
    {
        return supplierID;
    }

    public void setSupplierID(int supplierID) 
    {
        this.supplierID = supplierID;
    }

    // toString method for debugging
    @Override
    public String toString() 
    {
        return "Restaurant{" +
                "restaurantID=" + restaurantID +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", branch='" + branch + '\'' +
                ", supplierID=" + supplierID +
                '}';
    }
    
    
    //nnnnnnnnnnnnnnnnnnnnnnnn
    
    public static Restaurant fromString(String toStringOutput) {
        Pattern pattern = Pattern.compile("Restaurant\\{restaurantID=(\\d+), name='(.*?)', address='(.*?)', phoneNumber='(.*?)', branch='(.*?)', supplierID=(\\d+)\\}");
        Matcher matcher = pattern.matcher(toStringOutput);

        if (matcher.find()) {
            int restaurantID = Integer.parseInt(matcher.group(1));
            String name = matcher.group(2).equals("null") ? null : matcher.group(2);
            String address = matcher.group(3).equals("null") ? null : matcher.group(3);
            String phoneNumber = matcher.group(4).equals("null") ? null : matcher.group(4);
            String branch = matcher.group(5).equals("null") ? null : matcher.group(5);
            int supplierID = Integer.parseInt(matcher.group(6));

            return new Restaurant(restaurantID, name, address, phoneNumber, branch, supplierID);
        } else {
            throw new IllegalArgumentException("Invalid toString output: " + toStringOutput);
        }
    }

    
    //nnnnnnnnnnnnnnnnnnnnnnnnn
    
    

    // New method to parse an array of Restaurant's toString outputs
    public static Restaurant[] fromStringArray(String arrayString) {
        Pattern pattern = Pattern.compile("Restaurant\\{restaurantID=(\\d+), name='(.*?)', address='(.*?)', phoneNumber='(.*?)', branch='(.*?)', supplierID=(\\d+)\\}");
        Matcher matcher = pattern.matcher(arrayString);

        List<Restaurant> restaurants = new ArrayList<>();
        while (matcher.find()) {
            int restaurantID = Integer.parseInt(matcher.group(1));
            String name = matcher.group(2).equals("null") ? null : matcher.group(2);
            String address = matcher.group(3).equals("null") ? null : matcher.group(3);
            String phoneNumber = matcher.group(4).equals("null") ? null : matcher.group(4);
            String branch = matcher.group(5).equals("null") ? null : matcher.group(5);
            int supplierID = Integer.parseInt(matcher.group(6));

            restaurants.add(new Restaurant(restaurantID, name, address, phoneNumber, branch, supplierID));
        }

        return restaurants.toArray(new Restaurant[0]);
    }
    
    
    
}
