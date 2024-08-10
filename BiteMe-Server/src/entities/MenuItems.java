package entities;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MenuItems {

    private int item_id;
    private int restaurant_id;
    private String name;
    private String description;
    private double price;
    private String category;
    private ArrayList<String> possible_changes; // Changed to ArrayList<String>

    // Constructor
    public MenuItems(int item_id, int restaurant_id, String name, String description, double price, String category, ArrayList<String> possible_changes) {
        this.item_id = item_id;
        this.restaurant_id = restaurant_id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.possible_changes = possible_changes;
    }

    // Default Constructor
    public MenuItems() {
        this.possible_changes = new ArrayList<>(); // Initialize with an empty list
    }

    // Getters and Setters
    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public int getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(int restaurant_id) {
        this.restaurant_id = restaurant_id;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ArrayList<String> getPossible_changes() {
        return possible_changes;
    }

    public void setPossible_changes(ArrayList<String> possible_changes) {
        this.possible_changes = possible_changes;
    }

    // toString method
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

    // fromString method
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

    // fromStringArray method
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
