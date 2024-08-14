package gui;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientController;
import client.ClientUI;
import common.ChatIF;
import entities.BiteOptions;
import entities.RestaurantOrders;
import entities.User;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label; 
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import supplier.MainPageSupplierController;
import manager.MainPagesMangerController;

import java.net.URL;
import java.util.ResourceBundle;

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
/**
 * Controller class for handling user login functionality in the BiteMe application.
 * This class manages the login process, user authentication, and navigation to appropriate
 * user interfaces based on user roles.
 *
 * <p>The controller interacts with the GUI elements defined in the LogInUser.fxml file
 * and communicates with the server through the ClientUI class for user authentication.</p>
 *
 * @author Kfir Amoyal
 * @author Israel Ohayon
 * @author Yaniv Shatil
 * @author Noam Furman
 * @author Omri Heit
 * @author Eitan Zerbel
 * @version August 2024
 */
public class LogInUserController {

	public static final String RESET = "\033[0m"; // Text Reset
	public static final String GREEN = "\033[0;32m"; // GREEN
	public static final String RED = "\033[0;31m"; // RED
	public static final String BLUE = "\033[0;34m"; // BLUE

	private MainPagesClientController sfc;

	@FXML
	private Label lbOrder_number;
	@FXML
	private Label messageLabel;

	@FXML
	private Button btnExit = null;
	@FXML
	private Button btnSend = null;

	@FXML
	private TextField user_nametxt;
	@FXML
	private TextField passField;
	@FXML
	private TextField HostIP;

	/**
	 * Retrieves the text from the user name text field.
	 *
	 * @return the text from the user name text field.
	 */
	private String getuser_nametxt() {
		return user_nametxt.getText();
	}

	/**
	 * Retrieves the text from the password field.
	 *
	 * @return the text from the password field.
	 */
	private String getuser_passwordtxt() {
		return passField.getText();
	}

	@FXML
	private Pane snowflakePane;

	/*
	 * @FXML private Button logIn;
	 */

	private static final int WIDTH = 800;
	private static final int HEIGHT = 500;
	private static final int NUM_WORDS = 1;
	private static final String[] WORDS = { "🍔", "🍟", "🍕", "🌭", "🍜", "🍙" };

    /**
     * Initializes the login screen with animated food emojis falling in the background.
     * This method is automatically called after the fxml file has been loaded.
     */
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
				tt.setToY(HEIGHT + 50);
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
		Color[] colors = { Color.GREY, Color.PURPLE, Color.BLUE, Color.LIGHTGRAY, Color.CRIMSON, Color.DARKSALMON,
				Color.DEEPPINK, Color.GOLD, Color.KHAKI };
		int randomIndex = new Random().nextInt(colors.length);
		return colors[randomIndex];
	}

	/**
	 * Handles the login process for the user. Validates the input fields and
	 * communicates with the server to verify the user's credentials. Depending on
	 * the user's role, it opens the appropriate window.
	 *
	 * @param event The action event triggered by the user interaction.
	 * @throws Exception if an error occurs during the FXML loading process.
	 */
	@SuppressWarnings("null")
	public void Send(ActionEvent event) throws Exception {
		ClientUI.chat.setMyhost(HostIP.getText());

		String username = getuser_nametxt();
		String userpassword = getuser_passwordtxt();
		FXMLLoader loader = new FXMLLoader();
		Pane root = null;

		System.out.println("test1: in" + GREEN + " Class SummaryOfExistingOrderController" + RESET
				+ "The customer enters login details " + RESET + RED);

		if ((userpassword.trim().isEmpty()) || (username.trim().isEmpty())) {
			System.out.println("You must enter username and password");
			messageLabel.setText("You must enter username and password");
			messageLabel.setVisible(true);
		} 
		else 
		{

		    User user = new User(0,username,userpassword,null,null,null,null,false,0,null);
			BiteOptions option = new BiteOptions(user.toString(), BiteOptions.Option.LOGIN);

			System.out.println("test1: in" + GREEN + " Class SummaryOfExistingOrderController" + RESET
					+ " login details we send TO " + RESET + BLUE + "func --accept--" + RESET);

			ClientUI.chat.accept(option);
			
			
			System.out.println("test1: in" + GREEN + " Class SummaryOfExistingOrderController" + RESET
					+ " There is an active standby at " + RESET + BLUE + "func --accept--" + RESET
					+ "until a message returns from the server (return message)");

			if (ChatClient.user1.getUsername() == null || ChatClient.user1.getUsername().equals("-1")) {
				System.out.println("User Name Not Found");
				messageLabel.setText("User Name Not Found");
				messageLabel.setVisible(true);

			} else if (ChatClient.user1.getPassword() == null || ChatClient.user1.getPassword().equals("-2")) {
				System.out.println("Incorrect password");
				messageLabel.setText("Incorrect password");

			} else if (ChatClient.user1.getLoggedIn() == 1) {
				System.out.println("User is already logged in");
				messageLabel.setText("User is already logged in");
				messageLabel.setVisible(true);
			} else {
				System.out.println("User exists in the system");
				System.out.println("test1: in" + GREEN + " Class SummaryOfExistingOrderController" + RESET
						+ " The server returned that User exists in the system, we will hide this window " + RESET + RED
						+ "Summary Of Existing Order" + RESET);
				((Node) event.getSource()).getScene().getWindow().hide();

				System.out.println("test1: in" + GREEN + " Class SummaryOfExistingOrderController" + RESET
						+ " Open the new window: " + RESET + RED + "Order_number Management Tool" + RESET);
				Stage primaryStage = new Stage();

				// Determine the user's role and load the appropriate FXML
				try {
					String userType = ChatClient.user1.getPermission();
					switch (userType) {
					case "supplier":
						System.out.println("User type: Supplier");
						root = loader.load(getClass().getResource("/supplier/MainPageSupplier.fxml").openStream());
						MainPageSupplierController MainPageSupplierController = loader.getController();
						MainPageSupplierController.setUserSupplier(ChatClient.user1);
						MainPageSupplierController.setRestaurant(null);
						break;
					case "customer":
						System.out.println("User type: Customer");
						root = loader.load(getClass().getResource("/gui/MainPagesClient.fxml").openStream());
						MainPagesClientController MainPagesClientController = loader.getController();
						//MainPagesClientController.loadOrder(ChatClient.s1);// פונקציית עזר אחרי שאנחנו יוצרים מופע של קונטרולר,אנחנו מעדכנים נתונים לשדה של הזמנה,וככה נעשה כל פעם רק בצורה אחרת
						MainPagesClientController.loadUserClient(ChatClient.user1);// בצאט-קליינט כשחוזרת הודעה מהשרת אז נגדיר שדות סטטיק שיהיה אפשר לטעון אותם לכאן
						MainPagesClientController.initialize(ChatClient.user1.getUsername(), ChatClient.user1.getaccountStatus(), ChatClient.user1.getBranch());
						primaryStage.setTitle("User-Portal");

						break;
					case "admin":
						System.out.println("User type: Admin");
						root = loader.load(getClass().getResource("/manager/MainPagesManger.fxml").openStream());
						MainPagesMangerController MainPagesMangerController  = loader.getController();
						MainPagesMangerController.loadUserClient(ChatClient.user1);
						
					default:
						break;
					}
					if(root!=null) {
						
						Scene scene = new Scene(root);
						//primaryStage.setTitle("Page Home");

						primaryStage.setScene(scene);
						primaryStage.show();
					}
				} catch (IOException e) {
					e.printStackTrace();
					messageLabel.setText("Error loading the main page");
					messageLabel.setVisible(true);
				}
			}
		}
	}


    /**
     * Starts the login screen by loading the LogInUser.fxml file and displaying it.
     *
     * @param primaryStage The primary stage for this application, onto which
     *                     the application scene can be set.
     * @throws Exception if an error occurs during the FXML loading process.
     */
	public void start(Stage primaryStage) throws Exception {

		Parent root = FXMLLoader.load(getClass().getResource("/gui/LogInUser.fxml"));
																						
		Scene scene = new Scene(root);
		primaryStage.setTitle("Log-In BiteMe");
		primaryStage.setScene(scene);
		primaryStage.show();

	}
    /**
     * Handles the exit button click event by closing the application.
     *
     * @param event The action event triggered by the user interaction.
     * @throws Exception if an error occurs during the exit process.
     */
	public void getExitBtn(ActionEvent event) throws Exception {
		System.out.println("exit Log-In BiteMe");
		System.exit(0);// add for the method to actually close client window
	}
    /**
     * Loads an order into the main page controller.
     * This method is likely used for passing order information between controllers.
     *
     * @param s1 The RestaurantOrders object to be loaded.
     */
	public void loadOrder(RestaurantOrders s1) {
		this.sfc.loadOrder(s1);
	}
    /**
     * Displays a message, typically used for debugging or user feedback.
     *
     * @param message The message to be displayed.
     */
	public void display(String message) {
		System.out.println("message");

	}

}
