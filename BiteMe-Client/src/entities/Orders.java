package entities;

import java.io.Serializable;

public class Orders implements Serializable
{

	private String restaurant;
	private int Order_number;
	private float Total_price;
	private int Order_list_number;
	private String Order_address;
	private int  restaurantID;
	private int UserID;
	private ClientInfo cl;

	
	/**
	 * @param restaurant
	 * @param Order_number
	 * @param Total_price
	 * @param Order_list_number
	 * @param Order_address
	 */
	
	public Orders(){};
	
	public Orders(String restaurant, int Order_number, float Total_price, int Order_list_number, String Order_address ,ClientInfo c) 
	{
		super();
		this.restaurant = restaurant;
		this.Order_number = Order_number;
		this.Total_price = Total_price;
		this.Order_list_number = Order_list_number;
		this.Order_address = Order_address;
		this.cl= c;
	}

	
	public int getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(int restaurantID) {
        this.restaurantID = restaurantID;
    }

    // Getter and Setter for UserID
    public int getUserID() {
        return UserID;
    }

    public void setUserID(int UserID) {
        this.UserID = UserID;
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
