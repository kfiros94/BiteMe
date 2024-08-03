package LogInAnimaLetters;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainLogIn extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
    	
    	LoginPageController a= new LoginPageController();
    	a.start(primaryStage);
    	
    	/*
        Parent root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
        primaryStage.setTitle("Login Page with Snowflakes");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    	*/
    }

    public static void main(String[] args) {
        launch(args);
    }
}
