package entities;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* Represents a Restaurant entity with various attributes.
* Provides methods to get and set these attributes, as well as to create a Restaurant object from a string representation.
* Also includes a utility method to parse a string representation of multiple Restaurant objects.
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
public class Restaurant 
{

    private int restaurantID;
    private String name;
    private String address;
    private String phoneNumber;
    private String branch;
    private int supplierID;

    /**
     * Default constructor for creating an empty Restaurant object.
     */
    public Restaurant() 
    {
    	
    }

    /**
     * Constructs a Restaurant object with the specified attributes.
     *
     * @param restaurantID  the unique identifier for the restaurant
     * @param name          the name of the restaurant
     * @param address       the address of the restaurant
     * @param phoneNumber   the phone number of the restaurant
     * @param branch        the branch of the restaurant
     * @param supplierID    the ID of the supplier associated with the restaurant
     */
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
    
    /**
     * Returns the restaurant's ID.
     *
     * @return the restaurant's ID
     */
    public int getRestaurantID() 
    {
        return restaurantID;
    }
    /**
     * Sets the restaurant's ID.
     *
     * @param restaurantID the restaurant's ID
     */
    public void setRestaurantID(int restaurantID) 
    {
        this.restaurantID = restaurantID;
    }
    /**
     * Returns the name of the restaurant.
     *
     * @return the name of the restaurant
     */
    public String getName() 
    {
        return name;
    }
    /**
     * Sets the name of the restaurant.
     *
     * @param name the name of the restaurant
     */
    public void setName(String name) 
    {
        this.name = name;
    }
    /**
     * Returns the address of the restaurant.
     *
     * @return the address of the restaurant
     */
    public String getAddress() 
    {
        return address;
    }
    /**
     * Sets the address of the restaurant.
     *
     * @param address the address of the restaurant
     */
    public void setAddress(String address) 
    {
        this.address = address;
    }
    /**
     * Returns the phone number of the restaurant.
     *
     * @return the phone number of the restaurant
     */
    public String getPhoneNumber() 
    {
        return phoneNumber;
    }
    /**
     * Sets the phone number of the restaurant.
     *
     * @param phoneNumber the phone number of the restaurant
     */
    public void setPhoneNumber(String phoneNumber) 
    {
        this.phoneNumber = phoneNumber;
    }
    /**
     * Returns the branch of the restaurant.
     *
     * @return the branch of the restaurant
     */
    public String getBranch() 
    {
        return branch;
    }
    /**
     * Sets the branch of the restaurant.
     *
     * @param branch the branch of the restaurant
     */
    public void setBranch(String branch) 
    {
        this.branch = branch;
    }
    /**
     * Returns the supplier ID associated with the restaurant.
     *
     * @return the supplier ID
     */
    public int getSupplierID() 
    {
        return supplierID;
    }
    /**
     * Sets the supplier ID associated with the restaurant.
     *
     * @param supplierID the supplier ID
     */
    public void setSupplierID(int supplierID) 
    {
        this.supplierID = supplierID;
    }

    /**
     * Returns a string representation of the Restaurant object.
     *
     * @return a string representation of the Restaurant object
     */
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
    
    
    
    /**
     * Creates a Restaurant object from a string representation.
     * The string should match the format of the {@code toString()} output.
     *
     * @param toStringOutput the string representation of the Restaurant
     * @return the Restaurant object created from the string
     * @throws IllegalArgumentException if the string format is invalid
     */
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

    
    
    
    /**
     * Creates a list of Restaurant objects from a string representation of multiple restaurants.
     * The string should contain multiple {@code toString()} outputs concatenated together.
     *
     * @param arrayString the string representation of multiple Restaurant objects
     * @return a list of Restaurant objects created from the string
     */
    public static ArrayList<Restaurant> fromStringArray(String arrayString) {
        Pattern pattern = Pattern.compile("Restaurant\\{restaurantID=(\\d+), name='(.*?)', address='(.*?)', phoneNumber='(.*?)', branch='(.*?)', supplierID=(\\d+)\\}");
        Matcher matcher = pattern.matcher(arrayString);

        ArrayList<Restaurant> restaurants = new ArrayList<>();
        while (matcher.find()) {
            int restaurantID = Integer.parseInt(matcher.group(1));
            String name = matcher.group(2).equals("null") ? null : matcher.group(2);
            String address = matcher.group(3).equals("null") ? null : matcher.group(3);
            String phoneNumber = matcher.group(4).equals("null") ? null : matcher.group(4);
            String branch = matcher.group(5).equals("null") ? null : matcher.group(5);
            int supplierID = Integer.parseInt(matcher.group(6));

            restaurants.add(new Restaurant(restaurantID, name, address, phoneNumber, branch, supplierID));
        }

        return restaurants;
    }
    
}
