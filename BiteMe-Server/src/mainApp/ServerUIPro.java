package mainApp;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.lang.reflect.InvocationTargetException;
import java.util.Vector;

import entities.Order;
import guiPro.ServerPortFrameControllerPro;
import server.EchoServerPro;

/**
 * The {@code ServerUIPro} class serves as the main entry point for the server-side application.
 * It extends the {@code Application} class from JavaFX to launch the server UI.
 * 
 * @author Kfir Amoyal
 * @author Israel Ohayon
 * @author Yaniv Shatil
 * @author Noam Furman
 * @author Omri Heit
 * @author Eithan Zerbel
 */

public class ServerUIPro extends Application {
    /**
     * The default port number on which the server listens for incoming connections.
     */
    final public static int DEFAULT_PORT = 5555;
    
    /**
     * The main method, which serves as the entry point of the application.
     * It launches the JavaFX application.
     *
     * @param args command-line arguments passed during the application launch
     * @throws Exception if an error occurs during the launch of the application
     */
    public static void main( String args[] ) throws Exception
       {   
         launch(args);
      } // end main
    
    /**
     * The start method is called after the JavaFX application is launched.
     * It initializes and displays the server port configuration frame.
     *
     * @param primaryStage the primary stage for this application, onto which the application scene can be set
     * @throws InvocationTargetException if an error occurs while invoking methods via reflection
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