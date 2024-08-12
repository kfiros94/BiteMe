package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import entities.BiteOptions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import entities.ClientInfo;
import entities.RestaurantOrders;
import entities.User;


// implements Initializable 
public class MainPagesClientController 
{
	
	@FXML
	private Label lbUserName;
	@FXML
	private Label lbMyStatus;
	@FXML
	private Label lbMybranch;
	@FXML
	private ImageView imageView;
	
    private RestaurantOrders s;//זה שדה של הקונטרולר שדרך הדף לוג-אין יוצרים מופע של המחלקה ומעדכנים את השדה דרך מתודה שהגדרנו כאן
    private User UserClient;


/*
    ObservableList<String> list;
*/       
    
     
 	public void initialize(String userName, String accountStatus , String branch)
 	{
 		
 		lbUserName.setText(userName);
 		lbMyStatus.setText(accountStatus);
 		lbMybranch.setText(branch);
 		
 		
 	}
 	

    public void loadOrder(RestaurantOrders s1)
    {
        this.s = s1;
      
    }
    
    
    public void loadUserClient(User UserClient) 
    {
        this.UserClient = UserClient;
		System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"+ this.UserClient.toString());

    }

    

    @FXML
    private void StartOrder(ActionEvent event) 
    {
        FXMLLoader loader = new FXMLLoader();
        Pane root = null;
        Stage primaryStage = new Stage();
        try {
            // Load the FXML file
            loader.setLocation(getClass().getResource("/gui/StartOrder.fxml"));

            root = loader.load();
            
            // Hide the current window
            ((Node) event.getSource()).getScene().getWindow().hide();

            // Set the new stage
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
            primaryStage.setTitle("User-Portal -> New Order");

            StartOrderController startOrderController = loader.getController();
            if (UserClient != null) {
                startOrderController.loadUserCustomer(UserClient);
            } else {
                System.err.println("Error: UserClient is null in MainPagesClientController");
                // You might want to show an error message to the user here
            }

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Could not load the Start Order page.");
        }
    }
    
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    
    @FXML
    private void OrderInProgress(ActionEvent event) 
    {
    	
    }
    
    @FXML
    private void ViewOrderHistory(ActionEvent event) 
    {
    	
    }
    
    
    @FXML
    private void CloseButton(ActionEvent event) throws IOException 
    {
    	
		BiteOptions option = new BiteOptions(ChatClient.user1.toString(), BiteOptions.Option.LOGOUT);//kkkkkkk
	    ClientUI.chat.accept(option);

        
        ChatClient.user1 = new User(0, null, null, null, null, null, null, false, 0,null);// מאפס את המשתמש בשביל הניסיון התחברות הבא בתור

        try 
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/LogInUser.fxml"));

            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Log-In BiteMe");

            stage.setScene(scene);
            stage.show();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
    
    
 
    
}
