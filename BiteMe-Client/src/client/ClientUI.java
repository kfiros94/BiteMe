package client;
import javafx.application.Application;

import javafx.stage.Stage;

import java.net.InetAddress;
import java.util.Vector;
import gui.LogInUserController;
import gui.MainPagesClientController;
import client.ClientController;
import entities.ClientInfo;
import entities.Order;

public class ClientUI extends Application {
	public static ClientController chat; //only one instance
	
    public static String ipAddress;

	//כלי עזר לשינוי צבע של הדפסות
    public static final String RESET = "\033[0m";  // Text Reset
    public static final String GREEN = "\033[0;32m";   // GREEN
    public static final String RED = "\033[0;31m";     // RED
	//כלי עזר לשינוי צבע של הדפסות
		
	public static void main( String args[] ) throws Exception
	   { 
		System.out.println("test0: run app Clinet from"+GREEN+ " Class ClientUI"+RESET);//TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT	
		System.out.println("test0: we will wait for the event from the"+RED+ " Summary of an Existing Order"+RESET+ " screen");//TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT	

		    launch(args);  
	   } 
	// end main
	 
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
