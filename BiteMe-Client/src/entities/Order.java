package entities;

import java.io.Serializable;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.lang.reflect.Type;

public class Order implements Serializable
{
	//The fields placed by the order inside the Data-base!
	private String restaurant_name;
	private int Order_number;
	private float total_price;
	private Map<String, Object> order_list; // List of JSON files, each represents an menu item.
	private String order_address;
	int user_id;
	int restaurant_id;
	String placing_order_date; //CHNAGE MAY NEEDED - inside database is of type "datetime".
	String status; //CHNAGE MAY NEEDED - inside database is of type "enum".
	String delivery_type; //CHNAGE MAY NEEDED - inside database is of type "enum".
	String order_requested_date; //CHNAGE MAY NEEDED - inside database is of type "datetime".
	String phoneNumber; //The phone number connected to the order (FROM ENTITY USER!!).
	private ClientInfo cl;
	
	
	/**
	 * Constructor of 'Order' that initialize ALL THE FIELDS!
	 * @param restaurant
	 * @param Order_number
	 * @param Total_price
	 * @param order_List
	 * @param Order_address
	 * @param user_id
	 * @param restaurant_id
	 * @param placing_order_date
	 * @param status
	 * @param delivery_type
	 * @param order_requested_date
	 * @param cl
	 */
	public Order(String restaurant_name, int Order_number, float Total_price, Map<String, Object> order_List,
		String Order_address ,int user_id,int restaurant_id, String placing_order_date, String status, 
		String delivery_type, String order_requested_date, ClientInfo cl) 
	{
		super();
		this.restaurant_name = restaurant_name;
		this.Order_number = Order_number;
		this.total_price = Total_price;
		this.order_list = order_List;
		this.order_address = Order_address;
		this.user_id = user_id;
		this.restaurant_id = restaurant_id;
		this.placing_order_date = placing_order_date;
		this.status = status;
		this.delivery_type = delivery_type;
		this.order_requested_date = order_requested_date;
		this.cl= cl;
	}
	
	/**
	 * constructor of 'Order' that match the order view in restaurants.
	 */
	/*
	public Order(int Order_number, String delivery_type, String placing_order_date, String status, String phoneNumber) {
		this.Order_number = Order_number;
		this.delivery_type = delivery_type;
		this.placing_order_date = placing_order_date;
		this.status = status;
        this.phoneNumber = phoneNumber;
	}*/
	
	/**
	 * Empty constructor of 'Order'.
	 */
	public Order() {
		
	}
	
	public Order(String restaurant_name, int Order_number, float Total_price, int Order__number, String Order_address ,ClientInfo c) 
	{
		super();
		this.restaurant_name = restaurant_name;
		this.Order_number = Order_number;
		this.total_price = Total_price;
		this.Order_number = Order__number;
		this.order_address = Order_address;
		this.cl= c;
	}
	
	/**
	 * @return the phone number of the order
	 */
	public String getPhoneNumber() 
	{
		return phoneNumber;
	}

	/**
	 * @param phoneNumber of the order
	 */
	public void setPhoneNumber(String phoneNumber) 
	{
		this.phoneNumber = phoneNumber;
	}

	public ClientInfo getClientInfo() 
	{
		return cl;
	}
	
	public void setClientInfo(ClientInfo cl) 
	{
		this.cl = cl;
	}
	
	/**
	 * @return the restaurant
	 */
	public String getRestaurantName() 
	{
		return restaurant_name;
	}

	/**
	 * @param restaurant the restaurant to set
	 */
	public void setRestaurantName(String restaurant) 
	{
		this.restaurant_name = restaurant;
	}
	
	/**
	 * @return the Order_number
	 */
	public int getOrderNumber() 
	{
		return Order_number;
	}

	/**
	 * @param Order_number the Order_number to set
	 */
	public void setOrderNumber(int Order_number) 
	{
		this.Order_number = Order_number;
	}

	/**
	 * @return the Total_price
	 */
	public float getTotalPrice() 
	{
		return total_price;
	}

	/**
	 * @param Total_price the Total_price to set
	 */
	public void setTotalPrice(float Total_price) 
	{
		this.total_price = Total_price;
	}

	/**
	 * @return the Order_List
	 */
	public Map<String, Object> getOrder_List()
	{
		return order_list;
	}

	/**
	 * @param Order_List -> the Order_List to set
	 */
	public void setOrderList(Map<String, Object> order_list)
	{
		this.order_list = order_list;
	}

	/**
	 * @return the Order_address
	 */
	public String getOrderAddress() 
	{
		return order_address;
	}

	/**
	 * @param Order_address the Order_address to set
	 */
	public void setOrderAddress(String Order_address) 
	{
		this.order_address = Order_address;
	}
	
	/**
	 * 
	 * @return the user id
	 */
	public int getUserID() {
		return user_id;
	}
	
	/**
	 * 
	 * @param order_id
	 */
	public void setUserID(int order_id) {
		this.user_id = user_id;
	}
	
	/**
	 * @return the restaurant id
	 */
	public int getRestaurantID() {
		return restaurant_id;
	}
	
	/**
	 * @param restaurant_id
	 */
	public void setRestaurantID(int restaurant_id) {
		this.restaurant_id = restaurant_id;
	}
	
	/**
	 * @return the placing order date
	 */
	public String getPlacingOrderDate() {
		return placing_order_date;
	}
	
	/**
	 * @param placing_order_date
	 */
	public void setPlacingOrderDate(String placing_order_date) {
		this.placing_order_date = placing_order_date;
	}
	
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	
	/**
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	/**
	 * @return the delivery type
	 */
	public String getDeliveryType() {
		return delivery_type;
	}
	
	/**
	 * @param delivery_type
	 */
	public void setDeliveryType(String delivery_type) {
		this.delivery_type = delivery_type;
	}
	
	/**
	 * @return order requested date
	 */
	public String getOrderRequestedDate() {
		return order_requested_date;
	}
	
	/**
	 * @param order_requested_date
	 */
	public void setOrderRequestedDate(String order_requested_date) {
		this.order_requested_date = order_requested_date;
	}
	
    /**
     * Converts a JSON string to a Map<String, Object>
     * 
     * @param jsonString the JSON string to convert
     * @return a Map<String, Object> representing the JSON data
     */
    public static Map<String, Object> jsonToMap(String jsonString) {
    	Map<String, Object> map = new HashMap<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            jsonObject.keys().forEachRemaining(key -> {
                try {
                    map.put(key, jsonObject.get(key));//CHANGE/////////////////////////
                    
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * Converts a Map<String, Object> to a JSON string
     * 
     * @param map the map to convert
     * @return a JSON string representing the map data
     */
    public static String mapToJson(Map<String, Object> map) {
        return new JSONObject(map).toString();
    }
    
    /**
     * Converts a simple String to an ArrayList<Order> using regex
     * 
     * @param str the string to convert
     * @return an ArrayList<Order> representing the orders
     */
    public static ArrayList<Order> stringToOrderList(String str) {
        ArrayList<Order> orders = new ArrayList<>();
        
        // Regex pattern to match each order
        String orderPattern = "(.*?),(\\d+),(\\d+\\.\\d+),(.*?),(\\d+),(\\d+),(.*?),(.*?),(.*?),(.*?);";
        Pattern pattern = Pattern.compile(orderPattern);
        Matcher matcher = pattern.matcher(str);

        while (matcher.find()) {
            // Extract each field using the matcher groups
            String restaurant_name = matcher.group(1);
            int Order_number = Integer.parseInt(matcher.group(2));
            float total_price = Float.parseFloat(matcher.group(3));
            String order_address = matcher.group(4);
            int user_id = Integer.parseInt(matcher.group(5));
            int restaurant_id = Integer.parseInt(matcher.group(6));
            String placing_order_date = matcher.group(7);
            String status = matcher.group(8);
            String delivery_type = matcher.group(9);
            String order_requested_date = matcher.group(10);

            // Create the Order object and add it to the list
            Order order = new Order(
                restaurant_name,
                Order_number,
                total_price,
                new HashMap<>(), // Assuming empty map for order_list
                order_address,
                user_id,
                restaurant_id,
                placing_order_date,
                status,
                delivery_type,
                order_requested_date,
                null // Assuming null for ClientInfo (can be parsed similarly if needed)
            );
            orders.add(order);
        }

        return orders;
    }

    /**
     * Converts an ArrayList<Order> to a simple String using regex
     * 
     * @param orders the list of orders to convert
     * @return a string representation of the orders
     */
    public static String orderListToString(ArrayList<Order> orders) {
        StringBuilder sb = new StringBuilder();

        for (Order order : orders) {
            sb.append(order.restaurant_name).append(",")
              .append(order.Order_number).append(",")
              .append(order.total_price).append(",")
              .append(order.order_address).append(",")
              .append(order.user_id).append(",")
              .append(order.restaurant_id).append(",")
              .append(order.placing_order_date).append(",")
              .append(order.status).append(",")
              .append(order.delivery_type).append(",")
              .append(order.order_requested_date).append(";");
        }

        // Remove the last semicolon if present
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }

        return sb.toString();
    }

    @Override
    public String toString() {
        return "Order{" +
                "restaurantName='" + restaurant_name + '\'' +
                ", orderNumber=" + Order_number +
                ", totalPrice=" + total_price +
                ", orderList=" + order_list +
                ", orderAddress='" + order_address + '\'' +
                ", userId=" + user_id +
                ", restaurantId=" + restaurant_id +
                ", placingOrderDate='" + placing_order_date + '\'' +
                ", status='" + status + '\'' +
                ", deliveryType='" + delivery_type + '\'' +
                ", orderRequestedDate='" + order_requested_date + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", clientInfo=" + cl +
                '}';
    }

	/*
    @Override
    public String toString() {
        return "Order{" +
                "restaurant='" + restaurant + '\'' +
                ", orderNumber=" + Order_number +
                ", totalPrice=" + Total_price +
                ", orderListNumber=" + Order_list_number +
                ", orderAddress='" + Order_address + '\'' +
                '}';
    }
    */
	
	
	
	
	
}
