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

    private String getPort() {
        return PortTxt.getText();
    }

    private String getDBPassword() {
        return DBPasswordTxt.getText();
    }

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
//            addClientInfo("192.168.0.3", "Connected1"); // Example usage
//            addClientInfo("192.168.0.4", "Connected2"); // Example usage
//            addClientInfo("192.168.0.5", "Connected3"); // Example usage
            list.clear();
           // sv.client_info.get(0).getIp();
          //  sv.client_info.get(0).getHost();
          //  sv.client_info.get(0).getStatus();

            
            
            
        } else {
            messageLabel.setText("Incorrect DB Password.");
            connection_Succ.setVisible(false);
            messageLabel.setVisible(true);
        }
    }

    public boolean runServer(String port, String dbPassword) {
        int portNumber = 0;

        try {
            portNumber = Integer.parseInt(port);
        } catch (NumberFormatException t) {
            System.out.println("ERROR - Could not connect!");
            return false;
        }

       server = new EchoServerPro(portNumber, this);
      //   sv = new EchoServerPro(portNumber, this);

        
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

    @FXML
    private void closeAction(ActionEvent event) {
        System.out.println("Closing application");
        System.exit(0);
    }

    public void addClientInfo(String ip, String status) {
        list.add(new ClientInfo("localhost", ip, status)); // Example host as "localhost"
    }
    
    
    public void clientConnected(String ip, String status) 
    {
    	addClientInfo(ip,status);
    }
    
    
}
