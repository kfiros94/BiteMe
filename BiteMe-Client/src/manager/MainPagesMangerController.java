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

public class MainPagesMangerController {

    @FXML
    private Label managerNameLabel;

    @FXML
    private Label branchNameLabel;

    @FXML
    private Button incomeReportsButton;

   /*
    @FXML
    private Button orderReportsButton;
   */


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
    
    
    
    /*
    // Initialize the controller
    @FXML
    private void initialize() 
    {
        // This method is automatically called after the FXML file has been loaded.
        // You can perform any setup actions here.
        managerNameLabel.setText(ChatClient.user1.getUsername());  // Example: Set manager's name
        branchNameLabel.setText(ChatClient.user1.getBranch());  // Example: Set branch name
        

        // Initialize ComboBox with report options
        orderReportsButton.getItems().addAll("Daily Report", "Weekly Report", "Monthly Report");

        // Optionally set a default value
        orderReportsButton.setValue("Select Report Type");
        
        // Set listener for selection changes
        orderReportsButton.setOnAction(event -> handleOrderReportsSelection());
        
        

    }

    
    //sssssssssssssssssss
    
    @FXML
    private void handleOrderReportsSelection() 
    {
        BiteOptions option = new BiteOptions(ChatClient.user1.getBranch().toString(), BiteOptions.Option.FETCH_ORDER_REPORTS);
        ClientUI.chat.accept(option);
        
        //ChatClient.Restaurantall_Type_Food_orders
    	

        String selectedReport = orderReportsButton.getValue();
        // Handle the selected report type here
        reportTextArea.setText("Selected report: " + selectedReport);

        // Add your logic to generate and display the report based on the selectedReport value
        generateReport(selectedReport);
        
    }

    
    
    
    private void generateReport(String reportType) {
        // Initialize a HashMap to store the count of each food item by restaurant
        HashMap<String, HashMap<String, Integer>> restaurantFoodCounts = new HashMap<>();

        // Iterate through the list of RestaurantOrders
        for (RestaurantOrders order : ChatClient.Restaurantall_Type_Food_orders) {
            String restaurant = order.getRestaurant();
            String orderListJson = order.getOrder_list(); // Assuming order_list is stored as a JSON string

            // Parse the order_list JSON to get the food items
            JSONArray orderList = new JSONArray(orderListJson);
            for (int i = 0; i < orderList.length(); i++) {
                JSONObject foodItem = orderList.getJSONObject(i);
                String itemName = foodItem.getString("item");

                // Update the count of this item in the restaurant's map
                restaurantFoodCounts
                    .computeIfAbsent(restaurant, k -> new HashMap<>())
                    .merge(itemName, 1, Integer::sum);
            }
        }

        // Clear previous data from the bar chart
        barChart.getData().clear();

        // Create a new series for the bar chart
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(reportType);

        // Populate the series with data
        for (String restaurant : restaurantFoodCounts.keySet()) {
            HashMap<String, Integer> foodCounts = restaurantFoodCounts.get(restaurant);
            for (String item : foodCounts.keySet()) {
                int count = foodCounts.get(item);
                series.getData().add(new XYChart.Data<>(restaurant + " - " + item, count));
            }
        }

        // Add the series to the bar chart
        barChart.getData().add(series);

        // Optionally, display the report in the reportTextArea
        StringBuilder report = new StringBuilder();
        for (String restaurant : restaurantFoodCounts.keySet()) {
            report.append("Restaurant: ").append(restaurant).append("\n");
            HashMap<String, Integer> foodCounts = restaurantFoodCounts.get(restaurant);
            for (String item : foodCounts.keySet()) {
                report.append("    Item: ").append(item)
                      .append(", Count: ").append(foodCounts.get(item))
                      .append("\n");
            }
        }
        reportTextArea.setText(report.toString());
    }
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

        // Optionally set a default value
        orderReportsButton.setValue("Select Restaurant");
    }

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

    private void generateReportForSelectedRestaurant(String restaurant) {
        // Initialize a HashMap to store the count of each food item
        HashMap<String, Integer> foodCounts = new HashMap<>();

        // Iterate through the list of RestaurantOrders and filter by the selected restaurant
        for (RestaurantOrders order : ChatClient.Restaurantall_Type_Food_orders) {
            if (order.getRestaurant().equals(restaurant)) {
                String orderListJson = order.getOrder_list(); // Assuming order_list is stored as a JSON string

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

    
    
    
    
    
    //sssssssssssssssssssssssssss
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
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

    
    
    
    
    
    
    
    
    
    /*
    
    // Event handler for the Order Reports button
    @FXML
    private void onOrderReportsButtonClick(ActionEvent event) 
    {
        // Add your logic for handling order reports here
        reportTextArea.setText("Displaying Order Reports...");
    }
*/
    
    
    
    /*
    // Event handler for the Performance Reports button
    @FXML
    private void onPerformanceReportsButtonClick(ActionEvent event) 
    {
        // Add your logic for handling performance reports here
        reportTextArea.setText("Displaying Performance Reports...");
    }
    */
    
    
    //wwwwwwwwwwwwwwww
    
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
    
    
    
    //wwwwwwwwwwwwww
    
    
    
    

    // Event handler for the Create New Customer button
    @FXML
    private void CreateNewCustomer(ActionEvent event) 
    {
        // Add your logic for creating a new customer here
    	
    	//llllllllllllll
    	
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
            
            /*
            if (UserClient != null) 
            {
                startOrderController.loadUserCustomer(UserClient);

            } 
            else 
            {
                System.err.println("Error: UserClient is null in MainPagesClientController");
                // You might want to show an error message to the user here
            }
            */
            
            
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
    	//llllllllllllllllllllll
    	
    	
    	
    }

    // Event handler for the Back button
    @FXML
    private void handleBack(ActionEvent event) 
    {
    	/*
        // Close the current window
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
        */
    	
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
    
    
    private void showAlert(String title, String message) 
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    
    
    public void loadUserClient(User UserClient) 
    {
        this.UserClient = UserClient;
		System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"+ this.UserClient.toString());

    }
    
    
    
    
    
}
