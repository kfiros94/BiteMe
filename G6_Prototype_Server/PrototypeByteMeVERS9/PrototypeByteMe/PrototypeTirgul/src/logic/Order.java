package logic;

import java.io.Serializable;

public class Order implements Serializable
{

	private String restaurant;
	private int Order_number;
	private float Total_price;
	private int Order_list_number;
	private String Order_address;

	
	/**
	 * @param restaurant
	 * @param Order_number
	 * @param Total_price
	 * @param Order_list_number
	 * @param Order_address
	 */
	public Order(String restaurant, int Order_number, float Total_price, int Order_list_number, String Order_address) 
	{
		super();
		this.restaurant = restaurant;
		this.Order_number = Order_number;
		this.Total_price = Total_price;
		this.Order_list_number = Order_list_number;
		this.Order_address = Order_address;
	}

	
	/**
	 * @return the restaurant
	 */
	public String getRestaurant() 
	{
		return restaurant;
	}

	
	
	/**
	 * @param restaurant the restaurant to set
	 */
	public void setRestaurant(String restaurant) 
	{
		this.restaurant = restaurant;
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
		return Total_price;
	}

	
	
	/**
	 * @param Total_price the Total_price to set
	 */
	public void setTotalPrice(float Total_price) 
	{
		this.Total_price = Total_price;
	}

	
	
	/**
	 * @return the Order_list_number
	 */
	public int getOrderListNumber()
	{
		return Order_list_number;
	}

	
	
	/**
	 * @param Order_list_number the Order_list_number to set
	 */
	public void setOrderListNumber(int Order_list_number)
	{
		this.Order_list_number = Order_list_number;
	}
	
	

	/**
	 * @return the Order_address
	 */
	public String getOrderAddress() 
	{
		return Order_address;
	}

	
	
	/**
	 * @param Order_address the Order_address to set
	 */
	public void setOrderAddress(String Order_address) 
	{
		this.Order_address = Order_address;
	}

	
	
	
	/*
	@Override
	public String toString() 
	{
		return String.format("Order[restaurant=%s, Order_number=%d, Total_price=%.2f, Order_list_number=%d, Order_address=%s]", 
			restaurant, Order_number, Total_price, Order_list_number, Order_address);
	}
	*/
	
	
	@Override
	public String toString() 
	{
		return String.format("[%s,%d,%.2f,%d,%s]", restaurant, Order_number, Total_price, Order_list_number, Order_address);
	}
	
	
	
	
	
}
