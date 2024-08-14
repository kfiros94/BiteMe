package guiPro;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import server.EchoServerPro;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.net.URL;
import java.util.ResourceBundle;

import entities.ClientInfo;
/**
 * Controller class for managing the server port frame GUI.
 * Handles interactions with the user, manages server connections, 
 * and updates the client connection table.
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
public class ServerPortFrameControllerPro implements Initializable {
    //private EchoServerPro ev;
   // private  EchoServerPro sv;

    @FXML
    private TextField PortTxt, DBPasswordTxt;

    @FXML
    private TextField ip1, ip2, ip3, ip4;

    @FXML
    private Button btnClose, startserverBtn, ConnectBtn;

    @FXML
    private Label messageLabel, connection_Succ;

    @FXML
    private TableColumn<ClientInfo, String> ipCol, statusCol;

    @FXML
    private TableView<ClientInfo> tableView;

    private ObservableList<ClientInfo> list = FXCollections.observableArrayList();
    
    private EchoServerPro server;
    /**
     * Initializes the controller class. Sets the IP address fields 
     * and configures the table view for displaying client information.
     * 
     * @param location The location used to resolve relative paths for the root object.
     * @param resources The resources used to localize the root object.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            String ipAddress = InetAddress.getLocalHost().getHostAddress();
            String[] ipSegments = ipAddress.split("\\.");
            if (ipSegments.length == 4) {
                ip1.setText(ipSegments[0]);
                ip2.setText(ipSegments[1]);
                ip3.setText(ipSegments[2]);
                ip4.setText(ipSegments[3]);
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        ipCol.setCellValueFactory(new PropertyValueFactory<>("ip"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        tableView.setItems(list);
    }
    /**
     * Starts the primary stage for the server GUI.
     * 
     * @param primaryStage The primary stage for this application.
     * @throws Exception if an error occurs while loading the FXML file.
     */
    public void start(Stage primaryStage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/guiPro/ServerGui.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setTitle("Server Connection");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading FXML file.");
        }
    }
    /**
     * Retrieves the port number entered by the user.
     * 
     * @return The port number as a String.
     */
    private String getPort() {
        return PortTxt.getText();
    }
    /**
     * Retrieves the database password entered by the user.
     * 
     * @return The database password as a String.
     */
    private String getDBPassword() {
        return DBPasswordTxt.getText();
    }
    /**
     * Handles the action event when the "Start Server" button is pressed.
     * Initiates the server and connects to the database.
     * 
     * @param event The action event triggered by the button press.
     * @throws Exception if an error occurs during server startup.
     */
    @FXML
    void serveStartAction(ActionEvent event) throws Exception {
        String port = getPort();
        String dbPassword = getDBPassword();

        if (port.trim().isEmpty() || dbPassword.trim().isEmpty()) {
            messageLabel.setText("Please fill in all fields.");
            messageLabel.setVisible(true);
            return;
        }

        if (runServer(port, dbPassword)) {
            System.out.println("Server connection succeeded");

            list.clear();

        } else {
            messageLabel.setText("Incorrect DB Password.");
            connection_Succ.setVisible(false);
            messageLabel.setVisible(true);
        }
    }
    /**
     * Starts the server with the specified port and database password.
     * 
     * @param port The port number to run the server on.
     * @param dbPassword The password for connecting to the database.
     * @return True if the server starts successfully, false otherwise.
     */
    public boolean runServer(String port, String dbPassword) {
        int portNumber = 0;

        try {
            portNumber = Integer.parseInt(port);
        } catch (NumberFormatException t) {
            System.out.println("ERROR - Could not connect!");
            return false;
        }

       server = new EchoServerPro(portNumber, this);

        
        if (!server.connectToDataBase(dbPassword)) {
            return false;
        }

        try {
            connection_Succ.setVisible(true);
            messageLabel.setVisible(false);
            server.listen();
            return true;
        } catch (Exception ex) {
            System.out.println("ERROR - Could not listen for clients!");
            return false;
        }
    }
    /**
     * Handles the action event when the "Close" button is pressed.
     * Exits the application.
     * 
     * @param event The action event triggered by the button press.
     */
    @FXML
    private void closeAction(ActionEvent event) {
        System.out.println("Closing application");
        System.exit(0);
    }
    
    /**
     * Adds a new client information entry to the table view.
     * 
     * @param ip The IP address of the connected client.
     * @param status The connection status of the client.
     */
    public void addClientInfo(String ip, String status) {
        list.add(new ClientInfo("localhost", ip, status)); // Example host as "localhost"
    }
    
    /**
     * Adds client information to the table view when a client connects.
     * 
     * @param ip The IP address of the connected client.
     * @param status The connection status of the client.
     */
    public void clientConnected(String ip, String status) 
    {
    	addClientInfo(ip,status);
    }
 
}
