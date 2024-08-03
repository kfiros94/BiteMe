package mainApp;
	
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import logic.Order;
import java.lang.reflect.InvocationTargetException;
import java.util.Vector;
import guiPro.ServerPortFrameControllerPro;
import server.EchoServerPro;

public class ServerUIPro extends Application {
	final public static int DEFAULT_PORT = 5555;
	
	 
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


}
