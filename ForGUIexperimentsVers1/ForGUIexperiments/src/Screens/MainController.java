package Screens;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;




public class MainController 
{

    @FXML
    private StackPane contentArea;

    @FXML
    public void initialize() 
    {
        showView1();  // Show the default view on startup
    }

    @FXML
    private void showView1() 
    {
        loadView("View1.fxml");
    }

    @FXML
    private void showView2() 
    {
        loadView("View2.fxml");
    }
    
    @FXML
    private void showView3() 
    {
        loadView("View3.fxml");
    }

    private void loadView(String fxmlFile) 
    {
        try 
        {
            Node view = FXMLLoader.load(getClass().getResource(fxmlFile));
            contentArea.getChildren().setAll(view);
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
    
    
    @FXML
    private void LogOut(ActionEvent event) 
    {
        try {
    		System.out.println("Test1:exit Summary of an BiteMe");	//TTTTTTTTTTTTTTTTTTTTT

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LogInAnimaLetters/LoginPage.fxml"));
    		System.out.println("Test2:exit Summary of an BiteMe");	//TTTTTTTTTTTTTTTTTTTTT

            Parent root = loader.load();
    		System.out.println("Test3:exit Summary of an BiteMe");	//TTTTTTTTTTTTTTTTTTTTT

            Scene scene = new Scene(root);
    		System.out.println("Test4:exit Summary of an BiteMe");	//TTTTTTTTTTTTTTTTTTTTT

    		//scene.getStylesheets().add(getClass().getResource("/gui/SummaryOfExistingOrder.css").toExternalForm());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Login Page");

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    
}
