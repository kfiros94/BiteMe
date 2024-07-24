package LogInAnimaLetters;



import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;



public class LoginPageController {

    @FXML
    private Pane snowflakePane;

    /*
    @FXML
    private Button logIn;
    */
    
    
    private static final int WIDTH = 800;
    private static final int HEIGHT = 500;
    private static final int NUM_WORDS = 1;

    private static final String[] WORDS = {"🍔","🍟","🍕","🌭","🍜","🍙"};

    @FXML
    public void initialize() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> {
            for (int i = 0; i < NUM_WORDS; i++) {
                Text word = new Text(getRandomWord());
                word.setFont(Font.font(25));
                word.setFill(getRandomColor());
                word.setX(new Random().nextInt(WIDTH));
                word.setY(0);
                snowflakePane.getChildren().add(word);

                TranslateTransition tt = new TranslateTransition(Duration.seconds(8 + new Random().nextInt(10)), word);
                tt.setToY(HEIGHT+50);
                tt.setOnFinished(e -> snowflakePane.getChildren().remove(word));
                tt.play();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private String getRandomWord() {
        return WORDS[new Random().nextInt(WORDS.length)];
    }

    private Color getRandomColor() {
        Color[] colors = {Color.GREY, Color.PURPLE, Color.BLUE, Color.LIGHTGRAY, Color.CRIMSON, Color.	DARKSALMON, Color.DEEPPINK, Color.GOLD, Color.	KHAKI};
        int randomIndex = new Random().nextInt(colors.length);
        return colors[randomIndex];
    }
    
    
    
    //bbbbbbbbbbbbbbb
    
    
    @FXML
	public void LogIn(ActionEvent event) throws Exception 
	{
		
		FXMLLoader loader = new FXMLLoader();
		
		
		System.out.println("User Found");
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		
		Stage primaryStage = new Stage();
		Pane root = loader.load(getClass().getResource("/Screens/Main.fxml").openStream());

		Scene scene = new Scene(root);			
		//scene.getStylesheets().add(getClass().getResource("/gui/OrderForm.css").toExternalForm());
		primaryStage.setTitle("Order_number Managment Tool");

		primaryStage.setScene(scene);		
		primaryStage.show();
			
	}
    
    //bbbbbbbbbbbbbbb
    
    
    //ccccccccccc
    
    
	public void start(Stage primaryStage) throws Exception {	
		Parent root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
				
		Scene scene = new Scene(root);
		//scene.getStylesheets().add(getClass().getResource("/gui/SummaryOfExistingOrder.css").toExternalForm());
		primaryStage.setTitle("Login Page with Snowflakes");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
    
    //cccccccccccc
    
    
    
    
    
}