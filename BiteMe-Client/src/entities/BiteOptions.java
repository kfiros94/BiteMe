package entities;

import java.io.Serializable;

import entities.BiteOptions.Option;

/**
 * BiteOptions is an entity that represents a set of options 
 * that are implemented by a enum.
 * This class helps for communication between client and server and 
 * also for casting the required classes options and their data.
 * 
 * This class is typically used to specify the type of operation to be performed
 * during client-server communication.
 * 
 * each options corresponds to a specific operation.
 * such as logging in, create order, update menu etc.
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
public class BiteOptions implements Serializable{
	/**
	 * Enum represents the different operations that can be performed.
     * Each constant corresponds to a specific client-server operation. 
	 */
	public enum Option{
		LOGIN, LOGOUT,SELECT_RESTAURANT,TEST_JSON,GET_SELECTED_REST_MENU, BACK_HOME_CUSTOMER_PAGE, CREATE_ORDER,GET_USER_ORDERS,UPDATE_ORDER_STATUS_CUSTOMER, DELETE_ORDER, RETRIEVE_ORDER_LIST, REGISTER_USER,
		SEND_PAYMENT, ORDER_SUMMARY, LOGIN_COMPANY, LOGIN_RESTAURANT,DELETE_ITEM_MENU,SHOW_MENU_RESTAURANT, FETCH_INCOME_REPORTS,FETCH_ORDER_REPORTS,FETCH_PERFORMENCE_REPORTS,
		PRE_ORDER_CREATE, REFUND_RECEIVED, REDEEM_REFUND, GENERATE_CUPON,CHANGE_ORDER_STATUS,GET_USER_FOR_NOTIFICATION,GET_RESTAURANT_ORDERS,CREATE_USER,
		INVOICE_GENERATION, ORDER_RECIEVED_BY_SUPPLIER_CONFIRMATION, NOTIFY_COSTUMER_VIA_SMS, ORDER_READY_CONFIRMATION,CLASIFY_UPDATE_USER_BY_EMAIL,
		ORDER_RECIEVED_BY_COSTUMER, UPDATE_MENU, INCOME_REPORT_GENERATION, ORDER_REPORT_GENERATION,UPDATE_CUSTOMER,UPDATE_RESTAURANT,
		PERFORMANCE_REPORT_GENERATION, PREFORMANCE_REPORT_GRAPH_SHOW, MANAGER_BRANCH_LOCATION, VIEW_REPORTS, PERFORMANCE_REPORT_HISTOGRAM, 
		QUARTERLY_REPORT_GENERATION_CEO, QUARTERLY_REPORT_GENERATION_CEO_SHOW, ORDER_DISTRIBUTION_GENERATION_HISTOGRAM, SIMULTANEOUS_VIEW_PAST_QUARTERLY_REPORT,
		SIMULTANEOUS_VIEW_PAST_QUARTERLY_REPORT_CEO, GENERATE_NEW_REPORTS_FOR_CEO, IMPORT_NEW_USERS;//We may add more options
	}
	/**
     * The data associated with the operation represented by the {@code Option}.
     */
	private Object data;
	/**
     * The specific option indicating the type of operation to be performed.
     */
	private Option option;
	/**
     * Default constructor for initializing a new instance with no data or option set.
     */
	public BiteOptions()
	{

	}
	
	/**
     * Constructs a new BiteOptions instance with the specified data and option.
     * 
     * @param data the data associated with the operation
     * @param option the specific option representing the operation
     */
	public BiteOptions(Object data, Option option)
	{
		this.data = data;
		this.option = option;
	}
	/**
     * Returns the data associated with this BiteOptions instance.
     * 
     * @return the data associated with the operation
     */
	public Object getData() {
		return data;
	}
    /**
     * Sets the data associated with this BiteOptions instance.
     * 
     * @param data the data to be associated with the operation
     */
	public void setData(Object data) {
		this.data = data;
	}
    /**
     * Returns the specific option representing the operation to be performed.
     * 
     * @return the option representing the operation
     */
	public Option getOption() {
		return option;
	}
    /**
     * Sets the specific option representing the operation to be performed.
     * 
     * @param option the option to be set for the operation
     */
	public void setOption(Option option) {
		this.option = option;
	}
    /**
     * Returns a string representation of the BiteOptions instance.
     * 
     * @return a string in the format "[data, option]"
     */
	@Override
	public String toString() 
	{
		return String.format("[%s, %s]", data, option);
	}
}
