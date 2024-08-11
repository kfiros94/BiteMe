package entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RestaurantOrders 
{

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
    private String order_received;

    // Constructor
    public RestaurantOrders(String restaurant, int order_number, double total_price, String order_list, 
                            String order_address, int user_id, int restaurant_id, 
                            String placing_order_date, String status, String delivery_type, 
                            String order_requested_date, String full_name, String phone_number, 
                            String branch, String order_received) 
    {
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

    // Default Constructor
    public RestaurantOrders() {
    }

    // Getters and Setters

    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public int getOrder_number() {
        return order_number;
    }

    public void setOrder_number(int order_number) {
        this.order_number = order_number;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public String getOrder_list() {
        return order_list;
    }

    public void setOrder_list(String order_list) {
        this.order_list = order_list;
    }

    public String getOrder_address() {
        return order_address;
    }

    public void setOrder_address(String order_address) {
        this.order_address = order_address;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(int restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public String getPlacing_order_date() {
        return placing_order_date;
    }

    public void setPlacing_order_date(String placing_order_date) {
        this.placing_order_date = placing_order_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDelivery_type() {
        return delivery_type;
    }

    public void setDelivery_type(String delivery_type) {
        this.delivery_type = delivery_type;
    }

    public String getOrder_requested_date() {
        return order_requested_date;
    }

    public void setOrder_requested_date(String order_requested_date) {
        this.order_requested_date = order_requested_date;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String isOrder_received() {
        return order_received;
    }

    public void setOrder_received(String order_received) {
        this.order_received = order_received;
    }

    // toString method
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
                ", placing_order_date=" + placing_order_date +
                ", status='" + status + '\'' +
                ", delivery_type='" + delivery_type + '\'' +
                ", order_requested_date=" + order_requested_date +
                ", full_name='" + full_name + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", branch='" + branch + '\'' +
                ", order_received=" + order_received +
                '}';
    }
    
    
    
    
    
    public static RestaurantOrders fromString(String toStringOutput) {
        Pattern pattern = Pattern.compile(
            "RestaurantOrders\\{restaurant='(.*?)', order_number=(\\d+), total_price=(\\d+\\.\\d+), order_list='(.*?)', order_address='(.*?)', user_id=(\\d+), restaurant_id=(\\d+), placing_order_date=(.*?), status='(.*?)', delivery_type='(.*?)', order_requested_date=(.*?), full_name='(.*?)', phone_number='(.*?)', branch='(.*?)', order_received=(true|false)\\}"
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
            String order_received = matcher.group(15).equals("null") ? null : matcher.group(15);

            return new RestaurantOrders(restaurant, order_number, total_price, order_list, order_address, user_id,
                    restaurant_id, placing_order_date, status, delivery_type, order_requested_date, full_name,
                    phone_number, branch, order_received);
        } else {
            throw new IllegalArgumentException("Invalid toString output: " + toStringOutput);
        }
    }

    
    
    
    
    public static ArrayList<RestaurantOrders> fromStringArray(String arrayString) {
        Pattern pattern = Pattern.compile(
            "RestaurantOrders\\{restaurant='(.*?)', order_number=(\\d+), total_price=(\\d+\\.\\d+), order_list='(.*?)', order_address='(.*?)', user_id=(\\d+), restaurant_id=(\\d+), placing_order_date=(.*?), status='(.*?)', delivery_type='(.*?)', order_requested_date=(.*?), full_name='(.*?)', phone_number='(.*?)', branch='(.*?)', order_received=(true|false)\\}"
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
            String order_received = matcher.group(15).equals("null") ? null : matcher.group(15);

            restaurantOrders.add(new RestaurantOrders(restaurant, order_number, total_price, order_list, order_address,
                    user_id, restaurant_id, placing_order_date, status, delivery_type, order_requested_date, full_name,
                    phone_number, branch, order_received));
        }

        return restaurantOrders;
    }

    
    
    
    
    
    
    
    
    
}
