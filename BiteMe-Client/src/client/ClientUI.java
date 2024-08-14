package client;
import javafx.application.Application;

import javafx.stage.Stage;

import java.net.InetAddress;
import java.util.Vector;
import gui.LogInUserController;
import gui.MainPagesClientController;
import client.ClientController;
import entities.ClientInfo;
import entities.RestaurantOrders;

public class ClientUI extends Application {
	public static ClientController chat; //only one instance
	
    public static String ipAddress;

    public static final String RESET = "\033[0m";  // Text Reset
    public static final String GREEN = "\033[0;32m";   // GREEN
    public static final String RED = "\033[0;31m";     // RED
	
    /**
     * The main method is the entry point of the application. 
     * It launches the JavaFX application.
     *
     * @param args the command line arguments
     * @throws Exception if an error occurs during application launch
     */
	public static void main( String args[] ) throws Exception
	   { 
		System.out.println("test0: run app Clinet from"+GREEN+ " Class ClientUI"+RESET);//TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT	
		System.out.println("test0: we will wait for the event from the"+RED+ " Summary of an Existing Order"+RESET+ " screen");//TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT	

		    launch(args);  
	   } 
	// end main
	 
	 
    /**
     * The start method is called after the application is launched.
     * It initializes the client controller with the local IP address and 
     * starts the login user interface.
     *
     * @param primaryStage the primary stage for this application
     * @throws Exception if an error occurs during initialization
     */
	@Override
	public void start(Stage primaryStage) throws Exception {
		ipAddress = InetAddress.getLocalHost().getHostAddress();
		System.out.println("IP: " + ipAddress);
		//chat= new ClientController("192.168.171.35", 5555);
		 chat= new ClientController(ipAddress, 5555);
		LogInUserController aFrame = new LogInUserController(); // create OrderFrame
		 
		aFrame.start(primaryStage);
		
		
	}
	
	
}
