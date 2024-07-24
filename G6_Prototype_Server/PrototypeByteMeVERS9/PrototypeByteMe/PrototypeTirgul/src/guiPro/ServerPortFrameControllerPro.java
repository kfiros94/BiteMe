package guiPro;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import logic.ClientInfo;
//import server.DBController;
import server.EchoServerPro;

import java.net.URL;
import java.util.ResourceBundle;

public class ServerPortFrameControllerPro implements Initializable 
{
    private EchoServerPro ev;

    // UI components
    @FXML
    private TextField PortTxt, DBPasswordTxt;

    // IP address text fields
    @FXML
    private TextField ip1, ip2, ip3, ip4;

    @FXML
    private Button btnClose;

    @FXML
    private Button startserverBtn;

    
    @FXML
    private Button ConnectBtn;
    
    @FXML
    private Label messageLabel;
    
    @FXML
    private Label connection_Succ;

    @FXML
    private TableColumn ipCol;
    
    @FXML
    private TableColumn statusCol;
    
    private List<TextField> txtFields = new ArrayList<>();

    @FXML
    private ObservableList<ClientInfo> list = FXCollections.observableArrayList();

    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
    	
        try 
        {
            String ipAddress = InetAddress.getLocalHost().getHostAddress();
            String[] ipSegments = ipAddress.split("\\.");
            if (ipSegments.length == 4) {
                ip1.setText(ipSegments[0]);
                ip2.setText(ipSegments[1]);
                ip3.setText(ipSegments[2]);
                ip4.setText(ipSegments[3]);
            }
        } 
        catch (UnknownHostException e) 
        {
            e.printStackTrace();
        }
    }

    
    
    public void start(Stage primaryStage) throws Exception 
    {
        try 
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/guiPro/ServerGui.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setTitle("Server Connection");
            primaryStage.setScene(scene);
            primaryStage.show();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            System.out.println("Error loading FXML file.");
        }
    }

    
    
    
    private String getPort() 
    {
        return PortTxt.getText();
    }

    
    
    private String getDBPassword() 
    {
        return DBPasswordTxt.getText();
    }

    
    
    
    // Method to handle server start action
    @FXML
    void serveStartAction(ActionEvent event) throws Exception 
    {
        String port = getPort();
        String dbPassword = getDBPassword();

        // Check if any field is empty
        if (port.trim().isEmpty() || dbPassword.trim().isEmpty()) 
        {
            messageLabel.setText("Please fill in all fields.");
            messageLabel.setVisible(true);
            return;
        }
        
    
        
        if (runServer(port, dbPassword)) 
        {
          System.out.println("Server connection succseed");
          //  ((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
           // runServer(port, dbPassword);
        }
        
        
        /*
        if (validateDBPassword(dbPassword)) 
        {
            ((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
            runServer(port, dbPassword);
        }
        */
        
        
        else 
        {
            messageLabel.setText("Incorrect DB Password A.");
        	connection_Succ.setVisible(false);
            messageLabel.setVisible(true);
        }
        

    }

    
    
    /*
    private boolean validateDBPassword(String dbPassword) 
    {
        // Replace with actual DB password validation logic
    	//משווים את הסיסמה לעצמה כרגע זה למלמי אבל מאד חשוב כאשר קוראים למתודה של התחלת שרת ששמה
    	//serveStartAction
        return "AAXXCi943756".equals(dbPassword);// aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
    }
	*/
    
    
    //קודם מנסה להתחבר למסד נתונים ורק אם הצלחתי מנסה להאזין ללקוחות
    // Method to run the server
    public boolean runServer(String port, String dbPassword) 
    {
        int portNumber = 0; // Port to listen on

        try 
        {
            portNumber = Integer.parseInt(port); // Set port
        } 
        catch (NumberFormatException t) 
        {
            System.out.println("ERROR - Could not connect!");
            return false;
        }

        EchoServerPro sv = new EchoServerPro(portNumber, this);
        
        
        
        if (!sv.connectToDataBase(dbPassword))
        {
        	//אם אני מעלים את ההדפסה של סיסמה לא נכונה א,אז רואים שזה עובד
        	//messageLabel.setText("Incorrect DB Password B.");//aaaaaaaaaaaaaaaaa
        	return false;
        }
        
        
        //כאן צריך להוסיף תנאי מה קורה אם לא הצלחנו להתחבר למסד נתונים בגלל סיסמה לא נכונה
      //  sv.connectToDataBase(dbPassword); // Connect to the database

        try 
        {
        	connection_Succ.setVisible(true);
        	messageLabel.setVisible(false);
            sv.listen();
            return true;
            
        } 
        catch (Exception ex) 
        {
            System.out.println("ERROR - Could not listen for clients!");
            return false;
        }
		
    }

    
    
    
    
    @FXML
    private void closeAction(ActionEvent event) 
    {
        System.out.println("Closing application");
        System.exit(0);
    }

    
    
    public void refreshTableView() 
    {
        // TODO: Implement method to refresh the TableView with client_info data
    }
}
