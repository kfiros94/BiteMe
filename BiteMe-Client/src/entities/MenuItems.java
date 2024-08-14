package entities;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
* Represents a menu item in a restaurant's menu, from the Customer's view.
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
public class MenuItems {

    private int item_id;
    private int restaurant_id;
    private String name;
    private String description;
    private double price;
    private String category;
    private ArrayList<String> possible_changes;

    /**
     * Constructs a new MenuItems object with the specified details.
     *
     * @param item_id          the unique ID of the menu item
     * @param restaurant_id    the ID of the restaurant this item belongs to
     * @param name             the name of the menu item
     * @param description      a description of the menu item
     * @param price            the price of the menu item
     * @param category         the category of the menu item
     * @param possible_changes a list of possible modifications that can be made to the menu item
     */
    public MenuItems(int item_id, int restaurant_id, String name, String description, double price, String category, ArrayList<String> possible_changes) {
        this.item_id = item_id;
        this.restaurant_id = restaurant_id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.possible_changes = possible_changes;
    }

    /**
     * Default constructor for the MenuItems class.
     * Initializes the possible_changes list as an empty ArrayList.
     */
    public MenuItems() {
        this.possible_changes = new ArrayList<>(); // Initialize with an empty list
    }

    // Getters and Setters
    
    /**
     * Gets the item ID.
     *
     * @return the item ID
     */
    public int getItem_id() {
        return item_id;
    }
    /**
     * Sets the item ID.
     *
     * @param item_id the new item ID
     */
    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }
    /**
     * Gets the restaurant ID.
     *
     * @return the restaurant ID
     */
    public int getRestaurant_id() {
        return restaurant_id;
    }
    /**
     * Sets the restaurant ID.
     *
     * @param restaurant_id the new restaurant ID
     */
    public void setRestaurant_id(int restaurant_id) {
        this.restaurant_id = restaurant_id;
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
     * @param name the new name of the menu item
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
     * @param description the new description of the menu item
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * Gets the price of the menu item.
     *
     * @return the price of the menu item
     */
    public double getPrice() {
        return price;
    }
    /**
     * Sets the price of the menu item.
     *
     * @param price the new price of the menu item
     */
    public void setPrice(double price) {
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
     * @param category the new category of the menu item
     */
    public void setCategory(String category) {
        this.category = category;
    }
    /**
     * Gets the list of possible changes that can be made to the menu item.
     *
     * @return the list of possible changes
     */
    public ArrayList<String> getPossible_changes() {
        return possible_changes;
    }
    /**
     * Sets the list of possible changes that can be made to the menu item.
     *
     * @param possible_changes the new list of possible changes
     */
    public void setPossible_changes(ArrayList<String> possible_changes) {
        this.possible_changes = possible_changes;
    }

    /**
     * Returns a string representation of the MenuItems object.
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return "MenuItems{" +
                "item_id=" + item_id +
                ", restaurant_id=" + restaurant_id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                ", possible_changes=" + possible_changes +
                '}';
    }

    /**
     * Constructs a MenuItems object from a string representation.
     *
     * @param toStringOutput the string representation of a MenuItems object
     * @return a MenuItems object constructed from the string representation
     * @throws IllegalArgumentException if the input string does not match the expected format
     */
    public static MenuItems fromString(String toStringOutput) {
        Pattern pattern = Pattern.compile(
                "MenuItems\\{item_id=(\\d+), restaurant_id=(\\d+), name='([^']*)', description='([^']*)', price=(\\d+\\.\\d+), category='([^']*)', possible_changes=\\[(.*?)\\]\\}"
        );
        Matcher matcher = pattern.matcher(toStringOutput);

        if (matcher.find()) {
            int item_id = Integer.parseInt(matcher.group(1));
            int restaurant_id = Integer.parseInt(matcher.group(2));
            String name = matcher.group(3).equals("null") ? null : matcher.group(3);
            String description = matcher.group(4).equals("null") ? null : matcher.group(4);
            double price = Double.parseDouble(matcher.group(5));
            String category = matcher.group(6).equals("null") ? null : matcher.group(6);

            String possibleChangesStr = matcher.group(7);
            ArrayList<String> possible_changes = new ArrayList<>();
            if (!possibleChangesStr.isEmpty()) {
                String[] changesArray = possibleChangesStr.split(", ");
                for (String change : changesArray) {
                    possible_changes.add(change.trim());
                }
            }

            return new MenuItems(item_id, restaurant_id, name, description, price, category, possible_changes);
        } else {
            throw new IllegalArgumentException("Invalid toString output: " + toStringOutput);
        }
    }

    /**
     * Constructs a list of MenuItems objects from a string representation.
     *
     * @param arrayString the string representation of a list of MenuItems objects
     * @return a list of MenuItems objects constructed from the string representation
     */
    public static ArrayList<MenuItems> fromStringArray(String arrayString) {
        Pattern pattern = Pattern.compile(
                "MenuItems\\{item_id=(\\d+), restaurant_id=(\\d+), name='([^']*)', description='([^']*)', price=(\\d+\\.\\d+), category='([^']*)', possible_changes=\\[(.*?)\\]\\}"
        );
        Matcher matcher = pattern.matcher(arrayString);

        ArrayList<MenuItems> menuItems = new ArrayList<>();
        while (matcher.find()) {
            int item_id = Integer.parseInt(matcher.group(1));
            int restaurant_id = Integer.parseInt(matcher.group(2));
            String name = matcher.group(3).equals("null") ? null : matcher.group(3);
            String description = matcher.group(4).equals("null") ? null : matcher.group(4);
            double price = Double.parseDouble(matcher.group(5));
            String category = matcher.group(6).equals("null") ? null : matcher.group(6);

            String possibleChangesStr = matcher.group(7);
            ArrayList<String> possible_changes = new ArrayList<>();
            if (!possibleChangesStr.isEmpty()) {
                String[] changesArray = possibleChangesStr.split(", ");
                for (String change : changesArray) {
                    possible_changes.add(change.trim());
                }
            }

            menuItems.add(new MenuItems(item_id, restaurant_id, name, description, price, category, possible_changes));
        }

        return menuItems;
    }
}
