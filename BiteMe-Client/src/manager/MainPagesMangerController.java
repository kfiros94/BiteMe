package manager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import client.ChatClient;
import client.ClientUI;
import entities.BiteOptions;
import entities.RestaurantOrders;
import entities.User;
import gui.LogInUserController;
import gui.MainPagesClientController;
import gui.OrderInProgressController;
import gui.StartOrderController;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
/**
 * Controller class for managing the main pages of the manager's interface.
 * Provides functionality for generating and displaying various reports
 * such as income, order, and performance reports, and allows navigation
 * to other parts of the application.
 * 
 * @author Kfir Amoyal
 * @author Noam Furman
 * @author Israel Ohayon
 * @author Eitan Zerbel
 * @author Yaniv Shatil
 * @author Omri Heit

 * @version August 2024
 */
public class MainPagesMangerController {

    @FXML
    private Label managerNameLabel;

    @FXML
    private Label branchNameLabel;

    @FXML
    private Button incomeReportsButton;


    @FXML
    private ComboBox<String> orderReportsButton; // Changed from Button to ComboBox
    

    @FXML
    private Button performanceReportsButton;

    @FXML
    private TextArea reportTextArea;

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private Label function;

    @FXML
    private Label domainFunction;

    @FXML
    private Button createNewCustomerButton;

    @FXML
    private Button backButton;
    
    
    private User UserClient; 
    
    

    /**
     * Initializes the controller.
     * Sets up the manager's name, branch name, and populates the order report ComboBox
     * with available restaurant names. Sets up event listeners for user actions.
     */
    @FXML
    private void initialize() 
    {
        managerNameLabel.setText(ChatClient.user1.getUsername());
        branchNameLabel.setText(ChatClient.user1.getBranch());

        // Populate the ComboBox with unique restaurant names
        populateRestaurantComboBox();

        // Set listener for selection changes
        orderReportsButton.setOnAction(event -> handleOrderReportsSelection());
    }
    /**
     * Populates the ComboBox with the names of restaurants available for generating reports.
     * This method ensures that only unique restaurant names are added to the ComboBox.
     */
    private void populateRestaurantComboBox() 
    {
    	
        BiteOptions option = new BiteOptions(ChatClient.user1.getBranch().toString(), BiteOptions.Option.FETCH_ORDER_REPORTS);
        ClientUI.chat.accept(option);
    	
    	
        HashMap<String, Boolean> restaurantMap = new HashMap<>();

        for (RestaurantOrders order : ChatClient.Restaurantall_Type_Food_orders) 
        {
            String restaurantName = order.getRestaurant();
            if (!restaurantMap.containsKey(restaurantName)) {
                restaurantMap.put(restaurantName, true);
                orderReportsButton.getItems().add(restaurantName);
            }
        }
        orderReportsButton.setValue("Select Restaurant");
    }
    
    /**
     * Handles the selection of a restaurant from the ComboBox.
     * Generates and displays a report for the selected restaurant.
     */
    @FXML
    private void handleOrderReportsSelection() 
    {
    
        BiteOptions option = new BiteOptions(ChatClient.user1.getBranch().toString(), BiteOptions.Option.FETCH_ORDER_REPORTS);
        ClientUI.chat.accept(option);
    	
        String selectedRestaurant = orderReportsButton.getValue();
        
        if (selectedRestaurant != null && !selectedRestaurant.equals("Select Restaurant")) {
            // Filter and display food items for the selected restaurant
            generateReportForSelectedRestaurant(selectedRestaurant);
        } else {
            reportTextArea.setText("Please select a restaurant.");
        }
    }

    /**
     * Generates a report for the selected restaurant.
     * The report includes the count of each food item ordered from the restaurant.
     * 
     * @param restaurant The name of the selected restaurant.
     */
    private void generateReportForSelectedRestaurant(String restaurant) {
        // Initialize a HashMap to store the count of each food item
        HashMap<String, Integer> foodCounts = new HashMap<>();

        // Iterate through the list of RestaurantOrders and filter by the selected restaurant
        for (RestaurantOrders order : ChatClient.Restaurantall_Type_Food_orders) {
            if (order.getRestaurant().equals(restaurant)) {
                String orderListJson = order.getOrder_list(); 

                // Parse the order_list JSON to get the food items
                JSONArray orderList = new JSONArray(orderListJson);
                for (int i = 0; i < orderList.length(); i++) {
                    JSONObject foodItem = orderList.getJSONObject(i);
                    String itemName = foodItem.getString("item");

                    // Update the count of this item
                    foodCounts.merge(itemName, 1, Integer::sum);
                }
            }
        }

        // Clear previous data from the bar chart
        barChart.getData().clear();

        // Create a new series for the bar chart
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(restaurant);

        // Populate the series with data
        for (Map.Entry<String, Integer> entry : foodCounts.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        // Add the series to the bar chart
        barChart.getData().add(series);

        // Optionally, display the report in the reportTextArea
        StringBuilder report = new StringBuilder();
        report.append("Restaurant: ").append(restaurant).append("\n");
        for (Map.Entry<String, Integer> entry : foodCounts.entrySet()) {
            report.append("    Item: ").append(entry.getKey())
                  .append(", Count: ").append(entry.getValue())
                  .append("\n");
        }
        reportTextArea.setText(report.toString());
    }

    

    
    /**
     * Handles the click event for the Income Reports button.
     * Generates and displays an income report for each restaurant.
     * 
     * @param event The ActionEvent triggered by the button click.
     */
    @FXML
    private void onIncomeReportsButtonClick(ActionEvent event) 
    {
        BiteOptions option = new BiteOptions(ChatClient.user1.getBranch().toString(), BiteOptions.Option.FETCH_INCOME_REPORTS);
        ClientUI.chat.accept(option);

        if (ChatClient.Restaurantall_orders != null && !ChatClient.Restaurantall_orders.isEmpty()) {
            ArrayList<RestaurantOrders> uniqueRestaurantOrders = new ArrayList<>();
            HashMap<String, Double> restaurantPriceMap = new HashMap<>();

            for (RestaurantOrders order : ChatClient.Restaurantall_orders) {
                String restaurantName = order.getRestaurant();
                double totalPrice = order.getTotal_price();

                if (restaurantPriceMap.containsKey(restaurantName)) {
                    restaurantPriceMap.put(restaurantName, restaurantPriceMap.get(restaurantName) + totalPrice);
                } else {
                    restaurantPriceMap.put(restaurantName, totalPrice);
                }
            }

            for (Map.Entry<String, Double> entry : restaurantPriceMap.entrySet()) {
                RestaurantOrders newOrder = new RestaurantOrders();
                newOrder.setRestaurant(entry.getKey());
                newOrder.setTotal_price(entry.getValue());
                uniqueRestaurantOrders.add(newOrder);
            }

            // Clear previous data from the bar chart
            barChart.getData().clear();
            
            // Create a series to add data to the bar chart
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Income by Restaurant");

            // Populate the series with data
            for (RestaurantOrders order : uniqueRestaurantOrders) 
            {
                series.getData().add(new XYChart.Data<>(order.getRestaurant(), order.getTotal_price()));
            }

            // Add the series to the bar chart
            barChart.getData().add(series);

            // Rotate the labels on the x-axis to avoid overlap
            CategoryAxis xAxis = (CategoryAxis) barChart.getXAxis();
            xAxis.setTickLabelRotation(45); // Rotate labels by 45 degrees

            // Set a larger gap between bars to provide more space for labels
            barChart.setCategoryGap(20);

            // Optionally, set the chart title and axis labels
            barChart.setTitle("Income by Restaurant");
            xAxis.setLabel("Restaurant");
            barChart.getYAxis().setLabel("Total Income");

            // Display the unique restaurant orders in the reportTextArea
            StringBuilder report = new StringBuilder();
            for (RestaurantOrders order : uniqueRestaurantOrders) {
                report.append("Restaurant: ").append(order.getRestaurant())
                      .append(", Total Income: ").append(String.format("%.2f", order.getTotal_price()))
                      .append("\n");
            }

            reportTextArea.setText(report.toString());
        } else {
            reportTextArea.setText("No orders available.");
        }
    }

    
    /**
     * Handles the click event for the Performance Reports button.
     * Generates and displays a performance report showing late deliveries by restaurant.
     * 
     * @param event The ActionEvent triggered by the button click.
     */
    @FXML
    private void onPerformanceReportsButtonClick(ActionEvent event) {
    	
    	 BiteOptions option = new BiteOptions(ChatClient.user1.getBranch().toString(), BiteOptions.Option.FETCH_PERFORMENCE_REPORTS);
         ClientUI.chat.accept(option);
        // Check if the data has been fetched
         
         
        if (ChatClient.Restaurantall_orders_per == null || ChatClient.Restaurantall_orders_per.isEmpty()) {
            reportTextArea.setText("No performance data available.");
            return;
        }
        
         
         
        // Clear previous data from the bar chart and text area
        barChart.getData().clear();
        reportTextArea.clear();

        ArrayList<RestaurantOrders> lateDeliveries = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Iterate through all orders to find late deliveries
        for (RestaurantOrders order : ChatClient.Restaurantall_orders_per) {
            try {
                LocalDateTime requestedDate = LocalDateTime.parse(order.getPlacing_order_date(), formatter);
                LocalDateTime receivedDate = LocalDateTime.parse(order.getOrder_received(), formatter);

                long hoursDifference = Duration.between(requestedDate, receivedDate).toHours();

                if (hoursDifference >= 1) {
                    lateDeliveries.add(order);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (!lateDeliveries.isEmpty()) {
            // Create a series to add data to the bar chart
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Late Deliveries by Restaurant");

            Map<String, Integer> lateDeliveryCount = new HashMap<>();

            for (RestaurantOrders order : lateDeliveries) {
                lateDeliveryCount.put(order.getRestaurant(),
                        lateDeliveryCount.getOrDefault(order.getRestaurant(), 0) + 1);
            }

            StringBuilder reportText = new StringBuilder();
            for (Map.Entry<String, Integer> entry : lateDeliveryCount.entrySet()) {
                series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
                // Append the late delivery count to the report text
                reportText.append("Restaurant: ").append(entry.getKey())
                          .append(", Late Deliveries: ").append(entry.getValue()).append("\n");
            }

            // Add the series to the bar chart
            barChart.getData().add(series);
            barChart.setTitle("Late Deliveries by Restaurant");
            CategoryAxis xAxis = (CategoryAxis) barChart.getXAxis();
            xAxis.setLabel("Restaurant");
            barChart.getYAxis().setLabel("Number of Late Deliveries");

            // Display the late deliveries in the text area
            reportTextArea.setText(reportText.toString());
        } else {
            reportTextArea.setText("No late deliveries to display.");
        }
    }
    
    
    

    

    /**
     * Handles the event when the "Create New User" button is clicked.
     * Navigates to the "Create New User" page and passes the current user data to the controller.
     * 
     * @param event The ActionEvent triggered by the button click.
     */
    @FXML
    private void CreateNewCustomer(ActionEvent event) 
    {
      
        FXMLLoader loader = new FXMLLoader();
        Pane root = null;
        Stage primaryStage = new Stage();
        try {
            // Load the FXML file
            loader.setLocation(getClass().getResource("/manager/CreateNewCustomer.fxml"));

            root = loader.load();
            
            // Hide the current window
            ((Node) event.getSource()).getScene().getWindow().hide();

            // Set the new stage
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
            primaryStage.setTitle("Admin-Portal -> Create New Customer");
            
    		System.out.println("JJJJJJJJJJJJ"+ UserClient);
    		System.out.println("AJJJJJJJJJJJJ"+ ChatClient.user1);



    		CreateNewCustomerController createNewCustomerController = loader.getController();
            

            
            if (ChatClient.user1 != null) 
            {
            	createNewCustomerController.loadUserClient(ChatClient.user1);
            } 
            else 
            {
                System.err.println("Error: UserClient is null in MainPagesClientController");
                // You might want to show an error message to the user here
            }
            

        } 
        catch (IOException e) 
        {
            e.printStackTrace();
            showAlert("Error", "Could not load the Start Order page.");
        }
    		
    }


    /**
     * Handles the event when the "Back" button is clicked.
     * Logs the user out and navigates back to the login page.
     * 
     * @param event The ActionEvent triggered by the button click.
     */
    @FXML
    private void handleBack(ActionEvent event) 
    {

    	
    	//aaaaaaaaa
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
    	
    	//aaaaaaaa

    }
    
    /**
     * Shows an alert dialog with the specified title and message.
     * 
     * @param title The title of the alert dialog.
     * @param message The message to display in the alert dialog.
     */  
    private void showAlert(String title, String message) 
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    
    /**
     * Loads the user data into this controller.
     * 
     * @param UserClient The user data to load.
     */
    public void loadUserClient(User UserClient) 
    {
        this.UserClient = UserClient;
		System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"+ this.UserClient.toString());

    }
    
    
    
    
    
}
