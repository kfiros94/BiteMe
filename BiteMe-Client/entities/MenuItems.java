package entities;

public class MenuItems {

    private int item_id;
    private int restaurant_id;
    private String name;
    private String description;
    private double price;
    private String category;
    private String possible_changes;

    // Constructor
    public MenuItems(int item_id, int restaurant_id, String name, String description, double price, String category, String possible_changes) {
        this.item_id = item_id;
        this.restaurant_id = restaurant_id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.possible_changes = possible_changes;
    }

    // Default Constructor
    public MenuItems()
    {
    	
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

    public String getPossible_changes() {
        return possible_changes;
    }

    public void setPossible_changes(String possible_changes) {
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
                ", possible_changes='" + possible_changes + '\'' +
                '}';
    }
}
