package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MenuItem implements Serializable {
    private int itemId;
    private int restaurantId;
    private String name;
    private String description;
    private float price;
    private String category;
    private String possibleChanges;

    // Constructor
    public MenuItem(int itemId, int restaurantId, String name, String description, float price, String category, String possibleChanges) {
        this.itemId = itemId;
        this.restaurantId = restaurantId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.possibleChanges = possibleChanges;
    }

    // Default Constructor
    public MenuItem() {}

    // Getters and Setters
    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getRestaurantItamId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPossibleChanges() {
        return possibleChanges;
    }

    public void setPossibleChanges(String possibleChanges) {
        this.possibleChanges = possibleChanges;
    }

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
    
    /*public static List<MenuItem> fromBiteOptions(BiteOptions biteOptions) {
        System.out.println("fromBiteOptions: Received BiteOptions - " + biteOptions);

        List<MenuItem> menuItems = new ArrayList<>();

        // Check if BiteOptions is valid and contains a list of MenuItem strings
        if (biteOptions != null && biteOptions.getData() instanceof List) {
            System.out.println("fromBiteOptions: BiteOptions contains valid data. Converting to MenuItem list.");

            List<String> menuItemStrings = (List<String>) biteOptions.getData();
            System.out.println("fromBiteOptions: Extracted menuItemStrings - " + menuItemStrings);

            for (String menuItemString : menuItemStrings) {
                try {
                    System.out.println("fromBiteOptions: Parsing MenuItem from string - " + menuItemString);
                    MenuItem menuItem = MenuItem.fromString(menuItemString);
                    menuItems.add(menuItem);
                    System.out.println("fromBiteOptions: Successfully parsed and added MenuItem - " + menuItem);
                } catch (IllegalArgumentException e) {
                    System.err.println("fromBiteOptions: Error parsing menu item: " + menuItemString);
                    e.printStackTrace();
                }
            }
        } else {
            System.err.println("fromBiteOptions: BiteOptions is null or contains invalid data.");
        }

        System.out.println("fromBiteOptions: Final list of MenuItems - " + menuItems);
        return menuItems;
    }*/

}
