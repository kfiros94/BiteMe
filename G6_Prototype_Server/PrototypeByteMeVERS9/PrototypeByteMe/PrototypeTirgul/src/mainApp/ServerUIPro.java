package mainApp;
	
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import logic.Order;
import java.lang.reflect.InvocationTargetException;
import java.util.Vector;
import guiPro.ServerPortFrameControllerPro;
import server.DBController;
import server.EchoServerPro;

public class ServerUIPro extends Application {
	final public static int DEFAULT_PORT = 5555;
	public static DBController dbc;

	 
	public static void main( String args[] ) throws Exception
	  {   
		 launch(args);
	  } // end main
	
	@Override
	  public void start(Stage primaryStage) throws InvocationTargetException {
	      try {
	         ServerPortFrameControllerPro aFrame = new ServerPortFrameControllerPro();
	         aFrame.start(primaryStage);

	      } catch (Exception var3) {
	         var3.printStackTrace();
	      }

	   }
	
	public static void runServer(String p)
	{
		int port = 0;
		try
        {
        	port = DEFAULT_PORT; //Set port to 5555
          
        }
        catch(Throwable t)
        {
        	System.out.println("ERROR - Could not connect!");
        }
        dbc = new DBController(port);
        
        try
        {
        	dbc.listen();//start listening for connections
        }
        catch (Exception ex) 
        {
          System.out.println("ERROR - Could not listen for clients!");
        }
	}
	public static void closeServer()
	{
		try 
        {
			dbc.sendToAllClients("Server Offline");
            dbc.close(); //stop listenning for connections
        } 
        catch (Exception ex) 
        {
          System.out.println("ERROR - Could not stop listen for clients!");
        }
	}


}
