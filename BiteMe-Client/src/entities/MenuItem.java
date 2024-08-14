package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
* Represents a menu item in a restaurant's menu, from the Supplier's view.
* Implements Serializable to allow the object to be serialized.
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
public class MenuItem implements Serializable {
    private int itemId;
    private int restaurantId;
    private String name;
    private String description;
    private float price;
    private String category;
    private String possibleChanges;

    /**
     * Constructs a new MenuItem with the specified details.
     *
     * @param itemId          the unique identifier for the menu item
     * @param restaurantId    the unique identifier for the restaurant
     * @param name            the name of the menu item
     * @param description     a description of the menu item
     * @param price           the price of the menu item
     * @param category        the category of the menu item (e.g., appetizer, main course)
     * @param possibleChanges any possible changes that can be made to the menu item
     */
    public MenuItem(int itemId, int restaurantId, String name, String description, float price, String category, String possibleChanges) {
        this.itemId = itemId;
        this.restaurantId = restaurantId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.possibleChanges = possibleChanges;
    }

    /**
     * Default constructor for creating an empty MenuItem.
     */
    public MenuItem() {}

    // Getters and Setters
    
    /**
     * Gets the item ID.
     *
     * @return the item ID
     */
    public int getItemId() {
        return itemId;
    }
    /**
     * Sets the item ID.
     *
     * @param itemId the item ID
     */
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
    /**
     * Gets the restaurant ID.
     *
     * @return the restaurant ID
     */
    public int getRestaurantItamId() {
        return restaurantId;
    }
    /**
     * Sets the restaurant ID.
     *
     * @param restaurantId the restaurant ID
     */
    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }
    /**
     * Gets the name of the menu item.
     *
     * @return the name of the menu item
     */
    public String getName() {
        return name;
    }
    /**
     * Sets the name of the menu item.
     *
     * @param name the name of the menu item
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Gets the description of the menu item.
     *
     * @return the description of the menu item
     */
    public String getDescription() {
        return description;
    }
    /**
     * Sets the description of the menu item.
     *
     * @param description the description of the menu item
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * Gets the price of the menu item.
     *
     * @return the price of the menu item
     */
    public float getPrice() {
        return price;
    }
    /**
     * Sets the price of the menu item.
     *
     * @param price the price of the menu item
     */
    public void setPrice(float price) {
        this.price = price;
    }
    /**
     * Gets the category of the menu item.
     *
     * @return the category of the menu item
     */
    public String getCategory() {
        return category;
    }
    /**
     * Sets the category of the menu item.
     *
     * @param category the category of the menu item
     */
    public void setCategory(String category) {
        this.category = category;
    }
    /**
     * Gets the possible changes that can be made to the menu item.
     *
     * @return the possible changes to the menu item
     */
    public String getPossibleChanges() {
        return possibleChanges;
    }
    /**
     * Sets the possible changes that can be made to the menu item.
     *
     * @param possibleChanges the possible changes to the menu item
     */
    public void setPossibleChanges(String possibleChanges) {
        this.possibleChanges = possibleChanges;
    }
    /**
     * Returns a string representation of the MenuItem.
     *
     * @return a string representation of the MenuItem
     */
    @Override
    public String toString() {
        return "MenuItem{" +
                "itemId=" + itemId +
                ", restaurantId=" + restaurantId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                ", possibleChanges='" + possibleChanges + '\'' +
                '}';
    }
    
    /**
     * Parses a string representation of a MenuItem and returns the corresponding MenuItem object.
     *
     * @param toStringOutput the string representation of the MenuItem
     * @return the corresponding MenuItem object
     * @throws IllegalArgumentException if the input string is invalid
     */   
    public static MenuItem fromString(String toStringOutput) {
        Pattern pattern = Pattern.compile("MenuItem\\{itemId=(\\d+), restaurantId=(\\d+), name='(.*?)', description='(.*?)', price=(\\d+\\.?\\d*), category='(.*?)', possibleChanges='(.*?)'\\}");
        Matcher matcher = pattern.matcher(toStringOutput);

        if (matcher.find()) {
            int itemId = Integer.parseInt(matcher.group(1));
            int restaurantId = Integer.parseInt(matcher.group(2));
            String name = matcher.group(3).equals("null") ? null : matcher.group(3);
            String description = matcher.group(4).equals("null") ? null : matcher.group(4);
            float price = Float.parseFloat(matcher.group(5));
            String category = matcher.group(6).equals("null") ? null : matcher.group(6);
            String possibleChanges = matcher.group(7).equals("null") ? null : matcher.group(7);

            return new MenuItem(itemId, restaurantId, name, description, price, category, possibleChanges);
        } else {
            throw new IllegalArgumentException("Invalid toString output: " + toStringOutput);
        }
    }
}
