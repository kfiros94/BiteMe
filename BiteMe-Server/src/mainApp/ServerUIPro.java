package mainApp;
	
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.lang.reflect.InvocationTargetException;
import java.util.Vector;

import entities.RestaurantOrders;
import guiPro.ServerPortFrameControllerPro;
import server.EchoServerPro;
/**
 * The class serves as the main entry point for the server-side
 * application. It extends the JavaFX {@code Application} class and manages the 
 * launch and initialization of the server UI.
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
public class ServerUIPro extends Application {
	final public static int DEFAULT_PORT = 5555;
	
    /**
     * The main method, which serves as the entry point of the application.
     *
     * @param args the command-line arguments
     * @throws Exception if there is an error during application launch
     */
	public static void main( String args[] ) throws Exception
	   {   
		 launch(args);
	  } // end main
	
	
    /**
     * The start method is called after the JavaFX application is launched.
     * This method initializes and starts the server UI.
     *
     * @param primaryStage the primary stage for this application
     * @throws InvocationTargetException if an exception is thrown by an invoked method or constructor
     */
	@Override
	  public void start(Stage primaryStage) throws InvocationTargetException {
	      try {
	         ServerPortFrameControllerPro aFrame = new ServerPortFrameControllerPro();
	         aFrame.start(primaryStage);

	      } catch (Exception var3) {
	         var3.printStackTrace();
	      }

	   }

}
