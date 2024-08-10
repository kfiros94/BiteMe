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
 * @author Yaniv shatil
 * @author Noam Furmann
 * @author Omri Hyat
 * @author Eithan Zerbel
 */

public class BiteOptions implements Serializable{
	/**
	 * Enum representing different options for casting 
	 */
	public enum Option{
		LOGIN, LOGOUT,SELECT_RESTAURANT,TEST_JSON,GET_SELECTED_REST_MENU, CREATE_ORDER, DELETE_ORDER, RETRIEVE_ORDER_LIST, REGISTER_USER,
		SEND_PAYMENT, ORDER_SUMMARY, LOGIN_COMPANY, LOGIN_RESTAURANT, 
		PRE_ORDER_CREATE, REFUND_RECEIVED, REDEEM_REFUND, GENERATE_CUPON,
		INVOICE_GENERATION, ORDER_RECIEVED_BY_SUPPLIER_CONFIRMATION, NOTIFY_COSTUMER_VIA_SMS, ORDER_READY_CONFIRMATION,
		ORDER_RECIEVED_BY_COSTUMER, UPDATE_MENU, INCOME_REPORT_GENERATION, ORDER_REPORT_GENERATION,
		PERFORMANCE_REPORT_GENERATION, PREFORMANCE_REPORT_GRAPH_SHOW, MANAGER_BRANCH_LOCATION, VIEW_REPORTS, PERFORMANCE_REPORT_HISTOGRAM, 
		QUARTERLY_REPORT_GENERATION_CEO, QUARTERLY_REPORT_GENERATION_CEO_SHOW, ORDER_DISTRIBUTION_GENERATION_HISTOGRAM, SIMULTANEOUS_VIEW_PAST_QUARTERLY_REPORT,
		SIMULTANEOUS_VIEW_PAST_QUARTERLY_REPORT_CEO, GENERATE_NEW_REPORTS_FOR_CEO, IMPORT_NEW_USERS;//We may add more options
	}
	
	private Object data;
	private Option option;
	
	public BiteOptions()
	{

	}
	
	
	public BiteOptions(Object data, Option option)
	{
		this.data = data;
		this.option = option;
	}
	
	public Object getData() {
		return data;
	}
	
	public void setData(Object data) {
		this.data = data;
	}
	
	public Option getOption() {
		return option;
	}
	
	public void setOption(Option option) {
		this.option = option;
	}
	
	@Override
	public String toString() 
	{
		return String.format("[%s, %s]", data, option);
	}
}
