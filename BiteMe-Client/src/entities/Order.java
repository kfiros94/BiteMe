package entities;

import java.io.Serializable;

/**
 * The Order class represents an order made in a restaurant.
 * It implements Serializable to allow its objects to be serialized.
 * 
 * @author Kfir Amoyal
 * @author Israel Ohayon
 * @author Yaniv Shatil
 * @author Noam Furmann
 * @author Omri Heit
 * @author Eithan Zerbel
 */

public class Order implements Serializable
{
	private String restaurant;
	private int Order_number;
	private float Total_price;
	private int Order_list_number;
	private String Order_address;
	private ClientInfo cl;
	
	/**
     * Constructs a new Order with the specified details.
     *
     * @param restaurant       		The name of the restaurant
     * @param Order_number     		The unique order number
     * @param Total_price      		The total price of the order
     * @param Order_list_number 	The list number of the order
     * @param Order_address    		The address where the order needs to be delivered
     * @param c                		The client information
     */
	public Order(String restaurant, int Order_number, float Total_price, int Order_list_number, String Order_address ,ClientInfo c) 
	{
		super();
		this.restaurant = restaurant;
		this.Order_number = Order_number;
		this.Total_price = Total_price;
		this.Order_list_number = Order_list_number;
		this.Order_address = Order_address;
		this.cl= c;
	}	

	/**
     * Gets the client information.
     *
     * @return the client information
     */
	public ClientInfo getClientInfo() 
	{
		return cl;
	}
	
	/**
     * Sets the client information.
     *
     * @param cl 	The client information to set
     */
	public void setClientInfo(ClientInfo cl) 
	{
		this.cl = cl;
	}
	
	/**
     * Gets the restaurant name.
     *
     * @return 	The restaurant name
     */
	public String getRestaurant() 
	{
		return restaurant;
	}	
	
	/**
     * Sets the restaurant name.
     *
     * @param restaurant the restaurant name to set
     */
	public void setRestaurant(String restaurant) 
	{
		this.restaurant = restaurant;
	}	

	/**
     * Gets the order number.
     *
     * @return the order number
     */
	public int getOrderNumber() 
	{
		return Order_number;
	}	
	
	/**
     * Sets the order number.
     *
     * @param Order_number the order number to set
     */
	public void setOrderNumber(int Order_number) 
	{
		this.Order_number = Order_number;
	}	
	
	/**
     * Gets the total price of the order.
     *
     * @return the total price
     */
	public float getTotalPrice() 
	{
		return Total_price;
	}	
	
	/**
     * Sets the total price of the order.
     *
     * @param Total_price the total price to set
     */
	public void setTotalPrice(float Total_price) 
	{
		this.Total_price = Total_price;
	}	
	
	/**
     * Gets the order list number.
     *
     * @return the order list number
     */
	public int getOrderListNumber()
	{
		return Order_list_number;
	}	
	
	/**
     * Sets the order list number.
     *
     * @param Order_list_number the order list number to set
     */
	public void setOrderListNumber(int Order_list_number)
	{
		this.Order_list_number = Order_list_number;
	}	

	/**
     * Gets the order address.
     *
     * @return the order address
     */
	public String getOrderAddress() 
	{
		return Order_address;
	}	
	
	/**
     * Sets the order address.
     *
     * @param Order_address the order address to set
     */
	public void setOrderAddress(String Order_address) 
	{
		this.Order_address = Order_address;
	}
	
	/**
     * Returns a string representation of the Order object.
     *
     * @return a string representation of the Order object
     */
	@Override
	public String toString() 
	{
		return String.format("[%s,%d,%.2f,%d,%s]", restaurant, Order_number, Total_price, Order_list_number, Order_address);
	}
}