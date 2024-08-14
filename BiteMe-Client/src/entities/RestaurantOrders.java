package entities;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.Serializable;
/**
 * The class represents an order made at a restaurant.
 * It contains various details related to the order, such as the restaurant name,
 * order number, total price, and other relevant information.
 * This class implements Serializable to allow objects of this type to be serialized.
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
public class RestaurantOrders implements Serializable {

    private String restaurant;
    private int order_number;
    private double total_price;
    private String order_list;  // Assuming this is a JSON string
    private String order_address;
    private int user_id;
    private int restaurant_id;
    private String placing_order_date;
    private String status;
    private String delivery_type;
    private String order_requested_date;
    private String full_name;
    private String phone_number;
    private String branch;
    private String order_received;  // Adjusted to String

    /**
     * Constructs a new RestaurantOrders object with the specified details.
     *
     * @param restaurant           the name of the restaurant
     * @param order_number         the order number
     * @param total_price          the total price of the order
     * @param order_list           a JSON string representing the list of items in the order
     * @param order_address        the delivery address of the order
     * @param user_id              the ID of the user who placed the order
     * @param restaurant_id        the ID of the restaurant
     * @param placing_order_date   the date when the order was placed
     * @param status               the status of the order
     * @param delivery_type        the type of delivery for the order
     * @param order_requested_date the date when the order is requested to be delivered
     * @param full_name            the full name of the person who placed the order
     * @param phone_number         the phone number of the person who placed the order
     * @param branch               the branch of the restaurant where the order was placed
     * @param order_received       the status of whether the order was received
     */
    public RestaurantOrders(String restaurant, int order_number, double total_price, String order_list,
                            String order_address, int user_id, int restaurant_id,
                            String placing_order_date, String status, String delivery_type,
                            String order_requested_date, String full_name, String phone_number,
                            String branch, String order_received) {  // Adjusted to String
        this.restaurant = restaurant;
        this.order_number = order_number;
        this.total_price = total_price;
        this.order_list = order_list;
        this.order_address = order_address;
        this.user_id = user_id;
        this.restaurant_id = restaurant_id;
        this.placing_order_date = placing_order_date;
        this.status = status;
        this.delivery_type = delivery_type;
        this.order_requested_date = order_requested_date;
        this.full_name = full_name;
        this.phone_number = phone_number;
        this.branch = branch;
        this.order_received = order_received;
    }

    /**
     * Default constructor for RestaurantOrders.
     */
    public RestaurantOrders() {
    }

    // Getters and Setters
    /**
     * Returns the name of the restaurant.
     *
     * @return the restaurant name
     */
    public String getRestaurant() {
        return restaurant;
    }
    /**
     * Sets the name of the restaurant.
     *
     * @param restaurant the restaurant name
     */
    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }
    /**
     * Returns the order number.
     *
     * @return the order number
     */
    public int getOrder_number() {
        return order_number;
    }
    /**
     * Sets the order number.
     *
     * @param order_number the order number
     */
    public void setOrder_number(int order_number) {
        this.order_number = order_number;
    }
    /**
     * Returns the total price of the order.
     *
     * @return the total price
     */
    public double getTotal_price() {
        return total_price;
    }
    /**
     * Sets the total price of the order.
     *
     * @param total_price the total price
     */
    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }
    /**
     * Returns the list of items in the order as a JSON string.
     *
     * @return the order list
     */
    public String getOrder_list() {
        return order_list;
    }
    /**
     * Sets the list of items in the order.
     *
     * @param order_list the order list as a JSON string
     */
    public void setOrder_list(String order_list) {
        this.order_list = order_list;
    }
    /**
     * Returns the delivery address of the order.
     *
     * @return the order address
     */
    public String getOrder_address() {
        return order_address;
    }
    /**
     * Sets the delivery address of the order.
     *
     * @param order_address the order address
     */
    public void setOrder_address(String order_address) {
        this.order_address = order_address;
    }
    /**
     * Returns the user ID of the person who placed the order.
     *
     * @return the user ID
     */
    public int getUser_id() {
        return user_id;
    }
    /**
     * Sets the user ID of the person who placed the order.
     *
     * @param user_id the user ID
     */
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    /**
     * Returns the restaurant ID.
     *
     * @return the restaurant ID
     */
    public int getRestaurant_id() {
        return restaurant_id;
    }
    /**
     * Sets the restaurant ID.
     *
     * @param restaurant_id the restaurant ID
     */
    public void setRestaurant_id(int restaurant_id) {
        this.restaurant_id = restaurant_id;
    }
    /**
     * Returns the date when the order was placed.
     *
     * @return the placing order date
     */
    public String getPlacing_order_date() {
        return placing_order_date;
    }
    /**
     * Sets the date when the order was placed.
     *
     * @param placing_order_date the placing order date
     */
    public void setPlacing_order_date(String placing_order_date) {
        this.placing_order_date = placing_order_date;
    }
    /**
     * Returns the status of the order.
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }
    /**
     * Sets the status of the order.
     *
     * @param status the status
     */
    public void setStatus(String status) {
        this.status = status;
    }
    /**
     * Returns the type of delivery for the order.
     *
     * @return the delivery type
     */
    public String getDelivery_type() {
        return delivery_type;
    }
    /**
     * Sets the type of delivery for the order.
     *
     * @param delivery_type the delivery type
     */
    public void setDelivery_type(String delivery_type) {
        this.delivery_type = delivery_type;
    }
    /**
     * Returns the date when the order is requested to be delivered.
     *
     * @return the order requested date
     */
    public String getOrder_requested_date() {
        return order_requested_date;
    }
    /**
     * Sets the date when the order is requested to be delivered.
     *
     * @param order_requested_date the order requested date
     */
    public void setOrder_requested_date(String order_requested_date) {
        this.order_requested_date = order_requested_date;
    }
    /**
     * Returns the full name of the person who placed the order.
     *
     * @return the full name
     */
    public String getFull_name() {
        return full_name;
    }
    /**
     * Sets the full name of the person who placed the order.
     *
     * @param full_name the full name
     */
    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }
    /**
     * Returns the phone number of the person who placed the order.
     *
     * @return the phone number
     */
    public String getPhone_number() {
        return phone_number;
    }
    /**
     * Sets the phone number of the person who placed the order.
     *
     * @param phone_number the phone number
     */
    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
    /**
     * Returns the branch of the restaurant where the order was placed.
     *
     * @return the branch
     */
    public String getBranch() {
        return branch;
    }
    /**
     * Sets the branch of the restaurant where the order was placed.
     *
     * @param branch the branch
     */
    public void setBranch(String branch) {
        this.branch = branch;
    }
    /**
     * Returns the status of whether the order was received.
     *
     * @return the order received status
     */
    public String getOrder_received() {
        return order_received;
    }
    /**
     * Sets the status of whether the order was received.
     *
     * @param order_received the order received status
     */
    public void setOrder_received(String order_received) {
        this.order_received = order_received;
    }

    /**
     * Returns a string representation of the {@code RestaurantOrders} object.
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return "RestaurantOrders{" +
                "restaurant='" + restaurant + '\'' +
                ", order_number=" + order_number +
                ", total_price=" + total_price +
                ", order_list='" + order_list + '\'' +
                ", order_address='" + order_address + '\'' +
                ", user_id=" + user_id +
                ", restaurant_id=" + restaurant_id +
                ", placing_order_date='" + placing_order_date + '\'' +
                ", status='" + status + '\'' +
                ", delivery_type='" + delivery_type + '\'' +
                ", order_requested_date='" + order_requested_date + '\'' +
                ", full_name='" + full_name + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", branch='" + branch + '\'' +
                ", order_received='" + order_received + '\'' +  // Adjusted to String
                '}';
    }

    /**
     * Parses a string representation of a {@code RestaurantOrders} object and returns
     * a new instance of {@code RestaurantOrders}.
     *
     * @param toStringOutput the string representation of a {@code RestaurantOrders} object
     * @return a new instance of {@code RestaurantOrders}
     * @throws IllegalArgumentException if the input string is not a valid representation of a {@code RestaurantOrders} object
     */
    public static RestaurantOrders fromString(String toStringOutput) {
        Pattern pattern = Pattern.compile(
            "RestaurantOrders\\{restaurant='(.*?)', order_number=(\\d+), total_price=(\\d+\\.\\d+), order_list='(.*?)', order_address='(.*?)', user_id=(\\d+), restaurant_id=(\\d+), placing_order_date='(.*?)', status='(.*?)', delivery_type='(.*?)', order_requested_date='(.*?)', full_name='(.*?)', phone_number='(.*?)', branch='(.*?)', order_received='(.*?)'\\}"  // Adjusted to String
        );
        Matcher matcher = pattern.matcher(toStringOutput);

        if (matcher.find()) {
            String restaurant = matcher.group(1).equals("null") ? null : matcher.group(1);
            int order_number = Integer.parseInt(matcher.group(2));
            double total_price = Double.parseDouble(matcher.group(3));
            String order_list = matcher.group(4).equals("null") ? null : matcher.group(4);
            String order_address = matcher.group(5).equals("null") ? null : matcher.group(5);
            int user_id = Integer.parseInt(matcher.group(6));
            int restaurant_id = Integer.parseInt(matcher.group(7));
            String placing_order_date = matcher.group(8).equals("null") ? null : matcher.group(8);
            String status = matcher.group(9).equals("null") ? null : matcher.group(9);
            String delivery_type = matcher.group(10).equals("null") ? null : matcher.group(10);
            String order_requested_date = matcher.group(11).equals("null") ? null : matcher.group(11);
            String full_name = matcher.group(12).equals("null") ? null : matcher.group(12);
            String phone_number = matcher.group(13).equals("null") ? null : matcher.group(13);
            String branch = matcher.group(14).equals("null") ? null : matcher.group(14);
            String order_received = matcher.group(15).equals("null") ? null : matcher.group(15);  // Adjusted to String

            return new RestaurantOrders(restaurant, order_number, total_price, order_list, order_address, user_id,
                    restaurant_id, placing_order_date, status, delivery_type, order_requested_date, full_name,
                    phone_number, branch, order_received);
        } else {
            throw new IllegalArgumentException("Invalid toString output: " + toStringOutput);
        }
    }

    /**
     * Parses a string containing multiple {@code RestaurantOrders} objects and returns
     * a list of {@code RestaurantOrders} instances.
     *
     * @param arrayString the string containing multiple {@code RestaurantOrders} objects
     * @return a list of {@code RestaurantOrders} instances
     */
    public static ArrayList<RestaurantOrders> fromStringArray(String arrayString) {
        Pattern pattern = Pattern.compile(
            "RestaurantOrders\\{restaurant='(.*?)', order_number=(\\d+), total_price=(\\d+\\.\\d+), order_list='(.*?)', order_address='(.*?)', user_id=(\\d+), restaurant_id=(\\d+), placing_order_date='(.*?)', status='(.*?)', delivery_type='(.*?)', order_requested_date='(.*?)', full_name='(.*?)', phone_number='(.*?)', branch='(.*?)', order_received='(.*?)'\\}"  // Adjusted to String
        );
        Matcher matcher = pattern.matcher(arrayString);

        ArrayList<RestaurantOrders> restaurantOrders = new ArrayList<>();
        while (matcher.find()) {
            String restaurant = matcher.group(1).equals("null") ? null : matcher.group(1);
            int order_number = Integer.parseInt(matcher.group(2));
            double total_price = Double.parseDouble(matcher.group(3));
            String order_list = matcher.group(4).equals("null") ? null : matcher.group(4);
            String order_address = matcher.group(5).equals("null") ? null : matcher.group(5);
            int user_id = Integer.parseInt(matcher.group(6));
            int restaurant_id = Integer.parseInt(matcher.group(7));
            String placing_order_date = matcher.group(8).equals("null") ? null : matcher.group(8);
            String status = matcher.group(9).equals("null") ? null : matcher.group(9);
            String delivery_type = matcher.group(10).equals("null") ? null : matcher.group(10);
            String order_requested_date = matcher.group(11).equals("null") ? null : matcher.group(11);
            String full_name = matcher.group(12).equals("null") ? null : matcher.group(12);
            String phone_number = matcher.group(13).equals("null") ? null : matcher.group(13);
            String branch = matcher.group(14).equals("null") ? null : matcher.group(14);
            String order_received = matcher.group(15).equals("null") ? null : matcher.group(15);  // Adjusted to String

            restaurantOrders.add(new RestaurantOrders(restaurant, order_number, total_price, order_list, order_address,
                    user_id, restaurant_id, placing_order_date, status, delivery_type, order_requested_date, full_name,
                    phone_number, branch, order_received));
        }

        return restaurantOrders;
    }
}
