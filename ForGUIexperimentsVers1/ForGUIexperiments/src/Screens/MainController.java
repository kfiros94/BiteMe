package Screens;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;
import javafx.scene.Node;

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
}
