package gui;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientController;
import client.ClientUI;
import common.ChatIF;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logic.Order;
import java.net.URL;
import java.util.ResourceBundle;


public  class SummaryOfExistingOrderController   
{
	
	//כלי עזר לשינוי צבע של הדפסות
    public static final String RESET = "\033[0m";  // Text Reset
    public static final String GREEN = "\033[0;32m";   // GREEN
    public static final String RED = "\033[0;31m";     // RED
    public static final String BLUE = "\033[0;34m";    // BLUE

	//כלי עזר לשינוי צבע של הדפסות
	
	
	
	private OrderFormController sfc;	
	private static int itemIndex = 3;
	
	
    @FXML
    private Label lbOrder_number;
	
    @FXML
    private Label messageLabel;
    
	@FXML
	private Button btnExit = null;
	
	@FXML
	private Button btnSend = null;
	
	@FXML
	private TextField Order_numbertxt;
	
	@FXML
	private TextField HostIP;
	
	private String getOrder_numbertxt() 
	{
		return Order_numbertxt.getText();
	}
	
	
	
	/*
	@FXML
	private void initialize() 
    {
        try 
        {
        	String ipAddress = InetAddress.getLocalHost().getHostAddress();
        	HostIP.setText(ipAddress);
        	System.out.println("IP: " + ipAddress);
        } 
        catch (UnknownHostException e) 
        {
            e.printStackTrace();
        }
    }
    */
	
	
	public void Send(ActionEvent event) throws Exception 
	{
		
		ClientUI.chat.setMyhost(HostIP.getText());//עדכון ההוסט של הלקוח בצורה ידנית מהמסך

		
		String Order_number;
		FXMLLoader loader = new FXMLLoader();
		
		Order_number=getOrder_numbertxt();
		System.out.println("test1: in"+GREEN+" Class SummaryOfExistingOrderController"+RESET+"The customer enters a number on the "+RESET+RED+"Order_numbertxt screen"+RESET);//TTTTTTTTTTTTTTTTTTTTTTTTTTTTT

		
		System.out.println("if in msg have only NUMBER: "+Order_number.matches("\\d+"));	
		
		if( !(Order_number.matches("\\d+")) || (Order_number.trim().isEmpty())) //אם במסך הראשון הלקוח מכניס משהו שהוא לא מספר טהור או לא רושם כלום
		{

			System.out.println("You must enter an order number");	
			
            messageLabel.setText("You must enter an order number");
            messageLabel.setVisible(true);
		}
		else
		{

		
			List<String> list=new ArrayList<>();
			list.add(Order_number);
			
			System.out.println("test1: in"+GREEN+" Class SummaryOfExistingOrderController"+RESET+" number of Order we send TO "+RESET+BLUE+"func --accept--"+RESET);//TTTTTTTTTTTTTTTTTTTTTTTTTTTTT
			ClientUI.chat.accept(list);
			System.out.println("test1: in"+GREEN+" Class SummaryOfExistingOrderController"+RESET+" There is an active standby at "+RESET+BLUE+"func --accept--"+RESET+"until a message returns from the server (return message)");//TTTTTTTTTTTTTTTTTTTTTTTTTTTTT

	   //	String a=ClientUI.chat.MyipHost;
			//ClientUI.chat.setMyhost(HostIP.getText());
		
			//אנחנו מגיעים לכאן לאחר שהשרת החזיר תשובה ללקוח והלקוח יצא מהמתנה פעילה
			//ניתן ערך של 1- במידה ומספר הזמנה שהקליד המשתמש לא קיים במסד נתונים
		//	if(String.valueOf(ChatClient.s1.getOrderNumber()).equals("-1"))
			if(ChatClient.s1.getRestaurant().equals("-1"))
			{
				System.out.println("Order_number Not Found");
				
	            messageLabel.setText("Order_number Not Found");
	            messageLabel.setVisible(true);
				
			}
			else 
			{
				System.out.println("Order_number Found");
				
				System.out.println("test1: in"+GREEN+" Class SummaryOfExistingOrderController"+RESET+" The server returned that there is an order, we will hide this window "+RESET+RED+"Summary Of Existing Order"+RESET);//TTTTTTTTTTTTTTTTTTTTTTTTTTTTT
				((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
				
				System.out.println("test1: in"+GREEN+" Class SummaryOfExistingOrderController"+RESET+" Open the new window: "+RESET+RED+"Order_number Managment Tool"+RESET);//TTTTTTTTTTTTTTTTTTTTTTTTTTTTT
				Stage primaryStage = new Stage();
				Pane root = loader.load(getClass().getResource("/gui/OrderForm.fxml").openStream());
				
				
				
				
				OrderFormController OrderFormController = loader.getController();		
				OrderFormController.loadOrder(ChatClient.s1);
			
				Scene scene = new Scene(root);			
				scene.getStylesheets().add(getClass().getResource("/gui/OrderForm.css").toExternalForm());
				primaryStage.setTitle("Order_number Managment Tool");
	
				primaryStage.setScene(scene);		
				primaryStage.show();
			}
		}
	}

	
	
	
	public void start(Stage primaryStage) throws Exception {	
		Parent root = FXMLLoader.load(getClass().getResource("/gui/SummaryOfExistingOrder.fxml"));
				
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/gui/SummaryOfExistingOrder.css").toExternalForm());
		primaryStage.setTitle("Summary of an Existing Order");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	public void getExitBtn(ActionEvent event) throws Exception {
		System.out.println("exit Summary of an Existing Order");	
		System.exit(0);//add for the method to actually close client window
	}
	
	public void loadOrder(Order s1) {
		this.sfc.loadOrder(s1);
	}	
	
	public  void display(String message) {
		System.out.println("message");
		
	}
	
}
